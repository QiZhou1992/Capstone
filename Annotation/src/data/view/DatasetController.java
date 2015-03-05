package data.view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import data.MainApp;
import data.model.Column;
import data.model.DataSet;
import data.model.InputFile;
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
	
	private TreeItem<MyData> parentNode;
	
	public DatasetController() {
    }
	
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	
    }
    
    public void setDataset(DataSet dataset, MainApp mainApp, TreeItem<MyData> myData){
    	this.parentNode = myData;
    	this.mainApp=mainApp;
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
    private void UploadAs() throws IOException {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "csv files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
   
        fileChooser.setTitle("Open Resource File");
        File file =fileChooser.showOpenDialog(mainApp.getPrimaryStage());
        InputFile content=new InputFile();
        ArrayList<String> column = new ArrayList<String>();
        column=content.read(file);
        
        //handle the input title
        String title="title";
        Table upload=input(title,column);
        dataset.addTable(upload);
        //add table node to tree view
        TreeItem<MyData> newTableNode = new TreeItem<MyData>(upload);
		Map<Long,Column> columns = upload.AllColumn();
		Iterator<Map.Entry<Long, Column>> columnEntries = columns.entrySet().iterator();
		while(columnEntries.hasNext()){
			Map.Entry<Long, Column> columnEntry = columnEntries.next();
			TreeItem<MyData> columnNode = new TreeItem<MyData>(columnEntry.getValue());
			newTableNode.getChildren().add(columnNode);
		}
        this.parentNode.getChildren().add(newTableNode);
    }
    
    private Table input(String title,ArrayList<String> column ) throws IOException{
    	
    	Table input = new Table(title, dataset);
    	int length = column.size();
    	for(int i=0;i<length;i++){
    		Column temp=new Column(column.get(i));
    		input.addColumn(temp);
    	}
    	
    	return input;
    }
	
}
