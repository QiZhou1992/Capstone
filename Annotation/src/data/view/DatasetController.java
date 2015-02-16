package data.view;

import java.util.Iterator;
import java.util.Map;

import data.MainApp;
import data.model.DataSet;
import data.model.MyData;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.util.Callback;

public class DatasetController {
	
	@FXML
	private TreeView<MyData> dataTree;
	@FXML
	private Label identifierLabel;
	@FXML
	private Label titleLabel;
	@FXML
	private Label descriptionLabel;
	
	private MainApp mainApp;
	
	public DatasetController() {
    }
	
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
    	
    	showTableDetails(null);
    	
    	this.dataTree.getSelectionModel().selectedItemProperty().addListener(
    			(observable, oldValue, newValue) -> showTableDetails(newValue));
    }
    
    private void showTableDetails(TreeItem<MyData> newValue){
    	/*
    	if(newValue!=null){
    		this.identifierLabel.setText(String.valueOf(newValue.Identifier()));
    		this.titleLabel.setText(newValue.name());
    		this.descriptionLabel.setText(newValue.description());
    	}else{
    		this.identifierLabel.setText("");
    		this.titleLabel.setText("");
    		this.descriptionLabel.setText("");
    	}
    	*/
    }
    
	public void setMainApp(MainApp mainApp) {
    	this.mainApp = mainApp;
        // Add observable list data to the tree
    	TreeItem<MyData> rootItem = new TreeItem<MyData>();
    	rootItem.setExpanded(true);
    	ObservableList<MyData> datasets = mainApp.getDataSetList();
    	for(int i=0;i<datasets.size();i++){
    		MyData dataset = datasets.get(i);
    		TreeItem<MyData> dataNode = new TreeItem<MyData>(dataset);
    		rootItem.getChildren().add(dataNode);
    		
    		
    		Map<Long,MyData> tables = ((DataSet)dataset).AllTable();
    		Iterator<Map.Entry<Long, MyData>> entries = tables.entrySet().iterator();
    		while(entries.hasNext()){
    			Map.Entry<Long, MyData> entry = entries.next();
    			TreeItem<MyData> tableNode = new TreeItem<MyData>(entry.getValue());
    			dataNode.getChildren().add(tableNode);
    		}
    	}
    	
    	this.dataTree.setRoot(rootItem);
    	
    	dataTree.setCellFactory(new Callback<TreeView<MyData>, TreeCell<MyData>>() {
			@Override
			public TreeCell<MyData> call(TreeView<MyData> paramP) {
				return new TreeCell<MyData>(){
					@Override
					protected void updateItem(MyData paramT, boolean empty) {
						super.updateItem(paramT, empty);
						if (empty) {
			                setText(null);
			                setGraphic(null);
			            } else if(paramT!=null){
							setText(paramT.name());
						}else{
							//System.out.println("test"+rootItem.getChildren().size());
						}
					}
				};
			}
		});
    	
    	/*
    	tableList.setItems(mainApp.getTableList());
    	tableList.setCellFactory(column -> {
			return new ListCell<Table>(){
				@Override
				protected void updateItem(Table item, boolean empty){
					super.updateItem(item, empty);
					if(item!=null){
						setText(item.name());
					}
				}
			};
    	});
    	*/
    }
}
