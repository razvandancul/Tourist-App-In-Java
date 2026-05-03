package app.model;

public class Client {

    private int id;
    private String name;
    private String surname;
    private String email;
    private String cnp;
    private String phoneNum;
    private String role;

    public Client(int id, String name, String surname, String email, String cnp, String phoneNum, String role) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.cnp = cnp;
        this.phoneNum = phoneNum;
        this.role = role;
    }

    public Client(String name, String surname, String email, String cnp, String phoneNum, String role) {
        this(-1, name, surname, email, cnp, phoneNum, role);
    }

    public boolean isBusiness(){
        return role.equalsIgnoreCase("BUSINESS");
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
