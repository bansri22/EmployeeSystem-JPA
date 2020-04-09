/***************************************************************************f******************u************zz*******y**
 * File: ProjectPojo.java
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
 * @author bansri shah 040920837
 */
package com.algonquincollege.cst8277.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

/**
 * Project class 
 */

@Entity(name="Projects")
@Table(name="PROJECTS")
@AttributeOverride(name = "id", column = @Column(name=" PROJ_ID"))
public class ProjectPojo extends PojoBase implements Serializable {
    /** explicit set serialVersionUID */
    private static final long serialVersionUID = 1L;
     

    private String description;
    private String name;
    protected List<EmployeePojo> employees;
    // TODO - persistent properties
    /**
     * @return the value for name
     */
@Column(name = "NAME")
    public String getName() {
        return name;
    }
/**
 * @param  name new value for name
 */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the value for description
     */
    @Column(name="DESCRIPTION")
    public String getDescription() {
        return description;
    }
    /**
     * @param  description new value for description
     */

    public void setDescription(String description) {
        this.description = description;
    }

    // JPA requires each @Entity class have a default constructor
    public ProjectPojo() {
    }
    /**
     * @return the value for projects
     */
   @ManyToMany(mappedBy="projects") // 'mappedBy' like @OneToMany
   public List<EmployeePojo> getEmployees()
   { return employees; 
   }
   /**
    * @param  projects new value for projects
    */
public void setEmployees(List<EmployeePojo> employees) {
    this.employees = employees;
}
   
}