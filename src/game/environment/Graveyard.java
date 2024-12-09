package game.environment;

import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;

/**
 * A graveyard environment, ground full of graveyards.
 * @author Yeoh Ming Wei
 */
public class Graveyard extends Ground {
	
	/**
	 * A constructor for the Graveyard class
	 */
    public Graveyard() {
		// The display character for graveyard
		super('n');
	}

	/**
	 * A method that manaage the enemy spawning every turn. The "if" conditional
	 * will determine if it is east side or west side. 
	 */
	@Override
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
