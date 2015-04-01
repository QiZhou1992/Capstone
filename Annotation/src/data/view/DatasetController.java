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
import data.model.Table;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.stage.FileChooser;

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
	private ListView<String> keywordList;
	@FXML
	private TextField keyword;
	@FXML
	private Button enterKeyword;
	@FXML
	private TextField landingPage;
	@FXML
	private Button apply;
	@FXML
	private Button add;
	
	private DataSet dataset;
	
	private MainApp mainApp;
	
	TreeItem<MyData> currentNode;
	
	public DatasetController() {
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
    public void setDataset(DataSet dataset, MainApp mainApp, TreeItem<MyData> myData){
    	this.dataset=dataset;
    	this.mainApp=mainApp;
    	this.currentNode = myData;
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
     * Called when the user clicks on the add button.
     * Add table to this data set.
     * @throws IOException 
     */
    @FXML
    private void handleAdd() throws IOException {
    	// TODO add action handler here
	    Table tempTable = new Table("tmp title",this.dataset);
	    boolean okClicked = this.mainApp.showNewTableDialog(tempTable);
	    if (okClicked) {
	        this.dataset.addTable(tempTable);
	        //add table node to tree view
	        TreeItem<MyData> newTableNode = new TreeItem<MyData>(tempTable);
			Map<Long,Column> columns = tempTable.AllColumn();
			Iterator<Map.Entry<Long, Column>> columnEntries = columns.entrySet().iterator();
			while(columnEntries.hasNext()){
				Map.Entry<Long, Column> columnEntry = columnEntries.next();
				TreeItem<MyData> columnNode = new TreeItem<MyData>(columnEntry.getValue());
				newTableNode.getChildren().add(columnNode);
			}
	        this.currentNode.getChildren().add(newTableNode);
	    }
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
        	this.dataset.KeyWords().clear();
        	this.dataset.KeyWords().addAll(this.keywordList.getItems());
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
    
    @FXML
    private void handleOutput() throws FileNotFoundException{
        FileChooser fileChooser = new FileChooser();
   
        fileChooser.setTitle("Out Put Dataset");
        File file =fileChooser.showSaveDialog(this.mainApp.getPrimaryStage());
        if(file!=null){
        	PrintWriter pw = new PrintWriter(file.getPath());
    		this.dataset.output(pw);
        }
    }
    
    @FXML
    private void handleDelete(){
    	this.mainApp.delete(this.dataset);
    	this.currentNode.getParent().getChildren().remove(this.currentNode);
    }
	
}
