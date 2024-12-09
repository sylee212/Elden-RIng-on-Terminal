package game.consume;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.player.Player;

/**

 * The ConsumeAction class represents an action where an actor consumes a consumable item.
 * It extends the Action class.
 * Created by: Loo Li Shen
 * @author Loo Li Shen
 * Modified by: Yeoh Ming Wei
 */

public class ConsumeAction extends Action {
    private ConsumeItem consumable;
    private int action;
    private String verb;
    private String actionName;
    private int usesLeft ;

    /**
     * Constructs a new ConsumeAction object.
     *
     * @param consumable  the consumable item to be consumed
     * @param action      the action value
     * @param verb        the verb describing the action
     * @param actionName  the name of the action
     * @param usesLeft    the number of uses remaining for the consumable item
     */
    public ConsumeAction(ConsumeItem consumable, int action, String verb, String actionName, int usesLeft) {
        this.consumable = consumable;
        this.action = action;
        this.verb = verb;
        this.actionName = actionName;
        this.usesLeft = usesLeft;
    }

    /**
     * Perform the Action.
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return a description of what happened that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Player player = Player.getInstance();

        //call ConsumeItem.use here
        consumable.use(player);
        consumable.setUsesLeft(consumable.getUsesLeft() - 1);

        // if after -1 and the uses left is 0 then the item will get removed from inventory
        if (consumable.getUsesLeft() == 0) {
            player.removeItemFromInventory(consumable);
        }

        String result = this.consumable + " " + this.verb + " " + this.action + this.actionName;
        return result;


    }

    /**
     * Returns a descriptive string
     *
     * @param actor The actor performing the action.
     * @return the text we put on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " use " + this.consumable + " and have " + this.usesLeft + " left ";
    }
}
