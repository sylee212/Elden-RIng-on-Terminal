package game.player;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.displays.Menu;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.ResetManager;
import game.Resettable;
import game.action.ChoiceInput;
import game.enemy.ActorTypes;
import game.enemy.Roles;
import game.rune.RuneManager;
import game.specialItems.RemembranceOfTheGrafted;
import game.weapon.*;


/**
 * Class representing the Player. It implements the Resettable interface.
 *
 * Note: use getInstance()
 * @author Lee Sing Yuan
 * @see RoleManager
 */
public class Player extends Actor implements Resettable {
	private final Menu menu = new Menu();

	// the 1 and only player in the game
	private static Player player;

	private int[] lastSiteOfLostGrace ;
	private Location location ;

	public static String playerName = "";


	/**
	 * Constructor that allows the children to use, but not the public to use
	 *
	 * @param name        Name to call the player in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 */
	protected Player(String name, int hitPoints) {
		super(name, '@', hitPoints);

		// add the type
		this.addCapability(ActorTypes.PLAYER);
		this.addCapability(Roles.ALLIES);

		// for reset
		this.lastSiteOfLostGrace = new int[2] ;
		this.lastSiteOfLostGrace[0] = -1 ; this.lastSiteOfLostGrace[1] = -1 ;

		playerName = name;

		// adding the grafted item
		this.addItemToInventory(new RemembranceOfTheGrafted());
	}

	/**
	 * The method to obtain an instance of this class,
	 *
	 * Apporach description:
	 * 		1) register the player as resettable
	 * 		2) check if player existed alredy or not
	 * 			2.1) if havent exist, instantiate the roleManager class to fill in the arraylist
	 * 			2.2) get user choice
	 * 			2.3) create player instance with wanted role info
	 *
	 * Note: the reason we need this method is because, the constructor has different parameters,
	 *      so we need another level before calling the constructor which will decide the parameters
	 *
	 * @return the player instance
	 */
	public static Player getInstance(){

		// making the player resettable
		ResetManager.registerResettable(player);

		// check if player already existed
		if (player == null){
			// instantiate the role manager
			RoleManager roleManager = new RoleManager();

			// asking for input \\
			int exit = RoleManager.playerRoles.size();

			// the printing
			System.out.println("Please select a role: ");
			for ( int y = 0 ; y < exit ; y++ ){
				System.out.println("" + y + ") " + RoleManager.playerRoles.get(y).getName() );
			}

			// get the user choice
			int choice = ChoiceInput.getChoiceInput(exit);

			// getting the role info \\

			// get the Player role information
			PlayerRole wantedRole = RoleManager.playerRoles.get(choice);

			// create the player
			player = new Player(wantedRole.getName(),wantedRole.getHp());

			// add all the possible weapons, items and capabilities to the player object
			RoleManager.addCapabilityItemWeapon(player,wantedRole);
		}

		// if player already exist
		return player;
	}

	/**
	 * Player's play turn
	 *
	 * Note: tick weapons to fix bug where actions dont appear in first round
	 *
	 * @param actions    collection of possible Actions for this Actor
	 * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
	 * @param map        the map containing the Actor
	 * @param display    the I/O object to which messages may be written
	 * @return the action decided
	 */
	@Override
	public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {

		// because to use weapons' get allowableActions,
		// it needs to know the current locations
		// so need to tick first
		for ( WeaponItem w : this.getWeaponInventory() )
		{
			if ( w.hasCapability(WeaponStatus.HAVE_NOT_TICKED) ) {
				w.tick(map.locationOf(this), this);
				actions.add(w.getAllowableActions());
			}
		}

		// because to use items' get allowableActions,
		// it needs to know the current locations
		// so need to tick first
		for ( Item i: this.getItemInventory() )
		{
			if ( i.hasCapability(WeaponStatus.HAVE_NOT_TICKED) ) {
				i.tick(map.locationOf(this), this);
				actions.add(i.getAllowableActions());
			}
		}

		this.location = map.locationOf(player) ;

		// Handle multi-turn Actions
		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();

		// to print the HP before printing all the available options
		System.out.printf("HP: %s, Rune: %d\n", this.printHp(), RuneManager.getInstance().returnRune()) ;
		// return/print the console menu
		return menu.showMenu(this, actions, display);
	}

	/**
	 * A function to return the name of the player.
	 */
	@Override
	public String toString() {
		return name;
	}

	/**
	 * A function to return the last visited Site of Lost Grace. 
	 * @return an array integer contains the coordinate of Site of Lost Grace
	 */
	public int[] lastVisited() {
		return lastSiteOfLostGrace ;
	}

	/**
	 * A function to set the last visited Site of Lost Grace. 
	 * @param x an x value of the map
	 * @param y a y value of the map
	 */
	public void setLastVisited(int x, int y) {
		lastSiteOfLostGrace[0] = x ;
		lastSiteOfLostGrace[1] = y ;
	}

	/**
	 * A function to reset the player's HP when reset is called. 
	 */
	@Override
	public void reset(GameMap map) {
		player.resetMaxHp(getMaxHp()) ;
	}

	/**
	 * A function to get the location of the player. 
	 * @return
	 */
	public Location getLocation() {
		return location ;
	}
}


