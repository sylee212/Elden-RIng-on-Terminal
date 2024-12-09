package game.enemy;

import game.rune.RuneManager;
import game.weapon.Scimitar;

/**
 * Cool guy
 * @author Lee Sing Yuan
 */
public class SkeletalBandit extends ParentSkeleton {

    /**
     * Constructor
     */
    public SkeletalBandit() {
        super("Skeletal Bandit", 'b', 184);

        // adding the weapon
        this.addWeaponToInventory(new Scimitar());

        // adding the runes
        RuneManager.addEnemyDropRune(name, super.getMinRune(), super.getMaxRune()) ;
    }
}

