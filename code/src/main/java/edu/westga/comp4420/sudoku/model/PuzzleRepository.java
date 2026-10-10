package edu.westga.comp4420.sudoku.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Represents a repository of Sudoku puzzles loaded from a CSV file, allowing retrieval of random puzzles by difficulty.
 * 
 * @author Raphael Gall
 * @version Fall 2026
 */
public class PuzzleRepository {

    public static final String PUZZLES_RESOURCE_PATH = "/edu/westga/comp4420/sudoku/sudoku-3m.csv";
    private final Map<String, List<Puzzle>> puzzlesByDifficulty;
    private final Random random;

    /**
     * Initializes the repository by loading puzzles from the CSV resource file and categorizing them by difficulty.
     * 
     * @precondition none
     * @postcondition puzzlesByDifficulty is populated with puzzles from the CSV file
     * 
     * @throws IllegalStateException if the CSV file cannot be found or read
     */
    public PuzzleRepository() {
        this.puzzlesByDifficulty = new HashMap<String, List<Puzzle>>();
        this.puzzlesByDifficulty.put("Easy", new ArrayList<Puzzle>());
        this.puzzlesByDifficulty.put("Medium", new ArrayList<Puzzle>());
        this.puzzlesByDifficulty.put("Hard", new ArrayList<Puzzle>());
        this.puzzlesByDifficulty.put("Expert", new ArrayList<Puzzle>());
        this.random = new Random();

        this.loadPuzzlesFromCsv();
    }

    /**
     * Loads puzzles from the CSV resource file and populates the puzzlesByDifficulty map.
     * 
     * @precondition none
     * @postcondition puzzlesByDifficulty is populated with puzzles from the CSV file
     * 
     * @throws IllegalStateException if the CSV file cannot be found or read
     */
    public void loadPuzzlesFromCsv() {
        InputStream stream = this.getClass().getResourceAsStream(PUZZLES_RESOURCE_PATH);
        if (stream == null) {
            throw new IllegalStateException("The dataset file " + PUZZLES_RESOURCE_PATH + " is not found.");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();
                if (!trimmed.isEmpty()) {
                    this.parseLine(trimmed);
                }
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Error while reading the dataset file.", exception);
        }
    }

    /**
     * Parses a line from the CSV file and adds the corresponding puzzle to the appropriate difficulty category.
     * 
     * @precondition line is a valid CSV line with at least 5 columns
     * @postcondition the puzzle is added to puzzlesByDifficulty if valid
     * 
     * @param line the CSV line to parse
     */
    public void parseLine(String line) {
        String[] parts = line.split(",");
        if (parts.length >= 5) {
            String puzzleStr = parts[1].trim();
            String solutionStr = parts[2].trim();
            try {
                double score = Double.parseDouble(parts[4].trim());
                String difficulty = getDifficultyLabel(score);
                Puzzle puzzle = new Puzzle(puzzleStr, solutionStr, difficulty);
                List<Puzzle> list = this.puzzlesByDifficulty.get(difficulty);
                if (list != null) {
                    list.add(puzzle);
                }
            } catch (NumberFormatException error) {
                // Ignore une ligne malformee dans le dataset
            }
        }
    }

    /**
     * Maps a numeric score to one of the four difficulty levels.
     * 
     * @precondition score >= 0.0
     * @postcondition none
     * 
     * @param score the numeric difficulty score
     * @return the difficulty label
     */
    public static String getDifficultyLabel(double score) {
        if (score < 0.0) {
            throw new IllegalArgumentException("The score cannot be negative.");
        }
        if (score < 1.0) {
            return "Easy";
        }
        if (score <= 1.8) {
            return "Medium";
        }
        if (score <= 3.0) {
            return "Hard";
        }
        return "Expert";
    }

    /**
     * Returns a random puzzle from the specified difficulty category.
     * 
     * @precondition difficulty != null
     * @postcondition none
     * 
     * @param difficulty the chosen difficulty level ("Easy", "Medium", "Hard", "Expert")
     * @return a random puzzle from the specified difficulty category
     */
    public Puzzle getRandomPuzzle(String difficulty) {
        if (difficulty == null) {
            throw new IllegalArgumentException("The difficulty cannot be null.");
        }
        List<Puzzle> list = this.puzzlesByDifficulty.get(difficulty);
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("No puzzles available for the difficulty : " + difficulty);
        }
        int index = this.random.nextInt(list.size());
        return list.get(index);
    }
}