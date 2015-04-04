package data.view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import data.model.Column;
import data.model.InputFile;
import data.model.Table;
import data.model.Validation;
import data.model.represents;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class NewTableDialogController {
	
	@FXML
	private TextField title;
	@FXML
	private TextField description;
	@FXML
	private ComboBox<String> represent;
	
	private Stage dialogStage;
	
	private Table table;

	private boolean okClicked = false;
	
	private Validation valid;
	
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     * @throws IOException 
     */
    @FXML
    private void initialize() throws IOException {
    	ObservableList<String> representList = FXCollections.observableArrayList();
		Map<Integer,String> representOptions = represents.allOptions();
		//represent id start from 1
    	for(int i=1;i<=representOptions.size();i++){
    		representList.add(representOptions.get(i));
    	}
    	this.represent.getItems().addAll(representList);
    }
    
    /**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    /**
     * Sets the table to be edited in the dialog.
     * 
     * @param person
     */
    public void setTable(Table table) {
        this.table = table;
    }
    
    /**
     * Returns true if the user clicked OK, false otherwise.
     * 
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }
    
    /**
     * Called when the user clicks ok.
     * @throws IOException 
     */
    @FXML
    private void handleOk() throws IOException {
    	
        if (isInputValid()) {
        	this.table.setTitle(this.title.getText());
        	if(this.description.getText()!=null){
        		this.table.setDesription(this.description.getText());
        	}
        	int representIndex = this.represent.getSelectionModel().getSelectedIndex();
        	if(representIndex>=0){
        		this.table.setRepresents(representIndex+1);
        	}
            okClicked = true;
            dialogStage.close();
        }else{
        	Set<String> errors = this.valid.ErrorField();
        	Alert alert = new Alert(AlertType.ERROR);
        	
        	alert.setTitle("Error Dialog");
        	alert.setHeaderText("Error in the dataset");
        	String message ="";
        	for(String s: errors){
        		message+=s+" ";
        	}
        	if(this.table.AllColumn().size()==0){
        		message+="content";
        	}
        	alert.setContentText("Missing input: "+message);

        	alert.showAndWait();
        }
        
    }
    
    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    
    
    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     * @throws IOException 
     */
    private boolean isInputValid() throws IOException {
    	// TODO complete form validation
    	this.table.setTitle(this.title.getText());
    	this.table.setDesription(this.description.getText());
    	this.table.setRepresents(this.represent.getSelectionModel().getSelectedIndex()+1);
    	valid = this.table.check();
    	if(this.table.AllColumn().size()==0){
    		return false;
    	}
    	if(valid.result()){
    		return true;
    	}else{
    		return false;
    	}
    }
    /**
     * Called when user clicks open button. Upload a table from csv file.
     * 
     * @throws IOException
     * @throws InterruptedException
     */
    @FXML
    private void UploadAs() throws IOException, InterruptedException {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "csv files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
   
        fileChooser.setTitle("Open Resource File");
        File file =fileChooser.showOpenDialog(this.dialogStage);
        if(file!=null){
	        InputFile content=new InputFile();
	        ArrayList<String> column = new ArrayList<String>();
	        column=content.read(file);
	        
	        //handle the input title
	    	for(int i=0;i<column.size();i++){
	    		Column temp=new Column(column.get(i));
	    		this.table.addColumn(temp);
	    	}
        }
    }
}
