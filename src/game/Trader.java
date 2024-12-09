package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.enemy.ActorTypes;
import game.enemy.Roles;
import game.weapon.*;


/**
 * A class for trader
 * All trading will be instantiated by player
 */
public class Trader extends Actor {
    /**
     * The name of the merchants
     */
    public static final String nameMerchantKale = "Merchant Kale";
    public static final String nameFingerReaderEnia = "Finger Reader Enia";


    /**
     * Constructor.
     *
     */
    public Trader(String initName , char initDisplayChar , int initHp) {
        super(initName,initDisplayChar,initHp);
        this.addCapability(ActorTypes.TRADER);
        this.addCapability(Roles.NEUTRAL);


    }

    /**
     * Gets merchantKale that players can buy things from
     * @return merchant Kale
     */
    public static Trader getMerchantKale(){
        Trader t =  new Trader(nameMerchantKale, 'K', 999);

        // add the weapons that can be bought by player here
        t.addWeaponToInventory(new Uchigatana());
        t.addWeaponToInventory(new GreatKnife());
        t.addWeaponToInventory(new Club());
        t.addWeaponToInventory(new Scimitar());
        t.addWeaponToInventory(new HeavyCrossbow());
        t.addWeaponToInventory(new AstrologerStaff());


        return t;

    }

    /**
     * Get finger reader enia which players cannot buy from but can sell to
     *
     * Apporach description:
     *      as long as this trader does not have any weapons,
     *      it would never invoke any weapon's get allowable actions
     *      which will never give the player the option to buy weapons from this trader
     *
     * @return Finger Reader Enia
     */
    public static Trader getFingerReaderEnia(){
        Trader t = new Trader(nameFingerReaderEnia, 'E', 999);
        return t;
    }


    /**
     * At each turn, do nothing
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return the valid action that can be performed in that iteration or null if no valid action is found
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        return new DoNothingAction();
    }

    /**
     * The trader can only be interacting with the actor with PLAYER capability
     *
     * Apporach description:
     *      1) gets the weapons in inventory
     *      2) tick all weapons and get their allowable actions
     *      3) return them to player
     *
     * @param otherActor    the Actor that might be performing attack
     * @param direction     String representing the direction of the other Actor
     * @param map           current GameMap
     * @return ActionList   the list of available actions that can be done by the player
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {

        ActionList actions = new ActionList();
        // to tick every item
        // because if the weapon has not gone perform tick yet
        // they cannot get the location and they cannot
        // getAllowableActions
        for ( WeaponItem w : this.getWeaponInventory() )
        {
            w.tick(map.locationOf(this),this);
            actions.add(w.getAllowableActions());
        }
        return actions;
    }
}
