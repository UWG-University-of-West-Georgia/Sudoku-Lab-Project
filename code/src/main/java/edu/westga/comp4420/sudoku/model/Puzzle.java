package edu.westga.comp4420.sudoku.model;

/**
 * Represents a Sudoku puzzle with its initial puzzle and solution.
 * 
 * @author Raphael Gall
 * @version Fall 2026
 */
public class Puzzle {

    public static final String ERROR_INVALID_LENGTH = "The string must contain exactly 81 characters.";
    private final String puzzleString;
    private final String solutionString;
    private final String difficulty;

    /**
     * Initialises a Sudoku puzzle.
     * 
     * @precondition puzzleString != null && puzzleString.length() == 81 && 
     *               solutionString != null && solutionString.length() == 81 && 
     *               difficulty != null
     * @postcondition getPuzzleString() == puzzleString && 
     *                getSolutionString() == solutionString && 
     *                getDifficulty() == difficulty
     * 
     * @param puzzleString the initial puzzle string
     * @param solutionString the complete solution string
     * @param difficulty the level or label of difficulty
     */
    public Puzzle(String puzzleString, String solutionString, String difficulty) {
        if (puzzleString == null || puzzleString.length() != 81) {
            throw new IllegalArgumentException(ERROR_INVALID_LENGTH);
        }
        if (solutionString == null || solutionString.length() != 81) {
            throw new IllegalArgumentException(ERROR_INVALID_LENGTH);
        }
        if (difficulty == null) {
            throw new IllegalArgumentException("The difficulty cannot be null.");
        }
        this.puzzleString = puzzleString;
        this.solutionString = solutionString;
        this.difficulty = difficulty;
    }

    /**
     * Returns the initial puzzle string.
     * 
     * @precondition none
     * @postcondition none
     * 
     * @return the initial puzzle string
     */
    public String getPuzzleString() {
        return this.puzzleString;
    }

    /**
     * Returns the complete solution string.
     * 
     * @precondition none
     * @postcondition none
     * 
     * @return the solution string
     */
    public String getSolutionString() {
        return this.solutionString;
    }

    /**
     * Returns the difficulty level or label.
     * 
     * @precondition none
     * @postcondition none
     * 
     * @return the difficulty
     */
    public String getDifficulty() {
        return this.difficulty;
    }
}