package game.consume;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.PickUpAction;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * A pick up action catered for consumable items. It is better compared
 * to PickUpItemAction because the player can know the amount of item
 * available in his inventory. 
 * Created by: Yeoh Ming Wei
 * @author Yeoh Ming Wei
 */
public class PickUpConsumeItemAction extends PickUpAction {

    /**
     * A consumable item. 
     */
    ConsumeItem item ;

    /**
     * A constructor for PickUpConsumeItemAction class. 
     * @param item The consumable item
     */
    public PickUpConsumeItemAction(ConsumeItem item) {
        super(item);
        this.item = item ;
    }

    /**
     * A function to execute the action. 
     * Check if the inventory contains the item, if the item is
     * not in the inventory, it will add. Otherwise, it will
     * increase the amount of item.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        if(!actor.getItemInventory().contains(item)) {
		    actor.addItemToInventory(item);
        } 
        map.locationOf(actor).removeItem(item);
        item.setUsesLeft(item.getUsesLeft() + 1);

		return String.format("%s picked up %s", actor, item) ;
	}
}
