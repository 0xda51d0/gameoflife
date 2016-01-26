package de.dks.gol.logik;

import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import de.dks.gol.events.ChangeEvent;
import de.dks.gol.events.MouseEvent;
import de.dks.gol.exception.ShapeException;
import de.dks.gol.model.CellGridHandler;
import de.dks.gol.model.SimulationGrid;
import de.dks.gol.scene.SceneHandler;
import de.dks.gol.shapes.Shape;
import de.dks.gol.shapes.ShapeCollection;

/**
 *
 * @author David Kunschke
 * @create 20.01.2016 15:28
 */
public class SimulationHandler {

    private double paneGridWidth;
    private double paneGridHeight;

    private double panePreviewGridWidth;
    private double panePreviewGridHeight;

    private int cellSize = 10;
    private int cellPreviewSize = 10;

    private int PAUSEDTIME = 30;

    private final SceneHandler sceneHandler;

    private AnimationTimer timer;
    private GraphicsContext gcGrid;
    private GraphicsContext gcGridPreview;

    private Canvas gridcanvas;
    private Pane gridBackgroundPane;

    private Canvas gridPreviewCanvas;
    private Pane gridPreviewBackgroundPane;

    private int timerCount = 0;
    private int rounds;
    private boolean isRunning;

    private final MouseEvent mouseDrawEvent;
    private final ChangeEvent changeEvent;

    protected CellGridHandler cellGridHandler;
    protected SimulationGrid simulationGrid;

    protected CellGridHandler cellPreviewGridHandler;
    protected SimulationGrid simulationPreviewGrid;

    private final ObservableList<Shape> shapeComboBoxData = FXCollections.observableArrayList();
    private Shape newShape;

    private boolean isGridOn;
    private boolean isColorOn;
    private boolean isOlderOn;

    /**
     * 
     * @param sceneHandler 
     */
    public SimulationHandler(SceneHandler sceneHandler) {
        this.sceneHandler = sceneHandler;

        mouseDrawEvent = new MouseEvent();
        changeEvent = new ChangeEvent();
    }// SimulationHandler

    /**
     * Initalisiert Handler Klassen
     */
    private void initHandlers() {
        sceneHandler.setSimualtionHandler(this);
        
    }// initHandlers

    /**
     * Initalisiert GUIComponents Klassen
     */
    private void initGUIComponents() {
        sceneHandler.getSimController().setTimeLabel(String.valueOf(PAUSEDTIME));
        sceneHandler.getSimController().setCellSizeLabel(String.valueOf(cellSize));
        gridcanvas = sceneHandler.getSimController().getGridCanvas();
        gridBackgroundPane = sceneHandler.getSimController().getGridBackgroundPane();

        gridPreviewCanvas = sceneHandler.getSimController().getGridPreviewCanvas();
        gridPreviewBackgroundPane = sceneHandler.getSimController().getGridPreviewBackgroundPane();

        gcGrid = gridcanvas.getGraphicsContext2D();
        gcGridPreview = gridPreviewCanvas.getGraphicsContext2D();

        gridcanvas.widthProperty().bind(gridBackgroundPane.widthProperty());
        gridcanvas.heightProperty().bind(gridBackgroundPane.heightProperty());

        gridPreviewCanvas.widthProperty().bind(gridPreviewBackgroundPane.widthProperty());
        gridPreviewCanvas.heightProperty().bind(gridPreviewBackgroundPane.heightProperty());

        paneGridWidth = gridBackgroundPane.getWidth();
        paneGridHeight = gridBackgroundPane.getHeight();

        panePreviewGridWidth = gridPreviewBackgroundPane.getWidth();
        panePreviewGridHeight = gridPreviewBackgroundPane.getHeight();
    }// initGUIComponents

