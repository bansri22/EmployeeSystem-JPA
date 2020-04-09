/***************************************************************************f******************u************zz*******y**
 * File: HomePhone.java
 * Course materials (20W) CST 8277
 * @author Mike Norman
 * @date 2020 02
 * @author bansri shah 040920837
 */
package com.algonquincollege.cst8277.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

//JPA Annotations here
@Entity
@DiscriminatorValue(value = "H")
public class HomePhone extends PhonePojo implements Serializable {
    /** explicit set serialVersionUID */
    private static final long serialVersionUID = 1L;
      // TODO - additional properties to match HPHONE table
    protected String googleMapDirections;
    /**
     * @return the value for googleMapDirections
     */
    @Column(name="MAP_COORDS")
    public String getGoogleMapDirections() {
        return googleMapDirections;
    }
    /**
     * @param googleMapDirections new value for googleMapDirections
     */
    public void setGoogleMapDirections(String googleMapDirections) {
        this.googleMapDirections = googleMapDirections;
    }
}