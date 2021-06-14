### Kalah Rules
Each of the two players has **​six pits** ​in front of him/her. To the right of the six pits, each player has a larger pit, his
Kalah or house.
At the start of the game, six stones are put in each pit.
The player who begins picks up all the stones in any of their own pits, and sows the stones on to the right, one in
each of the following pits, including his own Kalah. No stones are put in the opponent's' Kalah. If the players last
stone lands in his own Kalah, he gets another turn. This can be repeated any number of times before it's the other
player's turn.
When the last stone lands in an own empty pit, the player captures this stone and all stones in the opposite pit (the
other players' pit) and puts them in his own Kalah.
The game is over as soon as one of the sides run out of stones. The player who still has stones in his/her pits keeps
them and puts them in his/hers Kalah. The winner of the game is the player who has the most stones in his Kalah.

### Design
This is a Java RESTful Web Service that runs a game of 6-stone Kalah. This web service enables 2 human players to play the game, each in his own computer.

### Prerequisites
* [Java 1.8](https://www.oracle.com/java/technologies/javase-downloads.html)

### Built With
* [Spring-boot](https://spring.io/projects/spring-boot)
* [Maven](https://maven.apache.org/)
* [junit](https://junit.org/junit5/)
* [springdoc-openapi](https://springdoc.org/)
*[SpringBootTest](https://spring.io/guides/gs/testing-web/)


### Play Game:
* Checkout code from https://github.com/Thibbesh/backbase.git
* run mvn clean install
* Run KalahApplication.java from any IDE, Intellij IDEA
* Added postman chrome extension to chrome browser https://chrome.google.com/webstore/detail/postman/fhbjgbiflinjbdggehcddcbncdddomop?hl=en
* Access endpoints from postman to create a game http://localhost:8080/games
* Add Basic authentication to Headers.
* Username : backbase or user
* Password: backbase or user
* Access endpoints from postman to make a move http://localhost:8080/games/7a29690c-b641-43d7-99be-b260187adcff/pits/1
* Api documentation : http://localhost:8080/v3/api-docs
* SwaggerUI : http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config

### Author
Thibbesha Nanjegowda,
