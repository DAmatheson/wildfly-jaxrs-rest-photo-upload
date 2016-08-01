# JAX-RS / RESTEasy Photo Upload PoC

Proof of concept web service demonstrating file upload via RESTEasy and WildFly 10.

There are security issues that this PoC chooses to ignore for the sake of simplicity.
Guidelines about how to deal with these issues can be found here:
- [Mozilla Secure Coding Guidelines - Uploads](https://wiki.mozilla.org/WebAppSec/Secure_Coding_Guidelines#Uploads)
- [security.stackexchange Question](http://security.stackexchange.com/questions/7506/using-file-extension-and-mime-type-as-output-by-file-i-b-combination-to-dete/7531#7531)
- [stackoverflow Question](http://stackoverflow.com/questions/9354300/securing-file-upload/9357146#9357146)

## Requirements & Setup

### 1. Install Oracle Enterprise Pack for Eclipse
- Download and install OEPE for the Eclipse Mars - [Download Link](http://www.oracle.com/technetwork/developer-tools/eclipse/downloads/index.html)
  - Latest and version used at time of creation: `12.2.1.2.1`

### 2. Install JBoss Tools
1. Open Eclipse
2. Help -> Eclipse Marketplace
3. Search for and Install `JBoss Tools`
 - Latest and version used at time of creation: `4.3.1.Final`

### 3. Setup JBoss Tools to be aware of WildFly
1. Open Eclipse
2. Window -> Preferneces
3. JBoss Tools -> JBoss Runtime Detection
 - Add a new Paths entry pointing to the folder containing WildFly 10

### 4. Install Maven
1. Download Maven - [Download Link](https://maven.apache.org/download.cgi)
 - Latest and version used at time of creation: `3.3.9`
2. Extract it
3. Add the Maven `bin` directory to your path
 - [Install Instructions](https://maven.apache.org/install.html)
4. Restart Eclipse if it is running

### 5. Import The Project
1. Open Eclipse
2. File -> Import
3. Maven -> Existing Maven Projects -> Next
4. Set the root to either Prototypes or photo-upload
5. Select the `pom.xml` for `ca.drewm.example:photo-upload:1.0.0:war`
6. Finish

### 6. Deploy The Project
- Right-click the project folder in Project Explorer -> Run As -> Run on server