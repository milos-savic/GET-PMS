# GET-PMS
* Git Repository: `https://github.com/milos-savic/GET-PMS`

##  Instruction for Run Designer Application

1) Open project folder

	cd GET-PMS

2) Compile all the sources

	mvn clean install

3) Run application Designer Web Application

	mvn -f dist\pms-webapp

PMS Web App is available at http://127.0.0.1:8080


############################################################
## Instruction for development processes

* Run application

	mvn spring-boot:run

 * Open DB management console

	http://127.0.0.1:8080/h2-console/



## Packaging to executable archive

Build

    mvn clean package

To ensure everything worked, run:

    java -jar target/*.war

Then navigate to [http://localhost:8080](http://localhost:8080) in your browser.