    /**
     * Initalisiert Model Klassen 
     */
    private void initModel() {
        simulationGrid = new SimulationGrid((int) (paneGridWidth / cellSize), (int) (paneGridHeight / cellSize));
        simulationGrid.clear();

        cellGridHandler = new CellGridHandler(simulationGrid, cellSize, this);

        simulationPreviewGrid = new SimulationGrid((int) (panePreviewGridWidth / cellPreviewSize), (int) (panePreviewGridHeight / cellPreviewSize));
        simulationPreviewGrid.clear();

        cellPreviewGridHandler = new CellGridHandler(simulationPreviewGrid, cellPreviewSize, this);
    }// initModel
    
    /**
     *  Initalisiert Event Klassen
     */
    private void initEvents() {
        mouseDrawEvent.drawMouseEvent(gridcanvas, cellGridHandler, this);
        
        changeEvent.sliderCellCSizeChangeEvent(sceneHandler.getMainApp(),
                                               sceneHandler.getSimController().getCellSizeSlider(),
                                               sceneHandler.getSimController().getCellSizeLabel());

        changeEvent.sliderTimeChangeEvent(sceneHandler.getMainApp(),
                                          sceneHandler.getSimController().getTimeSlider(),
                                          sceneHandler.getSimController().getTimeLabel());

        changeEvent.checkboxGridChangeEvent (sceneHandler.getMainApp(),
                                             sceneHandler.getSimController().getGridOnCheckBox());
        changeEvent.checkboxColorChangeEvent(sceneHandler.getMainApp(),
                                             sceneHandler.getSimController().getCellOldColorCheckBox());
        changeEvent.checkboxOlderChangeEvent(sceneHandler.getMainApp(),
                                             sceneHandler.getSimController().getOldCellDelCheckBox());

    }// initEvents
    
    /**
     * Initalisiert alles was geht
     * @throws ShapeException 
     */
    public void init() throws ShapeException {
        initValues();
        
        filledShapeComboBox();
       
        initHandlers();
        
        initGUIComponents();
       
        initModel();

        getTimer();

        

        timer.start();

        initEvents();

        setNewShape(ShapeCollection.getShapeByName("Gleiter"));
        fill_In_Grid_NewShape();

    }// init

    /**
     * Initalisiert Standardwerte
     */
    private void initValues() {
        isGridOn = true;
        isColorOn = false;
        isOlderOn = false;
        timerCount = 0;
        rounds = 0;
    }// initValues

    // Setzen der Shapedaten in unser ComboBox
    private void filledShapeComboBox() {
        shapeComboBoxData.addAll(ShapeCollection.getShapes());
        sceneHandler.getSimController().setShapeComboBoxItems(shapeComboBoxData);
    }// filledShapeComboBox
    
    /**
     * Haupt-Updater
     */
    private void update() {
        simulationGrid.next();
    }// 

    /**
     * Haupt-renderer
     */
    public void render() {

        if (isGridOn) {
            cellGridHandler.paint(gcGrid, 1, 2, isColorOn, isOlderOn);
        } else {
            cellGridHandler.paint(gcGrid, 0, 0, isColorOn, isOlderOn);
        }// if

        cellPreviewGridHandler.paint(gcGridPreview, 0, 0, false, false);

    }// render
    
    /**
     * Haupt-Starter
     */
    public void starter() {
        if (!isRunning) {
            isRunning = true;
        }// if
    }// starter
    
    /**
     * Haupt-Starter
     */
    public void reset() {
        render();
    }// reset
    
    /**
     * Haupt-Starter
     */
    public void stopper() {
        if (isRunning) {
            isRunning = false;
        }// if
    }// stopper
    
    /**
     * Setzt unser neues Shape ins Haupt-Grid
     */
    public void fill_In_Grid_NewShape() {

        setShape(newShape);

    }// fill_In_Grid_NewShape
    
    /**
     * Säubert unser Haupt-Grid
     */
    public void clearGrid() {
        simulationGrid.clear();
        rounds = 0;
    }// clearGrid
    
    /**
     * Säubert unser Voschau-Grid
     */
    public void clearPreviewGrid() {
        simulationPreviewGrid.clear();
    }// clearPreviewGrid
    
