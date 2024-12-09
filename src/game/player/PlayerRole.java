package game.player;

import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.weapons.WeaponItem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A class to hold the information of the archetypes or roles
 * @author Lee Sing Yuan
 */
public class PlayerRole {
    /**
     * The name of the character
     */
    private String name;

    /**
     * The hp of the actor
     */
    private int hp;

    /**
     * The display character for UI purposes
     */
    private char displayChar;

    /**
     * A bag of items
     */
    private List<Item> itemInventory = new ArrayList<>();

    /**
     * Current capabilities/statuses
     */
    private Set<Enum<?>> capabilitySet = new HashSet<>();

    /**
     * A weapon inventory, making it possible to have more than two active weapons
     * (not limited to right-hand and left-hand weapons)
     */
    private List<WeaponItem> weaponInventory = new ArrayList<>();

    /**
     * A constructor for no weapons and no capabilities
     * @param initName name of the role
     * @param initHp hp of the role
     */
    public PlayerRole(String initName,int initHp){
        setName(initName);
        setHp(initHp);
    }

    public void addWeapon(WeaponItem w){
        weaponInventory.add(w);
    }

    public void addItem(Item i){
        itemInventory.add(i);
    }

    public void addCapability(Enum<?> capability){
        capabilitySet.add(capability);
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    private void setHp(int hp) {
        this.hp = hp;
    }

    public char getDisplayChar() {
        return displayChar;
    }

    public void setDisplayChar(char displayChar) {
        this.displayChar = displayChar;
    }

    public List<Item> getItemInventory() {
        return itemInventory;
    }

    public void setItemInventory(List<Item> itemInventory) {
        this.itemInventory = itemInventory;
    }

    public Set<Enum<?>> getCapabilitySet() {
        return capabilitySet;
    }

    public void setCapabilitySet(Set<Enum<?>> capabilitySet) {
        this.capabilitySet = capabilitySet;
    }

    public List<WeaponItem> getWeaponInventory() {
        return weaponInventory;
    }

    public void setWeaponInventory(List<WeaponItem> weaponInventory) {
        this.weaponInventory = weaponInventory;
    }


}
