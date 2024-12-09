package game.weapon;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.Application;
import game.TradeManager;
import game.action.*;

import java.util.ArrayList;
import java.util.List;

/**
 * A weapon used by skeleton
 * It deals 115 damage with 85% hit rate
 * Buying and selling are from the player's POV
 * Created by: Lee Sing Yuan
 * @author Lee Sing Yuan
 * Modified by:
 *
 */
public class Grossmesser extends WeaponItem implements Sellable{
    /**
     * used by getAllowableActions
     */
    private Location currentLocation;

    /**
     * the selling price
     */
    private int sellingPrice;

    /**
     * Constructor
     */
    public Grossmesser() {
        super("Grossmesser", '?', 115, "slashes", 85);
        addCapability(WeaponSkill.AREA_ATTACK);
        sellingPrice = 100;

        // to avoid the bug where in the first round
        // cannot get allowable actions
        this.addCapability(WeaponStatus.HAVE_NOT_TICKED);
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
     * Gets the selling price
     * @return int selling price
     */
    @Override
    public int getSellingPrice() {
        return sellingPrice;
    }

    /**
     * To make the weapons return all the possible actions that can be done
     * applies open close principle
     * Approach decription:
     *      1) use currentLocation which is updated by tick
     *      2) check if there is someone at the same location as the weapon
     *      3) if there is someone, proceed
     *          3.1) else, return nothing
     *      4) check if there is a valid target to use skill on
     *          4.1) if can skill, get all the targets around this actor
     *          4.2) add surrounding attack action
     *      7) loop through all valid surrounding targets and add AttackAction
     *
     * Assumption: needs tick to be executed at least once in order to have the available actions
     *
     * @return a list of actions that the wielder can do with this weapon
     *          list will be empty if no actions are possible
     */
    public List<Action> getAllowableActions(){
        boolean isSkill;

        // the resulting list of actions
        List<Action> res = new ArrayList<>();

        // checking to see if the weapon is held by someone or not
        // if weapon is on the ground and player is on top of it
        //      means player cannot use the actions which this weapon can give
        // if weapon is on the ground and player is not on it
        //      means should not be able to attack anyone or give the list of actions
        Actor whoHasThis = Application.staticGameMap.getActorAt(currentLocation);
        if ( whoHasThis == null )
        {
            return res;
        }


        List<Actor> targets;
        // attacking //
        // checks all locations around me
        // check if there is someone of different type to initiate skill
        isSkill = GetAllowableActions.canSkill(whoHasThis,currentLocation);

        // add all the targets around this actor
        targets = GetAllowableActions.getTargets(isSkill,currentLocation);

        // adding the attack surrounding actions after getting all the actors
        if ( targets.size() > 0 ) {
            res.add(new AttackSurroundingAction(targets, "attacks surrounding", this));
        }

        // get the target and exit information surrounding this actor
        ArrayList<SurroundingExit> surroundingExitsTargets = NearMe.getSurroundingExitTargets(whoHasThis,currentLocation);

        // adding the actions to all the enemies around this actor
        for ( SurroundingExit s : surroundingExitsTargets ){
            res.add(new AttackAction(s.getTarget(), s.getExit().getName(), this));
        }

        /////////////////////////////////

        // trading \\

        // selling called by player
        res.addAll(TradeManager.getSellingAction(whoHasThis,this,this.getSellingPrice()));

        return res;
    }
}

