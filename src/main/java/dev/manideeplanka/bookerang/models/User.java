package dev.manideeplanka.bookerang.models;

import org.jdbi.v3.core.mapper.reflect.ColumnName;



public class User {
    private String username;
    private String password;
    @ColumnName("first_name")
    private String firstName;
    @ColumnName("last_name")
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public String getPassword() {
        return password;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
