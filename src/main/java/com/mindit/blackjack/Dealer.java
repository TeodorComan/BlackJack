package com.mindit.blackjack;

public class Dealer extends Player {

	public DealerDecision decideNextAction(int humanPlayerCardValue) {

		if (humanPlayerCardValue > this.getCardsValue()) {
			return DealerDecision.HIT;
		} else if (humanPlayerCardValue < this.getCardsValue()) {
			return DealerDecision.STAY;
		} else {
			if (this.getCardsValue() < 17) {
				return DealerDecision.HIT;
			} else {
				return DealerDecision.STAY;
			}
		}

	}
}
