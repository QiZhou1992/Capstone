package data.view;

import data.MainApp;
import data.model.DataSet;
import javafx.fxml.FXML;

public class WelcomeController {
	
	private TreeViewController treeViewController;
	
	private MainApp mainApp;
	
	public void setWelcome(TreeViewController treeViewController,MainApp mainApp){
		this.treeViewController = treeViewController;
		this.mainApp = mainApp;
	}
	
	/**
	 * Called when the user clicks the new button. Opens a dialog to edit
	 * details for a new person.
	 */
	@FXML
	private void handleNewDataset() {
	    DataSet tempPerson = new DataSet("tmp title","mm/dd/yyyy");
	    boolean okClicked = this.mainApp.showPersonEditDialog(tempPerson);
	    if (okClicked) {
	        this.treeViewController.addNewDataset(tempPerson);
	    	this.mainApp.getDataSetList().add(tempPerson);
	    }
	}

}
