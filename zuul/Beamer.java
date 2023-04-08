/**
 * This class represents an item called Beamer.
 * A beamer is an item that can be charged and fired.
 * 
 * Charging a beamer in a room memorises that room.
 * Firing a beamer in any room will take the player back 
 * to the room in which the beamer was charged. 
 * 
 * @author Manit Jawa 101215842
 * @version A2 1.0 March 15, 2023
 */
public class Beamer extends Item{
    // keeps track if beamer is charged or not. Set to true when charged
    // and false otherwise.
    private boolean charged;
    
    /**
     * Constructs a Beamer item. 
     * Constructor for objects of class Beamer.
     */
    public Beamer(){
        super("a beamer", 10.0, "beamer");
        charged = false;
    }
    
    /**
     * Charges a beamer object. 
     * Sets the field charged to true.
     */
    public void charge(){
        if (charged == true){
            System.out.println("You have already charged the beamer.");
        }

        charged = true;
    }

    /**
     * Checks if beamer is charged or not.
     * Returns the value stored in field charged.
     * 
     * @return True, if beamer is charged; False otherwise.
     */
    public boolean isCharged(){
        return charged;
    }

    /**
     * Fires the beamer.
     */
    public void fire(){
        charged = false;
        System.out.println("Beamer successfully fired.");
    }
}
