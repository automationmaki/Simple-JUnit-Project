package com.nurse;

import com.nurse.Glucose;
import com.nurse.Inject;

public class Patient {

    @Inject
    Glucose glucose;

    public Glucose getGlucose() {
        return glucose;
    }
}
