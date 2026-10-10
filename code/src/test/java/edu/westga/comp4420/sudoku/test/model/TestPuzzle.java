package edu.westga.comp4420.sudoku.test.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import edu.westga.comp4420.sudoku.model.Puzzle;

/**
 * Tests unitaires pour la classe Puzzle.
 * 
 * @author Raphael Gall
 * @version Fall 2026
 */
public class TestPuzzle {

    private static final String VALID_PUZZLE = "...81.....2........1.9..7...7..25.934.2............5...975.....563.....4......68.";
    private static final String VALID_SOLUTION = "934817256728653419615942738176425893452398167389176542897564321563281974241739685";

    @Test
    public void testValidPuzzleCreation() {
        Puzzle puzzle = new Puzzle(VALID_PUZZLE, VALID_SOLUTION, "Facile");
        assertEquals(VALID_PUZZLE, puzzle.getPuzzleString());
        assertEquals(VALID_SOLUTION, puzzle.getSolutionString());
        assertEquals("Facile", puzzle.getDifficulty());
    }

    @Test
    public void testNullPuzzleStringThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Puzzle(null, VALID_SOLUTION, "Facile"));
    }

    @Test
    public void testInvalidLengthPuzzleStringThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Puzzle("123", VALID_SOLUTION, "Facile"));
    }

    @Test
    public void testNullSolutionStringThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Puzzle(VALID_PUZZLE, null, "Facile"));
    }

    @Test
    public void testInvalidLengthSolutionStringThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Puzzle(VALID_PUZZLE, "123", "Facile"));
    }

    @Test
    public void testNullDifficultyThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Puzzle(VALID_PUZZLE, VALID_SOLUTION, null));
    }
}