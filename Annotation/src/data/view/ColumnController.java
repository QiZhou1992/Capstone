package data.view;

import data.model.Column;
import data.model.Table;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ColumnController {
	
	@FXML
	private TextField title;
	@FXML
	private TextField description;
	@FXML
	private Button apply;
	
	private Column column;
	
	public ColumnController(){	
	}
	
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	
    }
    
    public void setColumn(Column column2){
    	this.column=column2;
    	this.title.setText(column2.getTitle());
    	this.description.setText(column2.getDescription());
    }
    
    /**
     * Called when the user clicks on the delete button.
     * Apply any changes to selected column.
     */
    @FXML
    private void handleApply() {
    	// TODO add action handler here
        System.out.println("click apply...");
        if(this.validation()){
        	this.column.setTitle(this.title.getText());
        	if(this.description.getText()!=null){
        		this.column.setDesription(this.description.getText());
        	}
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