    /**
     * Setzt Shape auf Haupt-Grid
     * @param shape 
     */
    public void setShape(Shape shape) {
        try {
            cellGridHandler.setShape(shape);
        } catch (ShapeException ex) {
            Logger.getLogger(SimulationHandler.class.getName()).log(Level.SEVERE, null, ex);
        }// try-catch

    }// setShape
    /**
     * Setzt Shape auf Vorschau-Grid
     * @param shape 
     */
    public void setPreviewShape(Shape shape) {
        try {
            cellPreviewGridHandler.setShape(shape);
        } catch (ShapeException ex) {
            Logger.getLogger(SimulationHandler.class.getName()).log(Level.SEVERE, null, ex);
        }// try-catch
    }// setPreviewShape

    /**
     * Dient den Vorschau-Grid der Größen-Anpassung von Shape und Vorschau-Grid
     * @param shape 
     */
    public void checkShapeForPreviewGrid(Shape shape) {
        gcGridPreview.clearRect(0, 0, panePreviewGridWidth, panePreviewGridHeight);
        clearPreviewGrid(); // säubert unser Vorschau-Grid
        
        // Shape Properties aufDimension setzen
        Dimension dim = shape.getDimension();

        int colsPreview = (int) (dim.width);
        int rowsPreview = (int) (dim.height);

        
        // Abfrage der Anzupassenden Größe
        if (colsPreview <= 5 && rowsPreview <= 5) {
            cellPreviewSize = 20;
            colsPreview = 5;
            rowsPreview = 5;
        } else if (colsPreview >= 6 && colsPreview <= 10 && rowsPreview <= 10) {
            cellPreviewSize = 10;
            colsPreview = 10;
            rowsPreview = 10;
        } else if (colsPreview >= 11 && colsPreview <= 20 && rowsPreview <= 20) {
            cellPreviewSize = 5;
            colsPreview = 20;
            rowsPreview = 20;
        } else if (colsPreview >= 21 && colsPreview <= 50 && rowsPreview <= 50) {
            cellPreviewSize = 2;
            colsPreview = 50;
            rowsPreview = 50;
        }// if
        
        // Malt das Voschau-Gitter-Netz
        for (int x = 0; x < colsPreview; x++) {
            for (int y = 0; y < rowsPreview; y++) {
                gcGridPreview.setStroke(Color.BLACK);
                gcGridPreview.setLineWidth(0.5);
                gcGridPreview.strokeRect(x * cellPreviewSize, y * cellPreviewSize, cellPreviewSize, cellPreviewSize);
            }// for
        }// for
        clearPreviewGrid();
        cellPreviewGridHandler.setPreviewCellSize(cellPreviewSize);
        setPreviewShape(shape);
    }// checkShapeForPreviewGrid
    
