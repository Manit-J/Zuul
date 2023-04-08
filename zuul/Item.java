
/**
 * This class represents an item which may be put
 * in a room in the game of Zuul.
 * 
 * Author Manit Jawa 101215842 used the sample solutions of A1 provided by
 * instructors and updated them by adding his code for A2.
 * 
 * @author Manit Jawa 101215842
 * @version A2 1.0 March 16 2023 (updated sample A1 solutions provided by instructors)
 * 
 * @author Lynn Marshall 
 * @version A2 Solution, used A1 sample solution provided and updated it.
 */
public class Item
{
    // description of the item
    private String description;
    
    // weight of the item in kilgrams 
    private double weight;

    // name of the item 
    private String name;

    /**
     * Constructor for objects of class Item.
     * 
     * @param description The description of the item
     * @param weight The weight of the item
     * @param name The name of the item
     */
    public Item(String description, double weight, String name)
    {
        this.description = description;
        this.weight = weight;
        this.name = name;
    }

    /**
     * Returns a description of the item, including its
     * name, description and weight.
     * 
     * @return  A description of the item
     */
    public String getDescription()
    {
        return name + ": " + description + " that weighs " + weight + "kg.";
    }

    /** 
     * Returns the name of the item.
     * 
     * @return  The name of the item
     */
    public String getName(){
        return name;
    }
}
