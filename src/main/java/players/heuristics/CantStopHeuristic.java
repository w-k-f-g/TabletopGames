package players.heuristics;

import core.AbstractGameState;
import core.CoreConstants;
import core.interfaces.IStateHeuristic;

public class CantStopHeuristic implements IStateHeuristic {
    public double evaluateState(AbstractGameState gs, int playerId){
        double score = gs.getGameScore(playerId);
        if (gs.getPlayerResults()[playerId]== CoreConstants.GameResult.WIN_GAME)
            score = score*2;
        if (gs.getPlayerResults()[playerId]== CoreConstants.GameResult.LOSE_GAME)
            score = score*0;
        if (gs.getPlayerResults()[playerId]== CoreConstants.GameResult.DRAW_GAME)
            score = score*0.8;
        return score;
    }
}
