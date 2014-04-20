package ru.ttk.baloo.rest.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 *
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Base implements Serializable {

    @Id
    @NotNull
    @Size(min = 36, max = 36)
    @Column(name = "id", nullable = false, length = 36)
    protected String id;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", nullable = false)
    protected Date createDate;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date", nullable = false)
    protected Date updateDate;

    @NotNull
    @Column(name = "created_by_person_id", nullable = false, length = 36)
    protected String createdByPersonId;

    @NotNull
    @Column(name = "updated_by_person_id", nullable = false, length = 36)
    protected String updatedByPersonId;


    public Base() {
        this.id = UUID.randomUUID().toString();
        this.createDate = new Date();
        this.updateDate = this.createDate;
        this.createdByPersonId = null;
        this.updatedByPersonId = null;
    }

    public Base(String createdByPersonId, String updatedByPersonId) {
        this.id = UUID.randomUUID().toString();
        this.createDate = new Date();
        this.updateDate = this.createDate;
        this.createdByPersonId = createdByPersonId;
        this.updatedByPersonId = updatedByPersonId;
    }

    public Base(String createdByPersonId) {
        this.id = UUID.randomUUID().toString();
        this.createDate = new Date();
        this.updateDate = this.createDate;
        this.createdByPersonId = createdByPersonId;
        this.updatedByPersonId = createdByPersonId;
    }

    public Base(String id, Date createDate, Date updateDate, String createdByPersonId, String updatedByPersonId) {
        this.id = id;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.createdByPersonId = createdByPersonId;
        this.updatedByPersonId = updatedByPersonId;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getCreatedByPersonId() {
        return createdByPersonId;
    }

    public void setCreatedByPersonId(String createdByPersonId) {
        this.createdByPersonId = createdByPersonId;
    }

    public String getUpdatedByPersonId() {
        return updatedByPersonId;
    }

    public void setUpdatedByPersonId(String updatedByPersonId) {
        this.updatedByPersonId = updatedByPersonId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Base)) return false;

        Base base = (Base) o;

        if (!id.equals(base.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Base{" +
                "id='" + id + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", createdByPersonId='" + createdByPersonId + '\'' +
                ", updatedByPersonId='" + updatedByPersonId + '\'' +
                '}';
    }
}
