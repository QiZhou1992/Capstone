package data.view;

import java.util.Set;

import data.model.DataSet;
import data.model.Validation;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/*
 * controller of data set
 */
public class DatasetEditController {

	@FXML
	private TextField title;
	@FXML
	private TextField description;
	@FXML
	private TextField created;
	@FXML
	private ListView<String> keywordList;
	@FXML
	private TextField keyword;
	@FXML
	private TextField landingPage;
	
	private DataSet dataset;
	
	private Validation valid;
	
    private Stage dialogStage;
    
    private boolean okClicked = false;
	
	public DatasetEditController() {
    }
	
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
     * Returns true if the user clicked OK, false otherwise.
     * 
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }
    
    /**
     * initialize this data set controller
     * 
     * @param dataset
     * current data set object
     * @param mainApp
     * main app object
     * @param myData
     * current tree node
     */
    public void setDataset(DataSet dataset){
    	this.dataset=dataset;
    	this.title.setText(dataset.getTitle());
    	this.description.setText(dataset.getDescription());
    	this.created.setText(dataset.getCreated());
    	this.keywordList.getItems().clear();
    	for(String nextKeyword: this.dataset.KeyWords()){
    		this.keywordList.getItems().add(nextKeyword);
    	}
    	this.landingPage.setText(dataset.getLandingPage());
    }
    
    /**
     * Called when user clicks on the enter button.
     * Add new keyword to the keyword list.
     */
    @FXML
    private void handleEnter(){
    	if(this.keyword.getText()!=null&&!this.keyword.getText().trim().equals("")){
    		this.keywordList.getItems().add(this.keyword.getText());
    		this.keyword.clear();
    	}
    }
    
    /**
     * Called when user clicks on the delete button.
     * Delete selected item in keywords list.
     */
    @FXML
    private void handleDelete(){
    	int selectedIdx = this.keywordList.getSelectionModel().getSelectedIndex();
    	if(selectedIdx!=-1){
    		int newSelectedIdx = (selectedIdx == this.keywordList.getItems().size()-1)?selectedIdx-1:selectedIdx;
    		this.keywordList.getItems().remove(selectedIdx);
    		this.keywordList.getSelectionModel().select(newSelectedIdx);
    	}
    }
    
    /**
     * Called when the user clicks on the apply button.
     * Apply any changes to selected data set.
     */
    @FXML
    private void handleOK() {
    	// TODO add action handler here
        if(this.validation()){
        	this.dataset.setTitle(this.title.getText());
        	if(this.description.getText()!=null){
        		this.dataset.setDesription(this.description.getText());
        	}
        	if(this.landingPage.getText()!=null){
        		this.dataset.setLandingPage(this.landingPage.getText());
        	}
        	this.dataset.KeyWords().clear();
        	this.dataset.KeyWords().addAll(this.keywordList.getItems());
        	

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
     * check form validation
     * @return
     */
    private boolean validation(){
    	// TODO complete form validation
		DataSet tmpDataset = new DataSet(this.title.getText(),"mm/dd/yyyy");
		tmpDataset.setDesription(this.description.getText());
		tmpDataset.KeyWords().addAll(this.keywordList.getItems());
		tmpDataset.setLandingPage(this.landingPage.getText());
		valid = tmpDataset.check();
		if(valid.result()){
			return true;
		}else{
			return false;
    	}
    }
	
}
