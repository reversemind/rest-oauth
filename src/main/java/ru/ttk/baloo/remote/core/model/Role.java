package ru.ttk.baloo.remote.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

/**
 */
@Entity
@Table(name = "x_role")
public class Role {

    @Id
    @Column(name = "id", nullable = false, length = 36)
    String uuid;

    @Column(name = "name", nullable = true, length = 255)
    String role;

    public Role() {
        this.uuid = UUID.randomUUID().toString();
        this.role = role;
    }

    public Role(String uuid, String role) {
        this.uuid = uuid;
        this.role = role;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Role{" +
                "uuid='" + uuid + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}


