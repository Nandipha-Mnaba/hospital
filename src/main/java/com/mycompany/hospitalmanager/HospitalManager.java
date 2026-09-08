import java.util.Scanner;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.hospitalmanager;

/**
 *
 * @author emeris
 */
public class HospitalManager {
 private static HospitalSystem system = new HospitalSystem();
    private static Scanner scanner = new Scanner(System.in);
 
    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1: registerPatientMenu(); break;
                case 2: searchPatientMenu(); break;
                case 3: updatePatientMenu(); break;
                case 4: deletePatientMenu(); break;
                case 5: system.displayAllPatients(); break;
                case 6: allocateBedMenu(); break;
                case 7: releaseBedMenu(); break;
                case 8: system.getWard().displayWardLayout(); break;
                case 9: system.getWard().displayAvailableBeds(); break;
                case 10: system.getWard().displayOccupiedBeds(); break;
                case 11: reportsMenu(); break;
                case 0:
                    running = false;
                    System.out.println("Exiting system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }
 
    private static void printMainMenu() {
        System.out.println("\n=========================================================");
        System.out.println("   MEDICARE HOSPITAL - PATIENT ADMISSION SYSTEM");
        System.out.println("=========================================================");
        System.out.println(" 1.  Register New Patient");
        System.out.println(" 2.  Search Patient");
        System.out.println(" 3.  Update Patient Details");
        System.out.println(" 4.  Delete Patient");
        System.out.println(" 5.  Display All Patients");
        System.out.println(" 6.  Allocate Bed");
        System.out.println(" 7.  Release Bed");
        System.out.println(" 8.  Display Ward Layout");
        System.out.println(" 9.  Display Available Beds");
        System.out.println("10.  Display Occupied Beds");
        System.out.println("11.  Reports Menu");
        System.out.println(" 0.  Exit");
        System.out.println("=========================================================");
    }
 
    // ---------------- Patient Management ----------------
 
    private static void registerPatientMenu() {
        System.out.println("\n--- Register New Patient ---");
        String id = readString("Enter Patient ID: ");
        if (system.findPatientById(id) != null) {
            System.out.println("Error: A patient with this ID already exists.");
            return;
        }
        String firstName = readString("Enter First Name: ");
        String lastName = readString("Enter Last Name: ");
        int age = readInt("Enter Age: ");
        String gender = readString("Enter Gender: ");
        String condition = readString("Enter Medical Condition: ");
        PatientCategory category = readCategory();
 
        Patient patient;
        if (category == PatientCategory.INPATIENT) {
            String wardNumber = readString("Enter Ward Number (e.g. Ward-1): ");
            patient = new Inpatient(id, firstName, lastName, age, gender, condition, wardNumber);
        } else {
            patient = new Patient(id, firstName, lastName, age, gender, condition, category);
        }
 
        boolean success = system.registerPatient(patient);
        System.out.println(success ? "Patient registered successfully." : "Registration failed.");
    }
 
    private static void searchPatientMenu() {
        String id = readString("Enter Patient ID to search: ");
        Patient p = system.findPatientById(id);
        if (p == null) {
            System.out.println("Patient not found.");
        } else {
            p.displayDetails();
        }
    }
 
    private static void updatePatientMenu() {
        String id = readString("Enter Patient ID to update: ");
        Patient p = system.findPatientById(id);
        if (p == null) {
            System.out.println("Patient not found.");
            return;
        }
        System.out.println("Leave fields as prompted - current values shown in brackets.");
        String firstName = readString("First Name [" + p.getFirstName() + "]: ");
        String lastName = readString("Last Name [" + p.getLastName() + "]: ");
        int age = readInt("Age [" + p.getAge() + "]: ");
        String gender = readString("Gender [" + p.getGender() + "]: ");
        String condition = readString("Medical Condition [" + p.getMedicalCondition() + "]: ");
        boolean success = system.updatePatient(id, firstName, lastName, age, gender, condition);
        System.out.println(success ? "Patient updated successfully." : "Update failed.");
    }
 
    private static void deletePatientMenu() {
        String id = readString("Enter Patient ID to delete: ");
        boolean success = system.deletePatient(id);
        System.out.println(success ? "Patient deleted successfully." : "Patient not found.");
    }
 
    // ---------------- Bed Management ----------------
 
    private static void allocateBedMenu() {
        String id = readString("Enter Inpatient's Patient ID: ");
        Patient p = system.findPatientById(id);
        if (!(p instanceof Inpatient)) {
            System.out.println("Only registered inpatients can be allocated a bed.");
            return;
        }
        if (system.getWard().countAvailableBeds() == 0) {
            System.out.println("No beds are available. Allocation cannot proceed.");
            return;
        }
        String bedNumber = readString("Enter Bed Number (e.g. B01), or leave blank for first available: ");
        boolean success;
        if (bedNumber.trim().isEmpty()) {
            String allocated = system.allocateFirstAvailableBed(id);
            success = allocated != null;
            if (success) {
                System.out.println("Bed " + allocated + " allocated successfully.");
                return;
            }
        } else {
            success = system.allocateBed(id, bedNumber);
        }
        System.out.println(success ? "Bed allocated successfully."
                : "Bed allocation failed (invalid bed, already occupied, or patient already has a bed).");
    }
 
    private static void releaseBedMenu() {
        String id = readString("Enter Inpatient's Patient ID: ");
        boolean success = system.releaseBed(id);
        System.out.println(success ? "Bed released successfully." : "Bed release failed.");
    }
 
    // ---------------- Reports ----------------
 
    private static void reportsMenu() {
        System.out.println("\n--- Reports Menu ---");
        System.out.println("1. Patient Report");
        System.out.println("2. Bed Occupancy Report");
        System.out.println("3. Sort Patients by Surname");
        System.out.println("4. Sort Patients by Patient ID");
        System.out.println("0. Back to Main Menu");
        int choice = readInt("Enter your choice: ");
        switch (choice) {
            case 1:
                system.displayPatientReport();
                break;
            case 2:
                system.displayBedOccupancyReport();
                break;
            case 3:
                system.sortPatientsBySurname();
                System.out.println("Patients sorted by surname:");
                system.displayAllPatients();
                break;
            case 4:
                system.sortPatientsById();
                System.out.println("Patients sorted by Patient ID:");
                system.displayAllPatients();
                break;
            case 0:
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
 
    // ---------------- Input Helpers ----------------
 
    private static PatientCategory readCategory() {
        while (true) {
            System.out.println("Select Patient Category:");
            System.out.println("1. Inpatient");
            System.out.println("2. Outpatient");
            System.out.println("3. Emergency");
            int choice = readInt("Enter choice: ");
            switch (choice) {
                case 1: return PatientCategory.INPATIENT;
                case 2: return PatientCategory.OUTPATIENT;
                case 3: return PatientCategory.EMERGENCY;
                default: System.out.println("Invalid choice, please try again.");
            }
        }
    }
 
    private static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
 
    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            }
        }
    }
}
  