# auspost-test-api
A barebones API written in Java that allows mobile clients to retrieve and add suburb and postcode combinations.

OpenAPI docs and a Postman collection have been included

## Endpoints

A hosted version for demonstration purposes is deployed using AWS CodePipeline from this repo and can be found at the following link:

```
http://auspostfinal-env.eba-gn2efhzh.ap-southeast-2.elasticbeanstalk.com
```

### `/suburbs`

* `GET` `/suburbs?postcode={postcode}` Returns a list of suburbs within the specified postcode or `404 NOT FOUND`

* `POST` `/suburbs/create` Used to create new postcode-suburb links
  * Secured with Spring Security - will return `401 UNAUTHORIZED` if unauthorized
  * Expects a JSON format request body of the following shape: `{ "suburb": String, "postcode": Integer }` or will return `400 BAD REQUEST`
  * Will attempt to update existing postcode/suburb information with the new link, or create new ones if not found

### `/postcodes`

* `GET` `/postcodes?suburb={suburbName}` Returns the postcode of the specified suburb or `404 NOT FOUND`


## How to use

* Using the command `./mvnw clean install` in the root folder will generate `post-api-0.0.1-SNAPSHOT.jar` under `/target`
* This project uses JPA for data persistence and requires an SQL database - it is intended to be deployed within a web server environment on AWS's Elastic Beanstalk with the following directions:
1. On AWS Elastic Beanstalk, create a new web server environment, name is unimportant
2. Under 'Platform', set the platform to 'Java' (other default options are fine here)
3. When prompted for application code, provide the `.jar` file generated earlier
4. Select 'Configure More Options'
5. Under 'Software' select 'Edit'
6. Under 'Environment properties', add a new property `SERVER_PORT` with value `5000` and click 'Save'
7. Back on the environment configuration screen, under 'Database', select 'Edit'
8. The default settings that load should be for the default `mysql` engine
9. Add the username `root` and the password `password` and select 'Save' (as set in the project files `src/resources/application.properties`)
10. Back on the environment configuration screen, select 'Create environment'
11. Wait for environment to be created (can be 10-15+ minutes)
12. Open the created environment and select 'Configuration' on the left side of the screen
13. In the category 'Database', copy the `Endpoint` value including the port to clipboard
    1.  For example, mine is `awseb-e-ewmvbzrdxj-stack-awsebrdsdatabase-ufrkc1dufnvq.clwlhq9e1gxx.ap-southeast-2.rds.amazonaws.com:3306`
14. In the category 'Software', select 'Edit'
15. Under 'Environment properties', add the following properties:
    1.  `SPRING_DATASOURCE_URL` with value `jdbc:mysql://{endpoint value copied earlier}/ebdb` 
        1.  For example, mine is `jdbc:mysql://awseb-e-ewmvbzrdxj-stack-awsebrdsdatabase-ufrkc1dufnvq.clwlhq9e1gxx.ap-southeast-2.rds.amazonaws.com:3306/ebdb`
    2.  `SPRING_DATASOURCE_USERNAME` with value `root`
    3.  `SPRING_DATASOURCE_PASSWORD` with value `password`
    4.  `SPRING_JPA_DATABASE_PLATFORM` with value `org.hibernate.dialect.MySQL5Dialect`
    5.  `SPRING_JPA_HIBERNATE_DDL_AUTO` with value `create` (see notes below)
16. After all settings have been saved and configuration is complete, Elastic Beanstalk will take some time to update the environment (5-10minutes), after which your URL should be available and ready for use!
* Some basic data is initialised for the sake of demonstration (Suburb, Postcode) from `import.sql` in `src/resources/`
  * Melbourne, 3000
  * East Melbourne, 3002
  * West Melbourne, 3003
  * Maidstone, 3012
  * Kingsville, 3012
  * Brooklyn, 3012
  * Tottenham, 3012
  * West Footscray, 3012
* A `GET` to `/suburbs?postcode=3012` will return a list containing the names of all suburbs in postcode 3012
* A `GET` to `/postcodes?suburb=Melbourne` will return the postcode of Melbourne, 3000
* More data can be added with a `POST` to `/suburbs/create` (see `/suburbs` endpoint above for expected request body)
  * To perform this request in Postman, first make a `POST` to `/login` with query parameters `username=user&password=password` to set the authentication
    * eg. `http://auspostfinal-env.eba-gn2efhzh.ap-southeast-2.elasticbeanstalk.com/login?username=user&password=password`
    * Username: `user`, Password: `password` for demonstration purposes

## Dependencies

This is a Spring Boot project using Maven, including the following dependencies:
* spring-boot-starter-data-jpa
* spring-boot-starter-validation
* spring-boot-starter-web
* spring-boot-starter-security
* spring-boot-devtools
* mysql-connector-java
* lombok
* spring-boot-starter-test (for testing)
* assertj-core (for testing)

## Notes

* Full test suite can be found in `src/test/`
* `spring.jpa.hibernate.ddl-auto` in `src/resources/application.properties` and in Elastic Beanstalk's environment properties is set to `create` to allow for data initialisation from `src/resources/`on first load - this has the side effect of dropping all tables on startup so would not be suitable for a production environment, but works well for demonstration. `validate` is likely the best value for production.
* Similarly, Spring Security is set up with hard-coded username and password for demonstration, but more secure methods like OAuth2 or JWT are preferred but would require more configuration/development time.
