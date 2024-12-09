package game.environment;

import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;

/**
 * A water environment called PuddleOfWater, where is the fish?
 * @author Yeoh Ming Wei
 */
public class PuddleOfWater extends Ground {
    
	/**
	 * A constructor for the PuddleOfWater class
	 */
    public PuddleOfWater() {
		// The display character for PuddleOfWater.
        super('~') ;
    }

	/**
	 * A method that manaage the enemy spawning every turn. The "if" conditional
	 * will determine if it is east side or west side. 
	 */
    public void tick(Location location) {
		super.tick(location) ;

		int mid = location.map().getXRange().max() / 2 ;
		
		if (location.x() < mid) {
			WestMapEnemyFactory.getInstance().addEnemy(this, location) ;
		} else {
			EastMapEnemyFactory.getInstance().addEnemy(this, location) ;
		}
				 
	}
				
}
