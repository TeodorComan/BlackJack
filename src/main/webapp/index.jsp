<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script>
	$(document).ready(function() {
		var playerPoints='<c:out value="${game.getHumanPlayerCardsValue()}"/>';
		
		function dealerTurn($msg) {
			  $( "#playerActions button" ).button( "option", "disabled", true );
				$("#modalDialog").html($msg);
				$("#modalDialog").dialog({
			        buttons: {
			          "Let the Dealer think": function() {
			        	  $.ajax({
								method : "POST",
								url : "game",
								data : {
									action : "getDealerDecision"
								},
								dataType : 'json',
								success : function(data) {
									if(data.dealerDecision=="STAY"){
										$("#modalDialog").html("The dealer decided to stay.");
										$("#modalDialog").dialog({
									        buttons: {
									          "Get Winner": function() {
									        	  $.ajax({
														method : "POST",
														url : "game",
														data : {
															action : "getGameWinner"
														},
														dataType : 'json',
														success : function(data) {
															var msg="";
															if(data.gameConclusion=="HUMAN_PLAYER_WINS"){
																msg="Congratulations! You WIN!";
															} else if(data.gameConclusion=="DEALER_WINS"){
																msg="The Dealer wins. How about another go?";
															} else {
																msg = "Looks like it is a draw.";
															}
															$("#modalDialog").html(msg);
															$("#modalDialog").dialog({
														        buttons: {
														          "New game": function() {
														        	 location.reload();
														          },
														          "Return to game": function() {
														        	  $( this ).dialog( "close" );
														          }
														          }
														        });
															$("#modalDialog").dialog( "open" );
														}
													});
									          }
									        }
									      });
									}
									else {
										$("#modalDialog").html("The dealer decided to hit.");
										$("#modalDialog").dialog({
									        buttons: {
									          "Let the Dealer hit": function() {
									        	  $.ajax({
														method : "POST",
														url : "game",
														data : {
															action : "dealerHits"
														},
														dataType : 'json',
														success : function(data) {
															var dealer = "<p>The dealer has the following cards:</p>";

															$.each(data.cards, function(index, element) {
																dealer = dealer.concat(element).concat("<br>");
															});
															$("#dealerCards").html(dealer);
															
															if(data.gameConclusion=="HUMAN_PLAYER_WINS") {
																$( "#playerActions button" ).button( "option", "disabled", true );
																$("#modalDialog").html("The Dealer got "+data.dealerCardValue+". <br> YOU WIN!");
																$("#modalDialog").dialog({
															        buttons: {
															          "New game": function() {
															        	 location.reload();
															          },
															          "Return to game": function() {
															        	  $( this ).dialog( "close" );
															          }
															          }
															        });
																$("#modalDialog").dialog( "open" );
															} 
															else if (data.gameConclusion=="DRAW") {
																$( "#playerActions button" ).button( "option", "disabled", true );
																$("#modalDialog").html("The Dealer got "+data.dealerCardValue+". It is a draw");
																$("#modalDialog").dialog({
															        buttons: {
															          "New game": function() {
															        	 location.reload();
															          },
															          "Return to game": function() {
															        	  $( this ).dialog( "close" );
															          }
															          }
															        });
																$("#modalDialog").dialog( "open" );
															}
															else {
																dealerTurn("The Dealer got "+data.dealerCardValue+". He  needs to think his next move.");
															}
														}
													});
									          }
									        }
									      });
									}
									
								}
							});
			          }
			        }
			      });
				$("#modalDialog").dialog( "open" );
			}
		
		
		$( "#playerActions button" ).button({disabled: false});
			
		    $( "#modalDialog" ).dialog({
		        resizable: false,
		        height: "auto",
		        width: 400,
		        modal: true,
		        autoOpen: false,
		        closeOnEscape: false,
		        open: function(event, ui) {
		            $(".ui-dialog-titlebar-close", ui.dialog | ui).hide();
		        },
		        buttons: {
		          "New game": function() {
		        	 location.reload();
		          },
		          "Return to game": function() {
		        	  $( this ).dialog( "close" );
		          }
		        }
		      });
			
			$("#hitme").click(function() {
				$.ajax({
					method : "POST",
					url : "game",
					data : {
						action : "hitme"
					},
					dataType : 'json',
					success : function(data) {

						var cards = "";

						$.each(data.cards, function(index, element) {
							cards = cards.concat(element).concat("<br>");
						});
						$("#playerCards").html(cards);

						if(data.gameConclusion=="DEALER_WINS"){
							$( "#playerActions button" ).button( "option", "disabled", true );
							$("#modalDialog").html("You got "+data.humanPlayerCardsValue+". You lost.");
							$("#modalDialog").dialog( "open" );

						} 
						
						playerPoints = data.humanPlayerCardsValue;
					}
				});
			});
			
			$("#stay").click(function() {
				$.ajax({
					method : "POST",
					url : "game",
					data : {
						action : "stay"
					},
					dataType : 'json',
					success : function(data) {
						
						var dealer = "<p>The dealer has the following cards:</p>";

						$.each(data.cards, function(index, element) {
							dealer = dealer.concat(element).concat("<br>");
						});
						$("#dealerCards").html(dealer);
						
						if(data.gameConclusion=="HUMAN_PLAYER_WINS") {
							$( "#playerActions button" ).button( "option", "disabled", true );
							$("#modalDialog").html("You chose to stay with "+playerPoints+" points. The dealer's hidden card is "+data.hiddenCard+". He has "+data.dealerCardValue+". <br> YOU WIN!");
							$("#modalDialog").dialog( "open" );
						} 
						else if (data.gameConclusion=="DRAW") {
							$( "#playerActions button" ).button( "option", "disabled", true );
							$("#modalDialog").html("You chose to stay with "+playerPoints+" points. The dealer's hidden card is "+data.hiddenCard+". He has "+data.dealerCardValue+". <br> It is a draw.");
							$("#modalDialog").dialog( "open" );
						}
						else {
							dealerTurn("You chose to stay with "+playerPoints+" points. The dealer's hidden card is "+data.hiddenCard+". He has "+data.dealerCardValue+". It is his turn now.");
						}								
					}
				});
			});
			
	});
</script>
</head>
<body>
	<h2>Welcome to a new game of BlackJack</h2>

	<div id="dealerCards">
		<p>The dealer has been dealt one hidden card and a</p>
		<c:forEach items="${game.getDealerRevealedCards()}" var="item">
    		${item.getNumber()} of ${item.getSuite()}<br>
		</c:forEach>
	</div>
	<p>You have the following cards:</p>
	<div id="playerCards">
		<c:forEach items="${game.getHumanPlayerCards()}" var="item">
    	${item.getNumber()} of ${item.getSuite()}<br>
		</c:forEach>
	</div>

	<div id="playerActions">
		<p>What do you want to do?</p>
		<button id="hitme">Hit me!</button>
		<button id="stay">I stay.</button>
	</div>
	
	<div id="modalDialog"></div>
  

</body>
</html>
