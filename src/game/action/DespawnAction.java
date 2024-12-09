package game.action;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * An action to despawn the actor.
 * @author Yeoh Ming Wei
 */
public class DespawnAction extends Action {

    /**
     * An actor that will despawn
     */
    private Actor actor ;

    /**
     * A constructor for DespawnAction. 
     * @param actor The actor that will despawn 
     */
    public DespawnAction(Actor actor) {
        this.actor = actor ;
    }

    /**
     * This method will remove an actor from the game map.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
            map.removeActor(actor);
            return System.lineSeparator() + menuDescription(actor);
    }

    /**
     * A method to show a description stating that an actor has been removed from the map.
     */
    public String menuDescription(Actor actor) {
        return actor + " removed from the map.";
    }
}


