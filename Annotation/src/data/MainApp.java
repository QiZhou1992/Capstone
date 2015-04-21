package data;

import java.io.IOException;
import java.util.List;

import data.model.DataSet;
import data.model.JoinTable;
import data.model.Table;
import data.view.DatasetEditController;
import data.view.EditJoinTableDialogController;
import data.view.RootLayoutController;
import data.view.TreeViewController;
import data.view.UploadMultipleTablesDialogController;
import data.view.WelcomeController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {
	//primary stage
	private Stage primaryStage;
	//root layout
	private BorderPane rootLayout;
	//All datasets
	private ObservableList<DataSet> datasets;
	
	private TreeViewController treeViewController;
	
	public MainApp() throws IOException, InterruptedException{
		this.datasets = FXCollections.observableArrayList();
	}
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Annotation App");
		
		initRootLayout();
		
		showDataOverview();
		
		showDataDetail();
		
	}
	/**
	 * initialize root layout
	 */
	public void initRootLayout(){
		try{
			//load root layout from fxml file	
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane)loader.load();
				
			//show the scene containing the root layout
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			
	        // Give the controller access to the main app.
	        RootLayoutController controller = loader.getController();
	        controller.setMainApp(this);
			
			primaryStage.show();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	/**
	 * show all data sets, and set the controller
	 */
	public void showDataOverview(){
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/DataOverview.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setLeft(personOverview);
            
            // Give the controller access to the main app.
            this.treeViewController = loader.getController();
         	this.treeViewController.setMainApp(this);
         	this.treeViewController.setMyData();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * show data detail and set the controller
	 */
	public void showDataDetail(){
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Welcome.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();
            
            WelcomeController controller = loader.getController();
    		controller.setWelcome(this.treeViewController,this);
    		
            // Set data overview into the center of root layout.
            rootLayout.setCenter(personOverview);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
    /**
     * Called when the user clicks on the add button.
     * Add table to this data set.
     */
    public boolean showDatasetEditDialog(DataSet dataset) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/DatasetEdit.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Dataset");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the data set into the controller.
            DatasetEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setDataset(dataset);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Called when the user clicks on the add button.
     * Add table to this data set.
     */
    public boolean showEditJoinTableDialog(JoinTable table) {
        try {   	
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/EditJoinTableDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add Join Table");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            EditJoinTableDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setTable(table);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Called when the user clicks on the Multiple Tables.
     * Add tables to this data set.
     */    
    public boolean showUploadMultipleTablesDialog(List<Table> tables){
        try {
            // TODO complete function
        	
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/UploadMultipleTablesDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Upload Tables");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            UploadMultipleTablesDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setTableList(tables);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * get the primary stage
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
    /**
     * replace current data scene with a new scene
     * 
     * @param newScene
     */
    public void replaceDataDetail(AnchorPane newScene){
    	this.rootLayout.setCenter(newScene);
    }
    
    /**
     * add new Data set to the data set list
     * @param newDataset
     */
    public void addDataset(DataSet newDataset){
    	this.datasets.add(newDataset);
    }
    
    /**
     * return the number of data sets
     * @return
     */
    public int size(){
    	return this.datasets.size();
    }
    
    /**
     * get ith data set
     * @param i
     * @return
     */
    public DataSet get(int i){
    	if(i<this.datasets.size()){
    	 	return this.datasets.get(i);
    	}else{
    		return null;
    	}
    }
    
    public void delete(DataSet dataset){
    	this.datasets.remove(dataset);
    }
    
	public static void main(String[] args) {
		launch(args);
	}
}
