package data.view;

import data.model.DataSet;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DatasetEditDialogController {
	
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
	
    private Stage dialogStage;
    private DataSet dataset;
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
     * Sets the data set to be edited in the dialog.
     * 
     * @param person
     */
    public void setDataset(DataSet dataset) {
        this.dataset = dataset;
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
        	this.dataset.setTitle(this.title.getText());
        	if(this.description.getText()!=null){
        		this.dataset.setDesription(this.description.getText());
        	}
        	if(this.landingPage.getText()!=null){
        		this.dataset.setLandingPage(this.landingPage.getText());
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
    	//keywords problem
    	if(this.title.getText()==null){
    		return false;
    	}
    	
		return true;
    }
}
