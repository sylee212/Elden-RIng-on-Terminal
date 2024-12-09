package game.specialItems;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.Application;
import game.TradeManager;
import game.Trader;
import game.weapon.AxeOfGodrick;
import game.weapon.GraftedDragon;
import game.weapon.WeaponStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * An item that is supposedly dropped by the demi god
 * @author Lee Sing Yuan
 */
public class RemembranceOfTheGrafted extends Item {

    /**
     * For getAllowableAction() to work
     */
    private Location currentLocation;

    /**
     * The list of weapons this item can be traded for
     */
    private ArrayList<WeaponItem> tradableWeapons;

    /**
     * The traders which this item can be traded with
     */
    private ArrayList<String> nameOfTradableTraders;


    /**
     * The selling price
     */
    private int sellingPrice;

    /**
     * Constructor
     */
    public RemembranceOfTheGrafted(){
        super("Remembrance of the Grafted",'O',true);

        // setting the selling price
        this.sellingPrice = 20000;

        // add all the weapons here
        tradableWeapons = new ArrayList<>();
        tradableWeapons.add(new AxeOfGodrick());
        tradableWeapons.add(new GraftedDragon());

        // to avoid the bug where in the first round
        // cannot get allowable actions
        this.addCapability(WeaponStatus.HAVE_NOT_TICKED);

        // add traders here
        nameOfTradableTraders = new ArrayList<>();
        nameOfTradableTraders.add(Trader.nameFingerReaderEnia);
    }

    /**
     * Gets the selling price
     * @return int selling price
     */
    public int getSellingPrice() {
        return sellingPrice;
    }

    /**
     * used to update the location so that getAllowableActions can use it
     * @param currentLocation The location of the actor carrying this Item.
     * @param actor The actor carrying this Item.
     */
    @Override
    public void tick(Location currentLocation, Actor actor) {

        this.currentLocation = currentLocation;
        this.removeCapability(WeaponStatus.HAVE_NOT_TICKED);
    }

    /**
     * All the possible actions that can be done with this item at the current round
     * @return a list of actions
     *         list size 0 if no actions possible
     */
    @Override
    public List<Action> getAllowableActions(){

        // the resulting list of actions
        List<Action> res = new ArrayList<>();

        // checks who has this item
        Actor whoHasThis = Application.staticGameMap.getActorAt(currentLocation);
        if ( whoHasThis == null )
        {
            return res;
        }

        // selling called by player
        res.addAll(TradeManager.getSellingAction(whoHasThis,this,this.getSellingPrice()));

        // trading called by player
        res.addAll(TradeManager.getTradeAction(whoHasThis,this,nameOfTradableTraders,tradableWeapons));

        return res;
    }
}
