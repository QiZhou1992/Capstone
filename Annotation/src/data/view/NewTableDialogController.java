package data.view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import data.model.Column;
import data.model.DataSet;
import data.model.InputFile;
import data.model.MyData;
import data.model.Table;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class NewTableDialogController {
	
	@FXML
	private TextField title;
	@FXML
	private TextField description;
	
	private Stage dialogStage;
	private Table table;
	private boolean okClicked = false;
	
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
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
     * Sets the person to be edited in the dialog.
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
     */
    @FXML
    private void handleOk() {
    	
        if (isInputValid()) {
        	this.table.setTitle(this.title.getText());
        	if(this.description.getText()!=null){
        		this.table.setDesription(this.description.getText());
        	}
            okClicked = true;
            dialogStage.close();
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
     */
    private boolean isInputValid() {
    	// TODO complete form validation
    	if(this.title.getText()==null||this.title.getText().trim().equals("")){
    		System.out.println("title...");
    		return false;
    	}
    	if(this.table.AllColumn().size()==0){
    		return false;
    	}
		return true;
    }
    
    @FXML
    private void UploadAs() throws IOException, InterruptedException {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "csv files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
   
        fileChooser.setTitle("Open Resource File");
        File file =fileChooser.showOpenDialog(this.dialogStage);
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
