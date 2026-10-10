package edu.westga.comp4420.sudoku.model;

/**
 * Represents a Sudoku board with its grid of cells and provides methods to manipulate and validate the board.
 * 
 * @author Raphael Gall
 * @version Fall 2026
 */
public class SudokuBoard {

    public static final int SIZE = 9;
    public static final int SUBGRID_SIZE = 3;

    public static final String ERROR_INVALID_COORDINATES = "Coordinates are out of bounds (0 to 8 expected).";
    public static final String ERROR_INVALID_VALUE = "The value must be between 0 and 9.";
    private final Cell[][] grid;

    /**
     * Initialises a 9x9 grid of empty modifiable cells.
     * 
     * @precondition none
     * @postcondition the grid contains 81 cells initialised to 0
     */
    public SudokuBoard() {
        this.grid = new Cell[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                this.grid[row][col] = new Cell(0, false);
            }
        }
    }

    /**
     * Returns the cell at the specified position.
     * 
     * @precondition row >= 0 && row < SIZE && col >= 0 && col < SIZE
     * @postcondition none
     * 
     * @param row the row index
     * @param col the column index
     * @return the corresponding cell
     */
    public Cell getCell(int row, int col) {
        this.validateCoordinates(row, col);
        return this.grid[row][col];
    }

    /**
     * Sets the value of the cell at the specified coordinates.
     * 
     * @precondition row >= 0 && row < SIZE && col >= 0 && col < SIZE && value >= 0 && value <= 9
     * @postcondition getCell(row, col).getValue() == value
     * 
     * @param row the row index
     * @param col the column index
     * @param value the new value (0 to clear)
     */
    public void setValue(int row, int col, int value) {
        this.validateCoordinates(row, col);
        this.grid[row][col].setValue(value);
    }

    /**
     * Charge une cellule initiale fixe dans la grille.
     * 
     * @precondition row >= 0 && row < SIZE && col >= 0 && col < SIZE && value >= 1 && value <= 9
     * @postcondition getCell(row, col).isGiven() == true
     * 
     * @param row the row index
     * @param col the column index
     * @param value the initial value
     */
    public void setGivenCell(int row, int col, int value) {
        this.validateCoordinates(row, col);
        if (value < 1 || value > SIZE) {
            throw new IllegalArgumentException(ERROR_INVALID_VALUE);
        }
        this.grid[row][col] = new Cell(value, true);
    }

    /**
     * Loads a puzzle from a string representation into the grid, setting given cells and empty cells accordingly.
     * 
     * @precondition puzzle != null && puzzle.length() == 81
     * @postcondition the grid contains the corresponding given and empty cells
     * 
     * @param puzzle the string of 81 characters representing the grid
     */
    public void loadPuzzle(String puzzle) {
        if (puzzle == null || puzzle.length() != SIZE * SIZE) {
            throw new IllegalArgumentException(ERROR_INVALID_COORDINATES);
        }
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                char character = puzzle.charAt(row * SIZE + col);
                if (character >= '1' && character <= '9') {
                    int value = Character.getNumericValue(character);
                    this.setGivenCell(row, col, value);
                } else {
                    this.grid[row][col] = new Cell(0, false);
                }
            }
        }
    }

    /**
     * Verifies if a move follows the Sudoku rules (row, column, 3x3 subgrid).
     * 
     * @precondition row >= 0 && row < SIZE && col >= 0 && col < SIZE && value >= 1 && value <= 9
     * @postcondition none
     * 
     * @param row the row index
     * @param col the column index
     * @param value the digit to test
     * @return true if the placement is valid, false otherwise
     */
    public boolean isValidMove(int row, int col, int value) {
        this.validateCoordinates(row, col);
        if (value < 1 || value > SIZE) {
            throw new IllegalArgumentException(ERROR_INVALID_VALUE);
        }
        return this.checkRow(row, col, value) 
            && this.checkColumn(row, col, value) 
            && this.checkSubgrid(row, col, value);
    }

    /**
     * Verifies if the entire grid is correctly filled without conflicts.
     * 
     * @precondition none
     * @postcondition none
     * 
     * @return true if the grid is completely valid and filled
     */
    public boolean isComplete() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                int value = this.grid[row][col].getValue();
                if (value == 0 || !this.isValidMove(row, col, value)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkRow(int row, int col, int value) {
        for (int current = 0; current < SIZE; current++) {
            if (current != col && this.grid[row][current].getValue() == value) {
                return false;
            }
        }
        return true;
    }

    private boolean checkColumn(int row, int col, int value) {
        for (int current = 0; current < SIZE; current++) {
            if (current != row && this.grid[current][col].getValue() == value) {
                return false;
            }
        }
        return true;
    }

    private boolean checkSubgrid(int row, int col, int value) {
        int startRow = (row / SUBGRID_SIZE) * SUBGRID_SIZE;
        int startCol = (col / SUBGRID_SIZE) * SUBGRID_SIZE;

        for (int currRow = startRow; currRow < startRow + SUBGRID_SIZE; currRow++) {
            for (int currCol = startCol; currCol < startCol + SUBGRID_SIZE; currCol++) {
                if ((currRow != row || currCol != col) && this.grid[currRow][currCol].getValue() == value) {
                    return false;
                }
            }
        }
        return true;
    }

    private void validateCoordinates(int row, int col) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
            throw new IllegalArgumentException(ERROR_INVALID_COORDINATES);
        }
    }
}