# hackathon 2021

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

### Architecture Diagram
![Hackathon - Architecture Proposal](https://user-images.githubusercontent.com/31447207/116496179-4ea62880-a859-11eb-9246-466239a69709.png)
