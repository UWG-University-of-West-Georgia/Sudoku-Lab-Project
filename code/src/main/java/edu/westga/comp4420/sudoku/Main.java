package edu.westga.comp4420.sudoku;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The main entry point for the Sudoku application.
 * 
 * @author Raphael Gall
 * @version Fall 2026
 */
public class Main extends Application {

    public static final String WINDOW_TITLE = "Sudoku App";
    public static final String MAIN_WINDOW_RESOURCE = "/edu/westga/comp4420/sudoku/view/codebehind/MainWindow.fxml";
    public static final String NEW_GAME_WINDOW_RESOURCE = "/edu/westga/comp4420/sudoku/view/codebehind/NewGameWindow.fxml";

    /**
     * Start the JavaFX application by loading the main window from FXML.
     * 
     * @precondition none
     * @postcondition none
     * 
     * @param primaryStage the primary stage for this application
     * @throws IOException if the FXML file cannot be loaded
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource(Main.MAIN_WINDOW_RESOURCE));
        Scene scene = new Scene(parent);
        primaryStage.setTitle(WINDOW_TITLE);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * The main entry point for the Java application.
     * 
     * @precondition none
     * @postcondition none
     * 
     * @param args arguments of the command line
     */
    public static void main(String[] args) {
        Main.launch(args);
    }
}