package data.view;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import data.model.Table;
import data.model.represents;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class TableController {
	
	@FXML
	private TextField title;
	@FXML
	private TextField description;
	@FXML
	private ComboBox<Entry<Integer,String>> comboBox;
	@FXML
	private Button apply;
	
	private Table table;
	
	public TableController(){	
	}
	
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	
    }
    
	public void setTable(Table table) throws IOException{
    	this.table=table;
    	this.title.setText(table.getTitle());
    	this.description.setText(table.getDescription());
    	ObservableList<Entry<Integer, String>> representList = FXCollections.observableArrayList();
    	table.theRepresent();
		Map<Integer,String> representOptions = represents.allOptions();
    	for(Entry<Integer,String> e:representOptions.entrySet()){
    		representList.add(e);
    	}
    	this.comboBox.setItems(representList);
    	
    	this.comboBox.setCellFactory(new Callback<ListView<Entry<Integer,String>>,ListCell<Entry<Integer,String>>>(){
            @Override
            public ListCell<Entry<Integer,String>> call(ListView<Entry<Integer,String>> l){
                return new ListCell<Entry<Integer,String>>(){
                    @Override
                    protected void updateItem(Entry<Integer,String> item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                        	setText(null);
			                setGraphic(null);
                        } else {
                            setText(item.getValue());
                        }
                    }
                };
            }
        });
    	this.comboBox.setConverter(new StringConverter<Entry<Integer,String>>() {
            @Override
            public String toString(Entry<Integer,String> item) {
            	if (item == null){
            		return null;
            	} else {
            		return item.getValue();
            	}
            }

            @Override
            public Entry<Integer,String> fromString(String userId) {
            	return null;
            }
    	});
    }
    
    /**
     * Called when the user clicks on the delete button.
     * Apply any changes to selected table.
     * @throws IOException 
     */
    @FXML
    private void handleApply() throws IOException {
    	// TODO add action handler here
        System.out.println("click apply...");
        if(this.validation()){
        	this.table.setTitle(this.title.getText());
        	if(this.description.getText()!=null){
        		this.table.setDesription(this.description.getText());
        	}
        	if(this.comboBox.getSelectionModel().getSelectedIndex()!=-1){
        	//System.out.println(represent);
        		this.table.setRepresents(this.comboBox.getSelectionModel().getSelectedItem().getKey());
        	}
        }
    }
    
    /**
     * check form validation
     * @return
     */
    private boolean validation(){
    	// TODO complete form validation
    	if(this.title.getText()==null){
    		return false;
    	}
		return true;
    }

}
