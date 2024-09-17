# Detekt Android Boilerplate

Includes boilerplate configuration to work with [Detekt](https://detekt.dev/) in a Kotlin project.

## Usage

To run "basic" Detekt, run the following command from the root of this project:

```shell
./gradlew detekt
```

To run Detekt with [Type Resolution](https://detekt.dev/docs/gettingstarted/type-resolution), run
the following:

```shell
./gradlew detektDebug detektDebugUnitTest detektDebugAndroidTest --continue
```

Repository includes:

1. A [basic Gradle configuration](#gradle--rules-configuration) to run Detekt
2. Two [Github Actions workflows](#github-actions-workflows) to run Detekt - one with Type
   Resolution and one without
3. A [pre-push Git hook](#git-hooks-tooling) + tooling to install it in order to run Detekt before
   pushing a commit

## Gradle + Rules Configuration

[build.gradle.kts](app/build.gradle.kts) includes a basic setup to run Detekt in a Kotlin project.
Some items of note include:

- Applying the Detekt Gradle plugin
- Configuring the Detekt plugin in the `detekt { }` block:
    - `autoCorrect = true` to automatically fix some issues
    - `buildUponDefaultConfig = true` to use the default Detekt configuration
    - `config.setFrom()` to specify the path to the Detekt configuration file
    - `source.setFrom()` to specify the path to the source sets you want to run Detekt on
    - `basePath` to support
      optional [SARIF report generation](https://detekt.dev/docs/introduction/reporting#integration-with-github-code-scanning)
      in Github Actions
- Recommended Detekt plugins:
    - [detekt-formatting](https://detekt.dev/docs/rules/formatting) for formatting rules +
      auto-correcting some rule violations
    - [Forked version of Twitter's Compose rules](https://github.com/mrmans0n/compose-rules) for
      Compose-specific rules
- [detekt.yml](app/config/detekt/detekt.yml) includes a basic configuration to run
  Detekt with some basic rule overrides geared toward accommodating Compose + test suites
    - Note that Detekt does not account for Compose conventions by default.
      See [Detekt's recommended Compose overrides](https://detekt.dev/docs/introduction/compose) for
      a good starting place to apply your preferred rules strategy for Compose projects.

## Github Actions workflows

1. [Detekt without Type Resolution](.github/workflows/detekt-without-type-resolution.yml)
   to run Detekt
   with [SARIF reporting](https://docs.github.com/en/code-security/code-scanning/integrating-with-code-scanning/sarif-support-for-code-scanning)
   enabled
2. [Detekt with Type Resolution](.github/workflows/detekt-with-type-resolution.yml) to run Detekt
   with Type Resolution (no reporting enabled for now)

## Git hooks tooling

If you would like to run Detekt as a pre-push hook:

1. Decide if you want to use Type Resolution or not - if you do, **first
   update [hooks/pre-push.sh](hooks/pre-push.sh)** to run the appropriate Gradle task(s). Feel free
   to modify the script to suit your needs!
2. Run the following commands to set up the pre-push hook:

   OSX:

   ```shell
   ./gradlew installGitHooksMac
   ```

   Windows:

   ```shell
   ./gradlew installGitHooksWindows
   ```

After this is done, Detekt should run and prevent pushing a commit if there are any issues.
