package com.mycompany.hospitalmanager;

/**
 * Represents the single hospital ward containing 20 beds arranged in a
 * 4 x 5 layout (B01 - B20), implemented as a two-dimensional array of Bed
 * objects. Uses nested loops throughout to traverse the layout.
 */

public class Ward {

    public static final int ROWS = 4;
    public static final int COLS = 5;
    public static final int TOTAL_BEDS = ROWS * COLS;

    private Bed[][] beds;

    public Ward() {
        beds = new Bed[ROWS][COLS];
        int bedCount = 1;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                String label = String.format("B%02d", bedCount);
                beds[row][col] = new Bed(label);
                bedCount++;
            }
        }
    }

    /**
     * Searches the 2D array for a bed matching the given bed number.
     */
    private Bed findBed(String bedNumber) {
        if (bedNumber == null) {
            return null;
        }
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (beds[row][col].getBedNumber().equalsIgnoreCase(bedNumber)) {
                    return beds[row][col];
                }
            }
        }
        return null;
    }

    public boolean bedExists(String bedNumber) {
        return findBed(bedNumber) != null;
    }

    public boolean isBedOccupied(String bedNumber) {
        Bed bed = findBed(bedNumber);
        return bed != null && bed.isOccupied();
    }

    /**
     * Allocates the given bed to the given patient.
     * Returns false if the bed does not exist or is already occupied
     * (preventing invalid or duplicate allocations).
     */
    public boolean allocateBed(String bedNumber, String patientId) {
        Bed bed = findBed(bedNumber);
        if (bed == null || bed.isOccupied()) {
            return false;
        }
        bed.occupy(patientId);
        return true;
    }

    /**
     * Allocates the first available bed found (row by row) to the patient.
     * Returns the bed number allocated, or null if the ward is full.
     */
    public String allocateFirstAvailableBed(String patientId) {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (!beds[row][col].isOccupied()) {
                    beds[row][col].occupy(patientId);
                    return beds[row][col].getBedNumber();
                }
            }
        }
        return null; // no beds available
    }

    public boolean releaseBed(String bedNumber) {
        Bed bed = findBed(bedNumber);
        if (bed == null || !bed.isOccupied()) {
            return false;
        }
        bed.release();
        return true;
    }

    /**
     * Displays the full 4x5 ward layout, marking each bed as occupied
     * or available.
     */
    public void displayWardLayout() {
        System.out.println("\n===================== WARD LAYOUT =====================");
        for (int row = 0; row < ROWS; row++) {
            StringBuilder rowText = new StringBuilder();
            for (int col = 0; col < COLS; col++) {
                Bed bed = beds[row][col];
                String status = bed.isOccupied() ? "[OCC]" : "[AVL]";
                rowText.append(bed.getBedNumber()).append(status).append("   ");
            }
            System.out.println(rowText.toString());
        }
        System.out.println("Legend: [AVL] = Available   [OCC] = Occupied");
        System.out.println("=========================================================");
    }

    public void displayAvailableBeds() {
        System.out.println("\n----- AVAILABLE BEDS -----");
        boolean anyAvailable = false;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (!beds[row][col].isOccupied()) {
                    System.out.println(beds[row][col].getBedNumber());
                    anyAvailable = true;
                }
            }
        }
        if (!anyAvailable) {
            System.out.println("No beds are currently available.");
        }
    }

    public void displayOccupiedBeds() {
        System.out.println("\n----- OCCUPIED BEDS -----");
        boolean anyOccupied = false;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Bed bed = beds[row][col];
                if (bed.isOccupied()) {
                    System.out.println(bed.getBedNumber() + " -> Patient ID: " + bed.getPatientId());
                    anyOccupied = true;
                }
            }
        }
        if (!anyOccupied) {
            System.out.println("No beds are currently occupied.");
        }
    }

    public int countOccupiedBeds() {
        int count = 0;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (beds[row][col].isOccupied()) {
                    count++;
                }
            }
        }
        return count;
    }

    public int countAvailableBeds() {
        return TOTAL_BEDS - countOccupiedBeds();
    }

    public double occupancyPercentage() {
        return ((double) countOccupiedBeds() / TOTAL_BEDS) * 100.0;
    }
}