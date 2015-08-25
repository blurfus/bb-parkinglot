package com.ochiwerks.app.demo.parkinglot.services;

import com.ochiwerks.app.demo.parkinglot.model.ParkingLot;

/**
 *
 * @Author Ivan Contramaestre-Luces
 * @Created 15-08-12.
 */
public class ParkingServiceImpl implements ParkingService {
    private int lotCapacity;
    private ParkingLot parkingLot;

    public ParkingServiceImpl() {
    }

    public boolean isParkingLotFull() {
        return (getUsedStalls() == getLotCapacity());
    }

    public boolean canAdmitOne() {
        return isParkingLotEmpty() || !isParkingLotFull();
    }

    public boolean isParkingLotEmpty() {
        return (getUsedStalls() == 0);
    }

    public synchronized void admitOneVehicle() {
        if (canAdmitOne()) {
            synchronized (parkingLot) {
                int stalls = getUsedStalls();
                setUsedStalls(++stalls);
            }
        }
    }

    @Override
    public synchronized void departOneVehicle() {
        if (!isParkingLotEmpty()) {
            synchronized (parkingLot) {
                int usedStalls = getUsedStalls();
                setUsedStalls(--usedStalls);
            }
        }
    }

    public int getEmptyStalls() {
        return (getLotCapacity() - getUsedStalls());
    }

    // GETTERS / SETTERS
    public int getLotCapacity() {
        return lotCapacity;
    }

    public void setLotCapacity(int lotCapacity) {
        this.lotCapacity = lotCapacity;
    }

    public int getUsedStalls() {
        return parkingLot.getUsedStalls();
    }

    public void setUsedStalls(int usedStalls) {
        parkingLot.setUsedStalls(usedStalls);
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }
}
