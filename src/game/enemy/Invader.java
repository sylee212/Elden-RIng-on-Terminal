package game.enemy;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.RandomNumberGenerator;
import game.ResetManager;
import game.Resettable;
import game.action.AttackAction;
import game.action.DespawnAction;
import game.behaviour.AttackBehaviour;
import game.behaviour.Behaviour;
import game.behaviour.FollowBehaviour;
import game.behaviour.WanderBehaviour;
import game.player.PlayerRole;
import game.player.RoleManager;
import game.rune.RuneManager;
import game.weapon.WeaponStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * The invader class
 *
 * Note: use getInstance()
 * @author Lee Sing Yuan
 */
public class Invader extends Actor implements Resettable {
    protected final Map<Integer, Behaviour> behaviours = new HashMap<>();

    /**
     * The runes that this actor can drop
     */
    private final int INVADER_MIN_RUNE = 1358  ;
    private final int INVADER_MAX_RUNE = 5578  ;

    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param hitPoints   the Actor's starting hit points
     */
    private Invader(String name, int hitPoints) {
        super(name, 'ඞ', hitPoints);

        // to add the respective capabilities
        this.addCapability(ActorTypes.INVADER);
        this.addCapability(Roles.ENEMIES);

        // putting in all the behaviours
        behaviours.put(FollowBehaviour.behaviorCode(), new FollowBehaviour());
        behaviours.put(AttackBehaviour.behaviorCode(), new AttackBehaviour());
        behaviours.put(WanderBehaviour.behaviorCode(), new WanderBehaviour());

        // making this enemy resettable
        ResetManager.registerResettable(this);

        // adding to the rune manager
        RuneManager.addEnemyDropRune(name, INVADER_MIN_RUNE, INVADER_MAX_RUNE);
    }

    /**
     * The method to obtain an instance of this class,
     *
     * Note: the reason we need this method is because, the constructor has different parameters,
     *      so we need another level before calling the constructor which will decide the parameters
     *      Also, needs to be instantiated after player cause of the role manager instantiation inside
     *
     * @return an Invader instance
     */
    public static Invader getInvaderInstance(){
        // to select a random role
        Random random = new Random();
        int choice = random.nextInt(RoleManager.playerRoles.size());

        // get the role information
        PlayerRole wantedRole = RoleManager.playerRoles.get(choice);

        // create the player
        Invader invader = new Invader("Invader: " + wantedRole.getName(),wantedRole.getHp());

        // adding the weapons,items and capabilities of the role
        RoleManager.addCapabilityItemWeapon(invader,wantedRole);

        return invader;
    }

    /**
     * do nothing
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

        // checks if can follow
        if(behaviours.containsKey(FollowBehaviour.behaviorCode())){
            Action action = behaviours.get(FollowBehaviour.behaviorCode()).getAction(this, map);

            // if the behaviour exist but cant do anything like follow anyone or player
            // it will return null so that can execute other behaviors
            if (action != null) {
                return action;
            }
        }

        // if cannot follow, check if can attack
        if(behaviours.containsKey(AttackBehaviour.behaviorCode())){
            Action action = behaviours.get(AttackBehaviour.behaviorCode()).getAction(this, map);

            // if the behaviour exist but cant do anything like attack anyone,
            // it will return null so that can execute other behaviors
            if (action != null) {
                return action;
            }
        }

        if (RandomNumberGenerator.getRandomInt(100) < 10) {
            return new DespawnAction(this);
        }

        // check if can roam
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
        //add despawn action
        behaviours.clear();
        DespawnAction despawnAction = new DespawnAction(this);
        despawnAction.execute(this, map);

    }
}
