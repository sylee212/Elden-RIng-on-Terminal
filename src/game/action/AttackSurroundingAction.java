package game.action;

import java.util.List;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.Weapon;

/**
 * An Action to allow the actor to perform a surrounding attack on other Actors within the area.
 * Acts like a wrapper that wraps a lot of attack actions together
 * @author Lee Sing Yuan
 */
public class AttackSurroundingAction extends Action {

    /**
     * The direction of incoming attack. for UI purposes
     */
    private String direction;

    /**
     * Weapon used for the attack
     */
    private Weapon weapon;

    /**
     * the coordinates of the surrounding area
     */
    private List<Actor> targets;

    /**
     * if the player wants to use attack surrounding
     */
    private Actor player;

    /**
     * Constructor for enemies attack behaviour
     *
     * @param initTargets is the list of targets around the area
     * @param direction the direction where the attack should be performed (only used for display purposes)
     * @param weapon the weapon used to attack the surrounding area
     */
    public AttackSurroundingAction( List<Actor> initTargets, String direction, Weapon weapon ) {
        this.targets = initTargets;
        this.direction = direction;
        this.weapon = weapon;
    }

    /**
     * Constructor for player
     *
     * @param player the player
     * @param direction the direction where the attack should be performed (only used for display purposes)
     * @param weapon the weapon used to attack the surrounding area
     */
    public AttackSurroundingAction( Actor player, String direction, Weapon weapon ) {
        this.player = player;
        this.direction = direction;
        this.weapon = weapon;
    }

    /**
     * Approach description:
     *      1) check if weapon is null
     *              if null, use intrinsic weapon
     *              else, use weapon
     *      2) check if its player who called this class
     *              if yes, get surrounding actors around player
     *              if no, continue
     *      3) iterate through all targets, create an AttackAction for each and every target
     *              also execute the AttackAction
     *
     * @param actor The actor performing the attack action.
     * @param map The map the actor is on.
     * @return the result of the attack, e.g. whether the target is killed, etc.
     * @see AttackAction
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        if (weapon == null) {
            weapon = actor.getIntrinsicWeapon();
        }

        // to allow player to do surrounding attack
        if ( player != null ){
            targets = NearMe.getSurroundingActors(player,map,1);
        }

        // just to make the UI prettier
        String result = actor.toString() + " attacks their surrounding! \n";

        // to loop through all the targets and attack them
        for (Actor tar : targets) {
            result += new AttackAction(tar, "surrounding area",weapon).execute(actor,map) + "\n" ;
        }
        return result;
    }

    /**
     * Describes which target the actor is attacking with which weapon
     *
     * @param actor The actor performing the action.
     * @return a description used for the menu UI
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " attacks " + direction + " with " + (weapon != null ? weapon : "Intrinsic Weapon");
    }

}

