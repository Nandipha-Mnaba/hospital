package com.mycompany.hospitalmanager;

/**
 * Represents an Inpatient, a Patient who requires a hospital bed.
 * Demonstrates inheritance: Inpatient extends Patient, uses super() to
 * initialise inherited attributes, and overrides displayDetails().
 */
public class Inpatient extends Patient {

    private String wardNumber;
    private String bedNumber; // null until a bed has been allocated

    public Inpatient(String patientId, String firstName, String lastName, int age,
                      String gender, String medicalCondition, String wardNumber) {
        // Call the superclass constructor to initialise inherited attributes.
        // Category is always INPATIENT for this subclass.
        super(patientId, firstName, lastName, age, gender, medicalCondition, PatientCategory.INPATIENT);
        this.wardNumber = wardNumber;
        this.bedNumber = null;
    }

    public String getWardNumber() {
        return wardNumber;
    }

    public void setWardNumber(String wardNumber) {
        this.wardNumber = wardNumber;
    }

    public String getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(String bedNumber) {
        this.bedNumber = bedNumber;
    }

    /**
     * Overrides Patient.displayDetails() to extend the behaviour of the
     * superclass method with ward and bed information.
     */
    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("Ward Number      : " + wardNumber);
        System.out.println("Bed Number       : " + (bedNumber == null ? "Not allocated" : bedNumber));
    }
}

