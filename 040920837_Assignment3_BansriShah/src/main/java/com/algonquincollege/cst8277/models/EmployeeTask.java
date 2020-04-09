/***************************************************************************f******************u************zz*******y**
 * File: EmployeeTask.java
 * Course materials (20W) CST 8277
 * @author Mike Norman
 * @date 2020 02
 *  @author bansri shah 040920837
 */
package com.algonquincollege.cst8277.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;

//JPA Annotations here
@Embeddable
@Table(name="EmployeeTask")
public class EmployeeTask {

    protected String description;
    // TODO - additional properties to match EMPLOYEE_TASKS table
   protected Timestamp task_start;
   protected Timestamp task_end_date;
   protected boolean task_done;
   public EmployeeTask() {
   }
   /**
    * @return the value for description
    */
  @Column(name="TASK_DESCRIPTION")
   public String getDescription() {
       return description;
   }
  /**
   * @param description new value for description
   */
   public void setDescription(String description) {
       this.description = description;
   }
   /**
    * @return the value for taskstart
    */
   @Column(name=" TASK_START")
    public Timestamp getTask_start() {
    return task_start;
}
   /**
    * @param taskstart new value for taskstart
    */

public void setTask_start(Timestamp task_start) {
    this.task_start = task_start;
}
/**
 * @return the value for taskend
 */
@Column(name="TASK_END_DATE")
public Timestamp getTask_end_date() {
    return task_end_date;
}
/**
 * @param taskend new value for taskend
 */
public void setTask_end_date(Timestamp task_end_date) {
    this.task_end_date = task_end_date;
}
/**
 * @return the value for taskdone
 */
@Column(name="TASK_DONE")
public boolean isTask_done() {
    return task_done;
}
/**
 * @param  taskdone new value for  taskdone
 */
public void setTask_done(boolean task_done) {
    this.task_done = task_done;
}

   

}