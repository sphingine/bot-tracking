# bot-tracking
<pre>
Project to enable / verify the users logging into the applications are not essentially bots. Initial implementation is to make sure that the users who are using our application knows how to add numbers. 

<b>REQUIREMENT :</b>

Create a server that does two things:

1. Respond to a client HTTP request with a minimum following in the response body
  a) A question with random numbers in the response body. 
    Please sum the numbers 9,5,3
    Please sum the numbers 10,2
    Please sum the numbers 5,7,1)

2. Receive a client HTTP request with a minimum following in the request
  a)    The question with the random numbers which was send earlier in the response of the first request.
  b)    The sum of the numbers in the question.
 And respond with,
  a)    If the sum of the numbers is correct, then return a HTTP 200 OK
  b)    If the sum of the numbers is wrong or if it’s an invalid request, then return a HTTP 400 Bad Request.
 You are not required to write the client application, but you can assume that a client for this server should:
  a)    Send a request to Service 1 and receives a response body with the question.
  b)    Sum the numbers in the question
  c)    Send the sum of the numbers to the service 2 along with the original response body from 1 which had the question.

<b>HIGH LEVEL IMPLEMENTATION :</b>

1. Technology Stack :
    SPRING BOOT, H2 DATABASE, JSON Web Token(JWT), RESTful Webservice
    
2. Web Service Details : 
    a. REQUEST FOR THE DIGITS TO BE ADDED
    curl -X POST -H "Content-Type: application/json" -d '{"username":"abc","password":"abc"}' -v localhost:8080/request-query
    b. VALIDATE THE RESPONSE
    curl -b 'jwt=YWJjYWJj' -X POST -H "Content-Type: application/json" -d '{"username":"abc","password":"abc","query":"Please sum the numbers 93,92,18","sum":"203"}' -v localhost:8080/authenticate

3. Avoiding system overload : 
    Based on the username and password (passed in the request body) and create a JSON Web Token(JWT). 
    We push it to the cookie, we validate the cookie in the VALIDATE SUM Web Service (We remove the Cookie during this WebService)
    The JWT token gets persisted in the DB along with randomly generated query and result
    The size of the table would be such that there would be one record per person
    Every time the user hit the REQUEST QUERY Web Service, we generate a new set of digits and update the DB record.
    During VALIDATE SUM, we check the JWT, Query, and Result posted by user
    
    
<b>SYSTEM PREREQUISITES : </b>

a. JAVA  : 
    java -version
b. MAVEN 
    mvn -version
    export M2_HOME=/Users/rasmi_vasudevan/Desktop/DEV/apache-maven-3.6.3/bin    
    export PATH=$PATH:$M2_HOME

<b>RUN COMMAND : </b>
    mvn clean install
    java -Dspring.profiles.active=dev -jar target/bot_tracking-0.0.1-SNAPSHOT.jar 
    
<b> PROPERTY FILE : </b>
    /bot_tracking/src/main/resources/application-dev.properties
    
<b> MANUAL CHANGES : </b>
    server.port=8081
    spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1
    
<b> H2 CONSOLE </b>
    URL : http://localhost:8080/h2-console
    spring.h2.console.enabled=true
    spring.h2.console.path=/h2-console

</pre>
