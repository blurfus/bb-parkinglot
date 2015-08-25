package com.ochiwerks.app.demo.parkinglot.model;

/**
 *
 * @Author Ivan Contramaestre-Luces
 * @Created 15-08-12.
 */
public class ParkingLotEntrance extends ParkingLotDoor {

    public ParkingLotEntrance(ParkingLot lot, Integer doorNumber) {
        setEntrySign("Entrance #" + doorNumber);
        setDoorNumber(doorNumber);
        setParkingLot(lot);
        doorThread = new Thread(this, getEntrySign());
        doorThread.start();
    }

    public ParkingLotEntrance() {
    }

    @Override
    public void carPassedThrough() {
        getParkingService().admitOneVehicle();
    }

    @Override
    public boolean canEnter() {
        return true;
    }

    @Override
    public boolean canExit() {
        return false;
    }

    @Override
    void processCommand() {
        synchronized (this) {
            if (getParkingLot().isCarAtEntrance() && this.canEnter()) {
                getParkingLot().setCarAtEntrance(false, this.getDoorNumber());
                getParkingLot().getParkingService().admitOneVehicle();
            }
        }
    }

    @Override
    public String toString() {
        return getEntrySign();
    }
}