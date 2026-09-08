package com.mycompany.hospitalmanager;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Core business logic of the Hospital Patient Admission System.
 * Kept separate from Main so it can be unit tested directly with JUnit,
 * independently of the console menu.
 *
 * Uses an ArrayList<Patient> to store patients (Feature: Array List Class)
 * and a Ward object (2D array of beds) to manage bed allocation.
 */
public class HospitalSystem {

    private ArrayList<Patient> patients;
    private Ward ward;

    public HospitalSystem() {
        patients = new ArrayList<>();
        ward = new Ward();
    }

    public Ward getWard() {
        return ward;
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }

    // ==================== Feature 1: Patient Management ====================

    /**
     * Registers a new patient. Returns false if a patient with the same
     * Patient ID already exists (prevents duplicate Patient IDs).
     */
    public boolean registerPatient(Patient patient) {
        if (patient == null || findPatientById(patient.getPatientId()) != null) {
            return false;
        }
        patients.add(patient);
        return true;
    }

    public Patient findPatientById(String patientId) {
        if (patientId == null) {
            return null;
        }
        for (Patient p : patients) {
            if (p.getPatientId().equalsIgnoreCase(patientId)) {
                return p;
            }
        }
        return null;
    }

    public boolean updatePatient(String patientId, String firstName, String lastName,
                                  int age, String gender, String medicalCondition) {
        Patient p = findPatientById(patientId);
        if (p == null) {
            return false;
        }
        p.setFirstName(firstName);
        p.setLastName(lastName);
        p.setAge(age);
        p.setGender(gender);
        p.setMedicalCondition(medicalCondition);
        return true;
    }

    /**
     * Deletes a patient. If the patient is an inpatient occupying a bed,
     * the bed is released first so it does not remain incorrectly marked
     * as occupied.
     */
    public boolean deletePatient(String patientId) {
        Patient p = findPatientById(patientId);
        if (p == null) {
            return false;
        }
        if (p instanceof Inpatient) {
            Inpatient inpatient = (Inpatient) p;
            if (inpatient.getBedNumber() != null) {
                ward.releaseBed(inpatient.getBedNumber());
            }
        }
        patients.remove(p);
        return true;
    }

    public void displayAllPatients() {
        if (patients.isEmpty()) {
            System.out.println("No patients registered.");
            return;
        }
        for (Patient p : patients) {
            p.displayDetails();
        }
    }

    // ==================== Feature 2: Bed Management ====================

    /**
     * Allocates a bed to an inpatient. Only inpatients may be allocated
     * a bed. Returns false if the patient does not exist, is not an
     * inpatient, already has a bed, or the requested bed is unavailable.
     */
    public boolean allocateBed(String patientId, String bedNumber) {
        Patient p = findPatientById(patientId);
        if (!(p instanceof Inpatient)) {
            return false;
        }
        Inpatient inpatient = (Inpatient) p;
        if (inpatient.getBedNumber() != null) {
            return false; // already has a bed allocated
        }
        boolean success = ward.allocateBed(bedNumber, patientId);
        if (success) {
            inpatient.setBedNumber(bedNumber);
        }
        return success;
    }

    /**
     * Allocates the first available bed to an inpatient automatically.
     * Returns the bed number allocated, or null if allocation failed
     * (e.g. patient is not an inpatient, already has a bed, or the ward is full).
     */
    public String allocateFirstAvailableBed(String patientId) {
        Patient p = findPatientById(patientId);
        if (!(p instanceof Inpatient)) {
            return null;
        }
        Inpatient inpatient = (Inpatient) p;
        if (inpatient.getBedNumber() != null) {
            return null;
        }
        String bedNumber = ward.allocateFirstAvailableBed(patientId);
        if (bedNumber != null) {
            inpatient.setBedNumber(bedNumber);
        }
        return bedNumber;
    }

    public boolean releaseBed(String patientId) {
        Patient p = findPatientById(patientId);
        if (!(p instanceof Inpatient)) {
            return false;
        }
        Inpatient inpatient = (Inpatient) p;
        if (inpatient.getBedNumber() == null) {
            return false;
        }
        boolean success = ward.releaseBed(inpatient.getBedNumber());
        if (success) {
            inpatient.setBedNumber(null);
        }
        return success;
    }

    // ==================== Feature 3: Reports ====================

    public int getTotalPatients() {
        return patients.size();
    }

    public int getTotalOccupiedBeds() {
        return ward.countOccupiedBeds();
    }

    public double getOccupancyPercentage() {
        return ward.occupancyPercentage();
    }

    public void displayPatientReport() {
        System.out.println("\n================ PATIENT REPORT ================");
        System.out.println("Total Registered Patients: " + getTotalPatients());
        displayAllPatients();
    }

    public void displayBedOccupancyReport() {
        System.out.println("\n============= BED OCCUPANCY REPORT =============");
        System.out.println("Total Beds         : " + Ward.TOTAL_BEDS);
        System.out.println("Occupied Beds      : " + getTotalOccupiedBeds());
        System.out.println("Available Beds     : " + ward.countAvailableBeds());
        System.out.printf("Occupancy Percentage: %.2f%%\n", getOccupancyPercentage());
    }

    // ==================== Sorting / Data Processing ====================

    public void sortPatientsById() {
        patients.sort(Comparator.comparing(Patient::getPatientId));
    }

    public void sortPatientsBySurname() {
        patients.sort(Comparator.comparing(Patient::getLastName, String.CASE_INSENSITIVE_ORDER));
    }
}