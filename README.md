# db-jdo-site
Sources for the Apache DB JDO web site

While under construction, the website can be found under [https://apache.github.io/db-jdo-site/](https://apache.github.io/db-jdo-site/) 

This repository contains the JDO website source.

 * The ASCIIDOC sources can be found in `src/main/asciidoc`
 * The website menu is defined in `src/main/template`
 * The website can be build with `mvn clean build`. This generates html files in `target/site`. These need to be copied manually into the `docs` folder. Then, after `git commit` and `git push`, they should be visible on the website (you may have to refresh the browser). Alternatively, the generated files can of course be viwed locally with a web browser.
 * The converter for migrating the old html files to asciidoc can be found in `src/main/java`

### TODO Asciidoc 
 * JDO Usage -> Guides are missing
 * Links in Usage -> Glossary
  
### TODO
 * Downloads -> Any release -> cgi files....
 * Development -> Sourcecode page
 * Development -> Coding standard page
 * Community -> Team?
 * Development -> Dependencies (SVN -> Git)
 * Cleanup everything
 
