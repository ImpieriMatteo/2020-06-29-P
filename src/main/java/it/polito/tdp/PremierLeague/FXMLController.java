/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.Arco;
import it.polito.tdp.PremierLeague.model.Match;
import it.polito.tdp.PremierLeague.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnConnessioneMassima"
    private Button btnConnessioneMassima; // Value injected by FXMLLoader

    @FXML // fx:id="btnCollegamento"
    private Button btnCollegamento; // Value injected by FXMLLoader

    @FXML // fx:id="txtMinuti"
    private TextField txtMinuti; // Value injected by FXMLLoader

    @FXML // fx:id="cmbMese"
    private ComboBox<Month> cmbMese; // Value injected by FXMLLoader

    @FXML // fx:id="cmbM1"
    private ComboBox<Match> cmbM1; // Value injected by FXMLLoader

    @FXML // fx:id="cmbM2"
    private ComboBox<Match> cmbM2; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doConnessioneMassima(ActionEvent event) {
    	this.txtResult.clear();
    	
    	List<Arco> archiBest = this.model.getConnessioneMax();
    	
    	this.txtResult.appendText("Coppie con connesione massima: \n\n");
    	for(Arco a : archiBest) {
    		this.txtResult.appendText(a.getMatch1().toString()+" - "+a.getMatch2()+" ("+a.getPeso()+")\n");
    	}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.txtResult.clear();
    	
    	Integer MIN = 0;
    	try {
    		MIN = Integer.parseInt(this.txtMinuti.getText());
    	}
    	catch(NumberFormatException e) {
    		this.txtResult.appendText("Inserisci un numero INTERO positivo!!");
    		return;
    	}
    	
    	Integer mese = 0;
    	try {
    		mese = this.cmbMese.getValue().getValue();
    	}
    	catch(NumberFormatException e) {
    		this.txtResult.appendText("Devi prima scegliere un MESE!!");
    	}
    	
    	String result = this.model.creaGrafo(MIN, mese);
    	
    	this.txtResult.appendText(result);
    	
    	this.btnConnessioneMassima.setDisable(false);
    	this.btnCollegamento.setDisable(false);
    	this.cmbM1.getItems().addAll(this.model.getMatchGrafo());
    	this.cmbM2.getItems().addAll(this.model.getMatchGrafo());
    }

    @FXML
    void doCollegamento(ActionEvent event) {
    	this.txtResult.clear();
    	
    	Match m1 = this.cmbM1.getValue();
    	Match m2 = this.cmbM2.getValue();
    	if(m1==null || m2==null) {
    		this.txtResult.appendText("Devi scegliere entrambi i MATCH!!");
    		return;
    	}
    	
    	this.model.trovaPercorso(m1, m2);
    	
    	this.txtResult.appendText("Percorso migliore da "+m1.toString()+" a "+m2.toString()+" : \n");
    	this.txtResult.appendText("Peso: "+this.model.getPesoPercorso()+"\n");
    	for(Match m : this.model.getPercorso()) {
    		this.txtResult.appendText("- "+m.toString()+"\n");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnConnessioneMassima != null : "fx:id=\"btnConnessioneMassima\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCollegamento != null : "fx:id=\"btnCollegamento\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMinuti != null : "fx:id=\"txtMinuti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbMese != null : "fx:id=\"cmbMese\" was not injected: check your FXML file 'Scene.fxml'.";        assert cmbM1 != null : "fx:id=\"cmbM1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbM2 != null : "fx:id=\"cmbM2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
  
    	this.cmbMese.getItems().addAll(Month.values());
    }
    
    
}
