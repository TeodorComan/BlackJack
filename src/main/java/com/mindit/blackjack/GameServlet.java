package com.mindit.blackjack;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mindit.blackjack.deck.Card;

public class GameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {

		Game game = new Game();

		HttpSession session = request.getSession(true);
		session.setAttribute("game", game);

		request.setAttribute("game", game);
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {

		HttpSession session = request.getSession(false);
		Game game = (Game) session.getAttribute("game");

		if (request.getParameter("action").equals("hitme")) {
			game.humanPlayerHits();

			JsonObjectBuilder jsonResponse = Json.createObjectBuilder();

			JsonArrayBuilder cards = Json.createArrayBuilder();

			for (Card card : game.getHumanPlayerCards()) {
				cards.add(card.getNumber() + " of " + card.getSuite());
			}

			jsonResponse.add("cards", cards);

			jsonResponse.add("humanPlayerCardsValue", game.getHumanPlayerCardsValue());

			jsonResponse.add("gameConclusion", game.getGameConclusion().toString());

			response.setContentType("application/json");

			response.getWriter().write(jsonResponse.build().toString());
		} else if (request.getParameter("action").equals("stay")) {

			game.humanPlayerStays();

			JsonObjectBuilder jsonResponse = Json.createObjectBuilder();
			JsonArrayBuilder cards = Json.createArrayBuilder();

			for (Card card : game.getDealerCards()) {
				cards.add(card.getNumber() + " of " + card.getSuite());
			}

			jsonResponse.add("cards", cards);

			jsonResponse.add("hiddenCard", game.getDealerHiddenCard().getNumber() + " of " + game.getDealerHiddenCard()
					.getSuite());

			jsonResponse.add("dealerCardValue", game.getDealerCardsValue());

			jsonResponse.add("gameConclusion", game.getGameConclusion().toString());

			response.setContentType("application/json");

			response.getWriter().write(jsonResponse.build().toString());

		} else if (request.getParameter("action").equals("getDealerDecision")) {

			JsonObjectBuilder jsonResponse = Json.createObjectBuilder();
			jsonResponse.add("dealerDecision", game.dealerDecides().toString());
			response.setContentType("application/json");

			response.getWriter().write(jsonResponse.build().toString());
		} else if (request.getParameter("action").equals("getGameWinner")) {
			game.dealerStays();
			JsonObjectBuilder jsonResponse = Json.createObjectBuilder();
			jsonResponse.add("gameConclusion", game.getGameConclusion().toString());
			response.setContentType("application/json");

			response.getWriter().write(jsonResponse.build().toString());
		
		} else if (request.getParameter("action").equals("dealerHits")) {
			
			game.dealerHits();
			
			JsonObjectBuilder jsonResponse = Json.createObjectBuilder();
			JsonArrayBuilder cards = Json.createArrayBuilder();

			for (Card card : game.getDealerCards()) {
				cards.add(card.getNumber() + " of " + card.getSuite());
			}

			jsonResponse.add("cards", cards);
			
			jsonResponse.add("dealerCardValue", game.getDealerCardsValue());
			
			jsonResponse.add("gameConclusion", game.getGameConclusion().toString());
			
			response.setContentType("application/json");

			response.getWriter().write(jsonResponse.build().toString());
		}
	}

}
