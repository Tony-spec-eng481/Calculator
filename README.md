#+ Calculator — Android

A lightweight, polished Android calculator app with built-in base-conversion and a persistent calculation history. The project is implemented in Java, targets Android API 21+, and uses a tabbed UI with separate screens for calculation, history, and app information.

## Key features

- Arithmetic calculator with support for complex expressions and operator precedence
- Base conversion between common bases (binary, octal, decimal, hexadecimal)
- Perform arithmetic in one base and view results in another
- Persistent history stored locally (SQLite)
- Clean, accessible UI with separate number/letter keyboards for hexadecimal input

## Quick start

Requirements

- JDK 11+ (or the JDK version compatible with your Android toolchain)
- Android Studio (recommended) or command-line Gradle
- Android SDK and an emulator or device with USB debugging enabled

Build and run (PowerShell on Windows)

1. Open the project in Android Studio and click Run.
2. Or use the bundled Gradle wrapper from the repository root:

```powershell
.\gradlew.bat assembleDebug
.\gradlew.bat installDebug
```

Run unit tests and instrumentation tests

```powershell
.\gradlew.bat test               # Run local unit tests
.\gradlew.bat connectedAndroidTest  # Run instrumentation tests on a connected device/emulator
```

Create a release build

The repository includes a keystore file named `calculator.jks` (if present) and `gradle.properties` may contain signing configs. Adjust the signing config in `app/build.gradle` or add your own keystore and credentials to `gradle.properties`.

```powershell
.\gradlew.bat assembleRelease
```

## Project structure (important files)

- `app/src/main/java/com/example/calculator/` — Java sources
	- `MainActivity.java` — hosts the ViewPager / TabLayout
	- `CalculatorFragment.java` — UI + calculation logic
	- `HistoryFragment.java`, `HistoryAdapter.java` — history screen and adapter
	- `DatabaseHelper.java`, `CalculationHistory.java` — persistence layer
	- `InfoFragment.java`, `ViewPagerAdapter.java` — app info and tab wiring
- `app/src/main/res/` — layouts, drawables, themes, and other resources
- `app/release/` — release metadata and baseline profiles
- `gradle/` — version catalog and wrapper config

## How it works (high level)

- Input is entered via a custom keyboard that adapts to the selected base (numeric vs letter keys)
- Expressions are parsed and evaluated with operator precedence (calculator logic contained in `CalculatorFragment`)
- Results can be converted between bases; conversions use decimal as an intermediate when necessary
- History is stored in a local SQLite database via `DatabaseHelper` and shown with a RecyclerView

## Testing and quality

- Unit tests live under `app/src/test/java` and can be executed with the Gradle `test` task.
- Instrumentation tests (Android tests) live under `app/src/androidTest/java` and can be run with `connectedAndroidTest`.

## Troubleshooting

- If the app fails to install, ensure developer mode and USB debugging are enabled on your device.
- If a conversion reports invalid input, check that the characters match the selected base (e.g., only 0/1 for binary).
- For database issues, clear app data or uninstall/reinstall the app to reset the local database.

## Contribution

Contributions are welcome. A suggested workflow:

1. Fork the repository
2. Create a feature branch (e.g., `feature/convert-improvement`)
3. Commit changes with clear messages
4. Open a pull request describing the problem and your solution

Please include unit tests for new logic where applicable.

## License

No license file is included in this repository. If you plan to make the project public, add a `LICENSE` file (for example, MIT or Apache 2.0) to clarify usage and redistribution terms.

## Contact

If you have questions, bugs, or feature requests, open an issue in this repository or contact the maintainer listed in the project metadata.

---

_README generated/updated to provide clearer developer and contributor guidance._
