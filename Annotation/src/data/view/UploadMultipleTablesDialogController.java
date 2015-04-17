package data.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import data.model.ClassColumn;
import data.model.Column;
import data.model.DataSet;
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
import javafx.stage.Stage;

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
     */
    @FXML
    private void handleUpLoadFile() {
    	// TODO upload file and initialize the type table
    	Map<String,List<String>> manualMap = new HashMap<String,List<String>>();
    	String title1 = "title1";
    	List<String> table1Column = new ArrayList<String>();
    	table1Column.add("column1Table");
    	table1Column.add("column2Table");
    	manualMap.put(title1, table1Column);
    	
    	String title2 = "title2";
    	List<String> table2Column = new ArrayList<String>();
    	table2Column.add("column1title");
    	table2Column.add("column2title");
    	manualMap.put(title2, table2Column);
    	
    	Iterator<Map.Entry<String, List<String>>> Entries = manualMap.entrySet().iterator();
    	while(Entries.hasNext()){
    		Map.Entry<String, List<String>> entry = Entries.next();
    		TableType nextType = new TableType(entry.getKey(),0);
    		this.tableMap.put(nextType, entry.getValue());
    		this.tables.getItems().add(nextType);
    	}
    } 
    
    /**
     * Called when the user clicks change.
     */
    @FXML
    private void handleChange() {
    	// TODO
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
    	// TODO
    	DataSet tmpDataset = new DataSet("parent","2015.04.17");
    	for(int i=0;i<this.tables.getItems().size();i++){
    		TableType tmpType = this.tables.getItems().get(i);
    		if(tmpType.getType().getValue().equals("normal")){
    			NormalTable normal = new NormalTable(tmpType.getTitle().getValue(),tmpDataset);
    			List<String> columnsTitle = this.tableMap.get(tmpType);
    			for(int j=0;j<columnsTitle.size();j++){
    				Column column = new Column(columnsTitle.get(j));
    				normal.addColumn(column);
    			}
    			this.tableList.add(normal);
    		}else{
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
