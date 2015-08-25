package com.ochiwerks.app.demo.parkinglot.services;

/**
 * Created by blurfus on 15-08-12.
 */
public interface ParkingService {

    boolean isParkingLotFull();

    boolean isParkingLotEmpty();

    void admitOneVehicle();

    void departOneVehicle();

    int getEmptyStalls();
}
