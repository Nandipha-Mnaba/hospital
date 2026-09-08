/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hospitalmanager;

/**
 *
 * @author emeris
 */
public class InpatientextendsPatient {
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
}
