package model;

import java.util.Objects;

public class User {

    private Long id;
    private String email;
    private String password;

    //конструктор1
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    //конструктор2
    public User(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return this.email.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }

        if(o == null || getClass() != o.getClass()) return false;

        User user = (User) o;
        return email.equals(user.email);
    }
}