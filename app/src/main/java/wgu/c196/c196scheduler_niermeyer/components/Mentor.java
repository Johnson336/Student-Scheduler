package wgu.c196.c196scheduler_niermeyer.components;

public class Mentor {
    private int id;
    private String name;
    private String phone;
    private String email;
    public Mentor() {

    }
    public Mentor(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }
    // Setters
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    // Getters
    public int getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public String getPhone() {
        return this.phone;
    }
    public String getEmail() {
        return this.email;
    }
}
