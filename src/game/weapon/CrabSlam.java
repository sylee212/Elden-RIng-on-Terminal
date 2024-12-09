package game.weapon;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.WeaponItem;

import java.util.List;

/**
 * A weapon used by GiantCrab to slam the area
 * It deals 208 damage with 90% hit rate
 * not droppable
 * @author Lee Sing Yuan
 */
public class CrabSlam extends WeaponItem {
    /**
     * used by getAllowableActions
     */
    private Location currentLocation;

    /**
     * Constructor
     * display character _ means dont drop
     */
    public CrabSlam() {

        super("CrabSlam", '_', 208, "slams", 90);

        // to make it not droppable and not pick up able
        togglePortability();
        addCapability(WeaponSkill.AREA_ATTACK);
    }

    /**
     * used to update the location so that getAllowableActions can use it
     * @param currentLocation The location of the actor carrying this Item.
     * @param actor The actor carrying this Item.
     */
    @Override
    public void tick(Location currentLocation, Actor actor) { this.currentLocation = currentLocation; }

    /**
     * To make the weapons return all the possible actions that can be done
     * applies open close principle
     *
     * Assumption: needs tick to be executed at least once in order to have the available actions
     * @return a list of actions that the wielder can do with this weapon
     */
    @Override
    public List<Action> getAllowableActions(){
        // the resulting list of actions
        List<Action> res = GetAllowableActions.getSurroundingAttackAllowableActions(currentLocation,this);
        return res;
    }

}
