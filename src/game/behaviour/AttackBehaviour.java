package game.behaviour;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.action.NearMe;
import game.action.AttackAction;
import game.weapon.WeaponSkill;
import game.weapon.isValid;
import java.util.Random;


import java.util.List;

/**
 * Decides whether an enemy should attack another actor or not
 * @author Lee Sing Yuan
 *
 */
public class AttackBehaviour implements Behaviour {
    private final Random random = new Random();
    private int skillChance = 50;

    /**
     * Decides whether an enemy should attack another actor or not
     *
     * Approach description:
     *      1) check if the attacker can perform a skill
     *          1.1) if can set skill to true
     *      2) if the skill is true, get allowable actions of that weapon
     *      3) if the attacker cannot perform skill, check if got weapon with no skill
     *          3.1) if have, then get the allowable actions
     *          3.2) choose random action and return
     *      4) if no weapon, perform an intrinsic attack to anyone that is valid within range
     *
     * Note: There are 3 scenarios:
     *              1) enemy has weapon with skill
     *              2) enemy has weapon with no skill
     *              3) enemy has no weapon
     *
     * @param actor the Actor acting
     * @param map   the GameMap containing the Actor
     * @return  Action, that actor can perform,
     *          null, if actor can't do this.
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {

        // to attack using weapons with skills //
        // used to check if the actor can use a weapon with a skill
        boolean canSkill = false;

        // checks if can the actor has a weapon with a skill
        for ( WeaponSkill type : WeaponSkill.values() ) {

            // if there is skill capability that the actor has
            // means that the actor can perform the skill
            if (actor.hasCapability(type))
            {
                canSkill = true;
            }
        }

        // if the actor has the capability to use a skill, then check if he wants to use the skill
        if ( canSkill == true ) {

            // check if the skill chance can execute
            if (random.nextInt(100) < skillChance) {

                // get the weapon skills that can be done
                WeaponItem w = actor.getWeaponInventory().get(0);
                List<Action> listOfActions = w.getAllowableActions();

                // to check if the weapon can give any actions
                // if cannot give, go to normal attack
                // actually can straight away return null, since if there is nothing the sword can do
                // means no attacking can be done
                if ( listOfActions.size() > 0 ){

                    // cause attackSurroundingActions is always first
                    return listOfActions.get(0);
                }
            }
        }

        // attack using weapons without skills //
        if( actor.getWeaponInventory().size() > 0 ) {
            WeaponItem w = actor.getWeaponInventory().get(0);
            List<Action> listOfActions = w.getAllowableActions();

            // must check if can do any attacks
            // cause list will be 0 if there is no one to attack
            if ( listOfActions.size() > 0 ){
                Action res = listOfActions.get( random.nextInt(listOfActions.size()) );
                return res;
            }
        }


        // to attack using intrinsic weapons //
        List<Actor> targets = NearMe.getSurroundingActors(actor,map,1);

        // if an enemy has the skill but decides not to use the skill, can choose to attack
        // individual targets
        // this is to get a random action
        if (!targets.isEmpty()) {

            // loop until no more targets or return an AttackAction with an attackable target
            while (targets.size() > 0) {
                // because we use random, sometimes the target can be of the same type and this makes the
                // enemy not attack and perform other behaviours instead
                Actor target = targets.get(random.nextInt(targets.size()));

                // after deciding who to attack, return a new AttackAction
                if (isValid.isValidRole(actor, target) && isValid.isValidActorType(actor, target)) {

                    // _ means no direction
                    return new AttackAction(target, "_",
                            actor.getWeaponInventory().size() > 0 ? actor.getWeaponInventory().get(0) : actor.getIntrinsicWeapon());
                } else {
                    // to remove the target
                    // because if cannot attack means the whole while loop will run until its empty
                    targets.remove(target);
                }
            }
        }

        return null;
    }

    public static int behaviorCode(){
        return 111;
    }

}
