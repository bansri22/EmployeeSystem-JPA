/***************************************************************************f******************u************zz*******y**
 * File: MobilePhone.java
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

@DiscriminatorValue(value = "M")
public class MobilePhone extends PhonePojo implements Serializable {
    /** explicit set serialVersionUID */
    private static final long serialVersionUID = 1L;
  
    protected String provider;
  // TODO - additional properties to match MPHONE table
    /**
     * @return the value for provider
     */
    @Column(name=" PROVIDER")
    public String getProvider() {
        return provider;
    }
    /**
     * @param provider new value for provider
     */
    public void setProvider(String provider) {
        this.provider = provider;
    }

}