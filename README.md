# gradle-jvm-module-arg-bug
Example showing java argument-parsing issue when using '--module' to execute a java9 module main class

Java code contains a single class with a trivial `main()` that prints out the arguments passed into it.

There are two gradle build files. The first (build.gradle) uses a standard non-modular 'run' target which works as expected. Output from main shows that there are no arguments given:

```
Task :run
Arguments to main():0
```

The second gradle build file (build_module.gradle) uses a Java9 module to house the main class, and injects the appropriate `--module` argument using Gradle's jvmArgs array. When calling 'run' with this second file (i.e. `gradle -b build_module.gradle run`) we see arguments intended for the JVM passed into `main()`

```
> Task :run
Arguments to main():5
0: -Dfile.encoding=UTF-8
1: -Duser.country=US
2: -Duser.language=en
3: -Duser.variant
4: dbramel.ArgPrinter
```

This happens because as of Java9 the `--module` argument is used to indicate that `java` should stop parsing JVM arguments and should pass the rest as arguments to `main()` (see https://docs.oracle.com/javase/9/tools/java.htm#JSWOR624). Previously the name of the main class was the terminating JVM argument, and that assumption was built into Gradle's java start-script templates (see the end of https://github.com/gradle/gradle/blob/master/subprojects/plugins/src/main/resources/org/gradle/api/internal/plugins/unixStartScript.txt)

