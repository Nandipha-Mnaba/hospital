/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hospitalmanager;

/**
 *
 * @author emeris
 */
public class Bed {
      private final String bedNumber;
    private boolean occupied;
    private String patientId; // ID of the patient occupying this bed, or null
 
    public Bed(String bedNumber) {
        this.bedNumber = bedNumber;
        this.occupied = false;
        this.patientId = null;
    }
    public String getBedNumber() {
        return bedNumber;
    }
 
    public boolean isOccupied() {
        return occupied;
    }
 
    public String getPatientId() {
        return patientId;
    }
 
    public void occupy(String patientId) {
        this.occupied = true;
        this.patientId = patientId;
    }
 
    public void release() {
        this.occupied = false;
        this.patientId = null;
    }
}

