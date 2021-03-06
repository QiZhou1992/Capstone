package data.view;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import data.MainApp;
import data.model.MyData;
import data.model.NormalTable;
import data.model.Validation;
import data.model.represents;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TreeItem;

public class NormalTableController {
	
	@FXML
	private TextField title;
	@FXML
	private TextField description;
	@FXML
	private ComboBox<String> represent;
	@FXML
	private Button apply;
	
	private NormalTable table;
	
	private TreeItem<MyData> treeNode;
	
	private Validation valid;
	
	public NormalTableController(){	
	}
	
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	
    }
    /**
     * Initialize the table controller
     * 
     * @param table
     * current table
     * @throws IOException
     */
	public void setTable(NormalTable table,MainApp mainApp,TreeItem<MyData> treeNode) throws IOException{
		this.treeNode = treeNode;
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
     * Called when the user clicks on the apply button.
     * Apply any changes to selected table.
     * @throws IOException 
     * @throws InterruptedException 
     */
    @FXML
    private void handleApply() throws IOException, InterruptedException {
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
        	Set<String> errors = this.valid.ErrorField();
        	Alert alert = new Alert(AlertType.ERROR);
        	
        	alert.setTitle("Error Dialog");
        	alert.setHeaderText("Error in the table");
        	String message ="";
        	for(String s: errors){
        		message+=s+" ";
        	}
        	alert.setContentText("Missing input: "+message);

        	alert.showAndWait();
        }
    }
    
    /**
     * check form validation
     * @return
     * @throws IOException 
     * @throws InterruptedException 
     */
    private boolean validation() throws IOException, InterruptedException{
    	NormalTable tmpTable = new NormalTable(this.title.getText(),this.table.parentDataSet());
    	tmpTable.setDesription(this.description.getText());
    	tmpTable.setRepresents(this.represent.getSelectionModel().getSelectedIndex()+1);
    	valid = tmpTable.check();
    	if(valid.result()){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    @FXML
    private void handleDelete(){
    	this.table.parentDataSet().RemoveTable(this.table);
    	this.table.removeBelongsTo();
    	this.treeNode.getParent().getChildren().remove(this.treeNode);
    }

}
