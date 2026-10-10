package edu.westga.comp4420.sudoku.view.codebehind;

import java.io.IOException;
import edu.westga.comp4420.sudoku.Main;
import edu.westga.comp4420.sudoku.model.Puzzle;
import edu.westga.comp4420.sudoku.model.PuzzleRepository;
import edu.westga.comp4420.sudoku.model.SudokuBoard;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controleur pour la fenetre principale du Sudoku.
 * 
 * @author Raphael Gall
 * @version Fall 2026
 */
public class MainWindow {

    private static final int MAX_LIVES = 3;

    @FXML
    private GridPane boardGrid;

    @FXML
    private Label titleLabel;

    @FXML
    private Label livesLabel;

    private SudokuBoard board;
    private TextField[][] cellsUi;
    private PuzzleRepository repository;
    private Puzzle currentPuzzle;
    private boolean isUpdatingUi = false;
    private boolean isGameFinished;
    private int remainingLives;

    @FXML
    void initialize() {
        this.board = new SudokuBoard();
        this.cellsUi = new TextField[SudokuBoard.SIZE][SudokuBoard.SIZE];
        this.repository = new PuzzleRepository();
        this.buildGridUi();
        this.handleNewGame(null);
    }

    private void buildGridUi() {
        this.boardGrid.getChildren().clear();
        for (int row = 0; row < SudokuBoard.SIZE; row++) {
            for (int col = 0; col < SudokuBoard.SIZE; col++) {
                TextField field = new TextField();
                field.setPrefWidth(40.0);
                field.setPrefHeight(40.0);
                field.setAlignment(Pos.CENTER);
                this.styleCell(row, col, field, "white", "#000000", false);

                final int currentRow = row;
                final int currentCol = col;

                field.textProperty().addListener((observable, oldValue, newValue) -> {
                    this.handleCellInput(currentRow, currentCol, field, newValue);
                });

                this.cellsUi[row][col] = field;
                this.boardGrid.add(field, col, row);
            }
        }
    }

    private void handleCellInput(int row, int col, TextField field, String text) {
        if (this.isUpdatingUi || this.isGameFinished || !field.isEditable()) {
            return;
        }
        String trimmedText = text == null ? "" : text.trim();
        if (trimmedText.isEmpty()) {
            this.board.setValue(row, col, 0);
            this.styleCell(row, col, field, "white", "#000000", false);
            return;
        }
        this.processDigitInput(row, col, field, trimmedText);
    }

    private void processDigitInput(int row, int col, TextField field, String text) {
        if (text.matches("^[1-9]$")) {
            int value = Integer.parseInt(text);
            this.board.setValue(row, col, value);
            int index = row * SudokuBoard.SIZE + col;
            int expected = Character.getNumericValue(this.currentPuzzle.getSolutionString().charAt(index));
            if (value == expected) {
                this.styleCell(row, col, field, "#d4edda", "#155724", true);
                this.checkGameCompletion();
            } else {
                this.styleCell(row, col, field, "#f8d7da", "#721c24", true);
                this.handleIncorrectGuess();
            }
        } else {
            this.board.setValue(row, col, 0);
            this.styleCell(row, col, field, "#fff3cd", "#856404", false);
        }
    }

    private void styleCell(int row, int col, TextField field, String background, String textColor, boolean bold) {
        double top = row % SudokuBoard.SUBGRID_SIZE == 0 ? 2 : 0.5;
        double right = col == SudokuBoard.SIZE - 1 || (col + 1) % SudokuBoard.SUBGRID_SIZE == 0 ? 2 : 0.5;
        double bottom = row == SudokuBoard.SIZE - 1 || (row + 1) % SudokuBoard.SUBGRID_SIZE == 0 ? 2 : 0.5;
        double left = col % SudokuBoard.SUBGRID_SIZE == 0 ? 2 : 0.5;
        String fontWeight = bold ? "bold" : "normal";
        field.setStyle("-fx-background-color: " + background + ";"
                + "-fx-text-fill: " + textColor + ";"
                + "-fx-font-weight: " + fontWeight + ";"
                + "-fx-border-color: #343a40;"
                + "-fx-border-width: " + top + " " + right + " " + bottom + " " + left + ";");
    }

    private void handleIncorrectGuess() {
        this.remainingLives--;
        this.updateLivesLabel();
        if (this.remainingLives == 0) {
            this.finishGame("Game over", "You have used all your lives.");
        }
    }

    private void checkGameCompletion() {
        if (this.board.isComplete()) {
            this.finishGame("Victory !", "Congratulations !", "You have solved the Sudoku !");
        }
    }

    private void loadDifficultyGame(String difficulty) {
        this.isUpdatingUi = true;
        try {
            this.currentPuzzle = this.repository.getRandomPuzzle(difficulty);
            this.board = new SudokuBoard();
            this.board.loadPuzzle(this.currentPuzzle.getPuzzleString());
            this.remainingLives = MAX_LIVES;
            this.isGameFinished = false;
            this.updateLivesLabel();

            for (int row = 0; row < SudokuBoard.SIZE; row++) {
                for (int col = 0; col < SudokuBoard.SIZE; col++) {
                    TextField field = this.cellsUi[row][col];
                    int value = this.board.getCell(row, col).getValue();
                    if (value > 0) {
                        field.setText(String.valueOf(value));
                        field.setEditable(false);
                        this.styleCell(row, col, field, "#e9ecef", "#000000", true);
                    } else {
                        field.clear();
                        field.setEditable(true);
                        this.styleCell(row, col, field, "white", "#000000", false);
                    }
                }
            }
        } finally {
            this.isUpdatingUi = false;
        }
    }

    private void updateLivesLabel() {
        this.livesLabel.setText("Lives: " + this.remainingLives + " / " + MAX_LIVES);
    }

    private void finishGame(String title, String message) {
        this.finishGame(title, title, message);
    }

    private void finishGame(String title, String header, String message) {
        this.isGameFinished = true;
        for (TextField[] row : this.cellsUi) {
            for (TextField field : row) {
                field.setEditable(false);
            }
        }

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void handleNewGame(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(Main.NEW_GAME_WINDOW_RESOURCE));
            Parent parent = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Parameters for New Game");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(parent));
            stage.showAndWait();

            NewGameWindow controller = loader.getController();
            if (!controller.isStartConfirmed()) {
                if (this.currentPuzzle == null) {
                    Platform.exit();
                }
                return;
            }
            String selectedDifficulty = controller.getSelectedDifficulty();
            this.titleLabel.setText("Sudoku - " + selectedDifficulty);
            this.loadDifficultyGame(selectedDifficulty);
        } catch (IOException exception) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Impossible to open the new game window.");
            alert.showAndWait();
        }
    }
}