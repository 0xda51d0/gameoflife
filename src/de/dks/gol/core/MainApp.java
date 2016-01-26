package de.dks.gol.core;

import de.dks.gol.scene.SceneHandler;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static javafx.application.Application.launch;

/**
 * 
 * @author David Kunschke alias @dks
 *
 * <p>In der MainClass die wichtigste Steuerungs-Klasse initalisiert
 * Der Scenehandler gibt uns alle Instanzen die wir für unser Programm brauchen.
 * Zudem wird im ScenenHandler unser Aktuelles Layout gesetzt<p>
 * 
 */
public class MainApp extends Application {
    
    public final static int SPLASH_SCENE        = 0;
    public final static int SIMULATION_SCENE    = 1;
    
    private final static double VERSIONS_NUMBER = 1.0;
    public final static String VERSION          = " ver." + VERSIONS_NUMBER;
    public final static String TITLE            = "Game Of Life";
    
    private Stage primaryStage;
    private SceneHandler sceneHandler;
    
    /**
     * Von JAvaFX wird die StartMethode "start" für das Initalisierung 
     * unseres Programmes überschrieben
     * @param _stage
     * @throws Exception 
     */
    @Override
    public void start(Stage _stage) throws Exception {
        this.primaryStage = _stage;
        this.primaryStage.initStyle(StageStyle.UNDECORATED);
        this.primaryStage.getIcons().add(new Image("/img/icon_gol.png"));   // App Icon setzten
        sceneHandler = new SceneHandler(this, primaryStage);                // ScenenHAndler initialisieren
        sceneHandler.switchScene(SPLASH_SCENE);                             // SplashScreen als Startbildschirm setzten
        
    }// start
    /**
     * Getter Gibt unsern Stage zurück
     * @return 
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }// getPrimaryStage
    /**
     * Getter Gibt unsern SceneHandler zurück
     * @return 
     */
    public SceneHandler getSceneHandler() {
        return sceneHandler;
    }// getSceneHandler
    /**
     * Start-Methode der Anfang allen Übels
     * @param _args 
     */    
    public static void main(String[] _args) {
        launch(_args);
    }// main
}// MainApp
