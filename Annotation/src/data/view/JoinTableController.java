package data.view;

import java.io.IOException;

import data.MainApp;
import data.model.JoinRelation;
import data.model.JoinTable;
import data.model.MyData;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;

public class JoinTableController {
	
	@FXML
	private Label title;
	@FXML
	private Label description;
	@FXML
	private TableView<JoinRelation> relationTable;
	@FXML
	private TableColumn<JoinRelation,String> sourceColumn;
	@FXML
	private TableColumn<JoinRelation,String> destinationColumn;
	@FXML
	private TableColumn<JoinRelation,String> relationColumn;
	
	private JoinTable table;
	
	private TreeItem<MyData> treeNode;
	
	private MainApp mainApp;
	
	public JoinTableController(){	
	}
	
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	this.sourceColumn.setCellValueFactory(cellData -> cellData.getValue().source().getTitleProperty());
    	this.destinationColumn.setCellValueFactory(cellData -> cellData.getValue().destiny().getTitleProperty());
    	this.relationColumn.setCellValueFactory(cellData -> cellData.getValue().relation().getStringProperty());
    }
    /**
     * Initialize the table controller
     * 
     * @param table
     * current table
     * @throws IOException
     */
	public void setTable(JoinTable table,MainApp mainApp,TreeItem<MyData> treeNode) throws IOException{
		this.treeNode = treeNode;
    	this.table=table;
    	this.mainApp = mainApp;
    	this.title.setText(table.getTitle());
    	this.description.setText(table.getDescription());
    	this.relationTable.getItems().clear();
    	this.relationTable.getItems().addAll(this.table.allRelations().values());
    }
	
	@FXML
	private void handleEdit() throws IOException{
		// TODO 
   	 	boolean okClicked = this.mainApp.showNewJoinTableDialog(this.table);
   	 	if(okClicked){
   	 		this.setTable(this.table, this.mainApp, this.treeNode);
   	 	}
	}
    
    @FXML
    private void handleDelete(){
    	this.table.parentDataSet().RemoveTable(this.table);
    	this.table.removeBelongsTo();
    	this.treeNode.getParent().getChildren().remove(this.treeNode);
    }

}
