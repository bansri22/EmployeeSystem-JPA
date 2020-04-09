package com.algonquincollege.cst8277.models;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2020-03-10T16:01:05.113-0400")
@StaticMetamodel(EmployeeTask.class)
public class EmployeeTask_ {
	public static volatile SingularAttribute<EmployeeTask, String> description;
	public static volatile SingularAttribute<EmployeeTask, Timestamp> task_start;
	public static volatile SingularAttribute<EmployeeTask, Timestamp> task_end_date;
	public static volatile SingularAttribute<EmployeeTask, Boolean> task_done;
}
