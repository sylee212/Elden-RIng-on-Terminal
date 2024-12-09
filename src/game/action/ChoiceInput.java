package game.action;
import java.util.Scanner;

/**
 * A class to accept and verify input.
 * To make player and tester's life easier instead of having to type names
 * @author Lee Sing Yuan
 */
public class ChoiceInput {

    /**
     * Approach description:
     *      1) set the start and choice
     *      2) let the user input a number
     *      3) if the input is not a number
     *              tell the user and allow user to retype
     *      4) if the number exceeds the range
     *              tell the user and allow user to retype
     *
     * @param exit the exit number, depends on the size of the Collections that this choiceInput is user for
     * @return an int which is the choice chosen
     */
    public static int getChoiceInput(int exit){
        // to receive input
        Scanner sel = new Scanner(System.in);

        int start = 0;
        int choice = start - 1;
        // allows single buy  ( choice > exit || choice < start )
        do {

            // if the user did not put a number
            try {
                String input = sel.nextLine();
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please input a number");
            }

            // if number exceed options, tell the player to choose again
            if ( choice > exit || choice < start ){
                System.out.println("Please input a number that is available");
            }

            // if we choose a number smaller than the available options or bigger then the exit, continue looping
        } while ( choice > exit || choice < start );
        return choice;
    }

}
