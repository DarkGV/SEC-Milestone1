# SEC-Project - Milestone 1

This is the first project for the subject of SEC.

We used docker in order to create an image of the database for it to be easier to test and run. And also we took it as an oportunity to learn a new tool that could be useful for this type of project.

The Database is pre-loaded with 25 users, 5 of which are byzantine, in min/max positions of 0/50.

All of this is configurable through the application.yml file.


## Usage


In order to build and run the application, first we go to the project's pom.xml directory:

``` .../SEC-MILESTONE1/milestone1 ```


Then we run this command to be sure that there won't be any issues compiling:

```bash
mvn clean compile
```


After compiling, to start the application, we must first initialize the database using docker. We do this by running the following docker-compose command:

```bash
sudo docker-compose up mysqldb
```


After waiting until the database is ready to take in connections and if we want to user docker, only then can we run the application, like so:
```bash
sudo docker-compose up app
```


## Testing


After everything is running, to test the server application and the database insertion, we simply need to provide an http request of the desired method with the address provided in the docker-compose file, in this case, localhost:8080.

If for example we want to test the submission of a specific user we insert this http header:

```http
http://localhost:8080/obtainLocationReport/{userId}/{epoch}/{callerUserId}/{isHa}
```

where: 
	- {userId} is the id of the user we want the location report of;
	- {epoch} is the epoch of the user report that we wish to obtain;
	- {callerUserId} is the userId of who called this method. If it's an HA user it can be different from the provided userId;
	- {isHa} true or false depending on the user being HA or not.


To test the submission of a user report, as this is a post request, we do a curl command:

```bash
curl --location --request POST 'http://localhost:8080/submitLocationReport' \
--header 'Content-Type: application/json' \
--data-raw '{
    "userId": 722,
    "epoch": 1,
    "pos": "10-20",
    "isByzantine": "true"
}'
```

All of those values are example values.


In order to test the "obtainUsersAtLocation" method which the HA can perform, we apply this http header:

```http
http://localhost:8080/obtainUsersAtLocation/{pos}/{epoch}/{isHa}
```

where:
	- {pos} is the position of that user in the grid (and database) in the format: x-y
	- {epoch} is the epoch in which we want the user from
	- {isHa} we verify if the user calling this method has HA status. If not, it throws an internal server error (code 500) as it is not allowed for regular users to call this method.
