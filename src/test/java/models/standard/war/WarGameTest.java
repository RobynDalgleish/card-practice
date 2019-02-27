package models.standard.war;

import models.Deck;
import models.standard.StandardDeck;
import models.standard.StandardPlayingCard;

import org.junit.Test;

import java.util.List;

import static models.standard.war.WarGame.evaluatePlay;
import static org.junit.Assert.*;

public class WarGameTest {
    @Test
    public void testWarGameHasDealer() {
        assertNotNull(new WarGame().getDealer());
    }

    @Test
    public void testWarGameHasAtLeastOnePlayers() {
        assertTrue(new WarGame().getPlayers().size() > 0);
    }

    @Test
    public void testWarGameHasStandardDeck() {
        Deck deck = new WarGame().getDeck();
        assertNotNull(deck);
        assertTrue(deck instanceof StandardDeck);
    }
    @Test
    public void testWarPlayersAndDealerEachStartWithHandThatIsDeckSizeDividedAsEquallyAsPossible() {
        WarGame game = new WarGame();
        List<WarPlayer> players = game.getPlayers();
        assertEquals(1, players.size());
        for (WarPlayer p : players) {
            assertEquals(26, p.getHand().size());
        }
        assertEquals(26, game.getDealer().getHand().size());


        WarGame newGame = new WarGame(3);
        List<WarPlayer> newPlayers = newGame.getPlayers();
        assertEquals(3, newPlayers.size());
        for (WarPlayer p : newPlayers) {
            assertEquals(13, p.getHand().size());
        }
        assertEquals(13, newGame.getDealer().getHand().size());
    }

    @Test
    public void testWarPlayerCanPlayOneCardOnTurn() {
        WarGame game = new WarGame();
        List<WarPlayer> players = game.getPlayers();
        assertEquals(1, players.size());
        WarPlayer onlyPlayer = players.get(0);

        assertEquals(26, onlyPlayer.getHand().size());
        onlyPlayer.play();
        assertEquals(25, onlyPlayer.getHand().size());
    }

    @Test
    public void testARoundOfPlayedCardsCanBeEvaluated() {
        WarGame game = new WarGame();
        List<WarPlayer> players = game.getPlayers();
        WarPlayer dealer = game.getDealer();
        for (WarPlayer p : players) {
            StandardPlayingCard playerPeeked = p.getHand().getCards().peek();
            int playerPeekedValue = playerPeeked.getRank().getValue();
            p.play();
            assertEquals(playerPeekedValue, evaluatePlay(p));
        }
        StandardPlayingCard dealerPeeked = dealer.getHand().getCards().peek();
        dealer.play();
        int dealerPeekedValue = dealerPeeked.getRank().getValue();
        assertEquals(dealerPeekedValue, evaluatePlay(dealer));
    }
}