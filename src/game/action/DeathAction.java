package game.action;

import game.Status;
import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.FancyMessage;
import game.ResetManager;
import game.enemy.ActorTypes;
import game.rune.DropRuneAction;
import game.rune.RuneManager;

/**
 * An action executed if an actor is killed.
 * Created by:
 * @author Adrian Kristanto
 * Modified by: Yeoh Ming Wei
 *
 */
public class DeathAction extends Action {

    /**
     * An actor which is the attacker.
     */
    private Actor attacker ;

    /**
     * An actor which is already dead.
     */
    private Actor deadBody ;

    /**
     * A constructor for DeathAction class. 
     * @param actor An actor which is the attacker.
     * @param deadBody An actor which is already dead.
     */
    public DeathAction(Actor actor, Actor deadBody) {
        this.attacker = actor ;
        this.deadBody = deadBody ;
    }

    /**
     * When the target is killed, the items & weapons carried by target
     * will be dropped to the location in the game map where the target was
     * 
     * If the target is player, it will reset the game and drop his runes. 
     *
     * @param target The actor performing the action.
     * @param map The map the actor is on.
     * @return result of the action to be displayed on the UI
     */
    @Override
    public String execute(Actor target, GameMap map) {
        String result = "";

        ActionList dropActions = new ActionList();
        // drop all items
        if(!target.hasCapability(ActorTypes.PLAYER)) {
            for (Item item : target.getItemInventory())
                dropActions.add(item.getDropAction(target));
            for (WeaponItem weapon : target.getWeaponInventory())
                dropActions.add(weapon.getDropAction(target));
            for (Action drop : dropActions)
                drop.execute(target, map);
            if (attacker.hasCapability(ActorTypes.PLAYER))
                RuneManager.getInstance().addRuneEnemy(attacker, deadBody);
            map.removeActor(target);
            result += System.lineSeparator() + menuDescription(target);
            return result;
        } else {
            
            target.addCapability(Status.DEAD) ;
            ResetManager.run(map) ;
            result = new DropRuneAction().execute(target, map) + '\n' + FancyMessage.YOU_DIED;
        
        }
        return result;
    }

    /**
     * A method to show description which an actor is killed.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " is killed.";
    }
}
