package game.enemy;

import game.rune.RuneManager;
import game.weapon.Grossmesser;

/**
 * Spooky, spooky skeleton
 * @author Lee Sing Yuan
 */
public class HeavySkeletonSwordsman extends ParentSkeleton {

    /**
     * Constructor
     */
    public HeavySkeletonSwordsman() {
        super("Heavy Skeleton Swordsman", 'q', 153);

        // add weapons
        this.addWeaponToInventory(new Grossmesser());


        // adding to the rune manager
        RuneManager.addEnemyDropRune(name, super.getMinRune(), super.getMaxRune()) ;
    }

}

