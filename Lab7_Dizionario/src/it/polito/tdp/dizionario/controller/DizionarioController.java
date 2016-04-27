/**
 * Sample Skeleton for 'Dizionario.fxml' Controller Class
 */

package it.polito.tdp.dizionario.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.dizionario.model.Model;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DizionarioController {
	
	private Model model = new Model();
	private String result = "";

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtLettere"
    private TextField txtLettere; // Value injected by FXMLLoader

    @FXML // fx:id="txtParola"
    private TextField txtParola; // Value injected by FXMLLoader

    @FXML // fx:id="btGrafo"
    private Button btGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btVicini"
    private Button btVicini; // Value injected by FXMLLoader

    @FXML // fx:id="btConn"
    private Button btConn; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void btReset(ActionEvent event) {
    	btConn.setDisable(true);
    	btVicini.setDisable(true);
    	btGrafo.setDisable(false);
    	txtLettere.setDisable(false);
    	
    	txtParola.clear();
    	txtLettere.clear();
    	txtResult.clear();
    }

    @FXML
    void doGrafo(ActionEvent event) {
    	
    	if (txtLettere.getText() != "" && txtLettere.getText().matches("[1-9]+")) {
    		
    		Task<String> task = new Task<String>() {
        		
    			@Override
    			protected String call() throws Exception {
		    		btGrafo.setDisable(true);
		    		txtLettere.setDisable(true);
		        	
		        	model.creaGrafo(Integer.parseInt(txtLettere.getText())); 
		        	
		           	result = "Il grafo è stato creato";
		        			        	
		        	return result;
    			}
    		};
    		
    		
    		task.setOnSucceeded( new EventHandler<WorkerStateEvent>() {
    			
    			@Override
    			public void handle(WorkerStateEvent event) {
    				btVicini.setDisable(false);
		        	btConn.setDisable(false);
		        	
		        	txtResult.setText(result);
    			}
    		} );
        	
           	
        	Thread th = new Thread(task) ;
        	th.setDaemon(true);
        	th.start();
        	
    	} else {
    		txtResult.setText("Non hai inserito il numero di lettere! ");
    	}
    		
    }

    @FXML
    void findConn(ActionEvent event) {
    	result = "";
    	
    	if(isCorrect(txtParola.getText())) {
    		List<String> conn = model.searchConn(txtParola.getText());
    		
    		if(conn != null)
    			for(String w : conn)
    				result += w + "\n";
    		else
    			result = "Nessuna corrispondenza per la parola inserita";
    	} 
    	
    	txtResult.setText(result);
    }
    
    private boolean isCorrect(String p) {
    	if(p != "") {
    		if(!p.matches("[a-z]+")) {
    			result = "La parola inserita contiene numeri/punteggiatura";
    			return false;
    		} else {
    			if (p.length() != Integer.parseInt(txtLettere.getText())) {
    				result = "La parola non ha il numero esatto di lettere richiesto";
    				return false;
    			} else 
    				return true;
    		}
    	} else {
    		result = "Non hai inserito nessuna parola";
    		return false;
    	}
    	
    }

    @FXML
    void findVicini(ActionEvent event) {
    	result = "";
    	
    	if(isCorrect(txtParola.getText())) {
    		List<String> vicini = model.searchVicini(txtParola.getText());
    		
    		if(vicini != null)
    			for(String w : vicini)
    				result += w + "\n";
    		else
    			result = "Nessuna corrispondenza per la parola inserita";
    	}

    	txtResult.setText(result);
    }
    
	/*public void setModel(Model model) {
		this.model = model;
		
	}*/

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtLettere != null : "fx:id=\"txtLettere\" was not injected: check your FXML file 'Dizionario.fxml'.";
        assert txtParola != null : "fx:id=\"txtParola\" was not injected: check your FXML file 'Dizionario.fxml'.";
        assert btGrafo != null : "fx:id=\"btGrafo\" was not injected: check your FXML file 'Dizionario.fxml'.";
        assert btVicini != null : "fx:id=\"btVicini\" was not injected: check your FXML file 'Dizionario.fxml'.";
        assert btConn != null : "fx:id=\"btConn\" was not injected: check your FXML file 'Dizionario.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Dizionario.fxml'.";

    }

}
