package data.view;

import data.model.DataSet;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/*
 * controller of data set
 */
public class DatasetController {

	@FXML
	private TextField title;
	@FXML
	private TextField description;
	@FXML
	private TextField created;
	@FXML
	private TextField keyword;
	@FXML
	private TextField landingPage;
	@FXML
	private Button apply;
	@FXML
	private Button add;
	
	private DataSet dataset;
	
	public DatasetController() {
    }
	
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	
    }
    
    public void setDataset(DataSet dataset){
    	this.dataset=dataset;
    	this.title.setText(dataset.getTitle());
    	this.description.setText(dataset.getDescription());
    	this.created.setText(dataset.getCreated());
    	this.keyword.setText(dataset.KeyWords().toString());
    	this.landingPage.setText(dataset.getLandingPage());
    }
    
    /**
     * Called when the user clicks on the add button.
     * Add table to this data set.
     */
    @FXML
    private void handleAdd() {
    	// TODO add action handler here
    	
    }
    
    /**
     * Called when the user clicks on the apply button.
     * Apply any changes to selected data set.
     */
    @FXML
    private void handleApply() {
    	// TODO add action handler here
        System.out.println("click apply...");
        if(this.validation()){
        	this.dataset.setTitle(this.title.getText());
        	if(this.description.getText()!=null){
        		this.dataset.setDesription(this.description.getText());
        	}
        	if(this.landingPage.getText()!=null){
        		this.dataset.setLandingPage(this.landingPage.getText());
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
    		System.out.println("title...");
    		return false;
    	}
    	String createdString = this.created.getText();
    	if(!timeValidation(createdString)){
    		System.out.println("time...");
    		return false;
    	}
    	
		return true;
    }
    
    /**
     * check time format
     * @param t
     * @return
     */
    public static boolean timeValidation(String t){
    	// TODO need time check function
    	
    	return true;
    }
	
}
