package de.dks.gol.model;

import java.awt.Dimension;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author David Kunschke alias @dks
 */
public class SimulationGrid implements CellGrid {

    private int cellRows;
    private int cellCols;
    private int generations;

    private final ConcurrentHashMap currentShape;
    private final ConcurrentHashMap nextShape;

    private Cell[][] grid;
    /**
     * Konstruktor werden die Zellzeilen und Spalten als Parameter mitgebenen
     * @param cellCols
     * @param cellRows 
     */
    public SimulationGrid(int cellCols, int cellRows) {
        this.cellCols = cellCols;
        this.cellRows = cellRows;
        currentShape = new ConcurrentHashMap();
        nextShape = new ConcurrentHashMap();

        grid = new Cell[cellCols][cellRows];
        for (int c = 0; c < cellCols; c++) {
            for (int r = 0; r < cellRows; r++) {
                grid[c][r] = new Cell(c, r);
            }// for
        }// for
    }// SimulationGrid
    /**
     * Unser Herz der Aufgabe in dieser Methode werden die Berechnung, Regeln
     * bearbeitet!!!
     */
    public synchronized void next() {
        Cell cell;
        int col, row;
        Enumeration enum1;

        generations++;
        nextShape.clear();

        // Setz Zellen zurück
        enum1 = currentShape.keys();
        while (enum1.hasMoreElements()) {
            cell = (Cell) enum1.nextElement();
            cell.neighbour = 0; // Nachbarn werden jede Runde zurückgesetzt
            cell.older += 1;    // Lebt die Zelle wird jede Runde 1 Jahr hinzugefügt
        }// 
        
        // Nachbarn der Zellen werden ermittelt
        enum1 = currentShape.keys();
        while (enum1.hasMoreElements()) {
            cell = (Cell) enum1.nextElement();
            col = cell.col;
            row = cell.row;

            int mx = col - 1;
            if (mx < 0) {
                mx = cellCols - 1;
            }// 
            int my = row - 1;
            if (my < 0) {
                my = cellRows - 1;
            }// 

            int gx = (col + 1) % cellCols;
            int gy = (row + 1) % cellRows;

            addNeighbour(mx, my);
            addNeighbour(col, my);
            addNeighbour(gx, my);
            addNeighbour(mx, row);
            addNeighbour(gx, row);
            addNeighbour(mx, gy);
            addNeighbour(col, gy);
            addNeighbour(gx, gy);
        }// 

        /**
         * REGEL
         * 
         * Da wir eigentlich 4 Regel haben 
         * 1) Jede lebende Zelle, die weniger als 2 lebende Nachbarzellen hat, stirbt
         * 2) Jede lebende Zelle, die 2 oder 3 lebendige Nachbarzellen hat, lebt weiter
         * 3) Jede lebende Zelle, die mehr als 3 lebende Nachbarzellen hat, stirbt
         * 4) Jede tote Zelle, die genau 3 lebende Nachbarzellen hat, wird lebendig
         * 
         * Wir kürzen unsere Regeln da wir nicht prüfen müssen Regel 2 weil wenn wir 3 lebende Zellen haben wird diese lebendig
         * laut Regel 4. Somit ist und bleibt der Zustand von 2 Nachbarzellen immer gleich. Regel 2 fällt weg..
         * 
         * Abfarge 1: Wenn ungleich 2 und ungleich 3 dann stirbt unsere Zelle >> REGEL 1.
         * Abfrage 2: Wenn genau 3 lebende Zellen, dann erwacht unser Zelle >> REGEL 2.
         */
        
        /**
         * ABFRAGE 1:
         */
        enum1 = currentShape.keys();
        while (enum1.hasMoreElements()) {
            cell = (Cell) enum1.nextElement();
            if (cell.neighbour != 3 && cell.neighbour != 2) {
                cell.older = 0;
                currentShape.remove(cell);
            }// 
        }// 
        
        /**
         * Abfrage 2:
         */
        enum1 = nextShape.keys();
        while (enum1.hasMoreElements()) {
            cell = (Cell) enum1.nextElement();
            if (cell.neighbour == 3) {
                setCell(cell.col, cell.row, true);
            }// 
        }// 
    }// 
    /**
     * Setzt die Zelle als Nachbarn
     * @param col
     * @param row 
     */
    public synchronized void addNeighbour(int col, int row) {
        try {
            Cell cell = (Cell) nextShape.get(grid[col][row]);
            if (cell == null) {
                // Zelle ist nicht in der HashMap erstelle eine 
                Cell c = grid[col][row];
                c.neighbour = 1;
                nextShape.put(c, c);
            } else {
                // Inkrement Nachbar
                cell.neighbour++;
            }// if
        } catch (ArrayIndexOutOfBoundsException e) {
            // ignore
        }// try-catch
    }// addNeighbour
    /**
     * Gibt boolschen Wert zurück ob die Zelle existiert mit den 
     * übergebenen Parametern als Koordinaten
     * @param col
     * @param row
     * @return 
     */
    @Override
    public boolean getCell(int col, int row) {
        try {
            return currentShape.containsKey(grid[col][row]);
        } catch (ArrayIndexOutOfBoundsException e) {
            // ignore
        }// try-catch
        return false;
    }// getCell
    /**
     * Setzt unser Zelle
     * @param col Zeile
     * @param row Spalte
     * @param isCreate Erstellen/Löschen
     */
    @Override
    public void setCell(int col, int row, boolean isCreate) {
        try {
            Cell cell = grid[col][row];
            if (isCreate) {
                currentShape.put(cell, cell);
            } else {
                currentShape.remove(cell);
            }// 
        } catch (ArrayIndexOutOfBoundsException e) {
            // ignore
        }// try-catch
    }// setCell
    
