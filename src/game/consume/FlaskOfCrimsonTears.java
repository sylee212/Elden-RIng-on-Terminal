package game.consume;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.ResetManager;
import game.Resettable;
import game.player.Player;
import game.weapon.WeaponStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * The FlaskOfCrimsonTears class represents a potion item called "Flask Of Crimson Tears" that can be consumed by actors in the game.
 * It extends the ConsumeItem class and implements the Resettable interface.
 * Created by: Loo Li Shen
 * @author Loo Li Shen
 * Modified by: Yeoh Ming Wei
 */

public class FlaskOfCrimsonTears extends ConsumeItem implements Resettable {

    /**
     * An integer represents the healing amount when Flask of Crimson Tears
     * is used. 
     */
    private int healAmount = 250;

    /**
     * The max capacity of Flask of Crimson Tears
     */
    private int maxCapacity = 2 ;

    /**
     * Initial initialization for Flask of Crimson Tears, it is set to null.
     */
    private static FlaskOfCrimsonTears flaskOfCrimsonTears = null ;

    /**
     * Constructor for Flask of Crimson Tears.
     */
    protected FlaskOfCrimsonTears() {
        super("Flask Of Crimson Tears", 'C', false, 2) ;

        
        this.addCapability(WeaponStatus.HAVE_NOT_TICKED);
    }

    /**
     * A method to assigned new FlaskOfCrimsonTears class
     * @return a FlaskOfCrimsonTears object
     */
    public static FlaskOfCrimsonTears getInstance() {
        ResetManager.registerResettable(flaskOfCrimsonTears) ;
        
        if(flaskOfCrimsonTears == null) {
            flaskOfCrimsonTears = new FlaskOfCrimsonTears() ;
        }

        return flaskOfCrimsonTears ;
    }

    /**
     * A method to perform an action every game turn. 
     * @param currentLocation The location of the actor carrying this Item.
     * @param actor The actor carrying this Item.
     */
    @Override
    public void tick(Location currentLocation, Actor actor) {
        this.removeCapability(WeaponStatus.HAVE_NOT_TICKED);
    }

    /**
     * A method to use the item. (Heal the player's HP by 250)
     * @param actor the player who is using the potion
     */
    @Override
    public void use(Actor actor) {
        actor.heal(healAmount);
    }

    public void addMaxCapacity(int value) {
        maxCapacity += value ;
    }

    /**
     * Returns a list of allowable actions for the flask.
     *
     * @return a list of allowable actions
     */
    @Override
    public List<Action> getAllowableActions(){

        List<Action> res = new ArrayList<>();
        Player player = Player.getInstance() ;
        
        if (player.getItemInventory().contains(this)) {
            res.add(new ConsumeAction(this, 250, "Heal for", " Health", super.getUsesLeft())) ;
        }
        
        return res;
    }

    /**
     * Resets the flask amount by its maximum capacity.
     *
     * @param map the game map
     */
    @Override
    public void reset(GameMap map) {
        setUsesLeft(maxCapacity);
    }
}
