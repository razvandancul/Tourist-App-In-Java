package app.model;

public class Destination {
    private int id;
    private String country;
    private String city;
    private String climate;

    public Destination(int id, String country, String city, String climate) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.climate = climate;
    }

    public Destination(String country, String city, String climate) {
        this(-1, country, city, climate);
    }

    @Override
    public String toString() {
        return city + ", " + country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getClimate() {
        return climate;
    }

    public void setClimate(String climate) {
        this.climate = climate;
    }
}


