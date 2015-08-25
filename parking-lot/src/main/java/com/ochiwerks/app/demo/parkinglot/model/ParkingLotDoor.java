package com.ochiwerks.app.demo.parkinglot.model;

import com.ochiwerks.app.demo.parkinglot.services.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @Author Ivan Contramaestre-Luces
 * @Created 15-08-12.
 */
public abstract class ParkingLotDoor implements Runnable {

    @Autowired
    private ParkingService parkingService;
    private ParkingLot parkingLot;
    private String entrySign;
    private int doorNumber;
    private boolean carAtDoor;
    protected Thread doorThread;

    abstract void carPassedThrough();

    abstract boolean canEnter();

    abstract boolean canExit();

    abstract void processCommand();

    public void run() {
        try {
            while (true) {
                Thread.sleep(1000);
                processCommand();
            }
        } catch (InterruptedException e) {
            //CommandPromptUtils.displayMessage( e.getMessage() );
            Thread.currentThread().interrupt();
        }
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public ParkingService getParkingService() {
        return parkingService;
    }

    public void setParkingService(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    public String getEntrySign() {
        return entrySign;
    }

    public void setEntrySign(String entrySign) {
        this.entrySign = entrySign;
    }

    public boolean isCarAtDoor() {
        return this.carAtDoor;
    }

    public void setCarAtDoor(boolean carAtDoor) {
        this.carAtDoor = carAtDoor;
    }

    public Thread getDoorThread() {
        return doorThread;
    }

    public void setDoorThread(Thread doorThread) {
        this.doorThread = doorThread;
    }

    public int getDoorNumber() {
        return doorNumber;
    }

    public void setDoorNumber(int doorNumber) {
        this.doorNumber = doorNumber;
    }
}
