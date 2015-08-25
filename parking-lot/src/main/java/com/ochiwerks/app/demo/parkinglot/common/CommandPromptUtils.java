package com.ochiwerks.app.demo.parkinglot.common;

/**
 *
 * @Author Ivan Contramaestre-Luces
 * @Created 8/13/2015.
 */
public class CommandPromptUtils {

    public static void printPrompt() {
        displayMessage(" ");
        displayMessage("Please enter a command [ s | a # | x # | ? | a? | q | h ]: ");
        displayMessage(" ");
    }

    public static void printHelp() {
        displayMessage("Please enter a command:");
        displayMessage("    (s) - to read the Parking Lot sign");
        displayMessage("    (a #) - to add a vehicle to the lot - '#' => the door number to use (i.e. a 1)");
        displayMessage("    (x #) - to remove a vehicle from the lot - '#' => the door number to use (i.e. x 2)");
        displayMessage("    (?) - to see information about the lot");
        displayMessage("    (a?) - to see if you can add a vehicle to the lot");
        displayMessage("    (q) - to quit the program");
        displayMessage("    (h) - to display these instructions");
        displayMessage("-------");
    }

    public static void printWelcome() {
        displayMessage("=============================================");
        displayMessage("   WELCOME TO OUR PARKING LOT MGMT SYSTEM");
        displayMessage("=============================================");
    }


    public static void displayMessage(String message) {
        System.out.println(message);
    }
}
