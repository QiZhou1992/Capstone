package data;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import data.model.Column;
import data.model.DataListWrapper;
import data.model.DataSet;
import data.model.Table;
import data.view.DatasetEditDialogController;
import data.view.RootLayoutController;
import data.view.TreeViewController;
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
	
	private Stage primaryStage;
	private BorderPane rootLayout;
	
	private static ObservableList<DataSet> datasets = FXCollections.observableArrayList();
	
	public MainApp() throws IOException{
		CreateDatasets(datasets);
		
	}
	
	//for test
	public static void CreateDatasets(ObservableList<DataSet> datasets2) throws IOException{
		DataSet d1 = new DataSet("dataset 1","mm/dd/yyyy");
		Table t1 = new Table("table 1",d1);
		t1.addColumn(new Column("column 1"));
		d1.addTable(t1);
		d1.addKeyword("keyword 1");
		d1.addKeyword("keyword 2");
		DataSet d2 = new DataSet("dataset 2","mm/dd/yyyy");
		Table t2 = new Table("table 2",d2);
		d2.addTable(t2);
		DataSet d3 = new DataSet("dataset 3","mm/dd/yyyy");
		Table t3 = new Table("table 3",d3);
		d3.addTable(t3);
		DataSet d4 = new DataSet("dataset 4","mm/dd/yyyy");
		Table t4 = new Table("table 4",d4);
		d4.addTable(t4);
		DataSet d5 = new DataSet("dataset 5","mm/dd/yyyy");
		Table t5 = new Table("table 5",d5);
		d5.addTable(t5);
		DataSet d6 = new DataSet("dataset 6","mm/dd/yyyy");
		Table t6 = new Table("table 6",d6);
		d6.addTable(t6);
		datasets2.addAll(d1,d2,d3,d4,d5,d6);
	}
    
    public ObservableList<DataSet> getDataSetList(){
    	return datasets;
    }
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Annotation App");
		
		initRootLayout();
		
		showDataOverview();
		
		showDataDetail();
		
	}
	
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
		
	    // Try to load last opened person file.
		/*
	    File file = getDatasetFilePath();
	    if (file != null) {
	        loadDataFromFile(file);
	    }
	    */
	}
	
	public void showDataOverview(){
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/DataOverview.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setLeft(personOverview);
            
         // Give the controller access to the main app.
            TreeViewController controller = loader.getController();
         	controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void showDataDetail(){
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/DatasetDetail.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
    /**
     * Called when the user clicks on the add button.
     * Add table to this data set.
     */
    public boolean showPersonEditDialog(DataSet dataset) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/DatasetEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("New Dataset");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            DatasetEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(dataset);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
	
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
    public BorderPane getRootLayout(){
    	return rootLayout;
    }
    

    /**
     * Returns the datasets file preference, i.e. the file that was last opened.
     * The preference is read from the OS specific registry. If no such
     * preference can be found, null is returned.
     * 
     * @return
     */
    public File getDatasetFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }
    
    /**
     * Sets the file path of the currently loaded file. The path is persisted in
     * the OS specific registry.
     * 
     * @param file the file or null to remove the path
     */
    public void setDataFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());
            
        } else {
            prefs.remove("filePath");

        }
    }
    
    /**
     * Loads data sets data from the specified file. The current data sets data will
     * be replaced.
     * 
     * @param file
     */
    public void loadDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(DataListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            DataListWrapper wrapper = (DataListWrapper) um.unmarshal(file);

            datasets.clear();
            datasets.addAll(wrapper.getDatasets());

            // Save the file path to the registry.
            setDataFilePath(file);

        } catch (Exception e) { // catches ANY exception
        	/*
            Dialogs.create()
                    .title("Error")
                    .masthead("Could not load data from file:\n" + file.getPath())
                    .showException(e);
                    */
        }
    }
    
    /**
     * Saves the current data sets data to the specified file.
     * 
     * @param file
     */
    public void saveDataToFile(File file) {
    	// TODO this function does not work correctly
        try {
        	
            JAXBContext context = JAXBContext.newInstance(DataListWrapper.class);
            System.out.println("test");
            
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            

            // Wrapping our person data.
            DataListWrapper wrapper = new DataListWrapper();
            
            
            wrapper.setDatasets(datasets);

            // Marshalling and saving XML to the file.
            m.marshal(wrapper, file);

            // Save the file path to the registry.
            //setDataFilePath(file);
           
        } catch (Exception e) { // catches ANY exception
        	System.out.println("error in saving workspace...");
        	/*
            Dialogs.create().title("Error")
                    .masthead("Could not save data to file:\n" + file.getPath())
                    .showException(e);
                    */
        }
    }
    
	public static void main(String[] args) {
		launch(args);
	}
}
