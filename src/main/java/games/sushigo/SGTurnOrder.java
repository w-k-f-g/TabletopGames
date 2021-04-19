package games.sushigo;

import core.AbstractGameState;
import core.turnorders.AlternatingTurnOrder;
import utilities.Utils;

public class SGTurnOrder extends AlternatingTurnOrder {
    public SGTurnOrder(int nPlayers) {
        super(nPlayers);
    }

    @Override
    public void endPlayerTurn(AbstractGameState gameState) {
        if(gameState.getGameStatus() != Utils.GameResult.GAME_ONGOING) return;
        turnCounter++;
        moveToNextPlayer(gameState, nextPlayer(gameState));
    }
}
