/***************************************************************************f******************u************zz*******y**
 * File: AddressPojo.java
 * Course materials (20W) CST 8277
 * @author Mike Norman
 * (Modified) @date 2020 02
 *
 * Copyright (c) 1998, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Original @authors dclarke, mbraeuer
 *  @author bansri shah 040920837
 */

package com.algonquincollege.cst8277.models;
import java.io.Serializable;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.algonquincollege.cst8277.models.PojoBase;

/**
 * Simple Address class
 */

@Entity(name="Address")
@Table(name="ADDRESS")
//JPA Annotations here
@AttributeOverride(name = "id", column = @Column(name="ADDR_ID"))
public class AddressPojo extends PojoBase implements Serializable {
    /** explicit set serialVersionUID */
    private static final long serialVersionUID = 1L;
    protected String city;
    private String country;
    private String postal;
    private String state;
    private String street;
    // TODO - additional properties needed to match ADDRESS table

    /**
     * JPA requires each @Entity class have a default constructor
     */
    public AddressPojo() {
        super();
    }
    /**
     * @return the value for city
     */
    @Column(name="CITY")
    public String getCity() {
        return city;
    }
    /**
     * @param city new value for city
     */
    public void setCity(String city) {
        this.city = city;
    }
    /**
     * @return the value for country
     */
    @Column(name="COUNTRY")
    public String getCountry() {
        return this.country;
    }
    /**
     * @param country new value for country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the value for postal
     */
     @Column(name="POSTAL")
    public String getPostal() {
        return this.postal;
    }
     /**
      * @param postal new value for postal
      */
    public void setPostal(String postal) {
        this.postal = postal;
    }
    /**
     * @return the value for state
     */

    @Column(name="STATE")
    public String getState() {
        return this.state;
    }
    /**
     * @param state new value for state
     */
    public void setState(String state) {
        this.state = state;
    }
    /**
     * @return the value for street
     */

    @Column(name="STREET")
    public String getStreet() {
        return this.street;
    }
    /**
     * @param street new value for street
     */
    public void setStreet(String street) {
        this.street = street;
    }


}