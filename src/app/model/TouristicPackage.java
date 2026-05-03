package app.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class TouristicPackage {

    private int id;
    private String name;
    private String type;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal basePrice;
    private int numOfPeople;
    private Hotel hotel;
    private Set<Guides> guides = new HashSet<>();

    public TouristicPackage(int id, String name, String type, LocalDate startDate, LocalDate endDate, BigDecimal basePrice, int numOfPeople, Hotel hotel, Set<Guides> guides) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.basePrice = basePrice;
        this.numOfPeople = numOfPeople;
        this.hotel = hotel;
        this.guides = guides;
    }

    public TouristicPackage(String name, String type, LocalDate startDate, LocalDate endDate, BigDecimal basePrice, int numOfPeople, Hotel hotel, Set<Guides> guides) {
        this.id = -1;
        this.name = name;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.basePrice = basePrice;
        this.numOfPeople = numOfPeople;
        this.hotel = hotel;
        this.guides = guides;
    }

    public TouristicPackage(int id, String name, String type, LocalDate startDate, LocalDate endDate, BigDecimal basePrice, int numOfPeople, Hotel hotel) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.basePrice = basePrice;
        this.numOfPeople = numOfPeople;
        this.hotel = hotel;
    }

    public Set<Guides> getGuides() {
        return guides;
    }

    public void setGuides(Set<Guides> guides) {
        this.guides = guides;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public int getNumOfPeople() {
        return numOfPeople;
    }

    public void setNumOfPeople(int numOfPeople) {
        this.numOfPeople = numOfPeople;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}

