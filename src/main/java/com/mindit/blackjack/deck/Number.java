package com.mindit.blackjack.deck;

public enum Number {
	TWO("2"),
	THREE("3"),
	FOUR("4"),
	FIVE("5"),
	SIX("6"),
	SEVEN("7"),
	EIGHT("8"),
	NINE("9"),
	TEN("10"),
	ACE("A"),
	JACK("J"),
	QUEEN("Q"),
	KING("K")
	;
	
	private final String symbol;
	
	Number(String symbol) {
		this.symbol=symbol;
	}
	
	public String getSymmbol() {
		return this.symbol;
	}
	
}
