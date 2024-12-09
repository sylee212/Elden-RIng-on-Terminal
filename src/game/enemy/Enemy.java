package game.enemy;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.Application;
import game.RandomNumberGenerator;
import game.ResetManager;
import game.Resettable;
import game.action.*;
import game.behaviour.AttackBehaviour;
import game.behaviour.Behaviour;
import game.behaviour.FollowBehaviour;
import game.behaviour.WanderBehaviour;
import game.weapon.WeaponStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * The parent class for the enemy
 */
public abstract class Enemy extends Actor implements Resettable {

    private GameMap map = null;

    // all the behaviours
    protected final Map<Integer, Behaviour> behaviours = new HashMap<>();
    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     */
    public Enemy(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);

        // to add enemies capabilities
        this.addCapability(Roles.ENEMIES);

        // putting in all the behaviours
        behaviours.put(FollowBehaviour.behaviorCode(), new FollowBehaviour());
        behaviours.put(AttackBehaviour.behaviorCode(), new AttackBehaviour());
        behaviours.put(WanderBehaviour.behaviorCode(), new WanderBehaviour());

        // making all enemies ressettable
        ResetManager.registerResettable(this);
    }

    /**
     * To give the player an intrinsic weapon choice
     *
     * @param otherActor    the Actor that might be performing attack
     * @param direction     String representing the direction of the other Actor
     * @param map           current GameMap
     * @return ActionList   the list of available actions that can be done by the player
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();

        // to only allow player to use this function
        if (otherActor.hasCapability(ActorTypes.PLAYER)) {
            // adding the intrinsic weapon choice
            actions.add(new AttackAction(this, direction));
        }
        return actions;
    }


    /**
     * At each turn, select a valid action to perform.
     *
     * Approach description:
     *      1) check if the actor has the FollowBehaviour
     *              1.1) if yes, check if can follow
     *      2) check if the actor has the AttackBehaviour
     *              2.1) if yes, check if can attack anyone
     *      3) check if actor can despawn
     *              3.1) if yes, despawn
     *      4) if actor was not despawned, check if actor has the WanderBehaviour
     *              4.1) if yes, roam around the map
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return the valid action that can be performed in that iteration or null if no valid action is found
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {

        if (this.map == null) {
            this.map = map ;
        }

        if (Application.staticGameMap == this.map) {        
            // because to use weapons' get allowableActions,
            // it needs to know the current locations
            // so need to tick first
            for ( WeaponItem w : this.getWeaponInventory() )
            {
                if ( w.hasCapability(WeaponStatus.HAVE_NOT_TICKED) ) {
                    w.tick(map.locationOf(this), this);
                    actions.add(w.getAllowableActions());
                }
            }

            // because to use items' get allowableActions,
            // it needs to know the current locations
            // so need to tick first
            for ( Item i: this.getItemInventory() )
            {
                if ( i.hasCapability(WeaponStatus.HAVE_NOT_TICKED) ) {
                    i.tick(map.locationOf(this), this);
                    actions.add(i.getAllowableActions());
                }
            }

            // follow has the highest precedence
            if(behaviours.containsKey(FollowBehaviour.behaviorCode())){
                Action action = behaviours.get(FollowBehaviour.behaviorCode()).getAction(this, map);

                // if the behaviour exist but cant do anything like follow anyone or player
                // it will return null so that can execute other behaviors
                if (action != null) {
                    return action;
                }
            }

            // attack has the second-highest precedence
            if(behaviours.containsKey(AttackBehaviour.behaviorCode())){
                Action action = behaviours.get(AttackBehaviour.behaviorCode()).getAction(this, map);

                // if the behaviour exist but cant do anything like attack anyone,
                // it will return null so that can execute other behaviors
                if (action != null) {
                    return action;
                }
            }

            // check if can despawn
            if (RandomNumberGenerator.getRandomInt(100) < 10) {
                return new DespawnAction(this);
            }

            // wander is the lowest precedence
            if(behaviours.containsKey(WanderBehaviour.behaviorCode()))
            {
                Action action = behaviours.get(WanderBehaviour.behaviorCode()).getAction(this, map);
                if (action != null) {
                    return action;
                }
            }

            return new DoNothingAction();
        } else {
            // check if can despawn
            if (RandomNumberGenerator.getRandomInt(100) < 10) {
                return new DespawnAction(this);
            }
            return new DoNothingAction();
        } 
    }

    /**
     * A function to reset the enemy class. 
     */
    public void reset(GameMap map) {
        //add despawn action
        behaviours.clear();
        DespawnAction despawnAction = new DespawnAction(this);
        despawnAction.execute(this, map);
    }
}
