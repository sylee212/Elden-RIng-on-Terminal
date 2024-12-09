package game.action;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.enemy.PileOfBones;

import java.util.Random;

/**
 * An Action to double the damage to the target
 * @author Lee Sing Yuan
 */
public class UnsheatheAction extends Action {

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
	 * the chance of the skill for every actor
	 */
	private int skillChance;

	/**
	 * Constructor with weapon that can use the skill
	 *
	 * @param target the Actor to attack
	 * @param direction the direction where the attack should be performed (only used for display purposes)
	 * @param weapon is Uchigatana
	 */
	public UnsheatheAction(Actor target, String direction, Weapon weapon) {
		this.target = target;
		this.direction = direction;
		this.weapon = weapon;
		this.skillChance = 60;
	}

	/**
	 * Apporach description:
	 * 		1) do normal AttackAction
	 * 		2) but when dealing the damage, we double it
	 *
	 * @param actor The Player
	 * @param map The map the actor is on.
	 * @return the result of the attack, e.g. whether the target is killed, etc.
	 * @see DeathAction
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		if (rand.nextInt(100) > skillChance){
			return actor + "'s skill misses the " + target + ".";
		}

		int damage = weapon.damage() * 2;
		String result = actor + " " + weapon.verb() + " " + target + " for " + damage + " damage.";
		target.hurt(damage);

		// this is for the HSS, because if he dies the first time, he actually uses the skill
		if (!target.isConscious() && target.hasCapability(PileOfBones.PILE_OF_BONES)){
			target.removeCapability(PileOfBones.PILE_OF_BONES);
			System.out.println(target + " turns to a pile of bones!");

		}

		else if (!target.isConscious()) {
			result += new DeathAction(actor, target).execute(target, map);
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
		return actor + " unsheathe " + weapon + " on " + target + " at " + direction ;
	}
}
