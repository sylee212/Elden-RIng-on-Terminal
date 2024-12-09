package game.action;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.FancyMessage;
import game.ResetManager;

/**
 * A reset action is an action that performs full reset of the game
 * Created by: Yeoh Ming Wei
 * @author Yeoh Ming Wei
 */
public class ResetAction extends Action {

    /**
     * A function to execute the action. 
     * Perform a full reset for the whole game.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        ResetManager.run(map) ;
        return menuDescription(actor) ;
    }

    /**
     * A method to show description which player died. 
     */
    @Override
    public String menuDescription(Actor actor) {
        return FancyMessage.YOU_DIED ;
    }
    
}
