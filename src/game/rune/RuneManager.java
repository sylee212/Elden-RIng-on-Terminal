package game.rune;

import java.util.ArrayList;
import java.util.HashMap;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import game.RandomNumberGenerator;

/**
 * A rune manager to manage the calculation of the rune including how many
 * rune from enemy drop will be transferred to the player or selling 
 * weapons will increase the player runes etc.
 * 
 * @author Yeoh Ming Wei
 */
public class RuneManager{
    /**
     * A Rune object
     */
    private Rune rune ;

    /**
     * Initial initialization for Rune Manager, it is set to null.
     */
    private static RuneManager rm = null;
    
    /**
     * A hash map to manage the enemy drop rune range.
     */
    public static HashMap<String, ArrayList<Integer>> runeRange = new HashMap<>();

    public static HashMap<Item, Integer> dropRuneAmount = new HashMap<>() ;
    
    /**
     * A private constructor for Rune Manager
     * Rune will be initialized upon initialization of RuneManager
     */
    private RuneManager() {
        rune = new Rune() ;
    }

    /**
     * A method to assigned new RuneManager class
     * @return new RuneManager class
     */
    public static RuneManager getInstance() {
        if (rm == null) 
            rm = new RuneManager() ;
        return rm ;
    }
    
    /**
     * A method to add Rune to player when the enemy dies.
     * @param player The player
     * @param enemy The enemy that dies
     */
    public void addRuneEnemy(Actor player, Actor enemy) {
        
        int value = 0 ;
        ArrayList<Integer> arr = RuneManager.runeRange.get(enemy.toString()) ;
        value = RandomNumberGenerator.getRandomInt(arr.get(0), arr.get(1)) ;
        addRune(player,value) ;
    }

    /**
     * A method to deduct the rune from the player.
     * @param player The player
     * @param value The value that needs to be deducted
     */
    public void deductRune(Actor player, int value) {
        rune.setRune(rune.getRune() - value) ;
        System.out.println(menuDescription(player, "decreased by", value)) ;
        
    }

    /**
     * A method to set the rune value. 
     */
    public void setRune(int value) {
        rune.setRune(value) ;
    }

    /**
     * A method to return the rune value.
     * @return An integer represents the amount of rune.
     */
    public int returnRune() {
        return rune.getRune() ;
    }

    /**
     * A method to add a specific amount of rune to the player.
     * @param player The player
     * @param value The value that needs to be added.
     */
    public void addRune(Actor player, int value){
        rune.setRune(rune.getRune() + value) ;
        System.out.println(menuDescription(player, "increased by", value)) ;
    }


    /**
     * The method that will shows a description indicating how many runes the player earns or removed, 
     * along with the amount of runes the player have now. 
     * @param player The player
     * @param verb Shows the action performed
     * @param value The increased or decreased amount of the rune
     * @return A string of description
     */
    public String menuDescription(Actor player, String verb, int value) {
        return String.format("%s's rune has %s by %d, now %s has %d runes.", player, verb, value, player, rune.getRune());
    }

    /**
     * A method to add the enemy name as key and the drop rune range as the value into the hashmap.
     * @param name The name of the enemy
     * @param min The minimum rune that can be dropped
     * @param max The maximum rune that can be dropped
     */
    public static void addEnemyDropRune(String name, int min, int max){
        ArrayList<Integer> temp = new ArrayList<>() ;
        temp.add(min) ;
        temp.add(max) ;
        runeRange.put(name, temp);
    }

    /**
     * A method to add drop rune with its value into the hash map
     * @param item The rune
     * @param value the amount of the rune
     */
    public static void addRuneDropValue(Item item, int value) {
        dropRuneAmount.put(item, value) ;
    }
}
