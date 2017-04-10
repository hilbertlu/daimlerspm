# daimlerspm

To setup the development environment:
1. git clone https://github.com/hilbertlu/daimlerspm_poc.git
2. unzip and copy folder "hybris" and "installer" from 6.3.0.1 into the repository directory with name "hybris" and "installer"
3. run ". setup.sh"
4. run "cd hybris/bin/platform"
5. run "./hybrisserver.sh" to start hybris server.


Please note:
-In step 3, if you are setting up a new envrionmnet installer will be called and addon will be installed, else it will only copy "local.properties" and "localextensions.xml" file of config/develop/ into hybris/config/
-If any addon is decided to be added or removed, please update the "localextensions.xml" in onfig/develop/ and also the "build.gradle" in config/installer/recipes/daimler/


123