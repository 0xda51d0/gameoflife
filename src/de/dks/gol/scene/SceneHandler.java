package de.dks.gol.scene;

import de.dks.gol.controller.SimulationLayoutController;
import de.dks.gol.controller.SplashLayoutController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.stage.Stage;


import de.dks.gol.core.MainApp;
import static de.dks.gol.core.MainApp.SIMULATION_SCENE;
import static de.dks.gol.core.MainApp.SPLASH_SCENE;
import de.dks.gol.exception.ShapeException;
import de.dks.gol.logik.SimulationHandler;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

/**
 *
 * @author David Kunschke alias dks
 * @create 20.01.2016 00.04
 *
 * <a>
 * Die Klasse dient der Steuerung der einzelnen Szenen. Beim Erstellen werden
 * die Attribute @(code) mainApp und @(code) primaryStage als Parameter an den
 * Konstruktor übergeben und die Instanzvariablen auf die Parameterwerte
 * gesetzt.
 * </a>
 *
 * <a>
 * Für das switchen der einzelenen Szenen dienen die Funktionen
 * @(code) setSimulationScene()
 * @(code) setSplashScene()
 * </a>
 *
 * <a>
 * Für die Instanzvariablen wurden die dementsprechenden Getter Funktionen
 * erstellt.
 * </a>
 */
public class SceneHandler {

    private final MainApp mainApp;
    private final Stage primaryStage;
    
    private Scene sim_Scene;
    private Scene splash_Scene;
    
    private Parent rootSplash;
    
    private FXMLLoader loader = null;

    private SimulationLayoutController simController;
    private SplashLayoutController splashController;
    private SimulationHandler simualtionHandler;
    /**
     * Konstrukter mit übergebene Parameter und das ersetzen mit dem
     * Parameterwerte
     *
     * @param mainApp
     * @param primaryStage
     */
    public SceneHandler(final MainApp mainApp, final Stage primaryStage) {
        this.mainApp = mainApp;
        this.primaryStage = primaryStage;
        
        createScene();
    }// SceneHandler

    private void createScene(){
        setSimulationScene();
        setSplashScene();
    }
    /**
     * 
     */
    private void setSimulationScene() {
        
        try {
            loader = new FXMLLoader(getClass().getResource("/fxml/SimulationLayout.fxml"));
            Parent root;
            root = loader.load();
            SceneAntialiasing sa = SceneAntialiasing.BALANCED;
            sim_Scene = new Scene(root,800,610,true,sa);
            primaryStage.toFront();
            FadeTransition fadeSplash = new FadeTransition(Duration.seconds(1.2), root);
                fadeSplash.setFromValue(0.0);
                fadeSplash.setToValue(1.0);
                fadeSplash.play();
            sim_Scene.getStylesheets().add("/styles/mainStyleSheet.css");
            simController = loader.getController();
            simController.setMainApp(mainApp);
        } catch (IOException ex) {
            Logger.getLogger(SceneHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// setSimulationScene

    private void setSplashScene() {
        try {
            loader = new FXMLLoader(getClass().getResource("/fxml/SplashLayout.fxml"));
            Parent root;
            rootSplash = loader.load();
            SceneAntialiasing sa = SceneAntialiasing.BALANCED;
            
            splash_Scene = new Scene(rootSplash,800,610,true,sa);
            primaryStage.toFront();
                FadeTransition fadeSplash = new FadeTransition(Duration.seconds(1.2), rootSplash);
                fadeSplash.setFromValue(0.0);
                fadeSplash.setToValue(1.0);
                fadeSplash.play();

            splash_Scene.getStylesheets().add("/styles/mainStyleSheet.css");
            splashController = loader.getController();
            splashController.setMainApp(mainApp);
        } catch (IOException ex) {
            Logger.getLogger(SceneHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// setSplashScene

    /**
     * Funktion dient dem Switchen und setzen der Scenne 
     * anhanhand des mitgegebenen Parameters
     * @param id 
     * @throws de.dks.gol.exception.ShapeException 
     */
    public void switchScene(int id) throws ShapeException{
        switch(id){
            case SPLASH_SCENE:
                primaryStage.setScene(splash_Scene);
                primaryStage.show();
                
                
                break;
            case SIMULATION_SCENE:
                
                primaryStage.setScene(sim_Scene);
                primaryStage.show();
                simualtionHandler = new SimulationHandler(this);
                simualtionHandler.init();
                
                break;
        }
    }// switchScene
    
    /**
     * Getter Funktionen für Instanz MainApp
     * @return mainApp
     */
    public MainApp getMainApp() {
        return mainApp;
    }// getMainApp

    /**
     * Getter Funktionen für Instanz Stage 
     * @return primaryStage
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }// getPrimaryStage

    /**
     * Getter Funktionen für Instanz SimulationLayoutController
     * @return primaryStage
     */
    public SimulationLayoutController getSimController() {
        return simController;
    }// SimulationLayoutController

    public SimulationHandler getSimualtionHandler() {
        return simualtionHandler;
    }

    public void setSimualtionHandler(SimulationHandler simualtionHandler) {
        this.simualtionHandler = simualtionHandler;
    }
}
