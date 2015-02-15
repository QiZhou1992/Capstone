package data.view;

import data.MainApp;
import data.model.Table;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

public class DatasetController {
	
	@FXML
	private ListView<Table> tableList;
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
    	
    	showTableDetails(null);
    	
    	this.tableList.getSelectionModel().selectedItemProperty().addListener(
    			(observable, oldValue, newValue) -> showTableDetails(newValue));
    }
    
    private void showTableDetails(Table table){
    	if(table!=null){
    		this.identifierLabel.setText(String.valueOf(table.Identifier()));
    		this.titleLabel.setText(table.name());
    		this.descriptionLabel.setText(table.description());
    	}else{
    		this.identifierLabel.setText("");
    		this.titleLabel.setText("");
    		this.descriptionLabel.setText("");
    	}
    }
    
	public void setMainApp(MainApp mainApp) {
    	this.mainApp = mainApp;
        // Add observable list data to the tree
    	tableList.setItems(mainApp.getTableList());
    	tableList.setCellFactory(column -> {
			return new ListCell<Table>(){
				@Override
				protected void updateItem(Table item, boolean empty){
					super.updateItem(item, empty);
					if(item!=null){
						setText(item.name());
					}
				}
			};
    	});
    }
}
