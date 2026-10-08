# Android 4 device matrix

The assignment branch is `conversationWithMyFriend`, based on starter and fork main commit `e2fa22d55d65d8f6bbee562e337caea785515250`. Origin is `https://github.com/Xussko/Foroom.git`. The fork already matched the starter when checked on 8 October 2026; no remote synchronization update was necessary.

## Device runs

| AVD / model | Android | API | Resolution | Density | Type | Scenario 1 | Scenario 2 | Scenario 3 | Each test independently | Repeat with saved data |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| ForoomAndroid13 / Pixel 2 | 13 | 33 | 1080 × 1920 | 420 dpi | Emulator | Pass | Pass | Pass | Pass (3/3) | Pass (3/3, Android Studio) |
| ForoomAndroid14 / Pixel 4 | 14 | 34 | 1080 × 2280 | 440 dpi | Emulator | Pass | Pass | Pass | Pass (3/3) | Pass (3/3, Android Studio) |
| ForoomAndroid15 / Pixel Tablet | 15 | 35 | 2560 × 1600 | 320 dpi | Emulator | Pass | Pass | Pass | Pass (3/3) | Pass (3/3, Android Studio) |

No physical Android device was connected. All devices use the default Foroom Training debug variant, JDK 17 for Gradle, and the same APK files. APKs and build outputs are excluded from the assignment files. Wi-Fi and mobile data are disabled for the independent and repeat runs.

SHA-256 of the tested debug app: `1A0BF80FFA767146E318E78B68274A4E367728724A474F504E0274C0083E2FA5`.

Scenario names:

1. `sendDrinkInvitationInJohnWeekAndReadItAfterReopening`
2. `askAboutFavouriteAutomationAcademyModuleInMyOwnChat`
3. `readOlderGreetingAsAnotherAccountAndReplyInTheSharedChat`

## Independent preparation

Every scenario registers two fictional local accounts through the UI: `android4_a_<suffix>` and `android4_b_<suffix>`, with the test-only password defined in the test class. The eight-character UUID suffix is known to that test instance and also identifies its messages. Both accounts live on the same device. Setup signs out of any saved session, registers both accounts, then explicitly signs in as User A. New account names isolate scenarios from earlier password changes and avoid registration conflicts without clearing existing chats or messages.

Setup searches for and reuses these exact chat titles, creating a chat with a selected image only if it is absent:

- `johnWeek`
- `Nikoloz Khuskivadze Android4`
- `something Android4`

The full name comes from the previous assignment and can be overridden with the instrumentation argument `studentFullName`. If duplicate titles exist, setup and both users consistently select the first search result. Search waits for an exact matching title before deciding to create a chat.

All sent-message assertions match both the unique text and the sender within the same message row in the expected conversation. Scenario 1 reopens the chat before checking persistence. Scenario 3 sends 25 additional messages, asserts the greeting is outside the initial viewport, switches accounts on the same device, and uses the existing `swiper` helper to reveal it. Gesture coordinates come from the RecyclerView's visible bounds and content padding. History search stops after at most 40 swipes and fails if the expected greeting and sender cannot be found.

## Reproduce

Open Foroom in Android Studio, use Gradle JDK 17, choose the `app` configuration and `debug` variant, and run `ConversationWithMyFriendTests` on the selected AVD. Setup prepares each device automatically; do not clear app data between repeated runs.

From PowerShell, with only the intended test device connected:

```powershell
$env:JAVA_HOME = 'C:\Users\Alexsandre\tools\jdk17\jdk-17.0.20.1+1'
$env:ANDROID_HOME = 'C:\Users\Alexsandre\tools\android-sdk'
.\gradlew.bat :app:connectedDebugAndroidTest '-Pandroid.testInstrumentationRunnerArguments.class=com.example.foroom.tests.ConversationWithMyFriendTests'
```

Run a scenario independently by appending `#<scenarioName>` to the class argument, or use its gutter run action in Android Studio. Repeat the full class on the existing installation to check saved sessions and earlier messages.

Raw instrumentation results and Android Studio's JUnit reports are saved in [android4-logs](android4-logs/). The Android Studio full-class runs repeat the scenarios after the individual runs without clearing data. The screenshots show actual Android Studio runs, rather than imported or recreated reports.

| Configuration | Android Studio report | Screenshot |
| --- | --- | --- |
| Android 13 / API 33 | [3 passing tests](android4-logs/api33-repeat.xml) | [Android Studio results](../screenshots/android4/api33.png) |
| Android 14 / API 34 | [3 passing tests](android4-logs/api34-repeat.xml) | [Android Studio results](../screenshots/android4/api34.png) |
| Android 15 / API 35 | [3 passing tests](android4-logs/api35-repeat.xml) | [Android Studio results](../screenshots/android4/api35.png) |

All final matrix checks were performed on 8 October 2026. Each configuration has three passing independent test runs and a passing Android Studio class run on the existing local data, for 18 passing scenario executions across these checks. Android 13 and Android 15 also have earlier passing full-class results in the evidence directory.

## Development findings

The initial Android 14 attempt could not install the APK because the emulator's package service was not ready; no scenarios executed in that attempt. The subsequent original implementation passed on Android 14. Android 13 then exposed an asynchronous search race that created duplicate chat titles and caused an ambiguous matcher. The final implementation waits for the matching title and scopes chat selection to the first result. Final validation preserves that device's existing duplicate chats, messages, and sessions.
