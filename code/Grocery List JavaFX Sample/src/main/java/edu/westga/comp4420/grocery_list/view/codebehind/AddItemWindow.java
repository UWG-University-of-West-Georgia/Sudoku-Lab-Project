package edu.westga.comp4420.grocery_list.view.codebehind;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import java.util.List;

import edu.westga.comp4420.grocery_list.model.GroceryItem;

/**
 * CodeBehind To Handle Processing for the AddItemWindow
 *
 * @author	Comp 4420
 * @version Fall 2026
 */
public class AddItemWindow {   
	@FXML private AnchorPane guiPane;
	
	private List<GroceryItem> groceryItems;
	
	public void setItemList(List<GroceryItem> groceryItems) {
		this.groceryItems = groceryItems;
	}
	
	@FXML
	void initialize() {
		assert this.guiPane != null : "fx:id=\"guiPane\" was not injected: check your FXML file 'MainWindow.fxml'.";
	}
}
