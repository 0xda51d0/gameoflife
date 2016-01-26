package de.dks.gol.controller;

import de.dks.gol.core.MainApp;
import de.dks.gol.exception.ShapeException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import static de.dks.gol.core.MainApp.SIMULATION_SCENE;

/**
 * SplashLayoutController Klasse
 *
 * @author David Kunschke alias dks
 * @create 20.01.2016 10:04
 */
public class SplashLayoutController implements Initializable {

    private MainApp mainApp;
    
    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
    }// setMainApp
    
    /**
     * Initalisiert die Controller Klasse
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {}// initialize   
   
    /**
     * Steuert unser Splashscreen für das weiter leiten zum "SimulationLayout"
     * @throws ShapeException 
     */
    @FXML
    private void handleSplashButton() throws ShapeException{
        mainApp.getSceneHandler().switchScene(SIMULATION_SCENE);
    }// handleSplashButton
}// SplashLayoutController
