package data.view;

import data.model.Column;
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
    
    public void setColumn(Column column2){
    	this.column=column2;
    	this.title.setText(column2.getTitle());
    	this.description.setText(column2.getDescription());
    	//test code below
    	this.columnTypeIndex = -1;
    	this.vbox.getChildren().clear();
    	this.columnType.getItems().addAll("Class","Measure","Temporal","Property");
    	this.represent.getItems().addAll("test1","test2","test3","test4");
    	this.unit.getItems().addAll("units1","units2","units3","units4","units5");
    	this.dimension.getItems().addAll("dimensions1","dimensions2","dimensions3","dimensions4");
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
