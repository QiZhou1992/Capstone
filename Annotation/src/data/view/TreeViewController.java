package data.view;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import data.MainApp;
import data.model.Column;
import data.model.DataSet;
import data.model.MyData;
import data.model.Table;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

public class TreeViewController {
	
	@FXML
	private TreeView<MyData> dataTree;

	
	private MainApp mainApp;
	
	public TreeViewController() {
    }
	
	public void addNewDataset(DataSet dataset){
		TreeItem<MyData> dataNode = new TreeItem<MyData>(dataset);
		this.dataTree.getRoot().getChildren().add(dataNode);
	}
	
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
    	
    	this.dataTree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showDataDetails(newValue));
    }
    
    private void showDataDetails(TreeItem<MyData> myData){    	
        try {
        	if(myData==null||myData.getValue()==null){
        		FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("view/Welcome.fxml"));
                AnchorPane personOverview = (AnchorPane) loader.load();
        		this.mainApp.getRootLayout().setCenter(personOverview);
        		
        		WelcomeController controller = loader.getController();
        		controller.setWelcome(this,this.mainApp);
        	}else if(myData.getValue().dataType()==0){
        		FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("view/DatasetDetail.fxml"));
                AnchorPane personOverview = (AnchorPane) loader.load();
        		this.mainApp.getRootLayout().setCenter(personOverview);
        		
        		//set the data set controller. doing cast here.
        		DatasetController controller = loader.getController();
        		controller.setDataset((DataSet)(myData.getValue()),this.mainApp, myData);
        	}else if(myData.getValue().dataType()==1){
        		FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("view/TableDetail.fxml"));
                AnchorPane personOverview = (AnchorPane) loader.load();
        		this.mainApp.getRootLayout().setCenter(personOverview);
        		
        		TableController controller = loader.getController();
        		controller.setTable((Table)(myData.getValue()));
        	}else if(myData.getValue().dataType()==2){
        		FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("view/ColumnDetail.fxml"));
                AnchorPane personOverview = (AnchorPane) loader.load();
        		this.mainApp.getRootLayout().setCenter(personOverview);
        		
        		ColumnController controller = loader.getController();
        		controller.setColumn((Column)myData.getValue(),((Column)myData.getValue()).parentTable(),myData.getParent(),myData);
        	}else{
        		System.err.println("DatasetController: invalid type"+myData.getValue().dataType());
        	}
        	
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
	public void setMainApp(MainApp mainApp) {
    	this.mainApp = mainApp;
        // Add observable list data to the tree
    	TreeItem<MyData> rootItem = new TreeItem<MyData>();
    	rootItem.setExpanded(true);
    	ObservableList<DataSet> datasets = mainApp.getDataSetList();
    	for(int i=0;i<datasets.size();i++){
    		DataSet dataset = datasets.get(i);
    		TreeItem<MyData> dataNode = new TreeItem<MyData>(dataset);
    		rootItem.getChildren().add(dataNode);
    		
    		
    		Map<Long,Table> tables = dataset.AllTable();
    		Iterator<Map.Entry<Long, Table>> tableEntries = tables.entrySet().iterator();
    		while(tableEntries.hasNext()){
    			Map.Entry<Long, Table> tableEntry = tableEntries.next();
    			TreeItem<MyData> tableNode = new TreeItem<MyData>(tableEntry.getValue());
    			dataNode.getChildren().add(tableNode);
    			
    			Map<Long,Column> columns = tableEntry.getValue().AllColumn();
    			Iterator<Map.Entry<Long, Column>> columnEntries = columns.entrySet().iterator();
    			while(columnEntries.hasNext()){
    				Map.Entry<Long, Column> columnEntry = columnEntries.next();
    				TreeItem<MyData> columnNode = new TreeItem<MyData>(columnEntry.getValue());
    				tableNode.getChildren().add(columnNode);
    			}
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
							setText(paramT.getTitle());
						}else{
							//System.out.println("test"+rootItem.getChildren().size());
						}
					}
				};
			}
		});  	
	}
}
