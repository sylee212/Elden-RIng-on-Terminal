package game.action;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.rune.RuneManager;

/**
 * A class to allow the trader to give purchase actions to the player
 * @author Lee Sing Yuan
 */
public class PurchaseAction extends Action {

	/**
	 * The Actor that is to be attacked
	 */
	private Actor trader;

	/**
	 * The weapon to sell
	 */
	private WeaponItem weaponItem;

	/**
	 * The item to buy
	 */
	private Item item;

	/**
	 * The price
	 */
	private int buyingPrice;

	/**
	 * Constructor to buy weapons
	 *
	 * @param trader the trader
	 * @param w the weapon to be bought
	 * @param buyingPrice the price of the weapon
	 */
	public PurchaseAction(Actor trader, WeaponItem w , int buyingPrice) {
		this.trader = trader;
		this.weaponItem = w;
		this.buyingPrice = buyingPrice;
	}

	/**
	 * Constructor to buy items
	 *
	 * @param trader the trader
	 * @param i the item to be bought
	 * @param buyingPrice the price of the weapon
	 */
	public PurchaseAction(Actor trader, Item i , int buyingPrice) {
		this.trader = trader;
		this.item = i;
		this.buyingPrice = buyingPrice;
	}


	/**
	 * Approach description:
	 * 		1) the class will be instantiated with the weapon that wants to be bought
	 * 		2) call the rune manager to get the player's runes
	 * 		3) check if the player can buy this weapon
	 * 			3.1) if can buy, add this weapon/item to the player's inventory and deduct the player's runes
	 *
	 * Assumption:
	 * 		the weapons are already instantiated once, in order to be available to buy
	 *
	 * @param actor The player
	 * @param map The map the actor is on.
	 * @return the result of the trading
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		// the result of the action if nothing is bought
		String result = "Insufficient rune";

		// get the runemanager
		RuneManager runeManager = RuneManager.getInstance();

		// get the player's runes
		int playerRunes = runeManager.returnRune();

		// checks if the player can buy
		if ( playerRunes >= this.buyingPrice )
		{

			// if player can buy
			// deduct the runes
			runeManager.deductRune(actor,buyingPrice);

			// checks if we are buying weapon
			if ( weaponItem != null ) {
				actor.addWeaponToInventory(weaponItem);
			}

			// checks if we are buying item
			if ( item != null ){
				actor.addItemToInventory(item);
			}

			result = actor + " buys " + weaponItem;
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
		return actor + " buys " + (weaponItem != null ? weaponItem : item) + " from " + trader;
	}

}

