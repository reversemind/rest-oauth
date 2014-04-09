package ru.ttk.baloo.rest.dto;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 */
public class DetailsDTO implements Serializable {

    private String entranceCount;
    private String flatCount;
    private String flatPerFloor;
    private String floors;
    private String flatsPerEntrance;

    private List<String> services;

    private boolean isSpreadingNetwork = false;
    private Date dateReady;

    public DetailsDTO(){

    }

    public DetailsDTO(String entranceCount, String flatCount, String flatPerFloor, String floors, String flatsPerEntrance, List<String> services, boolean spreadingNetwork, Date dateReady) {
        this.entranceCount = entranceCount;
        this.flatCount = flatCount;
        this.flatPerFloor = flatPerFloor;
        this.floors = floors;
        this.flatsPerEntrance = flatsPerEntrance;
        this.services = services;
        isSpreadingNetwork = spreadingNetwork;
        this.dateReady = dateReady;
    }

    public List<String> getServices() {
        return services;
    }

    public void setServices(List<String> services) {
        this.services = services;
    }

    public String getEntranceCount() {
        return entranceCount;
    }

    public void setEntranceCount(String entranceCount) {
        this.entranceCount = entranceCount;
    }

    public String getFlatCount() {
        return flatCount;
    }

    public void setFlatCount(String flatCount) {
        this.flatCount = flatCount;
    }

    public String getFlatPerFloor() {
        return flatPerFloor;
    }

    public void setFlatPerFloor(String flatPerFloor) {
        this.flatPerFloor = flatPerFloor;
    }

    public String getFloors() {
        return floors;
    }

    public void setFloors(String floors) {
        this.floors = floors;
    }

    public String getFlatsPerEntrance() {
        return flatsPerEntrance;
    }

    public void setFlatsPerEntrance(String flatsPerEntrance) {
        this.flatsPerEntrance = flatsPerEntrance;
    }


    public boolean isSpreadingNetwork() {
        return isSpreadingNetwork;
    }

    public void setSpreadingNetwork(boolean spreadingNetwork) {
        isSpreadingNetwork = spreadingNetwork;
    }

    public Date getDateReady() {
        return dateReady;
    }

    public void setDateReady(Date dateReady) {
        this.dateReady = dateReady;
    }
}
