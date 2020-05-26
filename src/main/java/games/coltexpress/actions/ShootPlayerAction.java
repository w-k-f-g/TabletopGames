package games.coltexpress.actions;

import core.AbstractGameState;
import core.actions.DrawCard;
import games.coltexpress.ColtExpressGameState;
import games.coltexpress.cards.ColtExpressCard;
import games.coltexpress.components.Compartment;

public class ShootPlayerAction extends DrawCard {

    private final int targetID;
    private final int playerCompartment;
    private final int targetCompartment;

    private final boolean isDjango;

    public ShootPlayerAction(int plannedActions, int playerDeck,
                             int playerCompartment, int targetCompartment, int targetID, boolean isDjango) {
        super(plannedActions, playerDeck);
        this.targetID = targetID;
        this.playerCompartment = playerCompartment;
        this.targetCompartment = targetCompartment;
        this.isDjango = isDjango;
    }


    @Override
    public boolean execute(AbstractGameState gs) {
        super.execute(gs);
        if (targetID == -1)
            return false;

        Compartment player = (Compartment) gs.getComponentById(playerCompartment);
        Compartment target = (Compartment) gs.getComponentById(targetCompartment);
        ColtExpressCard card = (ColtExpressCard) gs.getComponentById(cardId);

        ColtExpressGameState cegs = ((ColtExpressGameState) gs);
        cegs.addBullet(targetID, card.playerID);

        if (isDjango){
            int direction = target.getCompartmentID() - player.getCompartmentID();
            if (direction == 0)
            {
                throw new IllegalArgumentException("when django shoots the player needs to be in a different " +
                        "compartment than its target");
            }
            int movementIndex;
            if (direction > 0){
                movementIndex = target.getCompartmentID() + 1;
            } else{
                movementIndex = target.getCompartmentID() - 1;
            }

            if (movementIndex > 0 && movementIndex <= cegs.getNPlayers())
            {
                Compartment movementCompartment = cegs.getTrainCompartments().get(movementIndex);

                // django's shots can move the player
                if (target.playersInsideCompartment.contains(targetID)) {
                    target.playersInsideCompartment.remove(targetID);
                    if (movementCompartment.containsMarshal) {
                        cegs.addNeutralBullet(targetID);
                        movementCompartment.playersOnTopOfCompartment.add(targetID);
                    } else
                        movementCompartment.playersInsideCompartment.add(targetID);
                }
            }
        }

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
        if (targetID != -1)
            return "Shoot player " + targetID;
        return "Player attempts to shoot but has not target available";
    }
}
