package game.allies;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.ResetManager;
import game.Resettable;
import game.Status;
import game.action.DespawnAction;
import game.behaviour.AttackBehaviour;
import game.behaviour.Behaviour;
import game.behaviour.WanderBehaviour;
import game.enemy.ActorTypes;
import game.enemy.Roles;
import game.player.Player;
import game.player.PlayerRole;
import game.player.RoleManager;
import game.weapon.WeaponStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * An ally class that can have one of the roles that player can have
 *
 * Note: to call this class, use getInstance()
 * @author Lee Sing Yuan
 */
public class Ally extends Actor implements Resettable {
    protected final Map<Integer, Behaviour> behaviours = new HashMap<>();
    private Player player = Player.getInstance() ;
    /**
     * Constructor to create the ally
     *
     * @param name        the name of the Actor
     * @param hitPoints   the Actor's starting hit points
     */
    private Ally(String name, int hitPoints) {
        super( name, 'A', hitPoints);

        // to add the respective capabilities
        this.addCapability(ActorTypes.ALLY);
        this.addCapability(Roles.ALLIES);

        // putting in all the behaviours
        behaviours.put(AttackBehaviour.behaviorCode(), new AttackBehaviour());
        behaviours.put(WanderBehaviour.behaviorCode(), new WanderBehaviour());

        // to make this actor resettable
        ResetManager.registerResettable(this);
    }

    /**
     * The method to obtain an instance of this class,
     *
     * Note: the reason we need this method is because, the constructor has different parameters,
     *      so we need another level before calling the constructor which will decide the parameters
     *      Also, needs to be instantiated after player cause of the role manager instantiation inside
     *
     * @return an Ally instance
     */
    public static Ally getAllyInstance(){
        // to select a random role
        Random random = new Random();
        int choice = random.nextInt(RoleManager.playerRoles.size());

        // get the role information
        PlayerRole wantedRole = RoleManager.playerRoles.get(choice);

        // create the player
        Ally ally = new Ally("Ally: " + wantedRole.getName(),wantedRole.getHp());

        // add the weapons, items and capabilities of that role
        RoleManager.addCapabilityItemWeapon(ally,wantedRole);

        return ally;
    }

    /**
     * Not used
     *
     * @param otherActor    the Actor that might be performing attack
     * @param direction     String representing the direction of the other Actor
     * @param map           current GameMap
     * @return ActionList   the list of available actions that can be done by the player
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();
        return actions;
    }


    /**
     * At each turn, select a valid action to perform.
     *
     * Approach description:
     *      1) check if the actor has the AttackBehaviour
     *              1.1) if yes, check if can attack anyone
     *      2) check if actor has the WanderBehaviour
     *              2.1) if yes, roam around the map
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return the valid action that can be performed in that iteration or null if no valid action is found
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {

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

        // checks if can attack
        if(behaviours.containsKey(AttackBehaviour.behaviorCode())){
            Action action = behaviours.get(AttackBehaviour.behaviorCode()).getAction(this, map);

            // if the behaviour exist but cant do anything like attack anyone,
            // it will return null so that can execute other behaviors
            if (action != null) {
                return action;
            }
        }

        // wander is the lowest precedence
        // checks if giant crab has this behaviour
        if(behaviours.containsKey(WanderBehaviour.behaviorCode()))
        {
            Action action = behaviours.get(WanderBehaviour.behaviorCode()).getAction(this, map);
            if (action != null) {
                return action;
            }
        }

        return new DoNothingAction();
    }

    public void reset(GameMap map) {
        if(!player.hasCapability(Status.RESTING)) {
            //add despawn action
            behaviours.clear();
            DespawnAction despawnAction = new DespawnAction(this);
            despawnAction.execute(this, map);
        }

    }
}
