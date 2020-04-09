/***************************************************************************f******************u************zz*******y**
 * File: EmployeeTestSuite.java
 * Course materials (20W) CST 8277
 * @author (original) Mike Norman
 * @author bansri shah 040920837
 *
 */
package com.algonquincollege.cst8277.models;


import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNotNull;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Root;

import org.eclipse.persistence.sessions.Project;
import org.hamcrest.Matcher;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMConfiguration;

import com.algonquincollege.cst8277.TestSuiteBase;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmployeeSystemTestSuite extends TestSuiteBase {
    private static final Class<?> _thisClaz = MethodHandles.lookup().lookupClass();
    private static final Logger logger = LoggerFactory.getLogger(_thisClaz);

    private static final ch.qos.logback.classic.Logger eclipselinkSqlLogger = (ch.qos.logback.classic.Logger) LoggerFactory
            .getLogger(ECLIPSELINK_LOGGING_SQL);

    private static final String SELECT_EMPLOYEE_1 = "SELECT EMP_ID, CREATED_DATE, EMAIL, FNAME, LNAME, SALARY, TITLE, UPDATED_DATE, VERSION, ADDR_ID FROM EMPLOYEE WHERE (EMP_ID = ?)";
 
    /**
     * check when there is no employee in database
     */
    @Test
    public void test01_no_Employees_at_start() {
        EntityManager em = emf.createEntityManager();

        logger.info("no employees, just demonstrating how to capture generated SQL");

        ListAppender<ILoggingEvent> listAppender = attachListAppender(eclipselinkSqlLogger, ECLIPSELINK_LOGGING_SQL);
        EmployeePojo emp1 = em.find(EmployeePojo.class, 1);
        detachListAppender(eclipselinkSqlLogger, listAppender);

        assertNull(emp1);
        List<ILoggingEvent> loggingEvents = listAppender.list;
        assertEquals(1, loggingEvents.size());
        assertThat(loggingEvents.get(0).getMessage(), startsWith(SELECT_EMPLOYEE_1));

        em.close();

    }

    /**
     * check data for employee that first insert and then see email is correct or not
     */
    @Test
    public void test02_check_data_in_the_employeePojo() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction TX = em.getTransaction();
        TX.begin();
        EmployeePojo e1 = new EmployeePojo();
        e1.setEmail("sss@email.com");
        e1.setFirstName("bansri");
        e1.setLastName("xyz");
        em.persist(e1);
        TX.commit();
        TypedQuery<EmployeePojo> findquery = em.createQuery("select e from Employee e where e.email =:param1",
                EmployeePojo.class);
        findquery.setParameter("param1", "sss@email.com");
        EmployeePojo e2 = findquery.getSingleResult();
        assertEquals(e2.getEmail(), "sss@email.com");

        em.close();

    }
    /**
     * count how many tasks are given to particular employee
     */
   @Test
    public void test03_count_tasks_for_employee() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction TX = em.getTransaction();
        TX.begin();
        EmployeePojo e1 = new EmployeePojo();
        EmployeeTask t1 = new EmployeeTask();
        t1.setDescription("hello");
        EmployeeTask t2 = new EmployeeTask();
        t2.setDescription("hi");
        EmployeeTask t3 = new EmployeeTask();
        t3.setDescription("hi1");
        List<EmployeeTask> T = new ArrayList<>();
        T.add(t1);
        T.add(t2);
        T.add(t3);
        e1.setId(1);
        e1.setEmployeeTasks(T);

        em.persist(e1);
        TX.commit();
        TypedQuery<EmployeePojo> findquery = em.createQuery("select e from Employee e", EmployeePojo.class);
        List<EmployeePojo> e = findquery.getResultList();
        EmployeePojo emp1 = em.find(EmployeePojo.class, 1);

        assertEquals(3, emp1.getEmployeeTasks().size());
        em.close();
    }
   /**
    * check added data in address db is not null
    */
    @Test
    public void test04_check_data_in_address() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction TX = em.getTransaction();
        TX.begin();
        AddressPojo e1 = new AddressPojo();
        e1.setId(1000);
        e1.setCity("AHMEDABAD");
        em.persist(e1);
        TX.commit();
        assertNotNull(e1.getId());
        em.close();

    }
    /**
     * check total number of employee is not zero
     */
    @Test
    public void test05_count_total_number_employee_not_zero() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Long> findquery = em.createQuery("select count(e) from Employee e", Long.class);
        Long e = findquery.getSingleResult();
        assertNotNull(e);
        em.close();
    }
 
    /**
     *check project description which is same as in database or not
     */
    @Test
    public void test06_check_project_for_employee() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction TX = em.getTransaction();
        TX.begin();
        ProjectPojo e1 = new ProjectPojo();
        e1.setName("bansri");
        e1.setDescription("staff");
        em.persist(e1);
        TX.commit();
        assertEquals(e1.getDescription(), "staff");
        em.close();
    }
    /**
     *check particular employee has project(so check project is not null at employee id 5)
     */
    @Test
    public void test07_add_project_for_employee() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction TX = em.getTransaction();
        TX.begin();
        EmployeePojo e1 = new EmployeePojo();
        ProjectPojo t1 = new ProjectPojo();
        t1.setId(5);
        t1.setDescription("CRUD FUNCTIONALITY");
        
        Set<ProjectPojo> T = new Set<ProjectPojo>() {

            @Override
            public <T> T[] toArray(T[] a) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Object[] toArray() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public int size() {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean remove(Object o) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public Iterator<ProjectPojo> iterator() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public boolean isEmpty() {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean contains(Object o) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public void clear() {
                // TODO Auto-generated method stub

            }

            @Override
            public boolean addAll(Collection<? extends ProjectPojo> c) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean add(ProjectPojo e) {
                // TODO Auto-generated method stub
                return false;
            }
        };
        T.add(t1);
        e1.setId(5);
        e1.setProjects(T);
        TX.commit();
        em.persist(e1);
        EmployeePojo emp1 = em.find(EmployeePojo.class, 5);

        assertNotNull(emp1.getProjects());
        em.close();

    }
    /**
     *check any employee has no projects using assertnotnull
     */
    @Test
    public void test08_check_projects_not_empty_for_employee() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<EmployeePojo> findquery = em.createQuery("select e from Employee e where e.projects is empty",
                EmployeePojo.class);
        List<EmployeePojo> e = findquery.getResultList();
        assertNotNull(e);
        em.close();
    }
    
    /**
     *check how many employee has salary less than 20 dollars
     */
    @Test
    public void test09_check_salary_morethan_zero() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction TX = em.getTransaction();
        TX.begin();

        TypedQuery<EmployeePojo> findquery = em.createQuery("select count(e) from Employee e where e.salary < 20",
                EmployeePojo.class);
        List<EmployeePojo> e = findquery.getResultList();
       

        TX.commit();
        assertEquals(e.size(), 1);
        em.close();

    }
    /**
     *check how many employee in database 
     */

    @Test
    public void test10_size_of_employee() {
        EntityManager em = emf.createEntityManager();

        EntityTransaction TX = em.getTransaction();
        TX.begin();
        EmployeePojo e1 = new EmployeePojo();
        e1.setEmail("ss@email.com");
        e1.setFirstName("bansri1");
        e1.setLastName("xyz1");
        em.persist(e1);

        e1 = new EmployeePojo();
        e1.setEmail("ss2@email.com");
        e1.setFirstName("bansri12");
        e1.setLastName("xyz12");
        em.persist(e1);

        e1 = new EmployeePojo();
        e1.setEmail("ss92@email.com");
        e1.setFirstName("bansri192");
        e1.setLastName("xyz192");
        em.persist(e1);
        e1 = new EmployeePojo();
        e1.setEmail("ss92@email.com");
        e1.setFirstName("bansri192");
        e1.setLastName("xyz192");
        em.persist(e1);
        TX.commit();

        TypedQuery<EmployeePojo> findquery = em.createQuery("select e from Employee e", EmployeePojo.class);
        List<EmployeePojo> e = findquery.getResultList();

        assertEquals(6, e.size());

        em.close();
    }
    /**
     *first add employee and then delete employee then check now it is null or not
     */
    @Test
    public void test11_delete_for_employyee_size() {
        EntityManager em = emf.createEntityManager();
        EmployeePojo e1 = new EmployeePojo();
        e1.setId(7);
        e1.setEmail("ss@email.com");
        e1.setFirstName("bansri1");
        e1.setLastName("xyz1");
        em.persist(e1);

        EmployeePojo emp1 = em.find(EmployeePojo.class, 7);
        em.remove(emp1);
        EmployeePojo emp2 = em.find(EmployeePojo.class, 7);
        assertNull(emp2);

    }
    /**
     *check employee-address is exists in database or not
     */
    @Test
    public void test12_check_address_not_null() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction TX = em.getTransaction();
        TX.begin();
        AddressPojo e1 = new AddressPojo();
        e1.setCity("AHMEDABAD");
        em.persist(e1);
        TX.commit();
        
        assertNotNull(e1.getId());
        em.close();
    }
    /**
     *first add address for employee and then delete address then check now it is exists for employee or not using(assertnull)
     */
    @Test
    public void test13_delete_and_check_not_exists() {
        EntityManager em = emf.createEntityManager();
        AddressPojo e1 = new AddressPojo();
        e1.setId(3);
        e1.setCity("gujarat");
        em.persist(e1);

        AddressPojo emp1 = em.find(AddressPojo.class, 3);
        em.remove(emp1);
        AddressPojo emp2 = em.find(AddressPojo.class, 3);
        assertNull(emp2);

    }
    /**
     *first add address for employee and then delete address then check now it is exists for employee or not using(assertnull)
     */
    @Test
    public void test14_size_of_addresspojo() {
        EntityManager em = emf.createEntityManager();

        EntityTransaction TX = em.getTransaction();
        TX.begin();
        AddressPojo e1 = new AddressPojo();
        e1.setId(4);
        e1.setCity("ahmedabad");
        e1.setCountry("india");
        em.persist(e1);

        e1 = new AddressPojo();
        e1.setId(5);
        e1.setCity("OTTAWA");
        e1.setCountry("CANADA");
        em.persist(e1);

        TX.commit();

        TypedQuery<AddressPojo> findquery = em.createQuery("select e from Address e", AddressPojo.class);
        List<AddressPojo> e = findquery.getResultList();

        assertEquals(4, e.size());

        em.close();
    }
    /**
     *first add project for employee and then delete project then check now it is still exists for employee or not using(assertnull)
     */
    @Test
    public void test15_something() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction TX = em.getTransaction();
        TX.begin();
        ProjectPojo e1 = new ProjectPojo();
        e1.setId(4);
        e1.setDescription("gujarat");
        em.persist(e1);
       
        ProjectPojo emp1 = em.find(ProjectPojo.class, 4);
        em.remove(emp1);
        TX.commit();
        ProjectPojo emp2 = em.find(ProjectPojo.class, 4);
        assertNull(emp2);
    }
    /**
     * check projectpojo for id=4 and 5 has same description or not
     */
    @Test
    public void test16_size_of_project() {
        EntityManager em = emf.createEntityManager();

        EntityTransaction TX = em.getTransaction();
        TX.begin();
        ProjectPojo e1 = new ProjectPojo();
        e1.setId(4);
        e1.setDescription("hello");

        em.persist(e1);

        e1 = new ProjectPojo();
        e1.setId(5);
        e1.setDescription("hi");
        em.persist(e1);

        TX.commit();

        ProjectPojo e = em.find(ProjectPojo.class, 5);
        ProjectPojo e2 = em.find(ProjectPojo.class, 4);
        assertNotEquals(e2.getDescription(), e.getDescription());

        em.close();
    }
    

  
    /**
     * check adding workphone exist or not using(assertnotnull)
     */

    @Test
    public void test17_check_value_in_workphone() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction TX = em.getTransaction();
        TX.begin();
        WorkPhone e1 = new WorkPhone();
        e1.setId(1);
        e1.setAreacode("123");

        em.persist(e1);

        TX.commit();
        assertNotNull(e1.getId());

        em.close();
    }
    /**
     *first add two phone for different areacode then check areacode=123 using query exist at which id number
     */
    @Test
    public void test18_check_field_exists_at_which_id_number() {

        EntityManager em = emf.createEntityManager();
        EntityTransaction TX = em.getTransaction();
        TX.begin();
        PhonePojo p1 = new MobilePhone();
        p1.setId(1);
        p1.setAreacode("123");

        em.persist(p1);
        TX.commit();
        TX.begin();
        PhonePojo pp1 = new MobilePhone();
        pp1.setId(2);
        pp1.setAreacode("3");

        em.persist(pp1);
        TX.commit();
        TypedQuery<PhonePojo> findquery = em.createQuery("Select e from Phone e where e.areacode = '123' ",
                PhonePojo.class);
        List<PhonePojo> e = findquery.getResultList();

        assertEquals(2, e.size());

        em.close();
    }
    /**
     *first add data in mobilephone and check it exists in phonepojo class or not
     */
    @Test
    public void test19_check_mobile_phone() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction TX = em.getTransaction();
        TX.begin();
        MobilePhone e1 = new MobilePhone();
        e1.setId(1);
        e1.setAreacode("123");

        em.persist(e1);

        TX.commit();
        PhonePojo e2 = new MobilePhone();
        assertNotNull(e2.getId());

        em.close();

    }
    /**
     *find employee whose title start from M using (Like) result:there is no any employee whose title start with m*/
    @Test
    public void test20_check_title() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction TX = em.getTransaction();
        TX.begin();

        TypedQuery<EmployeePojo> findquery = em.createQuery("Select e from Employee e where e.title LIKE 'M%' ",
                EmployeePojo.class);
        List<EmployeePojo> e = findquery.getResultList();

        TX.commit();
        assertEquals(0, e.size());

        em.close();

    }
    /**
     *find employee max salary 
      **/
   
    @Test
    public void test21_check_exists_projects() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction TX = em.getTransaction();
        TX.begin();
        EmployeePojo e2 = new EmployeePojo();

        TypedQuery<Long> findquery = em.createQuery("Select MAX(e.salary) from Employee e", Long.class);
        Long e = findquery.getSingleResult();

        TX.commit();
        assertEquals(64, e.SIZE);

        em.close();
    }
    /**
     * first check how many employee hasn version= 1  
      */
    @Test
    public void test22_something() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction TX = em.getTransaction();
        TX.begin();
       
        TX.commit();
        TypedQuery<EmployeePojo> findquery = em.createQuery("select e from Employee e where e.version = 1", EmployeePojo.class);
        List<EmployeePojo> e = findquery.getResultList();
       

        assertEquals(6, e.size());
        em.close();
    }
  
    /**
     *check adding homephone exist or not using(assertnotnull)
      */
    @Test
    public void test23_check_value_in_homephone() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction TX = em.getTransaction();
        TX.begin();
        HomePhone e1 = new HomePhone();
        e1.setId(1);
        e1.setAreacode("123");

        em.persist(e1);

        TX.commit();
        assertNotNull(e1.getId());

        em.close();

    }
    /**
     * check the email of employee with updated email 
      */

    @Test
    public void test24_update_email_employee() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction TX = em.getTransaction();
        TX.begin();
        EmployeePojo e2 = new EmployeePojo();
        e2.setId(7);
        e2.setEmail("A@GMAIL.COM");
        em.persist(e2);
        TX.commit();
        TX.begin();
        EmployeePojo emp1 = em.find(EmployeePojo.class, 7);
        emp1.setEmail("b@gmail.com");
        em.merge(emp1);
        TX.commit();
        EmployeePojo emp2 = em.find(EmployeePojo.class, 7);
        assertEquals("b@gmail.com", emp2.getEmail());

        em.close();
    }

    /**
     * check the particular employee has how many phones
      */
    @Test
    public void test25_something() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction TX = em.getTransaction();
        TX.begin();

        PhonePojo P1 = new MobilePhone();
        PhonePojo p2 = new HomePhone();
        List<PhonePojo> a1 = new ArrayList<PhonePojo>();
        a1.add(P1);
        a1.add(p2);
        EmployeePojo e1 = new EmployeePojo();
        e1.setId(6);
        e1.setPhones(a1);
        em.persist(e1);
        TX.commit();
        EmployeePojo emp2 = em.find(EmployeePojo.class, 6);
        assertEquals(2, emp2.getPhones().size());
        em.close();

    }
    /**
     * check updated value of address class field-city
      */
    @Test
    public void test26_UPDATE_ADDRESS_CLASS() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction TX = em.getTransaction();
        TX.begin();
        AddressPojo e2 = new AddressPojo();
        e2.setId(7);
        e2.setCity("AHMEDABAD");
        em.persist(e2);
        TX.commit();
        TX.begin();
        AddressPojo emp1 = em.find(AddressPojo.class, 7);
        emp1.setCity("GUJARAT");
        em.merge(emp1);
        TX.commit();
        AddressPojo emp2 = em.find(AddressPojo.class, 7);
        assertEquals("GUJARAT", emp2.getCity());

        em.close();
    }
    /**
     *  one-to one relationship between employee-address
      */

    @Test
    public void test27_something() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction TX = em.getTransaction();
        TX.begin();
        EmployeePojo e1 = new EmployeePojo();
        AddressPojo a1 = new AddressPojo();
        e1.setId(4);
        e1.setAddress(a1);
        em.persist(e1);
        em.persist(a1);
        TX.commit();
        EmployeePojo emp2 = em.find(EmployeePojo.class, 4);
        AddressPojo a2 = emp2.getAddress();
        assertEquals(a1, a2);

    }
    /**
     *  many-to many relationship between employee-projects(employee can have more projects)
      */

    @Test
    public void test28_something() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction TX = em.getTransaction();
        TX.begin();

        ProjectPojo P1 = new ProjectPojo();
        ProjectPojo p2 = new ProjectPojo();
        ProjectPojo p3 = new ProjectPojo();

        Set<ProjectPojo> a1 = new Set<ProjectPojo>() {

            @Override
            public <T> T[] toArray(T[] a) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Object[] toArray() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public int size() {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean remove(Object o) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public Iterator<ProjectPojo> iterator() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public boolean isEmpty() {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean contains(Object o) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public void clear() {
                // TODO Auto-generated method stub

            }

            @Override
            public boolean addAll(Collection<? extends ProjectPojo> c) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean add(ProjectPojo e) {
                // TODO Auto-generated method stub
                return false;
            }
        };
        a1.add(P1);
        a1.add(p2);
        a1.add(p3);
        EmployeePojo e1 = new EmployeePojo();
        e1.setId(7);
        e1.setProjects(a1);

        TX.commit();
        em.persist(e1);
        EmployeePojo emp2 = em.find(EmployeePojo.class, 7);
        assertNotNull(emp2.getProjects());

        em.close();
    }
    /**
     *  many-to many relationship between projects-employee (one project can have many employee )
      */

    @Test
    public void test29_something() {

        EntityManager em = emf.createEntityManager();
        EntityTransaction TX = em.getTransaction();
        TX.begin();

        EmployeePojo e1 = new EmployeePojo();
        EmployeePojo e2 = new EmployeePojo();
        EmployeePojo e3 = new EmployeePojo();

        List<EmployeePojo> a1 = new ArrayList<EmployeePojo>();

        a1.add(e1);
        a1.add(e2);
        a1.add(e3);
        ProjectPojo p1 = new ProjectPojo();
        p1.setId(2);
        p1.setEmployees(a1);

        TX.commit();
        em.persist(p1);
        ProjectPojo p2 = em.find(ProjectPojo.class, 2);
        assertEquals(3, p2.getEmployees().size());
        em.close();
    }
    /**
     * update one mobile phone and then check employee has 3 phones or not 
      */
 
    @Test
    public void test30_something() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction TX = em.getTransaction();
        TX.begin();
        PhonePojo P1 = new MobilePhone();
        PhonePojo p2 = new HomePhone();
        List<PhonePojo> a1 = new ArrayList<PhonePojo>();
        a1.add(P1);
        a1.add(p2);
        EmployeePojo e1 = new EmployeePojo();
        e1.setId(1);
        e1.setPhones(a1);

        TX.commit();
        TX.begin();
        PhonePojo p3 = new WorkPhone();
        a1.add(p3);
        EmployeePojo emp1 = em.find(EmployeePojo.class, 1);
        emp1.setPhones(a1);
        em.merge(emp1);
        TX.commit();
        EmployeePojo emp2 = em.find(EmployeePojo.class, 1);
        assertEquals(3, emp2.getPhones().size());

        em.close();

    }
    /**
     * check  areacode from phone id it is true or not
      */
    

    @Test
    public void test31_something() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction TX = em.getTransaction();
        TX.begin();
        TypedQuery<PhonePojo> query = em.createQuery("SELECT ph FROM Employee e JOIN e.phones ph WHERE ph.id=1",
                PhonePojo.class);
        List<PhonePojo> e = query.getResultList();
        PhonePojo p1 = em.find(PhonePojo.class, 1);
        TX.commit();
        assertEquals("123", p1.getAreacode());

        em.close();
    }
    /**
     * check emploee which has id=7 has at leat one project which is not null.
      */

    @Test
    public void test32_something() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction TX = em.getTransaction();
        TX.begin();
        TypedQuery<EmployeePojo> query = em.createQuery("SELECT e FROM Employee e WHERE e.id=7", EmployeePojo.class);
        List<EmployeePojo> e = query.getResultList();
        EmployeePojo p1 = em.find(EmployeePojo.class, 7);
        TX.commit();
        assertNotNull(p1.getProjects());
        em.close();

    }
    /**
     * check employee task is done or not by calling employee .
      */

    @Test
    public void test33_something() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction TX = em.getTransaction();
        TX.begin();
        EmployeeTask t1 = new EmployeeTask();
        t1.setTask_done(false);
        EmployeePojo e1 = new EmployeePojo();
        List<EmployeeTask> T = new ArrayList<>();
        T.add(t1);

        e1.setId(2);
        e1.setEmployeeTasks(T);

        em.persist(e1);
        TX.commit();
        EmployeeTask tt1 = new EmployeeTask();
        EmployeePojo p1 = em.find(EmployeePojo.class, 2);

        assertEquals(false, p1.getEmployeeTasks().contains(tt1.isTask_done()));
        em.close();

    }
    /**
     * find project description length .
      */

    
    @Test
    public void test34_something() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction TX = em.getTransaction();
        TX.begin();

        TX.commit();
        ProjectPojo p1 = em.find(ProjectPojo.class, 3);

        assertEquals(2, p1.getDescription().length());
        em.close();
    }
    /**
     *check how many employee has  lower case for title of employee like m
      */

    @Test
    public void test35_something() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction TX = em.getTransaction();
        TX.begin();
        EmployeePojo e = new EmployeePojo();
        e.setTitle("mmm");
        em.persist(e);
        TX.commit();
        TypedQuery<EmployeePojo> findquery = em.createQuery("Select e from Employee e where lower(e.title) LIKE 'm%' ",
                EmployeePojo.class);
        List<EmployeePojo> e1 = findquery.getResultList();

        assertEquals(1, e1.size());

        em.close();

    }
    /**
     *check how many employee has  upper case for title of employee like M
      */

    @Test
    public void test36_something() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction TX = em.getTransaction();
        TX.begin();
        EmployeePojo e = new EmployeePojo();
        e.setTitle("Mmm");
        em.persist(e);
        TX.commit();
        TypedQuery<EmployeePojo> findquery = em.createQuery("Select e from Employee e where upper(e.title) LIKE 'M%' ",
                EmployeePojo.class);
        List<EmployeePojo> e1 = findquery.getResultList();

        assertEquals(2, e1.size());

        em.close();
    }
    /**
     * count how many employee has more than 30 salary
      */


    @Test
    public void test37_something() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction TX = em.getTransaction();
        TX.begin();
        EmployeePojo e2 = new EmployeePojo();
        e2.setSalary(10.0);
        em.persist(e2);

        EmployeePojo e3 = new EmployeePojo();
        e3.setSalary(40.0);
        em.persist(e3);

        TX.commit();
        TypedQuery<EmployeePojo> findquery = em.createQuery("Select count(e.salary) from Employee e where e.salary >30",
                EmployeePojo.class);
        List<EmployeePojo> e = findquery.getResultList();

        assertEquals(1, e.size());

        em.close();
    }
    /**
     * check how many phones have phone number is in (x,y)
      */
    @Test
    public void test38_something() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction TX = em.getTransaction();
        TX.begin();
        PhonePojo p1 = new MobilePhone();

        p1.setPhonenumber("111-111-111");

        em.persist(p1);
        TX.commit();
        TypedQuery<PhonePojo> findquery = em.createQuery(
                "Select e from Phone e where e.phonenumber IN ('111-111-111', '222-222-222')  ", PhonePojo.class);
        List<PhonePojo> e1 = findquery.getResultList();

        assertEquals(1, e1.size());

        em.close();
    }
    /**
     * check employee who is from ahmedabad city using assertnotnull of employee table
      */
    @Test
    public void test39_something() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction TX = em.getTransaction();
        TX.begin();

        TX.commit();

        TypedQuery<AddressPojo> query = em.createQuery(
                "SELECT ph FROM Employee e JOIN e.address ph WHERE ph.city='AHMEDABAD'", AddressPojo.class);
        List<AddressPojo> e = query.getResultList();

        assumeNotNull(e);

        em.close();
    }
    /**
     * check phone number is equal to  111-111-111 in the database using criteriabuilder using assertnotnull
      */
    @Test
    public void test40_something() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction TX = em.getTransaction();
        TX.begin();

        CriteriaBuilder queryBuilder = em.getCriteriaBuilder();
        CriteriaQuery<EmployeePojo> query = queryBuilder.createQuery(EmployeePojo.class);
        Root<EmployeePojo> root = query.from(EmployeePojo.class);
  
        ListJoin<EmployeePojo, PhonePojo> phonesjoin = root.join(EmployeePojo_.phones);

        query.where(queryBuilder.equal(phonesjoin.get(PhonePojo_.phonenumber), 111 - 111 - 111));
        TypedQuery<EmployeePojo> query1 = em.createQuery(query);

        List<EmployeePojo> result2 = query1.getResultList();
        TX.commit();
        assertNotNull(result2);
        em.close();
    }
    /**
     * check how many employee salary is greater than 20 using criteriabuilder
      */
    @Test
    public void test41_something() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction TX = em.getTransaction();
        TX.begin();
        CriteriaBuilder queryBuilder = em.getCriteriaBuilder();
        CriteriaQuery<EmployeePojo> query = queryBuilder.createQuery(EmployeePojo.class);
        Root<EmployeePojo> root = query.from(EmployeePojo.class);
      
        query.where(queryBuilder.gt(root.get(EmployeePojo_.salary), 20));
        List<EmployeePojo> result2 = em.createQuery(query).getResultList();
        assertEquals(1, result2.size());
        em.close();
    }
    /**
     * check the value of description from employee task using criteriabuilder
      */

    @Test
    public void test42_something() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction TX = em.getTransaction();
        TX.begin();

        CriteriaBuilder queryBuilder = em.getCriteriaBuilder();
        CriteriaQuery<EmployeePojo> query = queryBuilder.createQuery(EmployeePojo.class);
        Root<EmployeePojo> root = query.from(EmployeePojo.class);
        // make sure we do not spell ‘lastname’wrong
        ListJoin<EmployeePojo, EmployeeTask> phonesjoin = root.join(EmployeePojo_.employeeTasks);
        // query.where(queryBuilder.equal(root.get(EmployeePojo_.title), "M%"));
        query.where(queryBuilder.equal(phonesjoin.get(EmployeeTask_.description), "hi"));
        TypedQuery<EmployeePojo> query1 = em.createQuery(query);

        List<EmployeePojo> result2 = query1.getResultList();
        assertEquals(1, result2.size());
        em.close();
    }
    /**
     * update the value of city in addresspojo using crietriabuilder
      */
    @Test
    public void test43_something() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction TX = em.getTransaction();
        TX.begin();
        AddressPojo e1 = new AddressPojo();
        e1.setCity("AHMEDABAD");
        e1.setId(6);
        em.persist(e1);
        TX.commit();
        TX.begin();
     
        CriteriaBuilder queryBuilder = em.getCriteriaBuilder();
        CriteriaUpdate<AddressPojo> u = queryBuilder.createCriteriaUpdate(AddressPojo.class);
        Root<AddressPojo> root = u.from(AddressPojo.class);
        u.set(AddressPojo_.city, "AHMEDABAD1");
        u.where(queryBuilder.greaterThanOrEqualTo(root.get(AddressPojo_.city), "AHMEDABAD"));

        // perform update
         int u1=em.createQuery(u).executeUpdate();
      
        
        assertEquals(6, u1);
        TX.commit();
        em.close();
    }
    /**
     * delete the value of city in addresspojo using crietriabuilder
      */
    @Test
    public void test44_something() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction TX = em.getTransaction();
      
       
     
        CriteriaBuilder queryBuilder = em.getCriteriaBuilder();
        CriteriaDelete<AddressPojo> u = queryBuilder.createCriteriaDelete(AddressPojo.class);
        Root<AddressPojo> root = u.from(AddressPojo.class);
        u.where(queryBuilder.equal(root.get(AddressPojo_.city), "AHMEDABAD1"));
        TX.begin();

        // perform update
         int u1=em.createQuery(u).executeUpdate();
      
        
        assertEquals(6, u1);
        TX.commit();
        em.close();
    }
    /**
     * check version more than 1 how many emplloyee has more than one version
      */

    @Test
    public void test45_something() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction TX = em.getTransaction();
        TX.begin();
        CriteriaBuilder queryBuilder = em.getCriteriaBuilder();
        CriteriaQuery<EmployeePojo> query = queryBuilder.createQuery(EmployeePojo.class);
        Root<EmployeePojo> root = query.from(EmployeePojo.class);
        // make sure we do not spell ‘lastname’wrong

        query.where(queryBuilder.gt(root.get(EmployeePojo_.version), 1));
        List<EmployeePojo> result2 = em.createQuery(query).getResultList();
        TX.commit();
        assertEquals(2, result2.size());
        em.close();
    }
    /**
     * first add project using entitymanager an then update value using criteriabuilder
      */

    @Test
    public void test46_something() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction TX = em.getTransaction();
        TX.begin();
        ProjectPojo e1 = new ProjectPojo();
        e1.setDescription("hello");
        e1.setId(6);
        em.persist(e1);
        TX.commit();
        TX.begin();
     
        CriteriaBuilder queryBuilder = em.getCriteriaBuilder();
        CriteriaUpdate<ProjectPojo> u = queryBuilder.createCriteriaUpdate(ProjectPojo.class);
        Root<ProjectPojo> root = u.from(ProjectPojo.class);
        u.set(ProjectPojo_.description, "hi");
        u.where(queryBuilder.equal(root.get(ProjectPojo_.description), "hello"));

        // perform update
         em.createQuery(u).executeUpdate();
         TX.commit();
         TX.begin();
         
         CriteriaBuilder queryBuilder1 = em.getCriteriaBuilder();
         CriteriaDelete<ProjectPojo> u1 = queryBuilder1.createCriteriaDelete(ProjectPojo.class);
         Root<ProjectPojo> root1 = u1.from(ProjectPojo.class);
         u1.where(queryBuilder1.equal(root1.get(ProjectPojo_.description), "hi"));
         

         // perform delete
          int u2=em.createQuery(u1).executeUpdate();
       
          TX.commit();
         assertEquals(3, u2);
        
         ProjectPojo p1 = em.find(ProjectPojo.class, 6);
          assertEquals(null, p1);
        
      
       
        em.close();
    }
    /**
     * first check how many employee has version greater than 1  using CriteriaBuilder
      */
    @Test
    public void test47_something() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction TX = em.getTransaction();
        TX.begin();
        CriteriaBuilder queryBuilder = em.getCriteriaBuilder();
        CriteriaQuery<EmployeePojo> query = queryBuilder.createQuery(EmployeePojo.class);
        Root<EmployeePojo> root = query.from(EmployeePojo.class);
       
        query.where(queryBuilder.gt(root.get(EmployeePojo_.version), 1));
        List<EmployeePojo> result2 = em.createQuery(query).getResultList();
        TX.commit();
        assertEquals(2, result2.size());
        em.close();
    }
    /**
     * first check how many employee has firstname like hello   using CriteriaBuilder
      */
    @Test
    public void test48_something() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction TX = em.getTransaction();
        TX.begin();
        
        EmployeePojo e1 = new EmployeePojo();
        e1.setFirstName("hello");
        e1.setId(6);
        TX.commit();
        em.persist(e1);
       
        TX.begin();
        CriteriaBuilder queryBuilder = em.getCriteriaBuilder();
        CriteriaQuery<EmployeePojo> query = queryBuilder.createQuery(EmployeePojo.class);
        Root<EmployeePojo> root = query.from(EmployeePojo.class);
        // make sure we do not spell ‘lastname’wrong

        query.where(queryBuilder.like(root.get(EmployeePojo_.firstName),"%hello"));
        List<EmployeePojo> result2 = em.createQuery(query).getResultList();
       
       
        assertEquals(1, result2.size());
     
        em.close();
    }
    /**
     *check how many employee has salary between 30 to 40
      */
    @Test
    public void test49_something() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction TX = em.getTransaction();
        TX.begin();

        TypedQuery<EmployeePojo> findquery = em
                .createQuery("Select e from Employee e where e.salary Between 10 and 40", EmployeePojo.class);
        List<EmployeePojo> e = findquery.getResultList();

        TX.commit();
        assertEquals(2, e.size());

        em.close();
    }
    /**
     * first check how many phonenumber like 111-123-321  using CriteriaBuilder
      */
    @Test
    public void test50_something() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction TX = em.getTransaction();
        TX.begin();
        
        PhonePojo e1 = new  MobilePhone();
        e1.setId(1);
        e1.setPhonenumber("111-123-321");
        
        TX.commit();
        em.persist(e1);
       
        TX.begin();
        CriteriaBuilder queryBuilder = em.getCriteriaBuilder();
        CriteriaQuery<PhonePojo> query = queryBuilder.createQuery(PhonePojo.class);
        Root<PhonePojo> root = query.from(PhonePojo.class);
        // make sure we do not spell ‘lastname’wrong

        query.where(queryBuilder.like(root.get( PhonePojo_.phonenumber),"111-123-321"));
        List<PhonePojo> result2 = em.createQuery(query).getResultList();
       
       
        assertEquals(1, result2.size());
     
        em.close();
    }
  
}