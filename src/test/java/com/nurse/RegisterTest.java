package com.nurse;

import com.nurse.Glucose;
import com.nurse.Patient;
import com.nurse.Register;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class RegisterTest {


    private Register register;

    @Before
    public void setUp() throws Exception {
        register = new Register();
    }

    @Test(expected = RuntimeException.class)
    public void absentObjectTrowsException(){
        Object something = register.get("absent");

    }

    @Test
    public void canRegisterSomethingandRetrieve(){
        Object one = new Object();
        register.add("one", one);
        Object two = new Object();
        register.add("two", two);
        Object retrievedOne = register.get("one");
        Object retrievedTwo = register.get("two");
        Assert.assertNotNull(retrievedOne);
        Assert.assertSame(one, retrievedOne);
        Assert.assertSame(two, retrievedTwo);
        Assert.assertNotSame(retrievedOne, retrievedTwo);

    }

    @Test
    public void canRegisterByTypeAndRetrieve () {
        Glucose glucose = new Glucose();
        register.add(glucose);
        Water water = new Water();
        register.add(water);
        Glucose retrievedGlucose = register.get(Glucose.class);
        Water retrievedWater = register.get(Water.class);
        Assert.assertSame(glucose, retrievedGlucose);
        Assert.assertSame(water, retrievedWater);
    }

    @Test(expected = RuntimeException.class)
    public void secondRegisterOfSameNamethrowsWxception(){
        register.add("same", new Object());
        register.add("same", new Object());
    }

    @Test
    public void patientShouldBeInjectedByClucose(){
        Glucose glucose = new Glucose();
        register.add(new Glucose());
        register.add(glucose);
        register.inject();
        register.add(new Patient());
        Patient retrievedPatient = register.get(Patient.class);
        Glucose injectedGlucose =  retrievedPatient.getGlucose();
        Assert.assertNotNull(injectedGlucose);
        Assert.assertSame(glucose,injectedGlucose);
    }

}
