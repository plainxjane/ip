# Jelly

Jelly is a Java task-management chatbot. This file explains how to set up,
run, and test the project. For instructions on using Jelly's commands, see the
[User Guide](docs/README.md).

## Prerequisites

- JDK 25
- IntelliJ IDEA (recommended) or a terminal
- macOS, Windows, or Linux

The project uses JavaFX for its graphical interface and Gradle for building.

## Set up in IntelliJ IDEA

1. Open IntelliJ IDEA and select **Open**.
2. Select the project folder and accept the default import settings.
3. Configure the project SDK to **JDK 25**. Set the project language level to
   **SDK default**.
4. Allow IntelliJ to finish importing the Gradle project and downloading its
   dependencies.

## Run Jelly

### From IntelliJ IDEA

Open `src/main/java/jelly/Main.java` and run `Main.main()` to start the JavaFX
application.

### From a terminal

Open a terminal in the project folder, meaning the folder containing `gradlew`.
Make sure JDK 25 is active, then start Jelly's JavaFX GUI with the Gradle
wrapper.

On macOS or Linux:

```bash
cd path/to/ip
./gradlew run
```

On Windows (PowerShell):

```powershell
cd path\to\ip
gradlew.bat run
```

The first run may take longer because Gradle downloads the required
dependencies. Close the GUI window to stop Jelly. Refer to the
[User Guide](docs/README.md) for instructions on using Jelly.

## Build and test

Build the project with:

```bash
./gradlew build
```

Run the automated tests with:

```bash
./gradlew test
```

The generated application JAR can be created with:

```bash
./gradlew shadowJar
```

## Project structure

```text
src/main/java/       Application source code
src/main/resources/  JavaFX views, styles, and images
src/test/java/       Automated tests
docs/                User Guide and UI image
data/jelly.txt       Saved tasks, created when Jelly runs
```

Keep Java source files under `src/main/java` so that Gradle and IntelliJ can
compile the project correctly.
