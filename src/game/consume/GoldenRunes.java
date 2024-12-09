package game.consume;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.PickUpAction;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.RandomNumberGenerator;
import game.Status;
import game.player.Player;
import game.rune.RuneManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Golden Rune is an item that can be found in the ground. 
 * Golden Rune can be picked up and dropped. 
 * Golden Rune can be consume by player to earn an amount of rune.
 * Is it possible to eat a rune? 
 * 
 * Created by: Yeoh Ming Wei
 * @author Yeoh Ming Wei
 * Modified by: Yeoh Ming Wei
 *
 */
public class GoldenRunes extends ConsumeItem {
    
    /**
     * An integer representing the minimum amount of rune
     */
    private final int MIN = 200;

     /**
     * An integer representing the maximum amount of rune
     */
    private final int MAX = 10000;

    /**
     * An integer representing a random amount of rune generated
     */
    public int runesAdded = RandomNumberGenerator.getRandomInt(MIN, MAX);

    /**
     * A constructor for GoldenRunes class. 
     */
    public GoldenRunes() {
        super("Golden Runes", '*', true, 0);
    }

    /**
     * A method to use the item. (Add a random amount of rune to the player)
     *
     * @param actor the player who is using the potion
     */
    @Override
    public void use(Actor actor) {
        Player player = Player.getInstance();
        RuneManager.getInstance().addRune(player, runesAdded);
    }

    /**
     * Returns a list of allowable actions for the golden runes.
     *
     * @return a list of allowable actions
     */
    @Override
    public List<Action> getAllowableActions() {

        List<Action> res = new ArrayList<>();
        Player player = Player.getInstance() ;
        if (player.getItemInventory().contains(this)) {
            res.add(new ConsumeAction(this, runesAdded, "Collected for", "Runes", super.getUsesLeft())) ;
        }
        
        return res ;
  
    }

    /**
     * Adds golden runes to random locations on the given game map.
     * Gets the size of the map and then the item is added if that map doesn't have any collusion.
     *
     * @param map   the game map
     * @param actor the actor to add the seeds for
     */
    public void addGoldenRunesToRandomLocation(GameMap map, Actor actor) {

		int width = map.getXRange().max();
        int height = map.getYRange().max();

        for (int i = 0; i <= width; i++) {
            for (int j = 0; j <= height ; j++) {

                int p = RandomNumberGenerator.getRandomInt(100) ;
                Location location = map.at(i, j) ;
                if (p < 1 && location.canActorEnter(actor) && !location.getGround().hasCapability(Status.ITEM_NOT_SPAWNABLE)) {
                    map.at(i, j).addItem(new GoldenRunes()); 
                }

            }
        }
    }
    
    /**
     * A pick up action for GoldenRune.
     * Note: I use this method so that I can know the amount of golden rune.
     *       It is used at consume action which requires the amount. 
     */
    @Override
    public PickUpAction getPickUpAction(Actor actor) {
        return new PickUpConsumeItemAction(this) ;
    }
}
