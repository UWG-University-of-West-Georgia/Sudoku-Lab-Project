package edu.westga.comp4420.sudoku.view.codebehind;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

/**
 * Controller for the New Game window in the Sudoku application.
 * 
 * @author Raphael Gall
 * @version Fall 2026
 */
public class NewGameWindow {

    @FXML
    private ComboBox<String> difficultyCombo;

    @FXML
    private Button startButton;

    private String selectedDifficulty = "Easy";
    private boolean startConfirmed;

    @FXML
    void initialize() {
        this.difficultyCombo.setItems(FXCollections.observableArrayList(
            "Easy", 
            "Medium", 
            "Hard", 
            "Expert"
        ));
        this.difficultyCombo.setValue("Easy");
    }

    @FXML
    void handleStartGame(ActionEvent event) {
        this.selectedDifficulty = this.difficultyCombo.getValue();
        this.startConfirmed = true;
        Stage stage = (Stage) this.startButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Indicates whether the user confirmed starting a new game.
     *
     * @return true if the Start button was clicked
     */
    public boolean isStartConfirmed() {
        return this.startConfirmed;
    }

    /**
     * Returns the selected difficulty level from the ComboBox.
     * 
     * @precondition none
     * @postcondition none
     * 
     * @return the selected difficulty level as a string
     */
    public String getSelectedDifficulty() {
        return this.selectedDifficulty;
    }
}