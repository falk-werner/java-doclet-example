# Java Doclet Example

This repository contain an example how to create a Java Doclet using Gradle.

## Example Project

The doclet contained in this repository creates an XML documentation of the test cases contained in the same project.

To create the XML documentation use the following command:

````
./gradlew doc
````

This creates an XML file located at `lib/build/docs/testcases.xml`:

````xml
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<testsuite>
    <testcase classname="org.example.doclet.LibraryTest" name="someTest">
        <description>A Dummy Testcase.</description>
    </testcase>
    <testcase classname="org.example.doclet.LibraryTest" name="anotherTest">
        <description>Another Testcase.</description>
    </testcase>
    <testcase classname="org.example.doclet.LibraryTest" name="undocumentedTest">
        <description/>
    </testcase>
</testsuite>
````

## Pitfalls

There were some pitfalls encountered during development of the example.

### Gradle needs additional JavaDoc options

Gradles makes use of some specific JavaDoc options that are contained in the standard JavaDoc doclet but not necessarily in a custom Doclet:

| Option | Parameter | Description |
| ------ | --------- | ----------- |
| -d     | directory | Specify directory of output files |
| -notimestamp | -   | Do not include hidden time stamp |

In order to let gradle use a custom Doclet these options mus be available in the Doclet's command line options.

### Quirks in build.gradle

The `build.gradle` in this project comes with some quirks:

````gradle
afterEvaluate {
  task doc(type: Javadoc) {
    dependsOn classes
    source = sourceSets.test.allJava
    classpath += sourceSets.test.compileClasspath
    options.doclet = "org.example.doclet.TestCaseDoclet"
    options.docletpath = (sourceSets.main.output + sourceSets.main.compileClasspath) as List
    destinationDir = project.docsDir
    title = ""
  }
}
````

This reason for these quirks is the uncommon use case of this example to document test cases of the same project that contains the Doclet. I simple wanted to test the
Doclet without the need of another project.

