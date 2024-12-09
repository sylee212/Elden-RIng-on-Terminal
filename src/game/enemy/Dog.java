package game.enemy;

import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.rune.RuneManager;

/**
 * a cuter awoo
 * @author Lee Sing Yuan
 */
public class Dog extends GodrickCastleInhabitants {

    /**
     * The runes that this actor can drop
     */
    private final int DOG_MIN_RUNE = 52 ;
    private final int DOG_MAX_RUNE = 1390 ;

    /**
     * Constructor
     */
    public Dog() {
        super("Dog", 'a', 104);

        // to add to the rune manager
        RuneManager.addEnemyDropRune(name, DOG_MIN_RUNE, DOG_MAX_RUNE) ;
    }

    @Override
    public IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(101, "bites", 93);
    }
}
