package data.view;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import data.model.ClassColumn;
import data.model.JoinTable;
import data.model.Validation;
import data.model.represents;
import data.model.semanticRelations;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
/*
 * controller of column
 */
public class ClassColumnController {
	
	@FXML
	private TextField title;
	@FXML
	private TextField description;
	@FXML
	private ComboBox<String> semanticRelation;
	@FXML
	private ComboBox<String> represent;
	
	private ClassColumn column;
	//parent table
	private JoinTable table;
	// check validation
	private Validation valid;
	
	public ClassColumnController(){	
	}
	
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	
    }
    
    /**
     * initialize this column controller
     * 
     * @param column2 
     * current column
     * @param table
     * parent table
     * @param myData
     * parent tree node
     * @param columnNode
     * current tree node
     * @throws IOException
     */
    public void setColumn(ClassColumn column2, JoinTable table) throws IOException{
    	this.column=column2;
    	this.table=table;
    	this.title.setText(column2.getTitle());
    	this.description.setText(column2.getDescription());
    	ObservableList<String> semanticRelationList = FXCollections.observableArrayList();
    	Map<Integer,String> semanticRelationOptions = semanticRelations.allOptions();
    	//semantic relation id start from 1
    	for(int i=1;i<semanticRelationOptions.size();i++){
    		semanticRelationList.add(semanticRelationOptions.get(i));
    	}
    	this.semanticRelation.getItems().addAll(semanticRelationList);
    	if(this.column.thisSemanticRelation().getValue()!=0){
    		this.semanticRelation.setValue(this.column.thisSemanticRelation().getString());
    	}
    	
    	ObservableList<String> representList = FXCollections.observableArrayList();
    	Map<Integer,String> representOptions = represents.allOptions();
    	//represent id start from 1
    	for(int i=1;i<=representOptions.size();i++){
    		representList.add(representOptions.get(i));
    	}
    	this.represent.getItems().addAll(representList);
    	if(((ClassColumn)this.column).Represent()!=null){
    		this.represent.setValue(((ClassColumn)this.column).Represent().getString());
    	}else{
    		((ClassColumn)this.column).setRepresent(0);
    		this.represent.setValue(((ClassColumn)this.column).Represent().getString());
    	}
    }
    
    /**
     * Called when the user clicks on the apply button.
     * Save any changes to selected column.
     * @throws IOException 
     * @throws InterruptedException 
     */
    @FXML
    private void handleApply() throws IOException, InterruptedException {
        if(this.validation()){
        	this.column.setTitle(this.title.getText());
        	if(this.description.getText()!=null){
        		this.column.setDesription(this.description.getText());
       		}
           	int semanticRelationIndex = this.semanticRelation.getSelectionModel().getSelectedIndex();
           	if(semanticRelationIndex>=0){
           		this.column.modifiedSemanticRelations(semanticRelationIndex+1);
           	}
       		this.column.setBelongsTo(this.table);
       		this.column.setRepresent(this.represent.getSelectionModel().getSelectedIndex()+1);       			
        }else{
        	Set<String> errors = this.valid.ErrorField();
        	Alert alert = new Alert(AlertType.ERROR);
        	
        	alert.setTitle("Error Dialog");
        	alert.setHeaderText("Error in the column");
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
     * @throws InterruptedException 
     * @throws IOException 
     */
    private boolean validation() throws IOException, InterruptedException{
    	//this is a class column
    	ClassColumn tmpColumn = new ClassColumn(this.title.getText());
    	tmpColumn.setDesription(this.description.getText());
    		
       	tmpColumn.modifiedSemanticRelations(this.semanticRelation.getSelectionModel().getSelectedIndex()+1);

   		tmpColumn.setRepresent(this.represent.getSelectionModel().getSelectedIndex()+1);
   		valid = tmpColumn.check();
    	if(valid.result()){
    		return true;
    	}else{
    		return false;
    	}
    }

}
