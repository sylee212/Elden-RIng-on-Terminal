package game.rune;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.items.PickUpAction;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * This lucky player found a big amount of Rune on the ground. 
 * He faster pick up the rune without anyone realised. 
 * Created by: Yeoh Ming Wei
 * @author Yeoh Ming Wei
 */
public class PickUpRuneAction extends PickUpAction {

    /**
     * The rune.
     */
    private final Item item;
    
    /**
     * The constructor for PickUpRuneAction class. 
     * @param item
     */
    public PickUpRuneAction(Item item) {
        super(item) ;
        this.item = item ;
    }

    /**
     * A function to execute the action. 
     * It will generate a random amount of rune and add into the players rune.
     * It will also remove the item on the ground. 
     */
    public String execute(Actor actor, GameMap map) {
        int value = RuneManager.dropRuneAmount.get(item) ;

        RuneManager.getInstance().addRune(actor, value) ;

        map.locationOf(actor).removeItem(item);
        
        return String.format("%s pick up the rune with a value of %d", actor, value) ;

        
    }

    /**
     * A function to show a menu description which player can retrieve a rune with its amount. 
     */
    @Override
    public String menuDescription(Actor actor) {
        int value = RuneManager.dropRuneAmount.get(item) ;
        return String.format("%s retrieves Rune (value: %d)", actor, value) ;
    }
    

}
