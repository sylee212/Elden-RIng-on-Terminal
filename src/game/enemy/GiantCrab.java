package game.enemy;

import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.rune.RuneManager;
import game.weapon.CrabSlam;

/**
 * Crab here and there and everywhere!
 * @author Lee Sing Yuan
 */
public class GiantCrab extends ParentCrab {

    /**
     * The runes that this actor can drop
     */
    private final int GIANT_CRAB_MIN_RUNE = 318 ;
    private final int GIANT_CRAB_MAX_RUNE = 4961 ;

    /**
     * Constructor
     */
    public GiantCrab() {
        super("Giant Crab", 'C', 407);

        // add the skill as a weapon
        this.addWeaponToInventory(new CrabSlam());

        // adding to the rune manager
        RuneManager.addEnemyDropRune(name, GIANT_CRAB_MIN_RUNE, GIANT_CRAB_MAX_RUNE) ;
    }

    // same as weapon
    @Override
    public IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(208, "slams", 90);
    }
}