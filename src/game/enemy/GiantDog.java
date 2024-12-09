package game.enemy;

import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.rune.RuneManager;
import game.weapon.DogSlam;

/**
 * BEHOLD, DOG!
 * @author Lee Sing Yuan
 */
public class GiantDog extends ParentDog {

    /**
     * The runes that this actor can drop
     */
    private final int GIANT_DOG_MIN_RUNE = 313 ;
    private final int GIANT_DOG_MAX_RUNE = 1808 ;

    /**
     * Constructor
     */
    public GiantDog() {
        super("Giant Dog", 'G', 693);

        // add the skill as a weapon
        this.addWeaponToInventory(new DogSlam());

        // adding to the rune manager
        RuneManager.addEnemyDropRune(name, GIANT_DOG_MIN_RUNE, GIANT_DOG_MAX_RUNE);
    }

    @Override
    public IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(314, "bites", 90);  
    }

}
