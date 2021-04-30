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

Once the application starts up, navigate to ```http://localhost:<8080>/actuator/health``` 
and ```http://localhost:<8080>/actuator/info```</br>

### API Testing end point

```
To execute and hit the end point to recieve emails.
1. Download the EmailSendPostmanScript.postman_collection.json file and import in POSTMAN.
2. Update the Database table intacart_txn with <email_id_of_your>.
3. Execute the end point to get email.

```

### Database connection details

```
DATABASE : Cassandra Database
keyspace-name: emju
table-name : instacart_txn
```

### Architecture Diagram
![Hackathon - Architecture Proposal](https://user-images.githubusercontent.com/31447207/116496179-4ea62880-a859-11eb-9246-466239a69709.png)
