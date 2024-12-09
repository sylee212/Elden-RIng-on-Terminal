package game.weapon;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.Application;
import game.action.AttackSurroundingAction;

import java.util.ArrayList;
import java.util.List;

/**
 * A utils class for all the weapons that can do Attack Surrounding Action
 * @author Lee Sing Yuan
 */
public class GetAllowableActions {
    /**
     * To make the weapons return all the possible actions that can be done
     * applies open close principle
     * Approach decription:
     *      1) use currentLocation which is updated by tick
     *      2) check if there is someone at the same location as the weapon
     *      3) if there is someone, proceed
     *              else, return nothing
     *      4) checks surrounding
     *      5) if it has an actor
     *              check if there is a target that the attacker can use the skill on
     *                  if can, get the list of targets around this attacker
     *
     * Assumption: needs tick to be executed at least once in order to have the available actions
     *
     * @param currentLocation the current location of the weapon which is also the wielder
     * @param w the weapon used
     * @return a list of actions that the wielder can do with this weapon
     *          list is empty if
     *              this weapon is on ground and not held by anyone
     *              there is no one around
     */
    public static List<Action> getSurroundingAttackAllowableActions(Location currentLocation, WeaponItem w){
        // the resulting list of actions
        List<Action> res = new ArrayList<>();

        Boolean isSkill = false;

        // checking to see if the weapon is held by someone or not
        // if weapon is on the ground and player is on top of it
        //      means player cannot use the actions which this weapon can give
        //      can only pick up
        // if weapon is on the ground and player is not on it
        //      means should not be able to attack anyone or give the list of actions
        Actor whoHasThis = Application.staticGameMap.getActorAt(currentLocation);
        if ( whoHasThis == null )
        {
            return res;
        }

        List<Actor> targets;
        // checks all locations around me
        // first check if the there is an attackable enemy around this actor
        isSkill = canSkill(whoHasThis,currentLocation);

        // if there is, get all the actors around this actor
        targets = getTargets(isSkill,currentLocation);

        // adding the attack surrounding actions after getting all the actors
        if ( targets.size() > 0 ) {
            res.add(new AttackSurroundingAction(targets, "attacks surrounding", w));
        }

        return res;
    }

    /**
     * Checks if there is an actor of different type so that the attacker can use the skill
     *
     * Apporach description:
     *      1) get surrounding
     *      2) check if there is an actor
     *             2.1) if there is an actor
     *                  2.1.1) check if the actor and the attacker can attack each other
     *                      2.1.1.1) if can, return true
     *
     * @param whoHasThis the attacker
     * @param currentLocation the location of the attacker
     * @return true, if there is an actor of different type in the attacker's surrounding
     *          false, if there is no actor of different type in the attacker's surrounding
     */
    public static boolean canSkill(Actor whoHasThis , Location currentLocation ){
        boolean isSkill = false;

        // check the surrounding
        for (Exit exit : currentLocation.getExits() ){
            Location l = exit.getDestination();

            // check if this location has an actor
            if (l.containsAnActor()){

                // get that actor
                Actor target = l.getActor();

                // check that the actor is attackable by whoHasThis ( attacker )
                if ( isValid.isValidRole(whoHasThis,target) && isValid.isValidActorType(whoHasThis,target) )
                {
                    // the moment we found someone who is different, means we can use the skill
                    isSkill = true;
                    break;
                }
            }
        }
        return isSkill;
    }

    /**
     * If can use the skill, get all the actors around this position
     *
     * Apporach description:
     *      1) check if the skill can be activated
     *      2) get the surrounding
     *      3) check if it contains an actor
     *              3.1) if contains an actor, add to resulting list
     *
     * @param isSkill whether can skill or not
     * @param currentLocation the current position of the attacker
     * @return list of actors around the attacker
     *          list is empty if
     *               skill cannot be done
     *               no actor around
     */
    public static List<Actor> getTargets(boolean isSkill, Location currentLocation){
        List<Actor> targets = new ArrayList<>();
        if ( isSkill ) {
            for (Exit exit : currentLocation.getExits()) {
                Location l = exit.getDestination();

                // if it has an actor and it is attackable
                if (l.containsAnActor()) {

                    // get that actor and add the skill action and normal action to the person holding this
                    Actor target = l.getActor();
                    targets.add(target);
                }
            }
        }
        return targets;
    }
}
