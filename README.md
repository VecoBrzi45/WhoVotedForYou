# WHO VOTED FOR YOU? — Android prototype (v0.1)

An English-language, dark purple Android prototype for a friend-group anonymous voting game.

## Included in this first prototype
- Dark purple visual identity and branded home screen
- Room-code screen and Android share-sheet invite text
- Sample poll rounds with multiple-choice voting
- Local demo vote and results-preview screens
- Private-group positioning; no public feed
- GitHub Actions workflow to build a debug APK in the cloud

## Important: what is and is not live
This is a UI/gameplay prototype. Votes are stored only in the current app session on one device. Room codes are demo text, not real network rooms; sharing sends an invite message but does not connect players. Real anonymous multi-device voting requires a backend, database, room lifecycle, abuse reporting/moderation, and privacy/terms screens. Do not publish this prototype as a finished online game.

## Build APK with GitHub Actions
1. Create a GitHub account if you do not have one.
2. Create a new repository and upload all files in this project.
3. Open the repository's **Actions** tab.
4. Choose **Build Android APK** and press **Run workflow**.
5. When the workflow finishes, download the artifact `WhoVotedForYou-debug-apk`.
6. Extract the downloaded ZIP and install `app-debug.apk` on Android. Allow installation from that source if Android asks.

For local development, open the project in Android Studio on a computer with JDK 17.
