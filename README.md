# JDO Website

This repository contains the sources for the [Apache DB JDO website](https://db.apache.org/jdo/).

The website is mirrored on https://apache.github.io/db-jdo-site/.


## Building the Site

The content and styling of the site is defined in the [AsciiDoc](https://asciidoc.org/) format. It is built using [Maven](https://maven.apache.org/).

The site can be built by calling `mvn clean compile`. This generates the HTML files in `target/site`.

### Adding Javadoc

The site contains a packaged version of the JDO API javadoc. It can be updated as follows:

* Create the javadoc jar (e.g. jdo-api-3.2-javadoc.jar) in the db-jdo repository by calling `mvn clean install -Papache-release` in the api submodule.
* Create a new folder under docs e.g. docs/api32.
* Copy the javadocs jar info the new folder: e.g. `cp  jdo-api-3.2-javadoc.jar  docs/api32`.
* Create a new subfolder docs/api32/jdo-api-3.2-javadoc
* Unpack the javadoc jar in the subfolder
* Edit javadoc.adoc under src/main/asciidoc and create a new section 'JDO 3.2 javadoc'.
* Add two links: one referring index.html in the subfolder and one referring the javadoc jar.

## Contributing to the Site

Contributions to the website are always appreciated.
If you are new to this project, please have a look at our [Get Involved](https://db.apache.org/jdo/get-involved.html) page first.

This repository contains the JDO website source.

 * The AsciiDoc sources can be found in `src/main/asciidoc`.
 * The website menu is defined in `src/main/template`.
 * The converter for migrating the old HTML files to AsciiDoc can be found in `src/main/java`

Contributions to this repository follow the default [GitHub workflow](https://guides.github.com/introduction/flow/) using [forks](https://guides.github.com/activities/forking/).

To contribute changes, you can follow these steps:

 * Adapt the AsciiDoc files in `src/main/asciidoc` or the website menu in  `src/main/template`.
 * Call `mvn clean compile` to build the site and verify the generated website by viewing it locally with a web browser.
 * Commit the source changes (not the build artifacts) and open a pull request.

### TODO
 * If you find any issues please provide a PR or [create a JIRA ticket](https://issues.apache.org/jira/projects/JDO/issues/?filter=allopenissues)
