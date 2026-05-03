package app.model;

import java.util.Objects;

public class Guides {

    private int id;
    private String name;
    private String phoneNum;
    private String language;

    public Guides(int id, String name, String phoneNum, String language) {
        this.id = id;
        this.name = name;
        this.phoneNum = phoneNum;
        this.language = language;
    }

    public Guides(String name, String phoneNum, String language) {
        this.id = -1;
        this.name = name;
        this.phoneNum = phoneNum;
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Guides guides = (Guides) o;
        return id == guides.id ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
