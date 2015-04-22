package data.view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import data.model.ClassColumn;
import data.model.Column;
import data.model.DataSet;
import data.model.InputFile;
import data.model.JoinTable;
import data.model.NormalTable;
import data.model.Table;
import data.model.TableType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/*
 * controller of adding multiple tables
 */
public class UploadMultipleTablesDialogController {

	@FXML
	private Button upLoadFile;
	@FXML
	private TableView<TableType> tables;
	@FXML
	private TableColumn<TableType,String> titleColumn;
	@FXML
	private TableColumn<TableType,String> typeColumn;
	@FXML
	private TextField title;
	@FXML
	private ComboBox<String> tableType;
	@FXML
	private Button change;
	@FXML
	private Button ok;
	@FXML
	private Button cancel;
	
	private Stage dialogStage;
	
	private TableType selectedTable;
	
	private List<Table> tableList;
	
	private Map<TableType,List<String>> tableMap;
	
	
	private boolean okClicked = false;
	
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     * @throws IOException 
     */
    @FXML
    private void initialize() throws IOException {
    	this.selectedTable = new TableType("tmpTitle",0);
    	this.tableMap = new HashMap<TableType,List<String>>();
    	this.titleColumn.setCellValueFactory(cellData -> cellData.getValue().getTitle());
    	this.typeColumn.setCellValueFactory(cellData -> cellData.getValue().getType());
    	
        this.tables.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showTypeDetails(newValue));
        this.tableType.getItems().addAll(TableType.allTypes().values());
    }
    
    private void showTypeDetails(TableType type){
    	this.selectedTable = type;
    	this.title.setText(type.getTitle().getValue());
    	this.tableType.setValue(type.getType().getValue());
    }
    
    /**
     * Set the stage of this dialog.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    /**
     * Set the table list
     * @param list
     */
    public void setTableList(List<Table> list){
    	this.tableList = list;
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
     * Called when the user clicks change.
     * @throws IOException 
     */
    @FXML
    private void handleUpLoadFile() throws IOException {
    	// TODO upload file and initialize the type table
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        List<String> legalExt = new ArrayList<String>();
        legalExt.add("*.csv");
        legalExt.add("*.xls");
        legalExt.add("*.xlsx");
        FileChooser.ExtensionFilter extFilterXLS = new FileChooser.ExtensionFilter("all files (*.csv) (*.xls) (*.xlsx)", legalExt);
        fileChooser.getExtensionFilters().add(extFilterXLS);
   
        fileChooser.setTitle("Open Resource File");
        File file =fileChooser.showOpenDialog(this.dialogStage);
        if(file!=null){
        	String path = file.getPath();
        	InputFile inputFile = new InputFile();
        	inputFile.setInputFile(path);
        	Map<String,List<String>> manualMap;
        	if(inputFile.getFileExtension(path).equals(".csv")){
        		manualMap = inputFile.readCsv();
        	}else{
        		manualMap = inputFile.readExcel();
        	}
        	Iterator<Map.Entry<String, List<String>>> Entries = manualMap.entrySet().iterator();
        	this.tableMap.clear();
        	this.tables.getItems().clear();
        	while(Entries.hasNext()){
        		Map.Entry<String, List<String>> entry = Entries.next();
        		TableType nextType = new TableType(entry.getKey(),0);
        		this.tableMap.put(nextType, entry.getValue());
        		this.tables.getItems().add(nextType);
        	}
        }else{
        	System.out.println("file is null");
        }
    } 
    
    /**
     * Called when the user clicks change.
     */
    @FXML
    private void handleChange() {
    	String title = this.title.getText();
    	int type = this.tableType.getSelectionModel().getSelectedIndex();
    	this.selectedTable.setTitle(title);
    	this.selectedTable.setType(type);
    }
    
    /**
     * Called when the user clicks OK.
     * @throws IOException 
     * @throws InterruptedException 
     */
    @FXML
    private void handleOK() throws IOException, InterruptedException {
    	//need first create a temporary data set as parent data set of newly created tables.
    	DataSet tmpDataset = new DataSet("parent","2015.04.17");
    	for(int i=0;i<this.tables.getItems().size();i++){
    		TableType tmpType = this.tables.getItems().get(i);
    		if(tmpType.getType().getValue().equals("normal")){
    			//this is a normal table
    			NormalTable normal = new NormalTable(tmpType.getTitle().getValue(),tmpDataset);
    			List<String> columnsTitle = this.tableMap.get(tmpType);
    			for(int j=0;j<columnsTitle.size();j++){
    				Column column = new Column(columnsTitle.get(j));
    				normal.addColumn(column);
    			}
    			this.tableList.add(normal);
    		}else{
    			//this is a join table
    			JoinTable join = new JoinTable(tmpType.getTitle().getValue(),tmpDataset);
    			List<String> classColumnsTitle = this.tableMap.get(tmpType);
    			for(int j=0;j<classColumnsTitle.size();j++){
    				ClassColumn classColumn = new ClassColumn(classColumnsTitle.get(j));
    				join.addColumn(classColumn);
    			}
    			this.tableList.add(join);
    		}
    	}
    	this.okClicked=true;
    	this.dialogStage.close();
    }   
    
    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    

}
