package data.view;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import data.model.ClassColumn;
import data.model.Column;
import data.model.JoinTable;
import data.model.MeasureColumn;
import data.model.MyData;
import data.model.NormalTable;
import data.model.Table;
import data.model.TemporalColumn;
import data.model.Validation;
import data.model.dimensions;
import data.model.represents;
import data.model.semanticRelations;
import data.model.units;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
/*
 * controller of column detail
 */
public class ColumnController {
	
	@FXML
	private TextField title;
	@FXML
	private TextField description;
	@FXML
	private ComboBox<String> semanticRelation;
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
	private ComboBox<String> tempType;
	@FXML
	private TextField tempFormat;
	@FXML
	private TextField tempGranularity;
	@FXML
	private Button apply;
	//type of this column
	private int columnTypeIndex;
	
	private Column column;
	//parent table
	private Table table;
	//parent tree node
	private TreeItem<MyData> myData;
	//current tree node
	private TreeItem<MyData> columnNode;
	//check validation
	private Validation valid;
	
	public ColumnController(){	
	}
	
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	
    }
    
    /**
     * initialize this column controller
     * 
     * @param column2 
     * current column
     * @param table
     * parent table
     * @param myData
     * parent tree node
     * @param columnNode
     * current tree node
     * @throws IOException
     */
    public void setColumn(Column column2, Table table, TreeItem<MyData> myData,TreeItem<MyData> columnNode) throws IOException{
    	this.column=column2;
    	this.table=table;
    	this.myData=myData;
    	this.columnNode=columnNode;
    	this.title.setText(column2.getTitle());
    	this.description.setText(column2.getDescription());
    	//initialize semantic relation combo box
    	ObservableList<String> semanticRelationList = FXCollections.observableArrayList();
    	Map<Integer,String> semanticRelationOptions = semanticRelations.allOptions();
    	//semantic relation id start from 1
    	for(int i=1;i<semanticRelationOptions.size();i++){
    		semanticRelationList.add(semanticRelationOptions.get(i));
    	}
    	this.semanticRelation.getItems().addAll(semanticRelationList);
    	if(this.column.thisSemanticRelation().getValue()!=0){
    		this.semanticRelation.setValue(this.column.thisSemanticRelation().getString());
    	}
    	// set the type of this column
    	this.columnTypeIndex = this.column.ColumnType();
    	
    	this.vbox.getChildren().clear();
    	//initialize column type selection
    	this.columnType.getItems().addAll("Class","Measure","Temporal","Property");
    	ObservableList<String> unitList = FXCollections.observableArrayList();
    	Map<Integer, String> unitOptions = units.allOptions();
    	
    	//initialize unit combo box. unit id start from 1
    	for(int i=1;i<=unitOptions.size();i++){
    		unitList.add(unitOptions.get(i));
    	}
    	this.unit.getItems().addAll(unitList);
    	
    	//initialize dimension combo box
    	ObservableList<String> dimensionList = FXCollections.observableArrayList();
    	Map<Integer, String> dimensionOptions = dimensions.allOptions();
    	//dimension id start from 1
    	for(int i=1;i<=dimensionOptions.size();i++){
    		dimensionList.add(dimensionOptions.get(i));
    	}
    	this.dimension.getItems().addAll(dimensionList);
    	
    	//initialize represent combo box
    	ObservableList<String> representList = FXCollections.observableArrayList();
    	Map<Integer,String> representOptions = represents.allOptions();
    	//represent id start from 1
    	for(int i=1;i<=representOptions.size();i++){
    		representList.add(representOptions.get(i));
    	}
    	this.represent.getItems().addAll(representList);
    	
    	//initialize temporal type combo box
    	this.tempType.getItems().addAll("Interval","Time Stamp");
    	
    	//show different form items according to the type of this column
    	if(this.column.ColumnType()==1){
    		//this is a class column.
    		this.columnType.setValue("Class");
    		this.vbox.getChildren().add(this.classPane);
    		// TODO create controller for class column
    		this.represent.setValue(((ClassColumn)this.column).Represent().getString());
    	}else if(this.column.ColumnType()==2){
    		//this is a measure column
    		this.represent.setValue(((MeasureColumn)this.column).Represent().getString());
    		this.vbox.getChildren().add(this.classPane);
    		this.columnType.setValue("Measure");
    		this.vbox.getChildren().add(this.measurePane);
    		this.unit.setValue(((MeasureColumn)this.column).Unit().getString());
    		this.dimension.setValue(((MeasureColumn)this.column).dimension().getString());
    	}else if(this.column.ColumnType()==3){
    		//this is a temporal column
    		this.represent.setValue(((TemporalColumn)this.column).Represent().getString());
    		this.vbox.getChildren().add(this.classPane);
    		this.columnType.setValue("Temporal");
    		this.vbox.getChildren().add(this.temporalPane);
    		this.tempType.setValue(((TemporalColumn)this.column).getTemporalType());
    		this.tempFormat.setText(((TemporalColumn)this.column).TemporalFormat());
    		this.tempGranularity.setText(((TemporalColumn)this.column).TemporalGranularity());
    	}else{
    		this.columnType.setValue("property & other");
    		//property column
    	}

    	//add action handler to the column type combo box
        this.columnType.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	vbox.getChildren().clear();
            	if(columnType.getSelectionModel().getSelectedIndex()==0){
            		columnTypeIndex=1;
            		vbox.getChildren().add(classPane);
            	}else if(columnType.getSelectionModel().getSelectedIndex()==1){
            		columnTypeIndex=2;
            		vbox.getChildren().add(classPane);
            		vbox.getChildren().add(measurePane);
            	}else if(columnType.getSelectionModel().getSelectedIndex()==2){
            		columnTypeIndex=3;
            		vbox.getChildren().add(classPane);
            		vbox.getChildren().add(temporalPane);
            	}
            }
        });
    }
    
    /**
     * Called when the user clicks on the apply button.
     * Save any changes to selected column.
     * @throws IOException 
     * @throws InterruptedException 
     */
    @FXML
    private void handleApply() throws IOException, InterruptedException {
        if(this.validation()){
        	if(this.columnTypeIndex == 1){
        		//this is a class column
        		ClassColumn newColumn = new ClassColumn(this.title.getText());
        		if(this.description.getText()!=null){
        			newColumn.setDesription(this.description.getText());
        		}
            	int semanticRelationIndex = this.semanticRelation.getSelectionModel().getSelectedIndex();
            	if(semanticRelationIndex>=0){
            		newColumn.modifiedSemanticRelations(semanticRelationIndex+1);
            	}
        		newColumn.setBelongsTo(this.table);
        		
        		newColumn.setColumnType(this.columnTypeIndex);
        		newColumn.setRepresent(this.represent.getSelectionModel().getSelectedIndex()+1);
        		
        		//need casting here
        		if(this.table.getTableType()==0){
        			((NormalTable)(this.table)).removeColumn(this.column);
        			((NormalTable)(this.table)).addColumn(newColumn);
        		}else if(this.table.getTableType()==1){
        			((JoinTable)(this.table)).removeColumn((ClassColumn)(this.column));
        			((JoinTable)(this.table)).addColumn(newColumn);        			
        		}else{
        			System.out.println("unknow table type");
        		}

        		TreeItem<MyData> newTreeNode = new TreeItem<MyData>(newColumn);
        		this.myData.getChildren().remove(this.columnNode);
        		this.myData.getChildren().add(newTreeNode);
        	}else if(this.columnTypeIndex == 2){
        		//this is a measure column
        		MeasureColumn newColumn = new MeasureColumn(this.title.getText());
        		if(this.description.getText()!=null){
        			newColumn.setDesription(this.description.getText());
        		}
            	int semanticRelationIndex = this.semanticRelation.getSelectionModel().getSelectedIndex();
            	if(semanticRelationIndex>=0){
            		newColumn.modifiedSemanticRelations(semanticRelationIndex+1);
            	}
        		newColumn.setBelongsTo(this.table);
        		
        		newColumn.setColumnType(this.columnTypeIndex);
        		newColumn.setRepresent(this.represent.getSelectionModel().getSelectedIndex()+1);
        		newColumn.setUnit(this.unit.getSelectionModel().getSelectedIndex()+1);
        		newColumn.setDimension(this.dimension.getSelectionModel().getSelectedIndex()+1);
        		
        		//need casting here
        		if(this.table.getTableType()==0){
        			((NormalTable)(this.table)).removeColumn(this.column);
        			((NormalTable)(this.table)).addColumn(newColumn);
        		}else if(this.table.getTableType()==1){
        			((JoinTable)(this.table)).removeColumn((ClassColumn)(this.column));
        			((JoinTable)(this.table)).addColumn(newColumn);        			
        		}else{
        			System.out.println("unknow table type");
        		}
        		
        		TreeItem<MyData> newTreeItem = new TreeItem<MyData>(newColumn);
        		this.myData.getChildren().remove(this.columnNode);
        		this.myData.getChildren().add(newTreeItem);
        	}else if(this.columnTypeIndex == 3){
        		//this is a temporal column
        		TemporalColumn newColumn = new TemporalColumn(this.title.getText());
        		if(this.description.getText()!=null){
        			newColumn.setDesription(this.description.getText());
        		}
            	int semanticRelationIndex = this.semanticRelation.getSelectionModel().getSelectedIndex();
            	if(semanticRelationIndex>=0){
            		newColumn.modifiedSemanticRelations(semanticRelationIndex+1);
            	}
        		newColumn.setBelongsTo(this.table);
        		
        		newColumn.setColumnType(this.columnTypeIndex);
        		newColumn.setRepresent(this.represent.getSelectionModel().getSelectedIndex()+1);
        		newColumn.setTemporalFormat(this.tempFormat.getText());
        		newColumn.setTemporalGranularity(this.tempGranularity.getText());
        		
        		//need casting here
        		if(this.table.getTableType()==0){
        			((NormalTable)(this.table)).removeColumn(this.column);
        			((NormalTable)(this.table)).addColumn(newColumn);
        		}else if(this.table.getTableType()==1){
        			((JoinTable)(this.table)).removeColumn((ClassColumn)(this.column));
        			((JoinTable)(this.table)).addColumn(newColumn);        			
        		}else{
        			System.out.println("unknow table type");
        		}
        		
        		TreeItem<MyData> newTreeItem = new TreeItem<MyData>(newColumn);
        		this.myData.getChildren().remove(this.columnNode);
        		this.myData.getChildren().add(newTreeItem);
        	}else{
        		//this is a property column, or the default column
        		this.column.setTitle(this.title.getText());
        		if(this.description.getText()!=null){
        			this.column.setDesription(this.description.getText());
        		}
            	int semanticRelationIndex = this.semanticRelation.getSelectionModel().getSelectedIndex();
            	if(semanticRelationIndex>=0){
            		this.column.modifiedSemanticRelations(semanticRelationIndex+1);
            	}
            	
            	this.column.setColumnType(this.columnTypeIndex);
        	}
        }else{
        	Set<String> errors = this.valid.ErrorField();
        	Alert alert = new Alert(AlertType.ERROR);
        	
        	alert.setTitle("Error Dialog");
        	alert.setHeaderText("Error in the column");
        	String message ="";
        	for(String s: errors){
        		message+=s+" ";
        	}
        	alert.setContentText("Missing input: "+message);

        	alert.showAndWait();
        }
    }
    
    /**
     * check form validation
     * @throws InterruptedException 
     * @throws IOException 
     */
    private boolean validation() throws IOException, InterruptedException{
    	if(this.columnTypeIndex == 1){
    		//this is a class column
    		ClassColumn tmpColumn = new ClassColumn(this.title.getText());
    		tmpColumn.setDesription(this.description.getText());
    		
        	tmpColumn.modifiedSemanticRelations(this.semanticRelation.getSelectionModel().getSelectedIndex()+1);

    		tmpColumn.setRepresent(this.represent.getSelectionModel().getSelectedIndex()+1);
    		valid = tmpColumn.check();
    	}else if(this.columnTypeIndex == 2){
    		//this is a measure column
    		MeasureColumn tmpColumn = new MeasureColumn(this.title.getText());
    		tmpColumn.setDesription(this.description.getText());
    		
        	tmpColumn.modifiedSemanticRelations(this.semanticRelation.getSelectionModel().getSelectedIndex()+1);
    		
    		tmpColumn.setRepresent(this.represent.getSelectionModel().getSelectedIndex()+1);
    		tmpColumn.setUnit(this.unit.getSelectionModel().getSelectedIndex()+1);
    		tmpColumn.setDimension(this.dimension.getSelectionModel().getSelectedIndex()+1);
    		valid = tmpColumn.check();
    	}else if(this.columnTypeIndex == 3){
    		//this is a temporal column
    		TemporalColumn tmpColumn = new TemporalColumn(this.title.getText());
    		tmpColumn.setDesription(this.description.getText());
    		
        	tmpColumn.modifiedSemanticRelations(this.semanticRelation.getSelectionModel().getSelectedIndex()+1);
        	
    		tmpColumn.setRepresent(this.represent.getSelectionModel().getSelectedIndex()+1);
    		tmpColumn.setTemporalFormat(this.tempFormat.getText());
    		tmpColumn.setTemporalGranularity(this.tempGranularity.getText());
    		valid = tmpColumn.check();
    	}else{
    		//this is a property column, or the default column
    		Column tmpColumn = new Column(this.title.getText());
    		tmpColumn.setDesription(this.description.getText());
    		tmpColumn.modifiedSemanticRelations(this.semanticRelation.getSelectionModel().getSelectedIndex()+1);
    		valid = tmpColumn.check();
    	}
    	if(valid.result()){
    		return true;
    	}else{
    		return false;
    	}
    }

}
