package data.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;

import data.MainApp;
import data.model.Column;
import data.model.DataSet;
import data.model.MyData;
import data.model.NormalTable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
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
    	this.created.setText(dataset.getCreated());
    	this.keywordList.getItems().clear();
    	for(String nextKeyword: this.dataset.KeyWords()){
    		this.keywordList.getItems().add(nextKeyword);
    	}
    	this.landingPage.setText(dataset.getLandingPage());
    }	
    
    @FXML
    private void handleExport() throws FileNotFoundException{
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Export Dataset");
    	File file = fileChooser.showSaveDialog(this.mainApp.getPrimaryStage());
    	if(file!=null){
    		PrintWriter pw = new PrintWriter(file.getPath());
    		this.dataset.output(pw);
    	}
    }
    /**
     * Add new table(normal) to the data set.
     * @throws IOException
     */
    @FXML
    private void handleAddNormal() throws IOException{
    	NormalTable tempTable = new NormalTable("tmp title",this.dataset);
    	boolean okClicked = this.mainApp.showNewNormalTableDialog(tempTable);
    	if(okClicked){
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
    }
}
