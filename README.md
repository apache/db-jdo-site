# JDO Website

This repository contains the sources for the [Apache DB JDO website](https://db.apache.org/jdo/).

The website is mirrored on https://apache.github.io/db-jdo-site/.


## Building the Site

The content and styling of the site is defined in the [AsciiDoc](https://asciidoc.org/) format. It is built using [Maven](https://maven.apache.org/). For details on publishing the site see section [Publishing the Site](#publishing-the-site).

The site can be built by calling `mvn clean compile`. This generates the HTML files in `target/site`.
Next, call `mvn install`. This copies the generated html files into the `docs/` directory.
If needed, call `mvn package`. This copies other generated files to the site.
The site can then be viewed by opening the local file target/site/index.html in a browser.

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
 * Call `mvn clean compile` to build the site and verify the generated website by viewing `target/index.html` locally with a web browser.
 * Commit the source changes (not the build artifacts) and open a pull request.

### TODO
 * If you find any issues please provide a PR or [create a JIRA ticket](https://issues.apache.org/jira/projects/JDO/issues/?filter=allopenissues)
 
### Publishing the Site
After changes have been made to the sources in the `db-jdo-site/src/main/asciidoc` or `db-jdo-site/src/main/template` directory, changes will be published automatically to the live web site by simply pushing changes to the master branch of the repository. The process is as follows:

1. Pushing changes to the master branch invokes the post-push script in [`db-jdo-site/.github/workflows/deploy-site.yml`](./.github/workflows/deploy-site.yml) which builds the site in `db-jdo-site/target/` via `mvn clean install`.

2. If the build is successful, the files are copied from `db-jdo-site/target/` to `db-jdo-site/docs/`. The `db-jdo-site/docs` directory is checked to see if any changes have been made.

3. It is possible that the user made changes to `db-jdo-site/src/main/asciidoc/` and also compiled and pushed them to `db-jdo-site/docs/`). In this case, proceed to step 5.

4. If changes were made to the docs directory by the post-push script, the script then commits the changes to the master branch.

5. Once the changes have been pushed to the master branch, the script in `db-jdo-site/.asf.yaml` is automatically invoked. This script is executed by Apache Infrastructure machines, and it publishes changes to `db.apache.org/jdo`. It may take some time for the changes to be seen on the live site.
Details on the use of .asf.yaml is found [here](https://cwiki.apache.org/confluence/display/INFRA/git+-+.asf.yaml+features#git.asf.yamlfeatures-WebSiteDeploymentServiceforGitRepositories)
