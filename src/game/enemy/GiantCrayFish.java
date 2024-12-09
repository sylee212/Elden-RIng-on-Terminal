package game.enemy;

import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.rune.RuneManager;
import game.weapon.CrayFishSlam;

/**
 * Is crayFish yummy? I dont know, I only knows its dangerous
 * @author Lee Sing Yuan
 */
public class GiantCrayFish extends ParentCrab {

    /**
     * The runes that this actor can drop
     */
    private final int GIANT_CF_MIN_RUNE = 500 ;
    private final int GIANT_CF_MAX_RUNE = 2374 ;

    /**
     * Constructor
     */
    public GiantCrayFish() {
        super("Giant Cray Fish", 'R', 4803);

        // add the skill as a weapon
        this.addWeaponToInventory(new CrayFishSlam());

        // adding to the rune manager
        RuneManager.addEnemyDropRune(name, GIANT_CF_MIN_RUNE, GIANT_CF_MAX_RUNE);
    }

    // same as weapon
    @Override
    public IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(527, "slams", 100);
    }
}