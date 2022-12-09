# Elevator Managment System

To start the application, clone and run the following command:

```
mvn spring-boot:run
```

After the application is started, you can send requests using web browser or using Postman

```
http://localhost:8080/
```

To order Elevator use post method:

```
http://localhost:8080/api/elevator/orderElevator/{startingFloor}&{destinationFloor}
```