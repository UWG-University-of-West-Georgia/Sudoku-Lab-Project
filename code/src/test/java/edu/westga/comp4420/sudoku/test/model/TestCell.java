package edu.westga.comp4420.sudoku.test.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import edu.westga.comp4420.sudoku.model.Cell;

/**
 * Tests unitaires pour la classe Cell.
 * 
 * @author Raphael Gall
 * @version Fall 2026
 */
public class TestCell {

    @Test
    public void testValidCellCreation() {
        Cell cell = new Cell(5, false);
        assertEquals(5, cell.getValue());
        assertFalse(cell.isGiven());
    }

    @Test
    public void testValidGivenCellCreation() {
        Cell cell = new Cell(9, true);
        assertEquals(9, cell.getValue());
        assertTrue(cell.isGiven());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 10, 100})
    public void testInvalidConstructorValueThrowsException(int invalidValue) {
        assertThrows(IllegalArgumentException.class, () -> new Cell(invalidValue, false));
    }

    @Test
    public void testSetValueValid() {
        Cell cell = new Cell(0, false);
        cell.setValue(7);
        assertEquals(7, cell.getValue());
    }

    @Test
    public void testSetValueOnGivenCellThrowsException() {
        Cell cell = new Cell(4, true);
        assertThrows(IllegalStateException.class, () -> cell.setValue(8));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 10})
    public void testSetValueInvalidRangeThrowsException(int invalidValue) {
        Cell cell = new Cell(0, false);
        assertThrows(IllegalArgumentException.class, () -> cell.setValue(invalidValue));
    }
}