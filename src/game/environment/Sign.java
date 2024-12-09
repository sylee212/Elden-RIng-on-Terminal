package game.environment;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.action.SummonAction;

/**
 * You realised that there is a weird crappy sign with a magic cirle on it. 
 * What will happen if you do a summon with your magic power?
 * Created by: Yeoh Ming Wei
 * @author Yeoh Ming Wei
 */
public class Sign extends Ground {

    /**
     * A constructor for sign class. 
     */
    public Sign() {
        super('=');
    }

    /**
     * Return allowable actions when the player is near the sign. 
     */
    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction){
        ActionList actions = new ActionList();

        actions.add(new SummonAction(location)) ;
		return actions ;
	}


    
}
