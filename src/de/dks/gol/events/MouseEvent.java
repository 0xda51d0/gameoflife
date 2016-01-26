package de.dks.gol.events;

import de.dks.gol.logik.SimulationHandler;
import de.dks.gol.model.CellGridHandler;
import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.Canvas;

/**
 *
 * @author David Kunschke alias @dks
 * In dieser Klasse wird anhand der Methode drawMouseEvent() geprüft wo und ob auf unseren Haupt-Grid 
 * mit der gedrückten Maus gemalt werden kann oder nicht.
 */
public class MouseEvent {
    
    public MouseEvent(){}
    
    /**
     * Es wird anhand des MouseEvent geprüft wo und ob auf unseren Haupt-Grid 
     * mit der gedrückten Maus gemalt werden kann oder nicht.
     * @param canvas
     * @param cellGridHandler
     * @param simulationHandler 
     */
    public void drawMouseEvent(Canvas canvas, final CellGridHandler cellGridHandler, final SimulationHandler simulationHandler){
        //Maus Released  v
        canvas.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_RELEASED, (javafx.scene.input.MouseEvent t) -> {
            cellGridHandler.draw((int) t.getX(), (int) t.getY());
        });
        //Maus Dragged @DrawFunction
        canvas.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_DRAGGED, (javafx.scene.input.MouseEvent t) -> {
            cellGridHandler.draw((int) t.getX(), (int) t.getY());
        });
        // Maus Pressed @DrawFunction
        canvas.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_PRESSED, (javafx.scene.input.MouseEvent t) -> {
            cellGridHandler.saveCellUnderMouse((int) t.getX(), (int) t.getY());
        });
        
        /**
         * Prüfen der Grid-Größe bei veränderung der weite werden die gesetzten Methoden aufgerufen 
         */
        canvas.widthProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            cellGridHandler.resized();
            simulationHandler.render();
        });
        /**
         * Prüfen der Grid-Größe bei veränderung der höhe werden die gesetzten Methoden aufgerufen 
         */
        canvas.heightProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            cellGridHandler.resized();
            simulationHandler.render();
        });
    }// drawMouseEvent
}// MouseEvent
