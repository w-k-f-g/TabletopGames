package games.coltexpress.actions;

import core.AbstractGameState;
import core.actions.DrawCard;
import core.components.Deck;
import games.coltexpress.ColtExpressGameState;
import games.coltexpress.cards.ColtExpressCard;
import games.coltexpress.components.Loot;

import java.util.LinkedList;
import java.util.Random;

import static utilities.CoreConstants.VERBOSE;

public class CollectMoneyAction extends DrawCard {

    private final int availableLoot;
    private final int loot;

    public CollectMoneyAction(int plannedActions, int playerDeck,
                              int loot, int availableLoot) {
        super(plannedActions, playerDeck);

        this.loot = loot;
        this.availableLoot = availableLoot;
    }

    @Override
    public boolean execute(AbstractGameState gameState) {
        super.execute(gameState);
        if (loot == -1)
            return false;

        Deck<Loot> availableLootDeck = (Deck<Loot>) gameState.getComponentById(availableLoot);
        LinkedList<Loot> lootOfCorrectType = new LinkedList<>();
        for (Loot available : availableLootDeck.getComponents()){
            if (available.getComponentID() == loot)
                lootOfCorrectType.add(available);
        }

        if (lootOfCorrectType.size() == 0 && VERBOSE){
            System.out.println();
        }
        ColtExpressCard card = (ColtExpressCard) gameState.getComponentById(cardId);
        ((ColtExpressGameState) gameState).addLoot(card.playerID,
                lootOfCorrectType.get(new Random().nextInt(lootOfCorrectType.size())));

        return true;
    }

    @Override
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException();
        //return false;
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException();
    }

    public String toString(){
        if (loot == -1)
            return "Attempt to collect loot but no loot is available";
        return "Collect loot";
    }
}
