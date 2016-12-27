package com.mindit.blackjack;

import java.util.ArrayList;
import java.util.List;

import com.mindit.blackjack.deck.Card;
import com.mindit.blackjack.deck.Deck;

public class Player {
	
	private List<Card> cards;
	private int cardsValue=0;
	private int aces=0;
	
	public Player() {
		cards = new ArrayList<Card>();
	}

	public void drawCard(Deck deck) {
		cards.add(deck.getCards().get(0));
		addCardValue(deck.getCards().get(0));
		deck.getCards().remove(0);
	}

	public int getCardsValue() {
		return this.cardsValue;
	}
	
	public List<Card> getCards() {
		return cards;
	}
	
	private void addCardValue(Card card) {	
		if (isInteger(card.getNumber().getSymmbol())) {
			cardsValue+=Integer.parseInt(card.getNumber().getSymmbol());
		} else {
			if (!"A".equals(card.getNumber().getSymmbol())) {
				cardsValue+=Card.NON_ACE_HIGH_VALUE;
			} else {
				cardsValue+=Card.ACE_HIGH_VALUE;
				aces++;
			}
		}
		
		while(cardsValue>21 && aces>0) {
			cardsValue-=Card.ACE_HIGH_VALUE-Card.ACE_LOW_VALUE;
			aces--;
		}
	}

	private boolean isInteger(String s) {
		if (s.isEmpty())
			return false;
		for (int i = 0; i < s.length(); i++) {
			if (i == 0 && s.charAt(i) == '-') {
				if (s.length() == 1)
					return false;
				else
					continue;
			}
			if (Character.digit(s.charAt(i), 10) < 0)
				return false;
		}
		return true;
	}
}
