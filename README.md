# video-rental-store
December 2017

1.	Download or clone git repository with the project from GitHub.

2.	Connect to a database. There are 2 options:
2.1.	Local database. 
You should pre-install and start MongoDB on your local PC. It can be done in a few simple steps as described here. Then you should verify that 
\video-rental-store\src\main\resources\application.properties file contains line like “spring.data.mongodb.uri=mongodb://localhost/admin”.
2.2.	 MongoDb in the cloud. 
You should register on MongoDB and got simplest cluster for free with 512MB space. For use it you should just put in the mentioned above application.properties file another line:
spring.data.mongodb.uri=mongodb://<username>:<password>@cluster0-shard-00-00-jzuzn.mongodb.net:27017,cluster0-shard-00-01-jzuzn.mongodb.net:27017,cluster0-shard-00-02-jzuzn.mongodb.net:27017/RentStore?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin

3.	Run "mvn package" to get jar file for the deployment and run. Since the application based on Spring Boot you don’t need any additional Servlet Containers to run it. It’s enough just to enter in CLI: “java -jar \video-rental-store\target\video-rental-store-1.0.jar” and application will be ready to use via browser.
Another option is that you can just import code of application as existed Maven project into any IDE and run VRStoreWebApplication.java file from \video-rental-store\src\main\java\info\kondratiuk location.
All described is related to Windows OS but in Linux everything listed above is very similar.

4.	Now you can open any browser (Google Chrome is preferred), enter http://localhost:8080/ and you should see welcome page. 

Application works in Demo mode, so no any payment system or real film database developed.