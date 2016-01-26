package de.dks.gol.model;

import java.awt.Dimension;
import java.util.Enumeration;

/**
 *
 * @author David Kunschke alias @dks
 * Interface mit allen gebrauchten Methoden
 */
public interface CellGrid {
    
    public boolean getCell(int col, int row);
    
    public void setCell(int col, int row, boolean cell);
    
    public Dimension getDimension();
    
    public Enumeration getEnum();
    
    public void resize(int col, int row);
    
    public void clear();
    
    public void removeCell(Cell c);
}
