package game.action;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.enemy.PileOfBones;

import java.util.ArrayList;
import java.util.Random;

/**
 * An Action to attack another Actor and then choose a random location to relocate to
 * @author Lee Sing Yuan
 */
public class QuickStepAction extends Action {

	/**
	 * The Actor that is to be attacked
	 */
	private Actor target;

	/**
	 * The direction of incoming attack.
	 */
	private String direction;

	/**
	 * Random number generator
	 */
	private Random rand = new Random();

	/**
	 * Weapon used for the attack
	 */
	private Weapon weapon;

	/**
	 * Constructor with weapons that can do quick step action
	 *
	 * @param target the Actor to attack
	 * @param direction the direction where the attack should be performed (only used for display purposes)
	 * @param weapon the weapon used to perform this skill
	 */
	public QuickStepAction(Actor target, String direction, Weapon weapon) {
		this.target = target;
		this.direction = direction;
		this.weapon = weapon;
	}

	/**
	 * Approach description:
	 * 		1) perform normal attacking like Attack action
	 * 		2) get all the possible location the actor can relocate to
	 * 		3) choose a random exit to go to
	 *
	 * @param actor The Player
	 * @param map The map the actor is on.
	 * @return the result of the attack, e.g. whether the target is killed, etc.
	 * @see edu.monash.fit2099.engine.actions.MoveActorAction
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		// attacking \\
		// check if the attacker miss the target
		if (!(rand.nextInt(100) <= weapon.chanceToHit())) {
			return actor + " misses " + target + ".";
		}

		// hurt the target
		int damage = weapon.damage();
		String result = actor + " " + weapon.verb() + " " + target + " for " + damage + " damage.";
		target.hurt(damage);

		// this is for the HSS, because if he dies the first time, he actually uses the skill
		if (!target.isConscious() && target.hasCapability(PileOfBones.PILE_OF_BONES)){

			// consume the skill by removing it
			target.removeCapability(PileOfBones.PILE_OF_BONES);
			System.out.println(target + " turns to a pile of bones!");

		}

		// if it is a normal target and hp is gone, just kill the target
		else if (!target.isConscious()) {
			result += new DeathAction(actor, target).execute(target, map);
		}

		// repositioning actor \\
		// code taken from wander behavior
		ArrayList<Action> actions = new ArrayList<>();

		// an arraylist just to make the UI inform where the player is going to
		ArrayList<Location> locations = new ArrayList<>();

		// gets the possible exits
		for (Exit exit : map.locationOf(actor).getExits()) {
			Location destination = exit.getDestination();

			// check if the actor can enter this area
			if (destination.canActorEnter(actor)) {
				actions.add(exit.getDestination().getMoveAction(actor, "around", exit.getHotKey()));

				// adding it to the locations list to make the UI more informative
				locations.add(exit.getDestination());
			}
		}

		// choosing a random position to go to after moving
		if (!actions.isEmpty()) {
			// choose a random number
			int chosen = rand.nextInt(actions.size());

			// execute a random action
			actions.get(chosen).execute(actor,map);

			// return where the actor moves to
			result += "\n" + actor + " moves to ( " + locations.get(chosen).x() + " , " + locations.get(chosen).y() + " )";
		}
		else {
			result += "\n" + "Cant move anywhere";
		}

		return result;
	}

	/**
	 * Describes which target the actor is attacking with which weapon
	 *
	 * @param actor The actor performing the action.
	 * @return a description used for the menu UI
	 */
	@Override
	public String menuDescription(Actor actor) {
		return actor + " attacks " + target + " at " + direction + " with " + weapon + " and moves away";
	}
}
