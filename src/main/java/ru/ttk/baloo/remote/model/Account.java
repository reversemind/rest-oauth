package ru.ttk.baloo.remote.model;

import javax.persistence.*;
import java.util.UUID;

/**
 *
 */
@Entity
@Table(name = "x_account")
public class Account {

    @Id
    @Column(name = "id", nullable = false, length = 36)
    String uuid;

    @Column(name = "locked", nullable = true)
    Boolean locked;

    @Column(name = "password", nullable = true, length = 255)
    String password;

    @Column(name = "person", nullable = true, length = 255)
    String personUri;

    @Column(name = "principal_name", nullable = true, length = 255)
    String principalName;

    public Account() {
        this.uuid = UUID.randomUUID().toString();
        this.locked = false;
        this.password = null;
        this.personUri = null;
        this.principalName = null;
    }

    public Account(String uuid, boolean locked, String password, String personUri, String principalName) {
        this.uuid = uuid;
        this.locked = locked;
        this.password = password;
        this.personUri = personUri;
        this.principalName = principalName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Boolean isLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPersonUri() {
        return personUri;
    }

    public void setPersonUri(String person) {
        this.personUri = person;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    @Override
    public String toString() {
        return "Account{" +
                "uuid='" + uuid + '\'' +
                ", locked=" + locked +
                ", password='" + password + '\'' +
                ", person='" + personUri + '\'' +
                ", principalName='" + principalName + '\'' +
                '}';
    }
}
