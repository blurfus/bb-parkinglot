package com.ochiwerks.app.demo.parkinglot;

import com.ochiwerks.app.demo.parkinglot.common.CommandPromptUtils;
import com.ochiwerks.app.demo.parkinglot.model.ParkingLot;
import com.ochiwerks.app.demo.parkinglot.model.ParkingLotEntrance;
import com.ochiwerks.app.demo.parkinglot.model.ParkingLotExit;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A Parking lot management system
 * - Allows parking lot with N entries and M exits and control the entry and exit count.
 * - It is able to identify the capacity and lock the entries until parking is available again.
 *
 * @Author Ivan Contramaestre-Luces
 * @Created 15-08-12
 * @Version 0.0.1
 */
public class ParkingLotApp extends Thread {
    private ParkingLot parkingLot;
    List<Thread> doorList;

    private void processCommand(String command, String target) {
        if (command.equalsIgnoreCase("a")) {
            CommandPromptUtils.displayMessage("Adding a Vehicle...");

            if (!parkingLot.getParkingService().isParkingLotFull()) {
                try {
                    if (Integer.valueOf(target) < getParkingLot().getNumberOfEntrances()) {
                        parkingLot.setCarAtEntrance(true, new Integer(target));
                    } else {
                        CommandPromptUtils.displayMessage("Parking Lot does not have such an entrance door number");
                    }
                } catch (NumberFormatException nfe) {
                    CommandPromptUtils.displayMessage("Invalid door number");
                }
            }

            if (parkingLot.getParkingService().isParkingLotFull()) {
                parkingLot.setLotSign(ParkingLot.PARKING_LOT_FULL_MSG);
                CommandPromptUtils.displayMessage(ParkingLot.PARKING_LOT_FULL_MSG);
            } else {
                CommandPromptUtils.displayMessage(getParkingLot().getParkingService().getEmptyStalls() + ParkingLot.EMPTY_STALLS);
            }

        } else if (command.equalsIgnoreCase("x")) {
            CommandPromptUtils.displayMessage("Removing a Vehicle...");

            if (!parkingLot.getParkingService().isParkingLotEmpty()) {
                try {
                    if (Integer.valueOf(target) < getParkingLot().getNumberOfExits()) {
                        parkingLot.setCarAtExit(true, new Integer(target));
                    } else {
                        CommandPromptUtils.displayMessage("Parking Lot does not have such an exit door number");
                    }
                } catch (NumberFormatException nfe) {
                    CommandPromptUtils.displayMessage("Invalid door number");
                }
            }

            if (parkingLot.getParkingService().isParkingLotEmpty()) {
                parkingLot.setLotSign(getParkingLot().getParkingService().getEmptyStalls() + " " + ParkingLot.EMPTY_STALLS);
                CommandPromptUtils.displayMessage(getParkingLot().getParkingService().getEmptyStalls() + " " + ParkingLot.EMPTY_STALLS);
            } else {
                CommandPromptUtils.displayMessage(getParkingLot().getParkingService().getEmptyStalls() + ParkingLot.EMPTY_STALLS);
            }
        }
    }

    private void processInputs(String[] inputs) {

        for (String inputLine : inputs) {
            String[] commandAndTarget = inputLine.split(" ");

            if (commandAndTarget.length == 1) {
                String command = commandAndTarget[0];
                processCommand(command);

            } else if (commandAndTarget.length == 2) {
                String command = commandAndTarget[0];
                String target = commandAndTarget[1];
                processCommand(command, target);

            } else {
                CommandPromptUtils.displayMessage("Invalid Command: " + inputLine);
                CommandPromptUtils.displayMessage("Command Ignored!");
            }
        }
    }

    private void processCommand(String command) {
        if (command.equalsIgnoreCase("s")) {
            CommandPromptUtils.displayMessage("Reading sign at Lot's entrance...");
            CommandPromptUtils.displayMessage(getParkingLot().getLotSign());

        } else if (command.equalsIgnoreCase("a?")) {
            CommandPromptUtils.displayMessage("Checking if can add a Vehicle...");
            if (getParkingLot().getParkingService().isParkingLotFull()) {
                CommandPromptUtils.displayMessage(ParkingLot.PARKING_LOT_FULL_MSG);
            } else {
                CommandPromptUtils.displayMessage("Parking Lot has room for more vehicles");
            }

        } else if (command.equalsIgnoreCase("?")) {
            CommandPromptUtils.displayMessage("This Parking Lot has:");
            CommandPromptUtils.displayMessage("* " + getParkingLot().getNumberOfEntrances() + " Entrances");
            CommandPromptUtils.displayMessage("* " + getParkingLot().getNumberOfExits() + " Exits");
            CommandPromptUtils.displayMessage("* " + getParkingLot().getParkingService().getEmptyStalls() + ParkingLot.EMPTY_STALLS);

            if (getParkingLot().getParkingService().isParkingLotFull()) {
                CommandPromptUtils.displayMessage(ParkingLot.PARKING_LOT_FULL_MSG);
            }

        } else if (command.equalsIgnoreCase("a") || command.equalsIgnoreCase("x")) {
            CommandPromptUtils.displayMessage("Missing second argument...");

        } else if (command.equalsIgnoreCase("h")) {
            CommandPromptUtils.printHelp();

        } else if (command.equalsIgnoreCase("q")) {
            CommandPromptUtils.displayMessage("Exiting Program...");
            System.exit(0);

        } else {
            CommandPromptUtils.displayMessage("Did not understand command: " + command);
        }
    }

    private void initParkingLot(ParkingLot parkingLot) {
        Integer numOfEntries = parkingLot.getNumberOfEntrances();
        Integer numOfExits = parkingLot.getNumberOfExits();

        parkingLot.setEntranceList(new ArrayList<ParkingLotEntrance>(numOfEntries));
        parkingLot.setExitList(new ArrayList<ParkingLotExit>(numOfExits));
        doorList = new ArrayList<Thread>(numOfEntries + numOfExits);

        for (int i = 1; i <= parkingLot.getNumberOfEntrances(); i++) {
            Thread t = new Thread(new ParkingLotEntrance(parkingLot, i));
            doorList.add(t);
        }

        for (int i = 1; i <= parkingLot.getNumberOfExits(); i++) {
            Thread t = new Thread(new ParkingLotExit(parkingLot, i));
            doorList.add(t);
        }

        this.setParkingLot(parkingLot);
    }

    public static void main(String[] args) throws InterruptedException {
        ParkingLotApp app = new ParkingLotApp();
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-bean.xml");

        ParkingLot lot = context.getBean(ParkingLot.class);
        app.initParkingLot(lot);

        // display welcome message
        CommandPromptUtils.printWelcome();
        CommandPromptUtils.printHelp();

        Scanner input = new Scanner(System.in);

        String command;

        // while command is not 'q' (for quitting program)
        while (!(command = input.nextLine()).equalsIgnoreCase("q")) {
            CommandPromptUtils.displayMessage("Selected command:" + command);

            app.processInputs(new String[]{command});

            for (Thread t : app.doorList) {
                t.join(1000);
            }

            CommandPromptUtils.printPrompt();
        }

        // 'q' pressed, quitting program.
        CommandPromptUtils.displayMessage("Selected command:" + command);

        CommandPromptUtils.displayMessage("Exiting Program...");
        for (Thread t : app.doorList) {
            t.interrupt();
        }

        System.exit(0);
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }
}
