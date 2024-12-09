package game.rune ;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.items.PickUpAction;
import edu.monash.fit2099.engine.positions.GameMap;
import game.ResetManager;
import game.Resettable;
import game.Status;
import game.player.Player;

/**
 * The currency of the game to buy weapons from the trader. 
 * It can be obtain through killing enemies. 
 * @author Yeoh Ming Wei
 */
public class Rune extends Item implements Resettable {

    /**
     * An integer attribute to represents the amount of rune available.
     */
    private int rune ;
    private int x ;
    private int y ;
    private Player player ;


    /**
     * A constructor for rune class.
     */
    public Rune() {
        super("Rune", '$', false) ;
        player = Player.getInstance() ;
        ResetManager.registerResettable(this);
    }


    /**
     * A setter method to set the rune value.
     * @param value The value that will replaced the rune. 
     */
    public void setRune(int value) {
        this.rune = value ;
    }

    /**
     * A getter method to get the rune value.
     * @return An integer represents the amount of rune.
     */
    public int getRune() {
        return this.rune ;
    }

    /**
     * A method to set the location of the rune.
     * @param x
     * @param y
     */
    public void setLocation(int x, int y) {
        this.x = x ;
        this.y = y ;
    }

    /**
     * A method to show an action that allows player to pick up the rune
     */
    @Override
	public PickUpAction getPickUpAction(Actor actor) {
		return new PickUpRuneAction(this);
	}

    /**
     * A method to remove the rune only if the player died. 
     * The rune will not remove when the player rest at Site Of Lost Grace. 
     */
    @Override
    public void reset(GameMap map) {
        if(!player.hasCapability(Status.RESTING)) {
            map.at(x, y).removeItem(this) ;
        }
    }

    
}