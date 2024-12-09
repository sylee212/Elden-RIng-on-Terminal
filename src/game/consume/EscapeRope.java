package game.consume;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;
import game.Application;

import game.environment.GoldenFogDoor;
import game.player.Player;
import game.weapon.WeaponStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * The EscapeRope class represents an item called "Escape Rope" that can be consumed only once by actors in the game.
 * It extends the ConsumeItem class.
 * Created by: Loo Li Shen
 * @author Loo Li Shen
 * Modified by: Yeoh Ming Wei
 *
 */

public class EscapeRope extends ConsumeItem {

    public EscapeRope() {
        super("Escape Rope", 'T', true, 1);
        this.addCapability(WeaponStatus.HAVE_NOT_TICKED);
    }

    /**
     * used to update the location so that getAllowableActions can use it
     * @param currentLocation The location of the actor carrying this Item.
     * @param actor The actor carrying this Item.
     */
    @Override
    public void tick(Location currentLocation, Actor actor) {
        this.removeCapability(WeaponStatus.HAVE_NOT_TICKED);
    }

    /**
     * Use this consumable, teleporting player to solg.
     * @param actor the player who is using the potion
     */
    @Override
    public void use(Actor actor) {
        Player player = Player.getInstance() ;
        GoldenFogDoor.transitionToMap(Application.table, player, player.getLocation());
    }

    /**
     * Returns a list of allowable actions for the EscapeRope.
     *
     * @return a list of allowable actions
     */
    @Override
    public List<Action> getAllowableActions(){
        List<Action> res = new ArrayList<>();
        Player player = Player.getInstance() ;
        if (player.getItemInventory().contains(this)) {
            res.add(new ConsumeAction(this,1, "teleport to", "st RoundTable", super.getUsesLeft())) ;
        }
        return res;
    }
}
