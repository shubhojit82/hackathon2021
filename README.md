# hackathon2021

### Build
Execute the following command from the parent directory to build the jar file:</br>
```
mvn clean install
```

### Run
From the parent directory, execute the following coommand to start the application:</br>
```
mvn spring-boot:run
```

Once the application starts up, navigate to ```http://localhost:<9080>/actuator/health``` 
and ```http://localhost:<9080>/actuator/info```</br>
