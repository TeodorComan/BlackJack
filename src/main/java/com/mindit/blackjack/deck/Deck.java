package com.mindit.blackjack.deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Deck {
	
	private List<Card> cards;
	
	public Deck() {
		makeDeck();
		shuffleDeck();
	}

	public void makeDeck() {
		cards = new ArrayList<Card>();
		
		for (Number number : Number.values()) {
			for (Suite suite : Suite.values()) {
			    Card card = new Card(number, suite);
			    cards.add(card);
			}
		}
	}
	
	public void shuffleDeck() {
		long seed = System.nanoTime();
		Collections.shuffle(cards, new Random(seed));
	}
	
	public List<Card> getCards() {
		return this.cards;
	}

}
