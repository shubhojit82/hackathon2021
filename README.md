# hackathon2021

### Build
Execute the following command from the parent directory to build the jar file:</br>
```
mvn clean install -Drevision=1.0.0-SNAPSHOT
```

### Run
From the parent directory, execute the following coommand to start the application:</br>
```
java -jar -Dspring.profiles.active=local target/ocrp-PartnershipRewards-1.0.0-SNAPSHOT.jar 
mvn spring-boot:run -Dspring.profiles.active=local
```

Once the application starts up, navigate to ```http://localhost:<9080>/actuator/health``` 
and ```http://localhost:<9080>/actuator/info```</br>
