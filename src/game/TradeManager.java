package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.action.NearMe;
import game.action.PurchaseAction;
import game.action.SellAction;
import game.action.TradeItemAction;
import game.enemy.ActorTypes;

import java.util.ArrayList;
import java.util.List;

/**
 * A utils class for trading
 * @author Lee Sing Yuan
 */
public class TradeManager {

    /**
     * Function that is called by player who can sell to trader
     * Approach description:
     *      1) check if the person who holds this weapon is player or not
     *      2) if it is player, get all the trader locations around this player if possible
     *      3) for each trader, add a sell action to that trader
     *
     * Note: only called by player
     * @param whoHasThis player
     * @param w a new instance of the weapon item
     * @param sellingPrice the selling price of the weapon
     * @return a list of selling actions to all possible traders
     *         list is of length 0 if no possible actions are possible
     */
    public static List<Action> getSellingAction(Actor whoHasThis , WeaponItem w , int sellingPrice ){

        List<Action> res = new ArrayList<>();

        // means if not player, cannot sell
        if ( !whoHasThis.hasCapability(ActorTypes.PLAYER) ){
            return res;
        }

        // gets list of traders
        ArrayList<Location> listOfTraderLocation = NearMe.isSpecificActorTypeInMyRange(whoHasThis,Application.staticGameMap,1,ActorTypes.TRADER);

        // if there is no trader will loop 0 times
        for ( Location l : listOfTraderLocation ){
            Actor trader = Application.staticGameMap.getActorAt(l);
            res.add(new SellAction(trader,w,sellingPrice));
        }
        return res;
    }

    /**
     * Function that is called by player who can sell to trader
     * Approach description:
     *      1) check if the person who holds this weapon is player or not
     *      2) if it is player, get all the trader locations around this player if possible
     *      3) for each trader, add a sell action to that trader
     *
     * Note: only called by player
     * @param whoHasThis player
     * @param i a new instance of the item
     * @param sellingPrice the selling price of the item
     * @return a list of selling actions to all possible traders
     *          list is of length 0 if no possible actions are possible
     */
    public static List<Action> getSellingAction(Actor whoHasThis , Item i , int sellingPrice ){

        List<Action> res = new ArrayList<>();

        // means if not player, cannot sell
        if ( !whoHasThis.hasCapability(ActorTypes.PLAYER) ){
            return res;
        }

        // gets list of traders
        ArrayList<Location> listOfTraderLocation = NearMe.isSpecificActorTypeInMyRange(whoHasThis,Application.staticGameMap,1,ActorTypes.TRADER);

        // if there is no trader will loop 0 times
        for ( Location l : listOfTraderLocation ){
            Actor trader = Application.staticGameMap.getActorAt(l);
            res.add(new SellAction(trader,i,sellingPrice));
        }
        return res;
    }

    /**
     * Function that is called by trader who can buy from trader
     * Approach description:
     *      1) check if the person who holds this weapon is trader or not
     *      2) if it is trader, get all the player locations around this trader if possible
     *      3) should only produce a list of len 1 if there is player nearby,
     *      4) add the purchase action
     *
     * Note: can only be called by trader
     * @param whoHasThis trader
     * @param w the weapon that can be bought from trader
     * @param purchasingPrice the purchase price of the weapon
     * @return a list of actions
     *          list is of length 0 if no possible actions are possible
     */
    public static List<Action> getPurchasingAction(Actor whoHasThis , WeaponItem w , int purchasingPrice ){

        List<Action> res = new ArrayList<>();

        if ( !whoHasThis.hasCapability(ActorTypes.TRADER) ){
            return res;
        }

        // gets list of traders
        ArrayList<Location> listOfPlayerLocation = NearMe.isSpecificActorTypeInMyRange(whoHasThis,Application.staticGameMap,1,ActorTypes.PLAYER);

        // if there is no player will loop 0 times
        for ( Location l : listOfPlayerLocation ){
            Actor trader = whoHasThis ;
            res.add(new PurchaseAction(trader,w,purchasingPrice));
        }
        return res;

    }

    /**
     * Function that is called by trader who can buy from trader
     * Approach description:
     *      1) check if the person who holds this weapon is trader or not
     *      2) if it is trader, get all the player locations around this trader if possible
     *      3) should only produce a list of len 1 if there is player nearby,
     *      4) add the purchase action
     *
     * Note: can only be called by trader
     * @param whoHasThis trader
     * @param i the item that can be bought from trader
     * @param purchasingPrice the purchase price of the item
     *         list is of length 0 if no possible actions are possible
     * @return a list of actions
     *          list is of length 0 if no possible actions are possible
     */
    public static List<Action> getPurchasingAction(Actor whoHasThis , Item i , int purchasingPrice ){

        List<Action> res = new ArrayList<>();

        if ( !whoHasThis.hasCapability(ActorTypes.TRADER) ){
            return res;
        }

        // gets list of traders
        ArrayList<Location> listOfPlayerLocation = NearMe.isSpecificActorTypeInMyRange(whoHasThis,Application.staticGameMap,1,ActorTypes.PLAYER);

        // if there is no player will loop 0 times
        for ( Location l : listOfPlayerLocation ){
            Actor trader = whoHasThis ;
            res.add(new PurchaseAction(trader,i,purchasingPrice));
        }
        return res;

    }

    /**
     * Function that is called by trader who can buy from trader
     * Approach description:
     *      1) check if the person who holds this weapon is player or not
     *      2) if it is player, get all the trader locations around this player if possible
     *      3) for each trader, check if the item is tradable with this trader or not using the list
     *          of tradable trader names
     *
     * @param whoHasThis the player
     * @param i the item to be swapped with
     * @param nameOfTradableTraders the list of all traders this item can be used to swap for another item/weapon with
     * @param tradableWeapons the list of all weapons this can be traded for
     * @return a list of actions
     *          list is of length 0 if no possible actions are possible
     */
    public static List<Action> getTradeAction(Actor whoHasThis , Item i , ArrayList<String> nameOfTradableTraders , ArrayList<WeaponItem>  tradableWeapons){

        List<Action> res = new ArrayList<>();

        // means if not player, cannot sell
        if ( !whoHasThis.hasCapability(ActorTypes.PLAYER) ){
            return res;
        }

        // gets list of traders
        ArrayList<Location> listOfTraderLocation = NearMe.isSpecificActorTypeInMyRange(whoHasThis,Application.staticGameMap,1,ActorTypes.TRADER);

        // if there is no trader will loop 0 times
        for ( Location l : listOfTraderLocation ){
            Actor trader = Application.staticGameMap.getActorAt(l);

            if ( nameOfTradableTraders.contains(trader.toString()) ) {
                res.add(new TradeItemAction(trader, i , tradableWeapons));
            }
        }

        return res;

    }



}
