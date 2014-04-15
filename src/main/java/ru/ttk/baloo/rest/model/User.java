package ru.ttk.baloo.rest.model;


import org.springframework.beans.factory.annotation.Qualifier;

import javax.persistence.*;

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
@Table(name = "r_user")
@PersistenceContext(name="JpaPersistenceUnitDefault")
//@Qualifier(value = "entityManagerFactoryDefault")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_name", nullable = false, length = 255)
    private String userName;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    public User() {
        this.userName = "USER_NAME";
        this.password = "PASSWORD";
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
