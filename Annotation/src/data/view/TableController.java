package data.view;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import data.model.Table;
import data.model.represents;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class TableController {
	
	@FXML
	private TextField title;
	@FXML
	private TextField description;
	@FXML
	private ComboBox<String> represent;
	@FXML
	private Button apply;
	
	private Table table;
	
	public TableController(){	
	}
	
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	
    }
    
	public void setTable(Table table) throws IOException{
    	this.table=table;
    	this.title.setText(table.getTitle());
    	this.description.setText(table.getDescription());
    	ObservableList<String> representList = FXCollections.observableArrayList();
		Map<Integer,String> representOptions = represents.allOptions();
		//represent id start from 1
    	for(int i=1;i<=representOptions.size();i++){
    		representList.add(representOptions.get(i));
    	}
    	this.represent.getItems().addAll(representList);
    	//0 is id of empty represent
    	if(this.table.theRepresent().getValue()!=0){
    		this.represent.setValue(this.table.theRepresent().getString());
    	}
    }
    
    /**
     * Called when the user clicks on the delete button.
     * Apply any changes to selected table.
     * @throws IOException 
     */
    @FXML
    private void handleApply() throws IOException {
    	// TODO add action handler here
        System.out.println("click apply...");
        if(this.validation()){
        	this.table.setTitle(this.title.getText());
        	if(this.description.getText()!=null){
        		this.table.setDesription(this.description.getText());
        	}
        	int representIndex = this.represent.getSelectionModel().getSelectedIndex();
        	if(representIndex>=0){
        		this.table.setRepresents(representIndex+1);
        	}
        }else{
        	
        }
    }
    
    /**
     * check form validation
     * @return
     */
    private boolean validation(){
    	// TODO complete form validation
    	if(this.title.getText()==null){
    		return false;
    	}
		return true;
    }

}
