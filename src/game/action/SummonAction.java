package game.action;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.RandomNumberGenerator;
import game.allies.Ally;
import game.enemy.Invader;

/**
 * A summon action is an action that allows player to summon an Ally. It can
 * be called when the player is near the summon sign ground. 
 * Created by: Yeoh Ming Wei
 * @author Yeoh Ming Wei
 */
public class SummonAction extends Action {

    private Location groundLocation ;
    /**
     * A constructor for SummonAction class. 
     */
    public SummonAction(Location location) {
        groundLocation = location ;
    }

    /**
     * A function to execute the action.
     * A new ally will be summoned at the surrounding area of the summon sign.
     * The summon sign will not work if its surrounding area is occupied.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        int p = RandomNumberGenerator.getRandomInt(100) ;

        for (Exit exit : groundLocation.getExits()) {
            Location destination = exit.getDestination() ;

            if (!destination.containsAnActor()) {
                if (p < 50) {
                    Ally ally = Ally.getAllyInstance() ;
                    destination.addActor(ally) ;
                    return String.format("%s summoned %s", actor, ally) ;
                } else {
                    Invader invader = Invader.getInvaderInstance() ;
                    destination.addActor(invader) ;
                    return String.format("%s summoned %s", actor, invader) ;
                }
            }
        }
        return "The surrounding of summon sign is occupied." ;
        
    }

    /**
     * A method to show description about player is allowed to summon Ally at summon sign. 
     */
    @Override
    public String menuDescription(Actor actor) {
        return String.format("%s perform summon at Summon Sign.", actor) ;
    }
    
}
