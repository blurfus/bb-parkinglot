package com.ochiwerks.app.demo.parkinglot.model;

import com.ochiwerks.app.demo.parkinglot.services.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author Ivan Contramaestre-Luces
 * @Created 8/13/2015.
 */
public class ParkingLot {
    private String lotName;
    private String lotSign;
    private String lotDescription;
    private Integer numberOfExits;
    private Integer numberOfEntrances;
    private Integer usedStalls;

    @Autowired
    private ParkingService parkingService;

    private List<ParkingLotEntrance> entranceList;
    private List<ParkingLotExit> exitList;

    public static final String PARKING_LOT_FULL_MSG = "Parking Lot is Full.";
    public static final String EMPTY_STALLS = " empty stalls";

    public volatile boolean carAtExit = false;
    public volatile boolean carAtEntrance = false;

    public ParkingLot(Integer entrances, Integer exits) {
        setNumberOfEntrances(entrances);
        setNumberOfExits(exits);
        usedStalls = 0;
    }

    // -------- GETTERS/SETTERS
    public String getLotName() {
        return lotName;
    }

    public void setLotName(String lotName) {
        this.lotName = lotName;
    }

    public String getLotDescription() {
        return lotDescription;
    }

    public void setLotDescription(String lotDescription) {
        this.lotDescription = lotDescription;
    }

    public ParkingService getParkingService() {
        return parkingService;
    }

    public void setParkingService(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    public String getLotSign() {
        return lotSign;
    }

    public void setLotSign(String lotSign) {
        this.lotSign = lotSign;
    }

    public Integer getNumberOfEntrances() {
        return numberOfEntrances;
    }

    public void setNumberOfEntrances(Integer numberOfEntrances) {
        this.numberOfEntrances = numberOfEntrances;
    }

    public Integer getNumberOfExits() {
        return numberOfExits;
    }

    public void setNumberOfExits(Integer numberOfExits) {
        this.numberOfExits = numberOfExits;
    }

    public synchronized boolean isCarAtExit() {
        return this.carAtExit;
    }

    public synchronized boolean isCarAtEntrance() {
        return this.carAtEntrance;
    }

    public synchronized void setCarAtExit(boolean isCarAtDoor, Integer exitNumber) {
        this.carAtExit = isCarAtDoor;
    }

    public synchronized void setCarAtEntrance(boolean isCarAtEntranceDoor, Integer entranceNumber) {
        this.carAtEntrance = isCarAtEntranceDoor;
    }

    public List<ParkingLotEntrance> getEntranceList() {
        return entranceList;
    }

    public void setEntranceList(List<ParkingLotEntrance> entranceList) {
        this.entranceList = entranceList;
    }

    public List<ParkingLotExit> getExitList() {
        return exitList;
    }

    public void setExitList(List<ParkingLotExit> exitList) {
        this.exitList = exitList;
    }

    public Integer getUsedStalls() {
        return usedStalls;
    }

    public void setUsedStalls(Integer usedStalls) {
        this.usedStalls = usedStalls;
    }
}
