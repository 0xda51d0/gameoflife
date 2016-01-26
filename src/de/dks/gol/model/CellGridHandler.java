package de.dks.gol.model;

import java.awt.Dimension;
import java.util.Enumeration;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import de.dks.gol.exception.ShapeException;
import de.dks.gol.logik.SimulationHandler;
import de.dks.gol.shapes.Shape;

/**
 *
 * @author David Kunschke alias @dks
 * @create 21.01.2016 19:02
 */
public class CellGridHandler {

    private boolean cellUnderMouse;

    private final SimulationHandler simulationHandler;

    private final CellGrid cellGrid;
    private float cellSize;
    private float newCellSize;

    private Shape newShape;
    /**
     * 
     * @param cellGrid
     * @param cellSize
     * @param simulationHandler 
     */
    public CellGridHandler(CellGrid cellGrid, float cellSize, SimulationHandler simulationHandler) {
        this.cellGrid = cellGrid;
        this.cellSize = cellSize;
        this.simulationHandler = simulationHandler;

    }// CellGridHandler
    
    /**
     * Setzt die Zellgröße des Haupt-Grids 
     * @param cellSize 
     */
    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
        resized();
        simulationHandler.render();
    }// setCellSize
    
    /**
     * Setzt die Zellgröße des Vorschau-Grids 
     * @param cellSize 
     */
    public void setPreviewCellSize(int cellSize) {
        this.cellSize = cellSize;
        resizedPreview();
        simulationHandler.render();
    }// setPreviewCellSize
    
    /**
     * Nutzung für die Zellgrößen veränderung in unseren Haupt-Grid
     */
    public void resized() {

        Dimension canvasDim;
        canvasDim = new Dimension((int) simulationHandler.getSceneHandler().getSimController().getGridCanvas().getWidth(),
                (int) simulationHandler.getSceneHandler().getSimController().getGridCanvas().getHeight());

        cellGrid.resize((int) (canvasDim.width / cellSize), (int) (canvasDim.height / cellSize));

    }// resized
    
    /**
     * Nutzung für die Zellgrößen veränderung in unseren Vorschau-Grid
     */
    public void resizedPreview() {

        Dimension canvasDim;
        //Größen Properties des Vorschau Grids setzen
        canvasDim = new Dimension((int) simulationHandler.getSceneHandler().getSimController().getGridPreviewCanvas().getWidth(),
                (int) simulationHandler.getSceneHandler().getSimController().getGridPreviewCanvas().getHeight());
        // Die Größe wird neu gesetzt
        cellGrid.resize((int) (canvasDim.width / cellSize), (int) (canvasDim.height / cellSize));

    }// resizedPreview
    
    /**
     * Prüft ob die Zelle existiert mit den Übergebenen Koordinaten 
     * und setzt boolean cellUnderMouse
     * @param x Maus-Koordinate X
     * @param y Maus-Koordinate Y
     */
    public void saveCellUnderMouse(int x, int y) {
        try {
            cellUnderMouse = cellGrid.getCell((int) (x / cellSize), (int) (y / cellSize));
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            // ignore
        }// try-catch
    }// saveCellUnderMouse

    /**
     * Setzt und Rendert eine neue gesetzte Zelle
     * @param x
     * @param y 
     */
    public void draw(int x, int y) {
        try {
            cellGrid.setCell((int) (x / cellSize), (int) (y / cellSize), !cellUnderMouse);
            simulationHandler.render();
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            // ignore
        }// try-catch
    }// draw
    
    /**
     * Rendert unser Canvas und alle befindlichen Objekte in CellGrid der Instanz Cell
     * @param gc
     * @param posOffset
     * @param sizeOffset
     * @param isColor
     * @param isOlder 
     */
    public void paint(GraphicsContext gc, int posOffset, int sizeOffset, boolean isColor, boolean isOlder) {

        
        if (isColor) {                                  // Wenn CheckBox isColorOn true
            Enumeration enum1 = cellGrid.getEnum();
            Cell c;
            while (enum1.hasMoreElements()) {
                c = (Cell) enum1.nextElement();
                if (c.older < 5) {                      // Wenn das Alter bestimmte Werte erreicht werden        
                    gc.setFill(Color.BLACK);            // diese Farblich dargestellt Rot Grün Gelb Schwarz 
                    gc.fillRect(c.col * cellSize + posOffset, c.row * cellSize + posOffset, cellSize - sizeOffset, cellSize - sizeOffset);
                } else if (c.older < 10 & c.older >= 5) {
                    gc.setFill(Color.GREEN);
                    gc.fillRect(c.col * cellSize + posOffset, c.row * cellSize + posOffset, cellSize - sizeOffset, cellSize - sizeOffset);
                } else if (c.older < 15 & c.older >= 10) {
                    gc.setFill(Color.YELLOW);
                    gc.fillRect(c.col * cellSize + posOffset, c.row * cellSize + posOffset, cellSize - sizeOffset, cellSize - sizeOffset);
                } else if (c.older < 20 & c.older >= 15) {
                    gc.setFill(Color.RED);
                    gc.fillRect(c.col * cellSize + posOffset, c.row * cellSize + posOffset, cellSize - sizeOffset, cellSize - sizeOffset);
                } else if (isOlder && c.older >= 20) {  // Wenn CheckBox isOlderOn werden die Ältesten Zellen aussgeblendet
                    cellGrid.setCell(c.col, c.row, true);
                } else {
                    gc.setFill(Color.RED);
                    gc.fillRect(c.col * cellSize + posOffset, c.row * cellSize + posOffset, cellSize - sizeOffset, cellSize - sizeOffset);
                }// if
            }// while
        } else {
            // Standard Darstellung gesetzte Zelle werden Schwarz dargestellt
            gc.setFill(Color.BLACK);
            Enumeration enum1 = cellGrid.getEnum();
            Cell c;
            while (enum1.hasMoreElements()) {
                c = (Cell) enum1.nextElement();
                gc.fillRect(c.col * cellSize + posOffset, c.row * cellSize + posOffset, cellSize - sizeOffset, cellSize - sizeOffset);
            }// while
        }// if
    }// paint

    /**
     * Setzt das mit dem Parameter übergebene Shape in unser CellGrid
     * 
     * @param shape
     * @throws ShapeException 
     */
    public synchronized void setShape(Shape shape) throws ShapeException {
        int xOffset;
        int yOffset;
        int[][] shapeGrid;
        Dimension dimShape;
        Dimension dimGrid;
        int i;

        // Übergibt die Shape und CellGrid Properties 
        dimShape = shape.getDimension();
        dimGrid = cellGrid.getDimension();

        // Abfrage ob das gewählte Shape mit den GridCanvas passt
        if (dimShape.width > dimGrid.width || dimShape.height > dimGrid.height) {
            throw new ShapeException("Shape passt nicht ins CanvasGrid (grid: " + dimGrid.width + "x" + dimGrid.height + ", shape: " + dimShape.width + "x" + dimShape.height + ")"); // shape doesn't fit on canvas
        }// if
        
        // Zentriert unser Shape
        xOffset = (dimGrid.width - dimShape.width) / 2;
        yOffset = (dimGrid.height - dimShape.height) / 2;

        // Malt unser Shape ins cellGridd
        Enumeration cells = shape.getCells();
        while (cells.hasMoreElements()) {
            int[] cell = (int[]) cells.nextElement();
            cellGrid.setCell(xOffset + cell[0], yOffset + cell[1], true);
        }// while
    }// setShape
}// CellGridHandler
