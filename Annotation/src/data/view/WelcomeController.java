package data.view;

import java.io.IOException;

import data.MainApp;
import data.model.DataSet;
import data.model.MyData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;

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
	 * @throws IOException 
	 */
	@FXML
	private void handleNewDataset() throws IOException {
	    DataSet tempPerson = new DataSet("tmp title","mm/dd/yyyy");
	    boolean okClicked = this.mainApp.showDatasetEditDialog(tempPerson);
	    if (okClicked) {
	        TreeItem<MyData> treeNode = this.treeViewController.addNewDataset(tempPerson);
	    	this.mainApp.addDataset(tempPerson);
	    	// replace data detail with new added data set
    		FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/DatasetDetail.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();
            this.mainApp.replaceDataDetail(personOverview);
    		//set the data set controller. doing cast here.
    		DatasetController controller = loader.getController();
    		controller.setDataset((DataSet)(treeNode.getValue()),this.mainApp, treeNode);
	    }
	}

}
