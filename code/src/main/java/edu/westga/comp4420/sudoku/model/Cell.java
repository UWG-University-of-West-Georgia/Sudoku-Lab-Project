package edu.westga.comp4420.sudoku.model;

/**
 * Represents a single cell in a Sudoku puzzle, which can hold a value and indicate whether it is a given (fixed) cell or a modifiable cell.
 * 
 * @author Raphael Gall
 * @version Fall 2026
 */
public class Cell {

    public static final String ERROR_INVALID_VALUE = "The value must be between 0 and 9.";
    public static final String ERROR_GIVEN_CELL = "Cannot modify a given cell.";

    private int value;
    private final boolean isGiven;

    /**
     * Creates a new cell with an initial value and a fixed or modifiable status.
     * 
     * @precondition value >= 0 && value <= 9
     * @postcondition getValue() == value && isGiven() == isGiven
     * 
     * @param value the initial value (0 if the cell is empty)
     * @param isGiven true if the cell is given (non-modifiable)
     */
    public Cell(int value, boolean isGiven) {
        if (value < 0 || value > 9) {
            throw new IllegalArgumentException(ERROR_INVALID_VALUE);
        }
        this.value = value;
        this.isGiven = isGiven;
    }

    /**
     * Returns the current value of the cell.
     * 
     * @precondition none
     * @postcondition none
     * 
     * @return the value of the cell (0 if empty)
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Indicates if the cell is a given cell provided by the initial puzzle.
     * 
     * @precondition none
     * @postcondition none
     * 
     * @return true if the cell is given, false otherwise
     */
    public boolean isGiven() {
        return this.isGiven;
    }

    /**
     * Modifies the value of the cell if it is not given.
     * 
     * @precondition newValue >= 0 && newValue <= 9 && !isGiven()
     * @postcondition getValue() == newValue
     * 
     * @param newValue the new value to assign
     */
    public void setValue(int newValue) {
        if (this.isGiven) {
            throw new IllegalStateException(ERROR_GIVEN_CELL);
        }
        if (newValue < 0 || newValue > 9) {
            throw new IllegalArgumentException(ERROR_INVALID_VALUE);
        }
        this.value = newValue;
    }
}