package com.algonquincollege.cst8277.models;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2020-03-26T15:22:06.182-0400")
@StaticMetamodel(PhonePojo.class)
public class PhonePojo_ extends PojoBase_ {
	public static volatile SingularAttribute<PhonePojo, String> areacode;
	public static volatile SingularAttribute<PhonePojo, String> phonenumber;
	public static volatile SingularAttribute<PhonePojo, EmployeePojo> owningEmployee;
}
