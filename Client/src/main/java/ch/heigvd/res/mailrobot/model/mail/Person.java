package ch.heigvd.res.mailrobot.model.mail;

public class Person {
    private String firstname;
    private String lastname;
    private String address;

    public Person(String firstname, String lastname, String address) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getAddress() {
        return address;
    }
}
