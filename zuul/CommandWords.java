/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.
 * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * Author Manit Jawa 101215842 used the sample solutions of A1 provided by
 * instructors and updated them by adding his code for A2.
 * 
 * @author Manit Jawa 101215842
 * @version A2 1.0 March 16 2023 (updated sample A1 solutions provided by instructors)
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 * 
 * @author Lynn Marshall
 * @version A1 Solution 
 */

public class CommandWords
{
    // a constant array that holds all valid command words
    private static final String[] validCommands = {
        "go", "quit", "help", "look", "eat", "back", "stackBack", "take", "drop", "charge", "fire"
    };

    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
        // nothing to do at the moment...
    }

    /**
     * Check whether a given String is a valid command word. 
     * 
     * @param aString The String to check
     * @return true if it is valid, false otherwise
     */
    public boolean isCommand(String aString)
    {
        for(int i = 0; i < validCommands.length; i++) {
            if(validCommands[i].equals(aString))
                return true;
        }
        // if we get here, the string was not found in the commands
        return false;
    }

    /**
     * Returns a String containing all valid commands.
     * 
     * @return a String of the valid commands
     */
    public String getCommandList() 
    {
        // let's use a StringBuilder (not required)
        StringBuilder s = new StringBuilder();
        for(String command: validCommands) {
            s.append(command + "  ");
        }
        String str = s.toString(); 
        return str.trim(); // removes spaces from beginning/end
    }
}
