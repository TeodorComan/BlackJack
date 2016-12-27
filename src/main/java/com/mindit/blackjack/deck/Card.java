package com.mindit.blackjack.deck;

public class Card {
	
	private Suite suite;
	private Number number;
	
	public final static Integer ACE_LOW_VALUE = 1;
	public final static Integer ACE_HIGH_VALUE = 11;
	public final static Integer NON_ACE_HIGH_VALUE = 10;
	
	public Card(Number number, Suite suite) {
		this.number = number;
		this.suite = suite;
	}
	
	public Suite getSuite() {
		return suite;
	}
	public void setSuite(Suite suite) {
		this.suite = suite;
	}
	public Number getNumber() {
		return number;
	}
	
	public void setNumber(Number number) {
		this.number = number;
	}
}
