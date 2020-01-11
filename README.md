# db-jdo-site
Sources for the Apache DB JDO web site

While under construction, the website can be found under [https://apache.github.io/db-jdo-site/](https://apache.github.io/db-jdo-site/) 

This repository contains the JDO website source.

 * The ASCIIDOC sources can be found in `src/main/asciidoc`
 * The website menu is defined in `src/main/template`
 * The converter for migrating the old html files to asciidoc can be found in `src/main/java`



How to build the website:
 * Use `git pull`  to get the latest version from the repository.
 * Use `git branch MyBranchName` and `git checkout MyBranchName` to create a branch and check it out.
 * Adapt the asciidoc files in `src/main/asciidoc` or the website menu in  `src/main/template
 * Call `mvn clean compile`. This generates html files in `target/site`. 
 * Copy all files from `target/site` into the `docs` folder, do not forget subfolders.
 * Verify the generated website by viewing it locally with a web browser. 
 * Commit changes with `git commit -m 'my commit message' `.
 * Push changes to the repository with `git push`.
 * Go to Github.com and create a PR for your branch
 * Once th PR is accepted, the changes should be visible on the website (you may have to refresh the browser). 

  
### TODO
 * Downloads -> Any release -> cgi files....
 * Development -> Sourcecode page
 * Development -> Coding standard page
 * Community -> Team?
 * Development -> Dependencies (SVN -> Git)
 * Cleanup everything
 
