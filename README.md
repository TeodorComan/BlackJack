# BlackJack

# Running the application
To run the application use: mvn clean install tomcat7:run and access the URL http://localhost:8080/blackjack/game

# Small rules changes
The dealer will always try to win or draw the game. 
If the dealer has more points than the player, he will stay. No need to risk it.
If the dealer and the player have equal points and the dealer has less than 17, he will hit to try and win the game, otherwise he will stay to force a draw.