    /**
     * Gibt die Dimension der Zellzeilen und Spalten zurück
     * @return 
     */
    @Override
    public Dimension getDimension() {
        return new Dimension(cellCols, cellRows);
    }// getDimension

    /**
     * Gibt die Shape als Enumeration zurück
     * @return 
     */
    @Override
    public Enumeration getEnum() {
        return currentShape.keys();
    }// getEnum

    /**
     * Vergrößerung oder verkleinerung der Zellen
     * @param cellColsNew
     * @param cellRowsNew 
     */
    @Override
    public void resize(int cellColsNew, int cellRowsNew) {
        if (cellCols == cellColsNew && cellRows == cellRowsNew) {
            return; // Keine vergrößerung oder verkleinerung
        }// if
        
        // Erstellt ein neues Grid und übergibt die bestehenden Zellen
        Cell[][] gridNew = new Cell[cellColsNew][cellRowsNew];
        for (int c = 0; c < cellColsNew; c++) {
            for (int r = 0; r < cellRowsNew; r++) {
                if (c < cellCols && r < cellRows) {
                    gridNew[c][r] = grid[c][r];
                } else {
                    gridNew[c][r] = new Cell(c, r);
                }// if
            }// for
        }// for

        // Kopiert existierendes Shape 
        Cell cell;
        Enumeration enum1;
        nextShape.clear();
        enum1 = currentShape.keys();
        while (enum1.hasMoreElements()) {
            cell = (Cell) enum1.nextElement();
            int colNew = cell.col;
            int rowNew = cell.row;
            try {
                nextShape.put(gridNew[colNew][rowNew], gridNew[colNew][rowNew]);
            } catch (ArrayIndexOutOfBoundsException e) {
                // nicht benötigt
            }// try-catch
        }// while

        // Kopiert ein neues Grid und HashMap zum bestehenden Grid und HashMap
        grid = gridNew;
        currentShape.clear();
        enum1 = nextShape.keys();
        while (enum1.hasMoreElements()) {
            cell = (Cell) enum1.nextElement();
            currentShape.put(cell, cell);
        }// while

        cellCols = cellColsNew;
        cellRows = cellRowsNew;
    }// 
    
    /**
     * Löscht das Grid
     * Überschreibt java.lang.Object
     */
    @Override
    public void clear() {
        generations = 0;
        currentShape.clear();
        nextShape.clear();
    }// clear
    
    /**
     * Löscht ein Zelle
     * Überschreibt java.lang.Object
     * @param c 
     */
    @Override
    public void removeCell(Cell c){
        currentShape.remove(c);
    }// removeCell

}// SimulationGrid
