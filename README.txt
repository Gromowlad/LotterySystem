How to run:

Install JAR in your local Maven repository: mvn install -Dmaven.test.skip=true
Run the installed JAR: java -jar target/LotterySystem-0.0.1-SNAPSHOT.jar

Implementation details:

Embedded virtual H2 database provided with logging enabled
Request parameters logger printing is enabled
Responses include the handled exception error messages

API supports the following requests:

Create a ticket (POST):
http://localhost:8080/lottery/ticket/create?numberOfLines={numberOfLines}

Get a ticket (GET):
http://localhost:8080/lottery/ticket?ticketId={ticketId}

Return a list of tickets (GET):
http://localhost:8080/lottery/ticket

Check a ticket (PUT):
http://localhost:8080/lottery/ticket/check?ticketId={ticketId}

Amend lines for a ticket (PUT):
http://localhost:8080/lottery/ticket/amend?ticketId={ticketId}&numberOfLines={numberOfLines}
