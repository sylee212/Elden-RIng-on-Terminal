package game.environment;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.Application;
import game.enemy.ActorTypes;
import game.enemy.Roles;
import game.player.Player;

/**
 *The GoldenFogDoor class represents a special type of ground in the game environment.
 *It acts as a door that allows only the player actor to enter.
 * Created by: Loo Li Shen
 * @author Loo Li Shen
 * Modified by: Loo Li Shen
 *
 */

public class GoldenFogDoor extends Ground {
    /**
     * Constructs a new GoldenFogDoor object.
     * The door is represented by the character 'D'.
     */
    public GoldenFogDoor() {
        super('D');

    }

    /**
     * Determines whether an actor is allowed to enter the GoldenFogDoor.
     *
     * @param actor the actor to check
     * @return true if the actor has the capability of ActorTypes.PLAYER, false otherwise
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return actor.hasCapability(ActorTypes.PLAYER);
    }

    /**
     * Performs the tick action for the GoldenFogDoor.
     * This method is called once per turn.
     *
     * @param location the location of the GoldenFogDoor
     */
    @Override
    public void tick(Location location) {
        Player player = Player.getInstance();
        GameMap mapIsAt = location.map();
        Display display = new Display();
        int x = location.x();

        // staticGameMap is limgrave currently
        // if 'D' is on x == 0 of the map then will go to castle
        if (mapIsAt == Application.limGrave && location.containsAnActor() && Application.staticGameMap == Application.limGrave) {
            if (x == 0) {
                transitionToMap(Application.castle, player, location);
                display.println("You have entered StormVeil Castle");
            } else {
                transitionToMap(Application.table, player, location);
                display.println("You have entered Roundtable Hold");
            }
        }

        // this is when player in castle
        // if 'D' is on x == 0 of the map then will go to limgrave
        if (mapIsAt == Application.castle && location.containsAnActor() && Application.staticGameMap == Application.castle) {
            if (x == 0) {
                transitionToMap(Application.limGrave, player, location);
                display.println("You have entered LimGrave");
            } else {
                transitionToMap(Application.boss, player, location);
                display.println("You have entered the Boss Room");
            }
        }

        // if player at roundtable then they will return to limgrave
        if (mapIsAt == Application.table && location.containsAnActor()){
            transitionToMap(Application.limGrave, player, location);
            display.println("You have entered LimGrave");
        }

    }

    /**
     * Transitions the player to a new map and updates the current game map.
     *
     * @param newMap  The new map to transition to.
     * @param player  The player actor.
     * @param location The location of the player.
     */
    public static void transitionToMap(GameMap newMap, Actor player, Location location) {
        GameMap currentGameMap = location.map();
        int x = currentGameMap.getXRange().max() ;
        int y = currentGameMap.getYRange().max() ;
        
        for (int i = 0; i <= x; i++) {
            for (int j = 0; j <= y ; j++) {

                Actor actor = currentGameMap.getActorAt(currentGameMap.at(i, j));
                if(!(actor == null) && actor.hasCapability(Roles.ENEMIES)) {
                    currentGameMap.removeActor(actor);
                }
            }
        }
        currentGameMap.removeActor(player);
        newMap.addActor(player, newMap.at(1, 2));
        Application.staticGameMap = newMap;
    }
}

