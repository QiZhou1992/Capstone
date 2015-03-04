package data.view;

import java.io.IOException;
import java.util.Map;

import data.model.Column;
import data.model.dimensions;
import data.model.represents;
import data.model.units;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ColumnController {
	
	@FXML
	private TextField title;
	@FXML
	private TextField description;
	//test variable
	@FXML
	private ComboBox<String> columnType;
	@FXML
	private VBox vbox;
	@FXML
	private GridPane classPane;
	@FXML
	private GridPane measurePane;
	@FXML
	private GridPane temporalPane;
	@FXML
	private ComboBox<String> represent;
	@FXML
	private ComboBox<String> unit;
	@FXML
	private ComboBox<String> dimension;
	@FXML
	private TextField tempFormat;
	@FXML
	private TextField tempGranularity;
	private int columnTypeIndex;
	
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
    
    public void setColumn(Column column2) throws IOException{
    	this.column=column2;
    	this.title.setText(column2.getTitle());
    	this.description.setText(column2.getDescription());
    	//test code below
    	this.columnTypeIndex = -1;
    	this.vbox.getChildren().clear();
    	this.columnType.getItems().addAll("Class","Measure","Temporal","Property");
    	ObservableList<String> unitList = FXCollections.observableArrayList();
    	Map<Integer, String> unitOptions = units.allOptions();
    	//unit id start from 1
    	for(int i=1;i<=unitOptions.size();i++){
    		unitList.add(unitOptions.get(i));
    	}
    	this.unit.getItems().addAll(unitList);
    	ObservableList<String> dimensionList = FXCollections.observableArrayList();
    	Map<Integer, String> dimensionOptions = dimensions.allOptions();
    	//dimension id start from 1
    	for(int i=1;i<=dimensionOptions.size();i++){
    		dimensionList.add(dimensionOptions.get(i));
    	}
    	this.dimension.getItems().addAll(dimensionList);
    	ObservableList<String> representList = FXCollections.observableArrayList();
    	Map<Integer,String> representOptions = represents.allOptions();
    	//represent id start from 1
    	for(int i=1;i<=representOptions.size();i++){
    		representList.add(representOptions.get(i));
    	}
    	this.represent.getItems().addAll(representList);

        this.columnType.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	vbox.getChildren().clear();
            	if(columnType.getSelectionModel().getSelectedIndex()==0){
            		columnTypeIndex=1;
            		vbox.getChildren().add(classPane);
            	}else if(columnType.getSelectionModel().getSelectedIndex()==1){
            		columnTypeIndex=2;
            		vbox.getChildren().add(measurePane);
            	}else if(columnType.getSelectionModel().getSelectedIndex()==2){
            		columnTypeIndex=3;
            		vbox.getChildren().add(temporalPane);
            	}
            }
        });
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
