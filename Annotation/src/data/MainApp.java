package data;

import java.io.IOException;

import data.model.DataSet;
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
	
	private ObservableList<Table> tables = FXCollections.observableArrayList();
	
	public MainApp(){
		CreateTables(tables);
	}
	
	public static void CreateTables(ObservableList<Table> tables){
		DataSet dataset = new DataSet("My dataset", "mm/dd/yyyy");
		Table t1 = new Table("table 1",dataset);
		Table t2 = new Table("table 2",dataset);
		Table t3 = new Table("table 3",dataset);
		Table t4 = new Table("table 4",dataset);
		Table t5 = new Table("table 5",dataset);
		Table t6 = new Table("table 6",dataset);
		tables.addAll(t1,t2,t3,t4,t5,t6);
	}

    public ObservableList<Table> getTableList() {
        return tables;
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
