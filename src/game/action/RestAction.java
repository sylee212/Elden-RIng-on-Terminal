package game.action;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.ResetManager;
import game.Status;

/**
 * A rest action is an action that performs partial reset of the game
 * Created by: Yeoh Ming Wei
 * @author Yeoh Ming Wei
 */
public class RestAction extends Action {

    /**
     * A function to execute the action. 
     * Perform a parital reset for the whole game.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        actor.addCapability(Status.RESTING);
        ResetManager.run(map) ; 
        return menuDescription(actor) ;

    }

    /**
     * A method to show description which rest at Site of Lost Grace. 
     */
    @Override
    public String menuDescription(Actor actor) {
        return String.format("%s rests at Site of Lost Grace", actor) ;
    }
    
}
