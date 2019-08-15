# File Access Examples

Examples of accessing resource files in Java and Kotlin

## Differences between Build Tools

Each build tool has its own way of structuring the compiled code. We start with this 
`src` structure and compare the output. 
```text
src/
  main/
    java/ or kotlin/
      ...
    resources/
      test.txt
```

### Structure of Executable JAR
To be clear, the structure of the packaged artifact (the executable JAR) is the 
same regardless of the build tool. That's reassuring! 

```text
com/
META-INF/
test.txt
```

### Maven

The location of `resources` files in Maven's working directory (`target`) matches 
that of the packaged artifact (executable JAR), so the file `test.txt` is logged 
by the diagnostic `Files.walk`.
Compare to **Gradle** and **Gradle + Kotlin** below where the file 
`test.txt` isn't at the location where you'd expect to to be, so it isn't logged
by the diagnostic `Files.walk`.

```
URI pathUri = Paths.get(Main.class.getClassLoader().getResource("").toURI());
Files.walk(pathUri, 1, FileVisitOption.FOLLOW_LINKS)
  .map(Path::toString)
  .collect(Collectors.joining("\n"))
// result 
// ...maven-app/target/classes
// ...maven-app/target/classes/com
// ...maven-app/target/classes/test.txt
```

#### Maven Build Directory Structure

```text
target/
  classes/
    com/
    test.txt
  generated-sources/
  maven-archiver/
  maven-status/
  app.jar
```


### Gradle
The location of `resources` files in Gradle's working directory  (`build`) does _not_ match 
that of the packaged artifact (executable JAR). 
Since the file `test.txt` isn't at the location where you'd expect to to be, 
it isn't logged by the diagnostic `Files.walk`.

```
URI pathUri = Paths.get(Main.class.getClassLoader().getResource("").toURI());
Files.walk(pathUri, 1, FileVisitOption.FOLLOW_LINKS)
  .map(Path::toString)
  .collect(Collectors.joining("\n"))
// result
// ...gradle-app/build/classes/java/main
// ...gradle-app/build/classes/java/main/com
// ...gradle-app/build/classes/java/main/gradle
```

#### Gradle Build Directory Structure

```text
build/
  classes/
    java/
      main/
        com/
  distributions/
  generated/
  libs/
    app.jar
  reports/
  resources/
    main/
      test.txt
  scripts/
  test-results/
  tmp/
```

### Gradle + Kotlin
The location of `resources` files in Kotlin's working directory (`out`) does _not_ match 
that of the packaged artifact (executable JAR). 
Since the file `test.txt` isn't at the location where you'd expect to to be, 
it isn't logged by the diagnostic `Files.walk`.

```
URI pathUri = Paths.get(Main.class.getClassLoader().getResource("").toURI());
Files.walk(pathUri, 1, FileVisitOption.FOLLOW_LINKS)
  .map(Path::toString)
  .collect(Collectors.joining("\n"))
// result
// ...kotlin-app/out/production/classes
// ...kotlin-app/out/production/classes/com
// ...kotlin-app/out/production/classes/META-INF
```

#### Kotlin Build Directory Structure

```text
out/
  production/
    classes/
      com/
      META-INF/
    resources/
      test.txt
  test/
```

#### Gradle Build Directory Structure
Running `gradle clean build` generates a `build` with the same structure as that shown above under *Gradle*

