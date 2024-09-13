# detekt-demo

Includes boilerplate configuration to work with [Detekt](https://detekt.dev/) in a Kotlin project.

## Usage

To run "basic" Detekt, execute the following command:

```shell
./gradlew detekt
```

To run Detekt with [Type Resolution](https://detekt.dev/docs/gettingstarted/type-resolution),
execute the following command:

```shell
./gradlew detektDebug detektDebugUnitTest detektDebugAndroidTest --continue
```

## Gradle + Rules Configuration

[build.gradle.kts](app/build.gradle.kts) includes a basic setup to run Detekt in a Kotlin project.
Some items of note include:

- Applying the Detekt plugin ("io.gitlab.arturbosch.detekt")
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
- [app/config/detekt/detekt.yml](app/config/detekt/detekt.yml) includes a basic configuration to run
  Detekt with some basic rule overrides - Detekt does not account for Compose
    * conventions by default.

## Github Actions workflows

1. [.github/workflows/detekt-without-type-resolution.yml](.github/workflows/detekt-without-type-resolution.yml)
   to run Detekt with SARIF reporting enabled
2. [.github/workflows/detekt-with-type-resolution.yml](.github/workflows/detekt-with-type-resolution.yml)
   to run Detekt with Type Resolution (no reporting enabled)
