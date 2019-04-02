<h4> Languages and frameworks:</h4>
 - Java Core
 - SQL

<h1> Design Pattern: MVC</h1>

<h1> Others: </h1>
    - File streams
    - Data serialization (XML - StAX, CSV - SuperCSV)
    - Command-line interface (JCommander)
    - Export in spreadsheet (Apache POI)
    - Maven
<h1> Overview</h1>
Hyper Reports is a module of the product Hyper Process. It transfers turnover daily reports data from different files into related database in order to provide more complex reporting functionality.

<h1> Specifications</h1>
Hyper Reports processes CSV and XML companies daily reports to database, and generates monthly, quarterly and yearly reports. App as well generates reports for Top N cities, departments and employees with highest/lowest turnover.

Technical details:</h1>
- Command-line application: Java Core
- Relational database: MariaDB

<h1> Build and run: </h1>
- Set app properties in resource/application.properties:
    - set database name and port
    - set path for local source and output folder
- Execute hyper.sql to build database structure
- Build

<h1> Command line interface:</h1>
    - From command line interface invoke method "help"
    - From comand line interface invoke method "load" (java -jar HyperReports.jar load) to download files from remote url
    - From comand line interface invoke method "write" (java -jar HyperReports.jar write) to process files to database
    - From comand line interface invoke method "report" (java -jar HyperReports.jar report) and add required parameters to generate reports

<h1> Prerequsites:</h1>
    - Java 8
    - Maven 4
    - MariaDB