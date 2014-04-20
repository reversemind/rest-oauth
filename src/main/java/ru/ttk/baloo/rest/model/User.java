package ru.ttk.baloo.rest.model;


import org.springframework.beans.factory.annotation.Qualifier;

import javax.persistence.*;
import java.util.UUID;

/**
 *
 *
 *
 -- Table: r_user

 -- DROP TABLE r_user;

 CREATE TABLE r_user
 (
 id bigint NOT NULL,
 user_name character(255) NOT NULL,
 password character varying,
 CONSTRAINT pk_x_user_id PRIMARY KEY (id )
 )
 WITH (
 OIDS=FALSE
 );
 ALTER TABLE r_user
 OWNER TO postgres;

 *
 */
@Entity
@Table(name = "rest_user")
public class User {

    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @Column(name = "user_name", nullable = false, length = 255)
    private String userName;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    public User() {
        this.id = UUID.randomUUID().toString();
        this.userName = "USER_NAME";
        this.password = "PASSWORD";
    }

    public User(String id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }

    public User(String userName, String password) {
        this.id = UUID.randomUUID().toString();
        this.userName = userName;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (!id.equals(user.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
