import java.util.Random;
import java.util.ArrayList;
/**
 * This class represents a room called Transporter Room.
 * When the player leaves this room using the "go" command,
 * the player is transported to any other random room in the game.
 * 
 * @author Manit Jawa 101215842
 * @version A2 1.0 March 15, 2023
 */
public class TransporterRoom extends Room {
    
    /**
     * Creates a transporter room with the 
     * description "a transporter room".
     */
    public TransporterRoom(){
        super("a transporter room");
    }

    /**
     * Returns a String which describes the exits of 
     * this room. In this particular case, the room is a 
     * transporter rooms so exits are anywhere.  
     * 
     * @return Exits from this room
     */
    private String getExitString(){
        return "Exits: anywhere";
    }

    /**
     * Returns a random room, independent of the direction parameter.
     *
     * @param direction Ignored.
     * @return A randomly selected room.
     */
    public Room getExit(String direction) {
        return findRandomRoom();
    }

    /**
     * Choose a random room.
     *
     * @return The room we end up in upon leaving this one.
     */
    private Room findRandomRoom() {

       Random random = new Random();
       ArrayList<Room> room_list = getRooms();
       int index = random.nextInt(getRooms().size());

       return room_list.get(index);
    }
}
