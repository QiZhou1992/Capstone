<<<<<<< HEAD
=======
package data;

import java.io.IOException;

import data.model.DataSet;
import data.model.MyData;
import data.model.Table;
import data.view.DatasetController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {
	
	private Stage primaryStage;
	private BorderPane rootLayout;
	
	private static ObservableList<MyData> tables = FXCollections.observableArrayList();
	private static ObservableList<MyData> datasets = FXCollections.observableArrayList();
	
	public MainApp(){
		CreateDatasets(datasets);
		
	}
	
	//for test
	public static void CreateTables(ObservableList<MyData> tables){
		DataSet dataset = new DataSet("My dataset", "mm/dd/yyyy");
		Table t1 = new Table("table 1",dataset);
		Table t2 = new Table("table 2",dataset);
		Table t3 = new Table("table 3",dataset);
		Table t4 = new Table("table 4",dataset);
		Table t5 = new Table("table 5",dataset);
		Table t6 = new Table("table 6",dataset);
		tables.addAll(t1,t2,t3,t4,t5,t6);
	}
	
	//for test
	public static void CreateDatasets(ObservableList<MyData> datasets2){
		DataSet d1 = new DataSet("dataset 1","mm/dd/yyyy");
		Table t1 = new Table("table 1",d1);
		d1.addTable(t1);
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

    public ObservableList<MyData> getTableList() {
        return tables;
    }
    
    public ObservableList<MyData> getDataSetList(){
    	return datasets;
    }
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Annotation App");
		
		initRootLayout();
		
		showDataOverview();
		
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
			primaryStage.show();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void showDataOverview(){
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/DataOverview.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);
            
         // Give the controller access to the main app.
            DatasetController controller = loader.getController();
         	controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
    public Stage getPrimaryStage() {
        return primaryStage;
    }

	public static void main(String[] args) {
		launch(args);
	}
}
>>>>>>> FETCH_HEAD
