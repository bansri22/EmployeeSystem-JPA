/***************************************************************************f******************u************zz*******y**
 * File: EmployeePojo.java
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
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.algonquincollege.cst8277.models.PojoBase;

/**
 * The Employee class demonstrates several JPA features:
 * <ul>
 * <li>OneToOne relationship
 * <li>OneToMany relationship
 * <li>ManyToMany relationship
 * </ul>
 */
@Entity(name = "Employee")
@Table(name = "EMPLOYEE")
@AttributeOverride(name = "id", column = @Column(name="EMP_ID"))
public class EmployeePojo extends PojoBase implements Serializable {
    /** explicit set serialVersionUID */
    private static final long serialVersionUID = 1L;

    protected String firstName;
    protected String lastName;
    protected String email;
    protected String title;
    protected Double salary;
    protected AddressPojo address;
    protected List<PhonePojo> phones;
    protected Set<ProjectPojo> projects;
    protected List<EmployeeTask> employeetasks;
  
    // TODO - additional properties for 1:1, 1:M, M:N

    // JPA requires each @Entity class have a default constructor
    public EmployeePojo() {
        super();
    }
    /**
     * @return the value for firstName
     */
    @Column(name = "FNAME")
    public String getFirstName() {
        return firstName;
    }
    /**
     * @param firstName new value for firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the value for lastName
     */
    @Column(name = "LNAME")
    public String getLastName() {
        return lastName;
    }
    /**
     * @param lastName new value for lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the value for email
     */
    @Column(name = "EMAIL")
    public String getEmail() {
        return email;
    }
    /**
     * @param email new value for email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the value for title
     */
    public String getTitle() {
        return title;
    }
    /**
     * @param title new value for title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the value for salary
     */
    @Column(name = "SALARY")
    public Double getSalary() {
        return salary;
    }
    /**
     * @param salary new value for salary
     */
    public void setSalary(Double salary) {
        this.salary = salary;
    }
    /**
     * @return the value for address(one to one relationship with employee)
     */

    @OneToOne
    @JoinColumn(name="ADDR_ID")
    public AddressPojo getAddress()
    {
        return address;
    }
    /**
     * @param address new value for address
     */
    public void setAddress(AddressPojo address) {
        this.address = address;
    }
    /**
     * @return the value for phone(one to many relationship with employee)
     */
    @OneToMany(mappedBy = "owningEmployee",cascade=CascadeType.ALL,orphanRemoval =true)
    public List<PhonePojo> getPhones()
    {
        return phones;
    }
    /**
     * @param phones new value for phones
     */
   
    public void setPhones(List<PhonePojo> phones) {
        this.phones = phones;
    }
    /**
     * @return the value for projects(many to many relationship with employee)
     */
    @ManyToMany
    @JoinTable(name="EMP_PROJ", joinColumns=@JoinColumn(name="EMP_ID",referencedColumnName="EMP_ID"),
    inverseJoinColumns = @JoinColumn(name="PROJ_ID",referencedColumnName="PROJ_ID"))
    public Set<ProjectPojo> getProjects() 
    { 
        return projects; 
        }
    /**
     * @param projects new value for projects
     */
    public void setProjects(Set<ProjectPojo> projects) {
        this.projects = projects;
    }
    /**
     * @return the value for employee task
     */
   
    @ElementCollection
    @CollectionTable(name="EMPLOYEE_TASKS",joinColumns = @JoinColumn(name="OWNING_EMP_ID"))
   
  public List<EmployeeTask> getEmployeeTasks()
    {
        return employeetasks;
    }
    /**
     * @param employeetask new value for employeetask
     */
    public void setEmployeeTasks(List<EmployeeTask> employeetasks) {
        this.employeetasks =employeetasks;
    }
}