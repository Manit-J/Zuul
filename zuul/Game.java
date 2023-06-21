import java.util.Stack;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery, visit different rooms and interact with some items.
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 *  Author Manit Jawa 101215842 used the sample solutions of A1 provided by
 *  instructors and updated them by adding his code for A2.
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

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Room previousRoom;
    private Stack<Room> previousRoomStack;

    // an attribute which tells us which item the player is holding currently.
    private Item item_held; 

    // counts the number of items carried so far. Always less than or equal to 5.
    private int item_count; 

    // keeps track of whether the player is hungry or not.
    private boolean hungry; 
    
    // the beamer's memory of the room it was charged in.
    private Room beamer_memory;

    // a seperate attribute to store picked up items which are beamers.
    private Beamer item_held_beamer;


    /**
     * Create the game and initialise its internal map, as well
     * as the previous room (none) and previous room stack (empty).
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        previousRoom = null;
        previousRoomStack = new Stack<Room>();
        item_held = null;
        item_count = 0;
        hungry = true;
        beamer_memory = null;
        item_held_beamer = null;
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theatre, pub, lab, office, transporter_room;
        Item chair1, chair2, chair3, chair4, bar, computer1, computer2, computer3, tree1, tree2, cookie1, cookie2;
        Beamer beamer1, beamer2;
        
        // create some items
        chair1 = new Item("a wooden chair",5, "chair");
        chair2 = new Item("a wooden chair",5, "chair");
        chair3 = new Item("a wooden chair",5, "chair");
        chair4 = new Item("a wooden chair",5, "chair");
        bar = new Item("a long bar with stools",95.67, "bar");
        computer1 = new Item("a PC",10, "PC");
        computer2 = new Item("a Mac",5, "Mac");
        computer3 = new Item("a PC",10, "PC");
        tree1 = new Item("a fir tree",500.5, "tree");
        tree2 = new Item("a fir tree",500.5, "tree");
        cookie1 = new Item("a cookie", 10, "cookie");
        cookie2 = new Item("a cookie", 10, "cookie");
        beamer1 = new Beamer();
        beamer2 = new Beamer();
       
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theatre = new Room("in a lecture theatre");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        transporter_room = new TransporterRoom();
        
        // put items in the rooms
        outside.addItem(tree1);
        outside.addItem(tree2);
        outside.addItem(cookie1);
        outside.addItem(beamer1);
        theatre.addItem(chair1);
        pub.addItem(bar);
        pub.addItem(cookie2);
        lab.addItem(chair2);
        lab.addItem(computer1);
        lab.addItem(chair3);
        lab.addItem(computer2);
        office.addItem(chair4);
        office.addItem(computer3);
        office.addItem(beamer2);
        
        // initialise room exits
        outside.setExit("east", theatre); 
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theatre.setExit("west", outside);

        pub.setExit("east", outside);
        pub.setExit("west", transporter_room);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);

        currentRoom = outside;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * 
     * @param command The command to be processed
     * @return true If the command ends the game, false otherwise
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("look")) {
            look(command);
        }
        else if (commandWord.equals("eat")) {
            eat(command);
        }
        else if (commandWord.equals("back")) {
            back(command);
        }
        else if (commandWord.equals("stackBack")) {
            stackBack(command);
        }
        else if (commandWord.equals("take")){
            take(command);
        }
        else if (commandWord.equals("drop")){
            drop(command);
        }
        else if (commandWord.equals("charge")){
            charge(command);
        }
        else if (commandWord.equals("fire")){
            fire(command);
        }
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print a cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.getCommands());
    }

    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     * If we go to a new room, update previous room and previous room stack.
     * 
     * @param command The command to be processed
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            previousRoom = currentRoom; // store the previous room
            previousRoomStack.push(currentRoom); // and add to previous room stack
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }

        if (item_held != null){
            System.out.println("You are carrying: ");
            System.out.println(item_held.getDescription());
        } 
        else{
            System.out.println("You are not carrying anything.");
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * 
     * @param command The command to be processed
     * @return true, if this command quits the game, false otherwise
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    /** 
     * "Look" was entered. Check the rest of the command to see
     * whether we really want to look.
     * 
     * @param command The command to be processed
     */
    private void look(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Look what?");
        }
        else {
            // output the long description of this room
            System.out.println(currentRoom.getLongDescription());
            if (item_held != null){
                System.out.println("You are carrying: ");
                System.out.println(item_held.getDescription());
            } 
            else{
                System.out.println("You are not carrying anything.");
            }
            
        }
    }
    
    /** 
     * "Eat" was entered. Check the rest of the command to see
     * whether we really want to eat.
     * 
     * @param command The command to be processed
     */
    private void eat(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Eat what?");
        }
        else if (item_held == null){
            System.out.println("You are not carrying anything that you can eat.");
        }
        else if (item_held.getName().equals("cookie") == false){
            System.out.println("You are not carrying anything that you can eat.");
        }
        else {
                // output that we have eaten
                System.out.println("You have eaten a cookie and are no longer hungry.");
                hungry = false;
                item_held = null;            
            }
    }
    
    
    /** 
     * "Back" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * 
     * @param command The command to be processed
     */
    private void back(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Back what?");
        }
        else {
            // go back to the previous room, if possible
            if (previousRoom==null) {
                System.out.println("No room to go back to.");
            } else {
                // go back and swap previous and current rooms,
                // and put current room on previous room stack
                Room temp = currentRoom;
                currentRoom = previousRoom;
                previousRoom = temp;
                previousRoomStack.push(temp);
                // and print description
                System.out.println(currentRoom.getLongDescription());
            }
        }
    }
    
    /** 
     * "StackBack" was entered. Check the rest of the command to see
     * whether we really want to stackBack.
     * 
     * @param command The command to be processed
     */
    private void stackBack(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("StackBack what?");
        }
        else {
            // step back one room in our stack of rooms history, if possible
            if (previousRoomStack.isEmpty()) {
                System.out.println("No room to go stack back to.");
            } else {
                // current room becomes previous room, and
                // current room is taken from the top of the stack
                previousRoom = currentRoom;
                currentRoom = previousRoomStack.pop();
                // and print description
                System.out.println(currentRoom.getLongDescription());
            }
        }
    }

    /**
     * Take the item which is mentioned. 
     * If no item name was mentioned, print "take what?".
     * Otherwise, update the item_held field to store the name of 
     * the item just taken. Print "You picked up" followed by 
     * the name of the item just taken. 
     * 
     * @param command The command to be processed
     */
    private void take(Command command){

        // if only "take" was entered as a command
        if (command.hasSecondWord() == false){
            System.out.println("take what?");
        }
        else if (command.getSecondWord().equals("cookie") && item_held == null){
            if (currentRoom.getItems().contains(command.getSecondWord())){
                item_held = currentRoom.removeItem(command.getSecondWord());
                System.out.println("You picked up " + command.getSecondWord());
            }
            else {
                System.out.println("This room does not have a cookie.");
            }
            
        }
        else if (item_held != null){
            System.out.println("You are already holding something.");
        }
        else if (hungry == true){
            System.out.println("You cannot take anything because you are hungry.");
            System.out.println("You must find a cookie and eat it before you can pick up anything.");
        }
        else{
            if (currentRoom.getItems().contains(command.getSecondWord())){
                if (command.getSecondWord().equals("beamer")){
                    item_held_beamer = new Beamer();
                }
                item_held = currentRoom.removeItem(command.getSecondWord());
                System.out.println("You picked up " + item_held.getName());
                item_count += 1;
                if (item_count == 5){
                    hungry = true;
                    item_count = 0;
                }
            }
            else{
                System.out.println("That item is not in the room.");
            }
            
        }
    }

    /**
     * Drops the item which is held. 
     * Single word command; if second word given, prints "drop what?".
     * If no item was taken, prints "You have nothing to drop."
     * Sets item_held field to null.
     * Prints "You have dropped" followed by the name of the item just dropped.
     * 
     * @param command The command to be processed
     */
    private void drop(Command command){

        if (command.hasSecondWord()){
            System.out.println("drop what?");
        }
        else if (item_held == null){
            System.out.println("You have nothing to drop.");
        }
        else{
            currentRoom.addItem(item_held);
            System.out.println("You have dropped " + item_held.getName());
            if (item_held.getName().equals("beamer")){
                item_held_beamer = null;
            }
            item_held = null;
        }
    }

    /**
     * Charges the beamer. 
     * Before charging, the method ensures that the player 
     * is carrying a beamer which is not charged.
     * 
     * @param command The command to be processed
     */
    private void charge(Command command){

        if (command.hasSecondWord()){
            System.out.println("charge what?");
        } 
        else if (item_held == null){
            System.out.println("You are not carrying a beamer.");
        }
        else if (item_held.getName().equals("beamer") == false){
            System.out.println("You are not carrying a beamer.");
        } 
        else{
            item_held_beamer.charge();
            beamer_memory = currentRoom;
            System.out.println("beamer successfully charged.");
        }
    }

    /**
     * Fires the beamer. 
     * Before firing, the method checks if the player 
     * is carrying a beamer which was charged. 
     * 
     * @param command The command to be processed
     */
    private void fire(Command command){

        if (command.hasSecondWord()){
            System.out.println("fire what?");
        }
        else if (item_held == null){
            System.out.println("You are not carrying a beamer.");
        }
        else if(item_held.getName().equals("beamer") == false){
            System.out.println("You are not carrying a beamer.");
        }
        else if (!item_held_beamer.isCharged()){
            System.out.println("You can't fire the beamer as you haven't charged it.");
        }
        else{
            item_held_beamer.fire();
            currentRoom = beamer_memory;
        }
    }
}
