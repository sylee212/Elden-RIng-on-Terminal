package game.enemy;

import game.rune.RuneManager;
import game.weapon.HeavyCrossbow;

/**
 * a long range attacker
 * @author Lee Sing Yuan
 */
public class GodrickSoldier extends GodrickCastleInhabitants {

    /**
     * The runes that this actor can drop
     */
    private final int GODRICK_SOLDIER_MIN_RUNE = 38 ;
    private final int GODRICK_SOLDIER_MAX_RUNE = 70 ;

    /**
     * Constructor
     */
    public GodrickSoldier() {
        super("Godrick Soldier", 'p', 198);

        // adding the weapon
        this.addWeaponToInventory(new HeavyCrossbow());

        // adding to the rune manager
        RuneManager.addEnemyDropRune(name, GODRICK_SOLDIER_MIN_RUNE, GODRICK_SOLDIER_MAX_RUNE) ;
    }

}
