package game.action;

import java.util.Random;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.enemy.PileOfBones;

/**
 * An Action to allow an actor ( attacker ) to reduce the hitpoints of another actor ( target )
 * @author Lee Sing Yuan
 */
public class AttackAction extends Action {

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
	 * Constructor for an attack action with weapon involved
	 * 
	 * @param target the Actor to attack
	 * @param direction the direction where the attack should be performed (only used for display purposes)
	 * @param weapon the weapon used for attack
	 */
	public AttackAction(Actor target, String direction, Weapon weapon) {
		this.target = target;
		this.direction = direction;
		this.weapon = weapon;
	}

	/**
	 * Constructor for attacking with intrinsic weapon
	 *
	 * @param target the actor to attack
	 * @param direction the direction where the attack should be performed (only used for display purposes)
	 */
	public AttackAction(Actor target, String direction) {
		this.target = target;
		this.direction = direction;
	}

	/**
	 * Approach description:
	 * 		1) check if weapon is null
	 * 				if null, use intrinsic
	 * 				else, use weapon
	 * 		2) check if miss the target
	 * 				if miss, return miss
	 * 				else, continue to hurt
	 * 		3) hurt the target
	 * 		4) check if the target is dead and has skill pileOfBones
	 * 				if yes, remove pile of bones capability ( simulates usage of skill )
	 * 				if no allow normal death action
	 *
	 * @param actor The actor performing the attack action.
	 * @param map The map the actor is on.
	 * @return the result of the attack, e.g. whether the target is killed, etc.
	 * @see DeathAction
	 */
	@Override
	public String execute(Actor actor, GameMap map) {

		// checks if there is a weapon involved in this attack
		if (weapon == null) {
			weapon = actor.getIntrinsicWeapon();
		}

		// checks if the attacker miss or not
		if (!(rand.nextInt(100) <= weapon.chanceToHit())) {
			return actor + " misses " + target + ".";
		}

		// hurt the victim
		int damage = weapon.damage();
		String result = actor + " " + weapon.verb() + " " + target + " for " + damage + " damage.";
		target.hurt(damage);

		// Check if the target has a special skill
		if (!target.isConscious() && target.hasCapability(PileOfBones.PILE_OF_BONES)){

			// if it has, consume the skill, by removing it
			target.removeCapability(PileOfBones.PILE_OF_BONES);
			result += "\n" + target + " turns to a pile of bones!";

		}

		// if it's a normal victim, just kill the victim
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
		return actor + " attacks " + target + " at " + direction + " with " + (weapon != null ? weapon : "Intrinsic Weapon");
	}
}
