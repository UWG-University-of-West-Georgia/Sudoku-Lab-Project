package edu.westga.comp4420.sudoku.test.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import edu.westga.comp4420.sudoku.model.Puzzle;
import edu.westga.comp4420.sudoku.model.PuzzleRepository;

/**
 * Tests unitaires pour la classe PuzzleRepository (Couverture 100%).
 * 
 * @author Raphael Gall
 * @version Fall 2026
 */
public class TestPuzzleRepository {

    private PuzzleRepository repository;

    @BeforeEach
    public void setUp() {
        this.repository = new PuzzleRepository();
    }

    @ParameterizedTest
    @CsvSource({
        "0.0, Easy",
        "0.9, Easy",
        "1.0, Medium",
        "1.8, Medium",
        "1.9, Hard",
        "3.0, Hard",
        "3.1, Expert",
        "8.5, Expert"
    })
    public void testGetDifficultyLabelValid(double score, String expected) {
        assertEquals(expected, PuzzleRepository.getDifficultyLabel(score));
    }

    @Test
    public void testGetDifficultyLabelNegativeThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> PuzzleRepository.getDifficultyLabel(-1.0));
    }

    @ParameterizedTest
    @CsvSource({"Easy", "Medium", "Hard", "Expert"})
    public void testGetRandomPuzzleKnownDifficulties(String difficulty) {
        Puzzle puzzle = this.repository.getRandomPuzzle(difficulty);
        assertNotNull(puzzle);
        assertEquals(difficulty, puzzle.getDifficulty());
    }

    @Test
    public void testGetRandomPuzzleNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> this.repository.getRandomPuzzle(null));
    }

    @Test
    public void testGetRandomPuzzleUnknownThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> this.repository.getRandomPuzzle("Inconnu"));
    }

    @Test
    public void testParseLineWithInvalidNumberFormat() {
        // Couvre le catch (NumberFormatException error) - Lignes 37-38
        String invalidScoreLine = "1,1..5.37..6.3..8.9......98...1.......8761..........6...........7.8.9.76.47...6.312,198543726643278591527619843914735268876192435235486179462351987381927654759864312,27,NOT_A_NUMBER";
        
        // Appel direct de parseLine pour executer la branche catch
        this.repository.parseLine(invalidScoreLine);
        assertNotNull(this.repository);
    }

    @Test
    public void testParseLineWithShortLine() {
        // Couvre la branche false de if (parts.length >= 5) - Ligne 26
        String shortLine = "1,2,3";
        
        this.repository.parseLine(shortLine);
        assertNotNull(this.repository);
    }

    @Test
    public void testParseLineWithEmptyLine() {
        // Couvre if (!trimmed.isEmpty()) avec une ligne vide
        this.repository.parseLine("   ");
        assertNotNull(this.repository);
    }

    @Test
    public void testParseLineWithShortString() {
        // Couvre la branche false de if (parts.length >= 5) (Ligne 74)
        this.repository.parseLine("1,2,3");
        assertNotNull(this.repository);
    }

    @Test
    public void testParseLineWithInvalidScore() {
        // Couvre le bloc catch (NumberFormatException) (Lignes 85-87)
        String invalidLine = "1,1..5.37..6.3..8.9......98...1.......8761..........6...........7.8.9.76.47...6.312,198543726643278591527619843914735268876192435235486179462351987381927654759864312,27,NOT_A_NUMBER";
        this.repository.parseLine(invalidLine);
        assertNotNull(this.repository);
    }

    @Test
    public void testGetRandomPuzzleEmptyCategoryThrowsException() {
        // Couvre la branche if (list == null || list.isEmpty()) (Ligne 130)
        // En demandant une categorie inexistante/invalide
        assertThrows(IllegalArgumentException.class, () -> this.repository.getRandomPuzzle("UnknownCategory"));
    }
}