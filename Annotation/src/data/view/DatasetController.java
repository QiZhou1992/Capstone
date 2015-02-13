package data.view;

import data.MainApp;
import data.model.Table;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.util.Callback;

public class DatasetController {
	
	@FXML
	private TableView<Table> tableTable;
	@FXML
	private TableColumn<Table,String> tableNameColumn;
	@FXML
	private Label identifierLabel;
	@FXML
	private Label titleLabel;
	@FXML
	private Label descriptionLabel;
	
	private MainApp mainApp;
	public DatasetController() {
    }
	
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        //firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        //lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
    	tableNameColumn.setCellValueFactory(cellData -> cellData.getValue().findIdentifier());
    }
    
    @SuppressWarnings("unchecked")
	public void setMainApp(MainApp mainApp) {
    	this.mainApp = mainApp;
        // Add observable list data to the tree
    	tableTable.setItems(mainApp.getTableList());
    }
}
