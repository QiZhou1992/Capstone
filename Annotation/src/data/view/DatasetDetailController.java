package data.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import data.MainApp;
import data.model.ClassColumn;
import data.model.Column;
import data.model.DataSet;
import data.model.JoinTable;
import data.model.MyData;
import data.model.NormalTable;
import data.model.OutputCheck;
import data.model.Table;
import data.model.Validation;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;

/*
 * controller of data set
 */
public class DatasetDetailController {

	@FXML
	private Label title;
	@FXML
	private Label description;
	@FXML
	private Label created;
	@FXML
	private ListView<String> keywordList;
	@FXML
	private Label landingPage;
	
	private DataSet dataset;
	
	MainApp mainApp;
	
	TreeItem<MyData> treeNode;
	
	public DatasetDetailController() {
    }
	
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	
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
    public void setDataset(DataSet dataset, MainApp mainApp, TreeItem<MyData> treeNode){
    	this.mainApp = mainApp;
    	this.treeNode = treeNode;
    	this.dataset=dataset;
    	this.title.setText(dataset.getTitle());
    	this.description.setText(dataset.getDescription());
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	this.created.setText(df.format(dataset.getCreated()));
    	this.keywordList.getItems().clear();
    	for(String nextKeyword: this.dataset.KeyWords()){
    		this.keywordList.getItems().add(nextKeyword);
    	}
    	this.landingPage.setText(dataset.getLandingPage());
    }	
    
    @FXML
    private void handleExport() throws FileNotFoundException{
    	// TODO check validation of the whole data set (its table, columns)
    	// need to check validation here. create check object and check.
    	OutputCheck check = this.dataset.outputCkeck();
	    if(check.result()){
	    	FileChooser fileChooser = new FileChooser();
	    	fileChooser.setTitle("Export Dataset");
	    	File file = fileChooser.showSaveDialog(this.mainApp.getPrimaryStage());
	    	if(file!=null){
	    		PrintWriter pw = new PrintWriter(file.getPath());
	    		this.dataset.output(pw);
	    	}
    	}else{
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Error Dialog");
    		alert.setHeaderText("Error occured when export this dataset");
    		alert.setContentText("Current dataset is not complete! Check the message below for more detail.");
    		
    		StringBuffer messages = new StringBuffer();
    		// need to add error message to messages
    		Set<Table> allErrorTables = new HashSet<Table>();
    		allErrorTables.addAll(check.TableError().keySet());
    		for(Map.Entry<Column, Validation> columnEntry:check.ColumnError().entrySet()){
    			allErrorTables.add(columnEntry.getKey().parentTable());
    		}
    		
    		for(Table tableEntry: allErrorTables){
    			StringBuffer nextTable = new StringBuffer();
    			if(check.TableError().containsKey(tableEntry)){
	    			nextTable.append("Missing items in table: \""+tableEntry.getTitle()+"\" \n");
	    			for(String s:check.TableError().get(tableEntry).ErrorField()){
	    				nextTable.append(s+", ");
	    			}
	    			nextTable.setLength(nextTable.length()-2);
	    			nextTable.append("\n");
    			}else{
    				nextTable.append("Missing Items in table: \""+tableEntry.getTitle()+"\" \n");
    			}
    			for(Map.Entry<Column, Validation> columnEntry: check.ColumnError().entrySet()){
    				if(columnEntry.getKey().parentTable().getIdentifier()==tableEntry.getIdentifier()){
    					StringBuffer nextColumn = new StringBuffer();
    					nextColumn.append("\tMissing items in column: \""+columnEntry.getKey().getTitle()+"\" \n\t\t ");
    					for(String s: columnEntry.getValue().ErrorField()){
    						nextColumn.append(s+", ");
    					}
    					nextColumn.setLength(nextColumn.length()-2);
    					nextColumn.append("\n");
    					nextTable.append(nextColumn);
    				}
    			}
    			messages.append(nextTable);
    		}
    		
    		// end of messages
    		
    		Label label = new Label("The missing items were:");
    		TextArea textArea = new TextArea(messages.toString());
    		textArea.setEditable(false);
    		textArea.setWrapText(true);

    		textArea.setMaxWidth(Double.MAX_VALUE);
    		textArea.setMaxHeight(Double.MAX_VALUE);
    		GridPane.setVgrow(textArea, Priority.ALWAYS);
    		GridPane.setHgrow(textArea, Priority.ALWAYS);

    		GridPane expContent = new GridPane();
    		expContent.setMaxWidth(Double.MAX_VALUE);
    		expContent.add(label, 0, 0);
    		expContent.add(textArea, 0, 1);

    		// Set expandable Exception into the dialog pane.
    		alert.getDialogPane().setExpandableContent(expContent);
    		
    		alert.showAndWait();
    	}
    }
    
    /**
     * Add multiple tables to the data set.
     * @throws IOException 
     */
    @FXML
    private void handleMultipleTable() throws IOException{
    	List<Table> tmpTables = new ArrayList<Table>();
    	boolean okClicked = this.mainApp.showUploadMultipleTablesDialog(tmpTables);
    	if(okClicked){
    		for(int i=0;i<tmpTables.size();i++){
    			Table nextTable = tmpTables.get(i);
    			nextTable.addBelongsTo(this.dataset);
    			
    			//add to the tree
    			if(nextTable.getTableType()==0){
    				//add normal table
    				NormalTable tempTable = (NormalTable)nextTable;
    	    		this.dataset.addTable(tempTable);
    	    		TreeItem<MyData> newTableNode = new TreeItem<MyData>(tempTable);
    	    		Map<Long,Column> columns = tempTable.AllColumn();
    	    		Iterator<Map.Entry<Long,Column>> columnEntries = columns.entrySet().iterator();
    	    		while(columnEntries.hasNext()){
    	    			Map.Entry<Long,Column> columnEntry = columnEntries.next();
    	    			TreeItem<MyData> columnNode = new TreeItem<MyData>(columnEntry.getValue());
    	    			newTableNode.getChildren().add(columnNode);
    	    		}
    	    		this.treeNode.getChildren().add(newTableNode);
    			}else if(nextTable.getTableType()==1){
    				//add join table
    				JoinTable tempTable = (JoinTable)nextTable;
    	    		this.dataset.addTable(tempTable);
    	    		TreeItem<MyData> newTableNode = new TreeItem<MyData>(tempTable);
    	    		Map<Long,ClassColumn> columns = tempTable.AllColumn();
    	    		Iterator<Map.Entry<Long,ClassColumn>> columnEntries = columns.entrySet().iterator();
    	    		while(columnEntries.hasNext()){
    	    			Map.Entry<Long,ClassColumn> columnEntry = columnEntries.next();
    	    			TreeItem<MyData> columnNode = new TreeItem<MyData>(columnEntry.getValue());
    	    			newTableNode.getChildren().add(columnNode);
    	    		}
    	    		this.treeNode.getChildren().add(newTableNode);
    			}else{
    				System.out.println("Error in DatasetDetailController: handleMultipleTable");
    			}
    		}
    	}
    }
    
    @FXML
    private void handleEdit(){
    	 boolean okClicked = this.mainApp.showDatasetEditDialog(this.dataset);
    	 if(okClicked){
    		 this.setDataset(this.dataset, this.mainApp, this.treeNode);
    	 }
    }
    
    @FXML
    private void handleDelete(){
    	this.mainApp.delete(this.dataset);
    	this.treeNode.getParent().getChildren().remove(this.treeNode);
    	this.mainApp.showDataDetail();
    }
}
