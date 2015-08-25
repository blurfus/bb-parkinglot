package com.ochiwerks.app.demo.parkinglot.model;

/**
 *
 * @Author Ivan Contramaestre-Luces
 * @Created 15-08-12.
 */
public class ParkingLotExit extends ParkingLotDoor {

    public ParkingLotExit(ParkingLot lot, Integer doorNumber) {
        setEntrySign("Exit #" + doorNumber);
        setDoorNumber(doorNumber);
        setParkingLot(lot);
        doorThread = new Thread(this, getEntrySign());
        doorThread.start();
    }

    public ParkingLotExit() {
    }

    @Override
    public void carPassedThrough() {
        getParkingService().departOneVehicle();
    }

    @Override
    public boolean canEnter() {
        return false;
    }

    @Override
    public boolean canExit() {
        return true;
    }

    @Override
    void processCommand() {
        synchronized (this) {
            if (getParkingLot().isCarAtExit() && this.canExit()) {
                getParkingLot().setCarAtExit(false, this.getDoorNumber());
                getParkingLot().getParkingService().departOneVehicle();
            }
        }
    }

    @Override
    public String toString() {
        return getEntrySign();
    }
}
