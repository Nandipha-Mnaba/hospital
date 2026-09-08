/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hospitalmanager;

/**
 *
 * @author emeris
 */
public class Patient {
private String patientId;
private String firstname ;
private String lastname;
private int age;
private String geneder;
private String medicalCondition;
private PatientCatergory category;



}
 public Patient(String patientId, String firstName, String lastName, int age,
                    String gender, String medicalCondition, PatientCategory category) {
        this.patientId = patientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.medicalCondition = medicalCondition;
        this.category = category;
    }
 
    // ---------- Getters and Setters (Encapsulation) ----------
 
    public String getPatientId() {
        return patientId;
    }
 
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
 
    public String getFirstName() {
        return firstName;
    }
 
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
 
    public String getLastName() {
        return lastName;
    }
 
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
 
    public int getAge() {
        return age;
    }
 
    public void setAge(int age) {
        this.age = age;
    }
 
    public String getGender() {
        return gender;
    }
 
    public void setGender(String gender) {
        this.gender = gender;
    }
 
    public String getMedicalCondition() {
        return medicalCondition;
    }
 public void setMedicalCondition(String medicalCondition) {
        this.medicalCondition = medicalCondition;
    }
 
    public PatientCategory getCategory() {
        return category;
    }
 
    public void setCategory(PatientCategory category) {
        this.category = category;
    }
 
    /**
     * Displays this patient's details to the console.
     * Overridden in Inpatient to add ward/bed information.
     */
    public void displayDetails() {
        System.out.println("------------------------------------------");
        System.out.println("Patient ID       : " + patientId);
        System.out.println("Name             : " + firstName + " " + lastName);
        System.out.println("Age              : " + age);
        System.out.println("Gender           : " + gender);
        System.out.println("Medical Condition: " + medicalCondition);
        System.out.println("Category         : " + category);
    }
 
    @Override
    public String toString() {
        return patientId + " - " + firstName + " " + lastName + " (" + category + ")";
    }
}