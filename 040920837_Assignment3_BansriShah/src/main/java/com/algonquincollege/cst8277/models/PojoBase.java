/***************************************************************************f******************u************zz*******y**
 * File: PojoBase.java
 * Course materials (20W) CST 8277
 * @author Mike Norman
 * @author bansri shah 040920837
 * @date 2020 02
 */
package com.algonquincollege.cst8277.models;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * Abstract class that is base of (class) hierarchy for all c.a.cst8277.models @Entity classes
 */
@MappedSuperclass
@Access(AccessType.PROPERTY)
@EntityListeners({PojoListener.class})
public abstract class PojoBase implements Serializable {
    private static final long serialVersionUID = 1L;

    protected int id;
    protected int version;
    protected LocalDateTime   C_DATE;
    protected LocalDateTime   P_DATE;
    // TODO - add audit properties

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Version
    public int getVersion() {
        return version;
    }
    public void setVersion(int version) {
        this.version = version;
    }
    /**
     * @return the value for created date
     */
    @Column(name="CREATED_DATE")
    public LocalDateTime  getCreatedDate() {
      
        return  C_DATE;
    }
    /**
     * @param  created date new value for  created date
     */
    public void setCreatedDate(LocalDateTime  C_DATE) {
        this.C_DATE = C_DATE;
    }
    /**
     * @return the value for updated date
     */
    @Column(name="UPDATED_DATE")
    public LocalDateTime  getUpdatedDate() {
        
        return  P_DATE;
    }
    /**
     * @param  updated date new value for  updated date
     */
    public void setUpdatedDate(LocalDateTime  P_DATE) {
        this.P_DATE = P_DATE;
    }

    // Strictly speaking, JPA does not require hashcode() and equals(),
    // but it is a good idea to have one that tests using the PK (@Id) field
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof PojoBase)) {
            return false;
        }
        PojoBase other = (PojoBase)obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }
}