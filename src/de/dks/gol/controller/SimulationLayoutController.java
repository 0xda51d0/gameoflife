package de.dks.gol.controller;

import de.dks.gol.core.MainApp;
import de.dks.gol.shapes.Shape;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * Die SimulationLayoutController Klasse
 * 
 * Klasse dient der Kommunikation zwischen unser View(Layout) 
 * und unserem Model
 * 
 *
 * @author David Kunschke alias dks
 * @create 20.01.2016 00:31
 */
public class SimulationLayoutController implements Initializable {

    private final Delta dragDelta = new Delta();

    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }// setMainApp

    /**
     * Initalisiert die Controller Klasse.
     * 
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {}// initialize

    /**
     * Ab hier Initialisierung der JavaFx Komponenten
     */
    
    @FXML
    private Pane gridBackgroundPane;

    @FXML
    private Canvas gridCanvas;

    @FXML
    private Pane gridPreviewBackgroundPane;

    @FXML
    private Canvas gridPreviewCanvas;

    @FXML
    private Slider timeSlider;

    @FXML
    private Label timeLabel;

    @FXML
    private Slider cellSizeSlider;

    @FXML
    private Label cellSizeLabel;

    @FXML
    private ComboBox<Shape> shapeComboBox;
    
    @FXML
    private Label shapeLabel;

    @FXML
    private Label roundsLabel; 
    
    @FXML
    private CheckBox cellOldColorCheckBox;
    
    @FXML
    private CheckBox oldCellDelCheckBox;
    
    @FXML
    private CheckBox gridOnCheckBox;
    
    /**
     *
     * Ab hier Initialisierung der JavaFx Steuerungen
     *
     */
    
    @FXML
    private void handleCloseButton() {
        System.exit(0);
    }// handleCloseButton

    /**
     * <p>Bei der Steuerungs-Methode @handleMinimizeButton(). 
     * Handelt das Minimieren unseres Fensters
     * <p>
     * 
     * @param mouseEvent 
     */
    @FXML
    private void handleMinimizeButton() {
        mainApp.getPrimaryStage().setIconified(true);
    }// handleMinimizeButton

    /**
     * <p>Bei der Steuerungs-Methode @handleStartButton(). 
     * Handelt das Starten unseres Timers für die Runden
     * <p>
     * 
     * @param mouseEvent 
     */
    @FXML
    private void handleStartButton() {
        mainApp.getSceneHandler().getSimualtionHandler().starter();
    }// handleStartButton

    /**
     * <p>Bei der Steuerungs-Methode @handleStopButton(). 
     * Handelt das Stoppen wir unseren Timers für die Runden
     * <p>
     * 
     * @param mouseEvent 
     */
    @FXML
    private void handleStopButton() {
        mainApp.getSceneHandler().getSimualtionHandler().stopper();
    }// handleStopButton
    
    /**
     * <p>Bei der Steuerungs-Methode @handleStopButton(). 
     * Handelt das Zurücksetzen useres Grids auf leer.
     * <p>
     * 
     * @param mouseEvent 
     */
    @FXML
    private void handleClearButton() {
        mainApp.getSceneHandler().getSimualtionHandler().clearGrid();
    }// handleClearButton
    
    /**
     * <p>Bei der Steuerungs-Methode @handleStopButton(). 
     * Handelt das Setzen des gewählten Shapes auf useren Haupt-Grids
     * <p>
     * 
     * @param mouseEvent 
     */
    @FXML
    private void handleSetShapeButton() {
        mainApp.getSceneHandler().getSimualtionHandler().fill_In_Grid_NewShape();
    }// handleSetShapeButton
    
    /**
     * <p>Bei der Steuerungs-Methode @handleStopButton(). 
     * Handelt das Setzen des gewählten Shapes auf useren Vorschau-Grid und setzt Shape newShape()
     * <p>
     * 
     * @param mouseEvent 
     */
    @FXML
    private void handleShapeComboBoxAction() {
        Shape shape = shapeComboBox.getSelectionModel().getSelectedItem();
        mainApp.getSceneHandler().getSimualtionHandler().setNewShape(shape);
    }// handleShapeComboBoxAction

    /**
     *
     * Hier beginnen die Steuerungen für die Bewegung des Fensters
     *
     */
    
    /**
     * <p>Bei der Steuerungs-Methode @handleMovedWindowOnMousePressed(MouseEvent mouseEvent)
     * wird die Instanz MouseEvent als Parameter übergeben. 
     * In der inneren statischen Klasse "Delta" werden die Werte aus der Differenz zwischen
     * Fenster-Koordinaten und den Maus-Koordinaten für die tatsächlichen Koordinaten
     * gespeichert.<p>
     * 
     * @param mouseEvent 
     */
    @FXML
    private void handleMovedWindowOnMousePressed(MouseEvent mouseEvent) {
        dragDelta.x = mainApp.getPrimaryStage().getX() - mouseEvent.getScreenX();
        dragDelta.y = mainApp.getPrimaryStage().getY() - mouseEvent.getScreenY();
    }// handleMovedWindowOnMousePressed
    
    /**
     * <p>Bei der Steuerungs-Methode @handleMovedWindowOnMouseDragged(MouseEvent mouseEvent)
     * wird die Instanz MouseEvent als Parameter übergeben. 
     * Bei den Drücken der Taste und gleichzeitiges Ziehen der Maus werden die Werte X und Y aus der 
     * inneren Klasse "Delta" abgerugfen. Mit den neuen MausKoordinaten + den gespeicherten Wert von "Delta"
     * kann das Fenster neu gesetzt werden.
     * 
     * @param mouseEvent 
     */
    @FXML
    private void handleMovedWindowOnMouseDragged(MouseEvent mouseEvent) {
        mainApp.getPrimaryStage().setX(mouseEvent.getScreenX() + dragDelta.x);
        mainApp.getPrimaryStage().setY(mouseEvent.getScreenY() + dragDelta.y);
    }// handleMovedWindowOnMouseDragged

    /**
     *
     * Hier beginn initialisierung Steuerung für das Anpassen der Größe des
     * Fensters
     *
     */
    
    /**
     * <p>Bei der Steuerungs-Methode @handleResizedWindowOnMousePressed(MouseEvent mouseEvent)
     * wird die Instanz MouseEvent als Parameter übergeben. 
     * In der inneren statischen Klasse "Delta" werden die Werte aus der Differenz zwischen
     * Fenster-Größe und den Maus-Koordinaten für die tatsächlichen Größe gespeichert.<p>
     * 
     * @param mouseEvent 
     */
    @FXML
    private void handleResizedWindowOnMousePressed(MouseEvent mouseEvent) {
        dragDelta.width = mainApp.getPrimaryStage().getWidth() - mouseEvent.getScreenX();
        dragDelta.height = mainApp.getPrimaryStage().getHeight() - mouseEvent.getScreenY();
    }// handleResizedWindowOnMousePressed
    
    /**
     * <p>Bei der Steuerungs-Methode @handleResizedWindowOnMouseDragged(MouseEvent mouseEvent)
     * wird die Instanz MouseEvent als Parameter übergeben. 
     * Bei den Drücken der Taste und gleichzeitiges Ziehen der Maus werden die Werte "width" und "height" aus der 
     * inneren Klasse "Delta" abgerugfen. Mit den neuen MausKoordinaten + den gespeicherten Werten von "Delta"
     * kann die Fenstergröße neu gesetzt werden.
     * 
     * @param mouseEvent 
     */
    @FXML
    private void handleResizedWindowOnMouseDragged(MouseEvent mouseEvent) {
        double width = mouseEvent.getScreenX() + dragDelta.width;
        double height = mouseEvent.getScreenY() + dragDelta.height;

        if (width < 800) {              // Abfrage Fensterweite ist kleiner als 800 dann Fensterweite gleich 800
            width = 800;
        }

        if (height < 610) {             // Abfrage Fensterhöhe ist kleiner als 610 dann Fensterhöhe gleich 610
            height = 610;
        }

        mainApp.getPrimaryStage().setWidth(width);
        mainApp.getPrimaryStage().setHeight(height);

    }// handleResizedWindowOnMouseDragged

    /**
     * Innere statische Klasse Anpassen der Fenstergröße und der FensterKoordinaten
     */
    private static class Delta {

        double x, y;
        double width, height;
    }// Delta

    /**
     * Getter Übergibt das Pane Objekt für den Gitterhintergrund zurück
     *
     * @return gridBackgroundPane
     */
    public Pane getGridBackgroundPane() {
        return gridBackgroundPane;
    }// getGridBackgroundPane

    /**
     * Getter Übergibt das Canvas Objekt für das Gitter zurück
     *
     * @return gridCanvas
     */
    public Canvas getGridCanvas() {
        return gridCanvas;
    }// getGridCanvas
    
    /**
     * Getter Übergibt das Pane Objekt für den Gitter-Hintergrund der Einfüge-Vorschau zurück
     *
     * @return gridPreviewBackgroundPane
     */
    public Pane getGridPreviewBackgroundPane() {
        return gridPreviewBackgroundPane;
    }// getGridPreviewBackgroundPane
    
    /**
     * Getter Übergibt das Canvas Objekt für das Gitter der Vorschau zurück
     *
     * @return gridPreviewCanvas
     */
    public Canvas getGridPreviewCanvas() {
        return gridPreviewCanvas;
    }// getGridPreviewCanvas

    /**
     * Getter Übergibt den Slider für die Einstellung des Zeitfaktors 
     * @return timeSlider
     */
    public Slider getTimeSlider() {
        return timeSlider;
    }// getTimeSlider
    
    /**
     * Getter Übergibt den Slider für die Einstellung der Zellgröße
     * @return cellSizeSlider
     */
    public Slider getCellSizeSlider() {
        return cellSizeSlider;
    }// getCellSizeSlider
    
    /**
     * Getter Übergibt der CheckBox, für die Einstellung für das Anzeigen des Zellalters
     * @return cellOldColorCheckBox
     */
    public CheckBox getCellOldColorCheckBox() {
        return cellOldColorCheckBox;
    }// getCellOldColorCheckBox
    
    /**
     * Getter Übergibt der CheckBox, für die Einstellung für das Ausblenden der ältesten Zellen
     * @return oldCellDelCheckBox
     */
    public CheckBox getOldCellDelCheckBox() {
        return oldCellDelCheckBox;
    }// oldCellDelCheckBox
    
    /**
     * Getter Übergibt der CheckBox, für die Einstellung für das Anzeigen des Gitternetzes
     * @return gridOnCheckBox
     */
    public CheckBox getGridOnCheckBox() {
        return gridOnCheckBox;
    }// getGridOnCheckBox
    
    /**
     * Getter Übergibt das Label für die Anzeige des Zeitfaktors 
     * @return timeLabel
     */
    public Label getTimeLabel() {
        return timeLabel;
    }// getTimeLabel
    
    /**
     * Setter Setzt das mitgebenen String Objekt mit den Text des ZeitfaktorLabels
     * @param timeLabelText 
     */
    public void setTimeLabel(String timeLabelText) {
        timeLabel.setText(timeLabelText);
    }// setTimeLabel
    
    /**
     * Getter Übergibt das Label für die Anzeige der Zellgröße
     * @return cellSizeLabel
     */
    public Label getCellSizeLabel() {
        return cellSizeLabel;
    }// getCellSizeLabel
    
    /**
     * Setter Setzt das mitgebenen String Objekt mit den Text des ZellGrößeLabels
     * @param timeLabelText 
     */
    public void setCellSizeLabel(String timeLabelText) {
        this.cellSizeLabel.setText(timeLabelText);
    }// setCellSizeLabel
    /**
     * Getter Übergibt das Label für die Anzeige des ShapeTextes
     * @return shapeLabel
     */
    public Label getShapeLabel(){
        return shapeLabel;
    }// getShapeLabel
    
    /**
     * Setter Setzt das mitgebenen String Objekt mit den Text des ShapeLabels
     * @param shapeLabelText 
     */
    public void setShapeLabelText(String shapeLabelText){
        this.shapeLabel.setText(shapeLabelText);
    }// setShapeLabelText
    
    /**
     * Getter Übergibt das Label für die Anzeige der Zellgröße
     * @return roundsLabel
     */
    public Label getRoundsLabel() {
        return roundsLabel;
    }// getRoundsLabel
    
    /**
     * Setter Setzt das mitgebenen String Objekt mit den Text des RundenLabels
     * @param roundsLabelText 
     */
    public void setRoundsLabelText(String roundsLabelText) {
        this.roundsLabel.setText(roundsLabelText);
    }// setRoundsLabelText

    /**
     * Setter Setzt die mitgegebene ObservableList mit unser ShapeComboBox Content
     * @param shapeComboBoxData 
     */
    public void setShapeComboBoxItems(ObservableList<Shape> shapeComboBoxData) {
        shapeComboBox.setItems(shapeComboBoxData);
    }// setShapeComboBoxItems
}// SimulationLayoutController
