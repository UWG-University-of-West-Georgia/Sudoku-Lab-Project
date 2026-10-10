package edu.westga.comp4420.sudoku.test.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import edu.westga.comp4420.sudoku.model.SudokuBoard;

/**
 * Tests unitaires pour la classe SudokuBoard.
 * 
 * @author Raphael Gall
 * @version Fall 2026
 */
public class TestSudokuBoard {

    private SudokuBoard board;

    @BeforeEach
    public void setUp() {
        this.board = new SudokuBoard();
    }

    @Test
    public void testInitialization() {
        for (int row = 0; row < SudokuBoard.SIZE; row++) {
            for (int col = 0; col < SudokuBoard.SIZE; col++) {
                assertEquals(0, this.board.getCell(row, col).getValue());
                assertFalse(this.board.getCell(row, col).isGiven());
            }
        }
    }

    @ParameterizedTest
    @CsvSource({"-1, 0", "9, 0", "0, -1", "0, 9"})
    public void testGetCellInvalidCoordinatesThrowsException(int row, int col) {
        assertThrows(IllegalArgumentException.class, () -> this.board.getCell(row, col));
    }

    @Test
    public void testSetValueValid() {
        this.board.setValue(2, 3, 5);
        assertEquals(5, this.board.getCell(2, 3).getValue());
    }

    @Test
    public void testSetGivenCellValid() {
        this.board.setGivenCell(4, 4, 7);
        assertEquals(7, this.board.getCell(4, 4).getValue());
        assertTrue(this.board.getCell(4, 4).isGiven());
    }

    @ParameterizedTest
    @CsvSource({"0, 0, 0", "0, 0, 10"})
    public void testSetGivenCellInvalidValueThrowsException(int row, int col, int value) {
        assertThrows(IllegalArgumentException.class, () -> this.board.setGivenCell(row, col, value));
    }

    @Test
    public void testLoadPuzzleValid() {
        String puzzle = "...81.....2........1.9..7...7..25.934.2............5...975.....563.....4......68.";
        this.board.loadPuzzle(puzzle);
        assertEquals(8, this.board.getCell(0, 3).getValue());
        assertTrue(this.board.getCell(0, 3).isGiven());
        assertEquals(0, this.board.getCell(0, 0).getValue());
        assertFalse(this.board.getCell(0, 0).isGiven());
    }

    @Test
    public void testLoadPuzzleInvalidThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> this.board.loadPuzzle("123"));
        assertThrows(IllegalArgumentException.class, () -> this.board.loadPuzzle(null));
    }

    @Test
    public void testIsValidMoveEmptyBoard() {
        assertTrue(this.board.isValidMove(0, 0, 5));
    }

    @Test
    public void testIsValidMoveRowConflict() {
        this.board.setValue(0, 5, 4);
        assertFalse(this.board.isValidMove(0, 1, 4));
    }

    @Test
    public void testIsValidMoveColumnConflict() {
        this.board.setValue(5, 2, 8);
        assertFalse(this.board.isValidMove(1, 2, 8));
    }

    @Test
    public void testIsValidMoveSubgridConflict() {
        this.board.setValue(1, 1, 3);
        assertFalse(this.board.isValidMove(2, 2, 3));
    }

    @ParameterizedTest
    @CsvSource({"0, 0, 0", "0, 0, 10"})
    public void testIsValidMoveInvalidValueThrowsException(int row, int col, int value) {
        assertThrows(IllegalArgumentException.class, () -> this.board.isValidMove(row, col, value));
    }

    @Test
    public void testIsCompleteWhenEmpty() {
        assertFalse(this.board.isComplete());
    }

    @Test
    public void testIsCompleteWithFullValidBoard() {
        int[][] validPuzzle = {
            {5, 3, 4, 6, 7, 8, 9, 1, 2},
            {6, 7, 2, 1, 9, 5, 3, 4, 8},
            {1, 9, 8, 3, 4, 2, 5, 6, 7},
            {8, 5, 9, 7, 6, 1, 4, 2, 3},
            {4, 2, 6, 8, 5, 3, 7, 9, 1},
            {7, 1, 3, 9, 2, 4, 8, 5, 6},
            {9, 6, 1, 5, 3, 7, 2, 8, 4},
            {2, 8, 7, 4, 1, 9, 6, 3, 5},
            {3, 4, 5, 2, 8, 6, 1, 7, 9}
        };

        for (int row = 0; row < SudokuBoard.SIZE; row++) {
            for (int col = 0; col < SudokuBoard.SIZE; col++) {
                this.board.setValue(row, col, validPuzzle[row][col]);
            }
        }

        assertTrue(this.board.isComplete());
    }

    @Test
    public void testLoadPuzzleWithSpecialCharacters() {
        String puzzleWithDotsAndZeros = "0.081.....2........1.9..7...7..25.934.2............5...975.....563.....4......68.";
        this.board.loadPuzzle(puzzleWithDotsAndZeros);
        assertEquals(0, this.board.getCell(0, 0).getValue());
        assertFalse(this.board.getCell(0, 0).isGiven());
    }

    @Test
    public void testIsCompleteWithInvalidMoveBoard() {
        this.board.setValue(0, 0, 5);
        this.board.setValue(0, 1, 5);
        assertFalse(this.board.isComplete());
    }

    @Test
    public void testLoadPuzzleWithEmptyCellsAndNonDigits() {
        String puzzleWithEmptyCells = "0.081.....2........1.9..7...7..25.934.2............5...975.....563.....4......68.";
        this.board.loadPuzzle(puzzleWithEmptyCells);

        assertEquals(0, this.board.getCell(0, 0).getValue());
        assertFalse(this.board.getCell(0, 0).isGiven());

        assertEquals(8, this.board.getCell(0, 3).getValue());
        assertTrue(this.board.getCell(0, 3).isGiven());
    }

    @Test
    public void testLoadPuzzleWithCharacterGreaterThanNine() {
        // Un puzzle de 81 caracteres contenant la lettre 'A' (supérieure à '9' en ASCII)
        String puzzleWithLetters = "A..81.....2........1.9..7...7..25.934.2............5...975.....563.....4......68.";
        this.board.loadPuzzle(puzzleWithLetters);

        // La lettre 'A' doit déclencher le else et initialiser la case à 0 (vide)
        assertEquals(0, this.board.getCell(0, 0).getValue());
        assertFalse(this.board.getCell(0, 0).isGiven());
    }
}