package data.view;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import data.MainApp;
import data.model.ClassColumn;
import data.model.Column;
import data.model.DataSet;
import data.model.JoinTable;
import data.model.MyData;
import data.model.NormalTable;
import data.model.Table;
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
	
	public TreeItem<MyData> addNewDataset(DataSet dataset){
		TreeItem<MyData> treeNode = new TreeItem<MyData>(dataset);
		this.dataTree.getRoot().getChildren().add(treeNode);
		return treeNode;
	}
	
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	this.dataTree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showDataDetails(newValue));
    }
    /**
     * Switch between data set view, table view, and column view accordingly.
     * 
     * @param myData
     */
    private void showDataDetails(TreeItem<MyData> treeNode){    	
        try {
        	if(treeNode==null||treeNode.getValue()==null){
        		FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("view/Welcome.fxml"));
                AnchorPane personOverview = (AnchorPane) loader.load();
        		this.mainApp.replaceDataDetail(personOverview);
        		
        		WelcomeController controller = loader.getController();
        		controller.setWelcome(this,this.mainApp);
        	}else if(treeNode.getValue().dataType()==0){
        		//need to show data set view
        		FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("view/DatasetDetail.fxml"));
                AnchorPane personOverview = (AnchorPane) loader.load();
                this.mainApp.replaceDataDetail(personOverview);
        		
        		//set the data set controller. doing cast here.
        		DatasetDetailController controller = loader.getController();
        		controller.setDataset((DataSet)(treeNode.getValue()),this.mainApp, treeNode);
        	}else if(treeNode.getValue().dataType()==1){
        		//need to show table view
        		if(((Table)(treeNode.getValue())).getTableType()==1){
        			//handle join table
	        		FXMLLoader loader = new FXMLLoader();
	                loader.setLocation(MainApp.class.getResource("view/JoinTableDetail.fxml"));
	                AnchorPane personOverview = (AnchorPane) loader.load();
	                this.mainApp.replaceDataDetail(personOverview);
	        		
	        		//set table controller
	        		JoinTableController controller = loader.getController();
	        		controller.setTable((JoinTable)(treeNode.getValue()),this.mainApp,treeNode);        			
        		}else if(((Table)(treeNode.getValue())).getTableType()==0){
        			//handle normal table
	        		FXMLLoader loader = new FXMLLoader();
	                loader.setLocation(MainApp.class.getResource("view/NormalTableDetail.fxml"));
	                AnchorPane personOverview = (AnchorPane) loader.load();
	                this.mainApp.replaceDataDetail(personOverview);
	        		
	        		//set table controller
	        		NormalTableController controller = loader.getController();
	        		controller.setTable((NormalTable)(treeNode.getValue()),this.mainApp,treeNode);
        		}
        	}else if(treeNode.getValue().dataType()==2){
        		//need to show column view
        		// TODO add function handle class column
        		if(((Table)(treeNode.getParent().getValue())).getTableType()==0){
	        		FXMLLoader loader = new FXMLLoader();
	                loader.setLocation(MainApp.class.getResource("view/ColumnDetail.fxml"));
	                AnchorPane personOverview = (AnchorPane) loader.load();
	                this.mainApp.replaceDataDetail(personOverview);
	        		
	        		//set column controller
	        		ColumnController controller = loader.getController();
	        		controller.setColumn((Column)treeNode.getValue(),((Column)treeNode.getValue()).parentTable(),treeNode.getParent(),treeNode);
        		}else if(((Table)(treeNode.getParent().getValue())).getTableType()==1){
	        		FXMLLoader loader = new FXMLLoader();
	                loader.setLocation(MainApp.class.getResource("view/ClassColumnDetail.fxml"));
	                AnchorPane personOverview = (AnchorPane) loader.load();
	                this.mainApp.replaceDataDetail(personOverview);
	        		
	        		//set column controller
	        		ClassColumnController controller = loader.getController();
	        		controller.setColumn((ClassColumn)treeNode.getValue(),(JoinTable)((ClassColumn)treeNode.getValue()).parentTable());
        		}
        	}else{
        		System.err.println("DatasetController: invalid type"+treeNode.getValue().dataType());
        	}
        	
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * set mainApp
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp){
    	this.mainApp = mainApp;
    }
    
    /**
     * set show the tree view of all data sets
     */
	public void setMyData() {
		if(this.mainApp==null){
			return;
		}
        // Add observable list data to the tree
    	TreeItem<MyData> rootItem = new TreeItem<MyData>();
    	rootItem.setExpanded(true);
    	for(int i=0;i<this.mainApp.size();i++){
    		DataSet dataset = this.mainApp.get(i);
    		TreeItem<MyData> dataNode = new TreeItem<MyData>(dataset);
    		rootItem.getChildren().add(dataNode);
    		
    		
    		Map<Long,Table> tables = dataset.AllTable();
    		Iterator<Map.Entry<Long, Table>> tableEntries = tables.entrySet().iterator();
    		while(tableEntries.hasNext()){
    			Map.Entry<Long, Table> tableEntry = tableEntries.next();
    			TreeItem<MyData> tableNode = new TreeItem<MyData>(tableEntry.getValue());
    			dataNode.getChildren().add(tableNode);
    			
    			//Map<Long,Column> columns = tableEntry.getValue().AllColumn();
    			//need casting, first check table type
    			if(tableEntry.getValue().getTableType() == 0 ){
    				Map<Long,Column> columns = ((NormalTable)(tableEntry.getValue())).AllColumn();
    				
        			Iterator<Map.Entry<Long, Column>> columnEntries = columns.entrySet().iterator();
        			while(columnEntries.hasNext()){
        				Map.Entry<Long, Column> columnEntry = columnEntries.next();
        				TreeItem<MyData> columnNode = new TreeItem<MyData>(columnEntry.getValue());
        				tableNode.getChildren().add(columnNode);
        			}
    			}else if(tableEntry.getValue().getTableType() == 1 ){
    				Map<Long, ClassColumn> columns = ((JoinTable)(tableEntry.getValue())).AllColumn();
        			Iterator<Map.Entry<Long, ClassColumn>> columnEntries = columns.entrySet().iterator();
        			while(columnEntries.hasNext()){
        				Map.Entry<Long, ClassColumn> columnEntry = columnEntries.next();
        				TreeItem<MyData> columnNode = new TreeItem<MyData>(columnEntry.getValue());
        				tableNode.getChildren().add(columnNode);
        			}
    			}else{
    				System.out.println("unknow table type: " + tableEntry.getValue().getTableType());
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
							setText("Click To Add New Data Set Here...");
						}
					}
				};
			}
		});  	
	}
}
