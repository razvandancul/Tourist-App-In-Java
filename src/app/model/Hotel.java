package app.model;

public class Hotel {

    private int id;
    private String name;
    private int stars;
    private String adress;
    private Destination destination;

    public Hotel(int id, String name, int stars, String adress, Destination destination) {
        this.id = id;
        this.name = name;
        this.stars = stars;
        this.adress = adress;
        this.destination = destination;
    }

    public Hotel(String name, int stars, String adress, Destination destination) {
        this(-1,  name, stars, adress, destination);
    }

    @Override
    public String toString() {
        return name;
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

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }
}

