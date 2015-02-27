package data.view;

import data.model.Table;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class TableController {
	
	@FXML
	private TextField title;
	@FXML
	private TextField description;
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
    
    public void setTable(Table table){
    	this.table=table;
    	this.title.setText(table.getTitle());
    	this.description.setText(table.getDescription());
    }
    
    /**
     * Called when the user clicks on the delete button.
     * Apply any changes to selected table.
     */
    @FXML
    private void handleApply() {
    	// TODO add action handler here
        System.out.println("click apply...");
        if(this.validation()){
        	this.table.setTitle(this.title.getText());
        	if(this.description.getText()!=null){
        		this.table.setDesription(this.description.getText());
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
