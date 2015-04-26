package data.view;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import data.model.ClassColumn;
import data.model.JoinRelation;
import data.model.JoinTable;
import data.model.Validation;
import data.model.semanticRelations;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class EditJoinTableDialogController {
	
	@FXML
	private TextField title;
	@FXML
	private TextField description;
	@FXML
	private HBox hbox;
	@FXML
	private TableView<JoinRelation> relationTable;
	@FXML
	private TableColumn<JoinRelation,String> sourceColumn;
	@FXML
	private TableColumn<JoinRelation,String> destinationColumn;
	@FXML
	private TableColumn<JoinRelation,String> relationColumn;
	@FXML
	private ComboBox<ClassColumn> source;
	@FXML
	private ComboBox<ClassColumn> destination;
	@FXML
	private ComboBox<String> relation;
	
	private Stage dialogStage;
	
	private JoinTable table;

	private boolean okClicked = false;
	
	private Validation valid;
	
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     * @throws IOException 
     */
    @FXML
    private void initialize() throws IOException {
    	this.sourceColumn.setCellValueFactory(cellData -> cellData.getValue().source().getTitleProperty());
    	this.destinationColumn.setCellValueFactory(cellData -> cellData.getValue().destiny().getTitleProperty());
    	this.relationColumn.setCellValueFactory(cellData -> cellData.getValue().relation().getStringProperty());
    	this.source.setConverter(new StringConverter<ClassColumn>() {

			@Override
			public String toString(ClassColumn object) {
				// TODO Auto-generated method stub
				return object.getTitle();
			}

			@Override
			public ClassColumn fromString(String string) {
				// TODO Auto-generated method stub
				throw new UnsupportedOperationException();
			}
    		
    	});
    	this.destination.setConverter(new StringConverter<ClassColumn>(){

			@Override
			public String toString(ClassColumn object) {
				// TODO Auto-generated method stub
				return object.getTitle();
			}

			@Override
			public ClassColumn fromString(String string) {
				// TODO Auto-generated method stub
				throw new UnsupportedOperationException();
			}
    		
    	});
    	this.hbox.setVisible(false);
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
     * @throws IOException 
     */
    public void setTable(JoinTable table) throws IOException {
        this.table = table;
        if(table.AllColumn().size()>0){
            this.hbox.setVisible(true);
            this.setRelationCombo();
            this.relationTable.getItems().addAll(this.table.allRelations().values());
            this.title.setText(this.table.getTitle());
            this.description.setText(this.table.getDescription());
        }
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
     * Called when the user clicks OK.
     * @throws IOException 
     * @throws InterruptedException 
     */
    @FXML
    private void handleOk() throws IOException, InterruptedException {
    	
        if (isInputValid()) {
        	this.table.setTitle(this.title.getText());
        	if(this.description.getText()!=null){
        		this.table.setDesription(this.description.getText());
        	}
        	this.table.clearRelation();
            for(JoinRelation next:this.relationTable.getItems()){
        		this.table.addNewRelation(next);
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
     * @throws InterruptedException 
     */
    private boolean isInputValid() throws IOException, InterruptedException {
    	// TODO complete form validation
    	JoinTable tmpTable = new JoinTable(this.title.getText(),this.table.parentDataSet());
    	tmpTable.setDesription(this.description.getText());
    	for(ClassColumn column:this.table.AllColumn().values()){
    		tmpTable.addColumn(column);
    	}
    	for(JoinRelation next:this.relationTable.getItems()){
    		tmpTable.addNewRelation(next);
    	}
    	valid = tmpTable.check();
    	if(tmpTable.AllColumn().size()==0){
    		return false;
    	}
    	if(valid.result()){
    		return true;
    	}else{
    		return false;
    	}
    }
    /**
     * initialize source, destination, and relation combo box
     * @throws IOException 
     */
    private void setRelationCombo() throws IOException{
    	this.source.getItems().addAll(this.table.AllColumn().values());
    	this.destination.getItems().addAll(this.table.AllColumn().values());
    	
    	ObservableList<String> semanticRelationList = FXCollections.observableArrayList();
    	Map<Integer,String> semanticRelationOptions = semanticRelations.allOptions();
    	//semantic relation id start from 1
    	for(int i=1;i<semanticRelationOptions.size();i++){
    		semanticRelationList.add(semanticRelationOptions.get(i));
    	}
    	this.relation.getItems().addAll(semanticRelationList);
    }
    
    /**
     * add new relation tuple to relation table
     * @throws IOException 
     * @throws InterruptedException 
     */
    @FXML
    private void handleAddRelation() throws IOException, InterruptedException{
    	ClassColumn s = this.source.getValue();
    	ClassColumn d = this.destination.getValue();
    	int semanticRelationIndex = this.relation.getSelectionModel().getSelectedIndex();
    	if(s==null||d==null||semanticRelationIndex<0){
    		return;
    	}
    	JoinRelation joinRelation = new JoinRelation(s,semanticRelationIndex+1,d);
    	this.relationTable.getItems().add(joinRelation);
    }
    /**
     * delete selected relation tuple in relation table
     */
    @FXML
    private void handleDeleteRelation(){
        int selectedIndex = this.relationTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            this.relationTable.getItems().remove(selectedIndex);
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.ERROR);      	
        	alert.setTitle("No Selection");
        	alert.setHeaderText("No Relation Selected");
        	alert.setContentText("Please select a relation in the table");
        }
    }
}