    /**
     * Erstellen AnimationTimer und des Steuerungen
     * 
     */
    private void getTimer() {

        timer = new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                // Säubert unser HaupGridLeinwand
                gcGrid.clearRect(0, 0, paneGridWidth, paneGridHeight);

                if (isGridOn) { // Gitter wird dargestellt oder nicht
                    // Unser Giiternetz wird gerendert
                    int cols = (int) (paneGridWidth / cellSize);
                    int rows = (int) (paneGridHeight / cellSize);
                    for (int x = 0; x < cols; x++) {
                        for (int y = 0; y < rows; y++) {
                            gcGrid.setStroke(Color.BLACK);
                            gcGrid.setLineWidth(0.5);
                            gcGrid.strokeRect(x * cellSize, y * cellSize, cellSize, cellSize);
                        }// for
                    }// for
                }// if
                
                // Aufruf Methoden
                render();
                setPaneSize();

                if (timerCount > PAUSEDTIME) { // Abfrage für das WENN-Updaten

                    // hier update und render
                    if (isRunning) {
                        update();
                        rounds++;
                    }// if

                    timerCount = 0;
                } else {
                    timerCount++;
                }// if
                sceneHandler.getSimController().setRoundsLabelText(String.valueOf(rounds)); //Runde Ende RoundsLabelText setzen
            }// handle
        };// AnimationTimer
    }// getTimer
    
    /**
     * Setzt die Weite und Höhe unseres Hintergrund Panes
     * an die Wert paneGridWidth, paneGridHeight
     */
    private void setPaneSize() {
        paneGridWidth = gridBackgroundPane.getWidth();
        paneGridHeight = gridBackgroundPane.getHeight();
    }// setPaneSize

    /**
     * Getter Gibt das GridCanvas(HauptCanvas) zurück
     * @return 
     */
    public Canvas getGridcanvas() {
        return gridcanvas;
    }// getGridcanvas
    
    /**
     * Getter Gibt den CellGridHandler zurück
     * @return 
     */
    public CellGridHandler getCellGridHandler() {
        return cellGridHandler;
    }// CellGridHandler
    
    /**
     * Getter Gibt den SimulationGrid zurück
     * @return 
     */
    public SimulationGrid getSimulationGrid() {
        return simulationGrid;
    }// getSimulationGrid
    
    /**
     * Getter Gibt den SceneHandler zurück
     * @return 
     */
    public SceneHandler getSceneHandler() {
        return sceneHandler;
    }// getSceneHandler
    
    /**
     * Getter Gibt den Integer PAUSEDTIME zurück
     * @return 
     */
    public int getPAUSEDTIME() {
        return PAUSEDTIME;
    }// getPAUSEDTIME
    
    /**
     * Setter Setzt PAUSEDTIME
     * @param PAUSEDTIME
     */
    public void setPAUSEDTIME(int PAUSEDTIME) {
        this.PAUSEDTIME = PAUSEDTIME;
    }// setPAUSEDTIME
    
    /**
     * Getter Gibt den CellGridHandler zurück
     * @return 
     */
    public int getCellSize() {
        return cellSize;
    }// getCellSize
    
    /**
     * Setter Setzt PAUSEDTIME
     * @param cellSize
     */
    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
        cellGridHandler.setCellSize(cellSize);
    }// setCellSize
    
    /**
     * Getter Gibt den CellGridHandler zurück
     * @return 
     */
    public Shape getNewShape() {
        return newShape;
    }// getNewShape
    
    /**
     * Setter Setzt PAUSEDTIME
     * @param shape
     */ 
    public void setNewShape(Shape shape) {
        this.newShape = shape;
        checkShapeForPreviewGrid(shape);
        sceneHandler.getSimController().setShapeLabelText(shape.toStringSmall());
    }// setNewShape
    
    /**
     * Getter Gibt den CellGridHandler zurück
     * @return 
     */
    public boolean isIsGridOn() {
        return isGridOn;
    }// isIsGridOn
    
    /**
     * Setter Setzt isGridOn
     * @param isGridOn
     */
    public void setIsGridOn(boolean isGridOn) {
        this.isGridOn = isGridOn;
    }// setIsGridOn
    
    /**
     * Getter Gibt den CellGridHandler zurück
     * @return 
     */
    public boolean isIsColorOn() {
        return isColorOn;
    }// isIsColorOn
    
    /**
     * Setter Setzt isColorOn
     * @param isColorOn
     */
    public void setIsColorOn(boolean isColorOn) {
        this.isColorOn = isColorOn;
    }// setIsColorOn
    
    /**
     * Getter Gibt den CellGridHandler zurück
     * @return 
     */
    public boolean isIsOlderOn() {
        return isOlderOn;
    }// isIsOlderOn
    
    /**
     * Setter Setzt isOlderOn
     * @param isOlderOn
     */
    public void setIsOlderOn(boolean isOlderOn) {
        this.isOlderOn = isOlderOn;
    }//setIsOlderOn
    
    /**
     * Getter Gibt den CellGridHandler zurück
     * @return 
     */
    public int getRounds() {
        return rounds;
    }// getRounds
    
    /**
     * Setter Setzt rounds
     * @param rounds
     */
    public void setRounds(int rounds) {
        this.rounds = rounds;
    }// setRounds

}// SimulationHandler
