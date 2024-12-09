package game.environment;

import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.RandomNumberGenerator;
import game.enemy.Dog;

/**
 * Open the cage and release all the cute awoos!!!
 * Created by: Yeoh Ming Wei
 * @author Yeoh Ming Wei
 */
public class Cage extends Ground {

    /**
     * A constructor for cage class.
     * @param displayChar character to display for this type of terrain
     */
    public Cage() {
        super('<');
    }

    /**
     * A method to spawn Dog every game turn.
     */
    @Override
	public void tick(Location location) {
		super.tick(location) ;

        int p = RandomNumberGenerator.getRandomInt(100) ;

        if (p < 37 && !location.map().isAnActorAt(location)) {
            location.map().at(location.x(), location.y()).addActor(new Dog()) ;
        }
	}
}
