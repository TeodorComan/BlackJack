package com.mindit.blackjack;

import java.util.ArrayList;
import java.util.List;

import com.mindit.blackjack.deck.Card;
import com.mindit.blackjack.deck.Deck;

public class Game {

	private Player humanPlayer;
	private Dealer dealer;
	private Deck deck;

	private GameConclusion gameConclusion;

	private boolean humanPlayerStayed = false;
	private boolean dealerStayed = false;

	public Game() {
		humanPlayer = new Player();
		dealer = new Dealer();
		gameConclusion = GameConclusion.UNKNOWN;

		deck = new Deck();

		humanPlayer.drawCard(deck);
		dealer.drawCard(deck);
		humanPlayer.drawCard(deck);
		dealer.drawCard(deck);
	}

	public void humanPlayerHits() {

		if (!gameConclusion.equals(GameConclusion.UNKNOWN)) {
			throw new RuntimeException("The game has finished.");
		}

		if (humanPlayerStayed == true) {
			throw new RuntimeException("The human player is no longer allowed to hit.");
		}

		if (humanPlayer.getCardsValue() > 21) {
			throw new RuntimeException("The human player already lost. No more hits allowed");
		}

		humanPlayer.drawCard(deck);

		checkGameConclusion();
	}

	private void checkGameConclusion() {
		if (humanPlayer.getCardsValue() > 21) {
			gameConclusion = GameConclusion.DEALER_WINS;
		} else if (dealer.getCardsValue() > 21) {
			gameConclusion = GameConclusion.HUMAN_PLAYER_WINS;
		} else if (humanPlayerStayed == true && dealerStayed == true) {

			if (humanPlayer.getCardsValue() == dealer.getCardsValue()) {
				gameConclusion = GameConclusion.DRAW;
			} else if (dealer.getCardsValue() > humanPlayer.getCardsValue()) {
				gameConclusion = GameConclusion.DEALER_WINS;
			}
		}

	}

	public void humanPlayerStays() {
		humanPlayerStayed = true;
		checkGameConclusion();
	}

	public void dealerHits() {
		if (!gameConclusion.equals(GameConclusion.UNKNOWN)) {
			throw new RuntimeException("The game has finished.");
		}

		if (humanPlayerStayed != true) {
			throw new RuntimeException("The game has finished.");
		}

		dealer.drawCard(deck);

		checkGameConclusion();
	}

	public void dealerStays() {
		
		if (!gameConclusion.equals(GameConclusion.UNKNOWN)) {
			throw new RuntimeException("The game has finished.");
		}
		
		if (humanPlayerStayed != true) {
			throw new RuntimeException("The game has finished.");
		}
		
		dealerStayed = true;
		checkGameConclusion();
	}

	public List<Card> getHumanPlayerCards() {
		return humanPlayer.getCards();
	}

	public List<Card> getDealerRevealedCards() {
		@SuppressWarnings("unchecked")
		List<Card> revealedCards = (List<Card>) ((ArrayList<Card>) dealer.getCards()).clone();

		revealedCards.remove(1);

		return revealedCards;
	}

	public List<Card> getDealerCards() {
		return dealer.getCards();
	}

	public Card getDealerHiddenCard() {
		return dealer.getCards().get(1);
	}

	public int getHumanPlayerCardsValue() {
		return humanPlayer.getCardsValue();
	}

	public int getDealerCardsValue() {
		return dealer.getCardsValue();
	}

	public GameConclusion getGameConclusion() {
		return gameConclusion;
	}

	public DealerDecision dealerDecides() {
		DealerDecision decision = dealer.decideNextAction(humanPlayer.getCardsValue());

		if (decision.equals(DealerDecision.STAY)) {
			checkGameConclusion();
		}

		return decision;
	}
}
