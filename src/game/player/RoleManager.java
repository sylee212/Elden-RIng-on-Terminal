package game.player;


import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.consume.EscapeRope;
import game.consume.FlaskOfCrimsonTears;
import game.weapon.AstrologerStaff;
import game.weapon.Club;
import game.weapon.GreatKnife;
import game.weapon.Uchigatana;

import java.util.ArrayList;

/**
 * The class that handles the information for all the roles
 * @author Lee Sing Yuan
 */
public class RoleManager {

    /**
     * An arraylist which can be accessed by others to allow them to set their instantiated object
     * to the info related to the roles
     *
     * Note: Need to instantiate the object first
     * @see Player
     */
    public static ArrayList<PlayerRole>  playerRoles = new ArrayList<>();

    /**
     * A constructor to put all the information of each role
     *
     * Note: add new classes here
     */
    public RoleManager(){
        // add all the roles here //
        
        // adding Samurai info
        PlayerRole Samurai = new PlayerRole("Samurai",455);
        Samurai.addWeapon(new Uchigatana());
        Samurai.addItem(FlaskOfCrimsonTears.getInstance());
        Samurai.addItem(new EscapeRope());
        playerRoles.add(Samurai);
        
        // adding Bandit info
        PlayerRole Bandit = new PlayerRole("Bandit",414);
        Bandit.addWeapon(new GreatKnife());
        Bandit.addItem(FlaskOfCrimsonTears.getInstance());
        Bandit.addItem(new EscapeRope());
        playerRoles.add(Bandit);

        // adding Wretch info
        PlayerRole Wretch = new PlayerRole("Wretch",414);
        Wretch.addWeapon(new Club());
        Wretch.addItem(FlaskOfCrimsonTears.getInstance());
        Wretch.addItem(new EscapeRope());
        playerRoles.add(Wretch);

        // adding Astrologer info
        PlayerRole Astrologer = new PlayerRole("Astrologer",396);
        Astrologer.addWeapon(new AstrologerStaff());
        Astrologer.addItem(FlaskOfCrimsonTears.getInstance());
        Astrologer.addItem(new EscapeRope());
        playerRoles.add(Astrologer);
    }

    /**
     * A method to add all the weapons, items and capabilities to the wanted actor
     * @param actor the wanted actor
     * @param wantedRole the object which holds the information about the roles
     */
    public static void addCapabilityItemWeapon(Actor actor, PlayerRole wantedRole){
        // adding the weapons to the player
        for (WeaponItem w : wantedRole.getWeaponInventory() ){
            actor.addWeaponToInventory(w);
        }

        // adding the items to the player
        for (Item i : wantedRole.getItemInventory() ){
            actor.addItemToInventory(i);
        }

        // adding the capabilities to the player
        for (Enum<?> enumType : wantedRole.getCapabilitySet() ){
            actor.addCapability(enumType);
        }
    }
}
