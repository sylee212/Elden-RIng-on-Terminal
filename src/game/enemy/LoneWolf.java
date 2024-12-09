package game.enemy;

import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.rune.RuneManager;

/**
 * BEHOLD, Awoooo!
 * @author Lee Sing Yuan
 */
public class LoneWolf extends ParentDog {

    /**
     * The runes that this actor can drop
     */
    private final int LONE_WOLF_MIN_RUNE = 55 ;
    private final int LONE_WOLF_MAX_RUNE = 1470 ;

    /**
     * Constructor
     */
    public LoneWolf() {
        super("Lone Wolf", 'h', 102);

        // adding to the rune manager
        RuneManager.addEnemyDropRune(name, LONE_WOLF_MIN_RUNE, LONE_WOLF_MAX_RUNE) ;
    }

    @Override
    public IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(97, "bites", 95);
    }
}
