package app.model;

import java.time.LocalDate;

public class Reservation {

    private int id;
    private LocalDate reservationDate;
    private Status status;
    private int numOfPeople;
    private Client client;
    private TouristicPackage touristicPackage;

    public enum Status{
        CONFIRMATA, ASTEPTARE, ANULATA;

        public static Status fromString(String s){
            try{
                return Status.valueOf(s);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Reservation(int id, LocalDate reservationDate, Status status, int numOfPeople, Client client, TouristicPackage touristicPackage) {
        this.id = id;
        this.reservationDate = reservationDate;
        this.status = status;
        this.numOfPeople = numOfPeople;
        this.client = client;
        this.touristicPackage = touristicPackage;
    }

    public Reservation(LocalDate reservationDate, Status status, int numOfPeople, Client client, TouristicPackage touristicPackage) {
        this.id = -1;
        this.reservationDate = reservationDate;
        this.status = status;
        this.numOfPeople = numOfPeople;
        this.client = client;
        this.touristicPackage = touristicPackage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getNumOfPeople() {
        return numOfPeople;
    }

    public void setNumOfPeople(int numOfPeople) {
        this.numOfPeople = numOfPeople;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public TouristicPackage getTouristicPackage() {
        return touristicPackage;
    }

    public void setTouristicPackage(TouristicPackage touristicPackage) {
        this.touristicPackage = touristicPackage;
    }
}

