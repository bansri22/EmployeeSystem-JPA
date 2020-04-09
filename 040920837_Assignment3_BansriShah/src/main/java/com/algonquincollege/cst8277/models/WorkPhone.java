/***************************************************************************f******************u************zz*******y**
 * File: WorkPhone.java
 * Course materials (20W) CST 8277
 * @author Mike Norman
 * @date 2020 02
 * @author bansri shah 040920837
 */
package com.algonquincollege.cst8277.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

//JPA Annotations here
@Entity
@DiscriminatorValue(value = "W")
public class WorkPhone extends PhonePojo implements Serializable {
    /** explicit set serialVersionUID */
    private static final long serialVersionUID = 1L;
    protected String DEPARTMENT;
    /**
     * @return the value for DEPARTMENT
     */
    @Column(name="DEPARTMENT")
    public String getDEPARTMENT() {
        return DEPARTMENT;
    }
    /**
     * @param  DEPARTMENT new value for DEPARTMENT
     */
    public void setDEPARTMENT(String dEPARTMENT) {
        DEPARTMENT = dEPARTMENT;
    }
    
  // TODO - additional properties to match MPHONE table

}