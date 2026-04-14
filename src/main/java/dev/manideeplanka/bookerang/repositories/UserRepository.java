package dev.manideeplanka.bookerang.repositories;

import dev.manideeplanka.bookerang.common.DatabaseConfig;
import dev.manideeplanka.bookerang.models.User;
import org.jdbi.v3.core.Jdbi;

import java.util.Optional;

public class UserRepository {

    Jdbi jdbi;

    public UserRepository() {
        this.jdbi = DatabaseConfig.getJdbi();
    }


    public Optional<User> findByUsername(String username) throws Exception {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM users WHERE username = :username").bind("username", username).mapToBean(User.class).findOne()
        );
    }


    public void signup(User user) throws Exception {
        jdbi.withHandle(handle -> {
                    handle.createUpdate("INSERT INTO users (first_name, last_name, username, password) VALUES (:firstName, :lastName, :username, :password)")
                            .bind("username", user.getUsername())
                            .bind("password", user.getPassword())
                            .bind("firstName", user.getFirstName())
                            .bind("lastName", user.getLastName())
                            .execute();
                    return null; //only returning this to avoid the warning in IDE, no practical use
                }
        );
    }

}
