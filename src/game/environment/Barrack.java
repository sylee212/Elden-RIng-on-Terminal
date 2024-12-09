package game.environment;

import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.RandomNumberGenerator;
import game.enemy.GodrickSoldier;

/**
 * Did you heard of Clash of Clans barracks? It can trains Barbarians, Archers
 * and many more. However, this barrack can only spawn Godrick Soldiers, can we
 * have more armies?
 * Created by: Yeoh Ming Wei
 * @author Yeoh Ming Wei 
 */
public class Barrack extends Ground {
    /**
     * A constructor for barrack class. 
     *
     * @param displayChar character to display for this type of terrain
     */
    public Barrack() {
        super('B');
    }

    /**
     * A method to spawn Goldrick Soldier every game turn. 
     */
    @Override
	public void tick(Location location) {
		super.tick(location) ;

        int p = RandomNumberGenerator.getRandomInt(100) ;

        if (p < 45 && !location.map().isAnActorAt(location)) {
            location.map().at(location.x(), location.y()).addActor(new GodrickSoldier()) ;
        }
	}
}
