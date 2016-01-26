package de.dks.gol.model;

/**
 *
 * @author David Kunschke alias dks
 * @create 20.01.2016
 * 
 */
public class Cell {
    public final short col;
    public final short row;

    public byte neighbour;
    
    public short older;

    private final int HASHFACTOR = 7000;
    /**
     * Konstruktor werden die Werte für int col und int row als Parameter übergeben
     * @param col
     * @param row 
     */
    public Cell(int col, int row) {
        this.col = (short) col;
        this.row = (short) row;
        neighbour = 0;
        older = 0;
    }// Cell
    /**
     * Gibt den boolschen Wert zurück ob das mitgebene Object
     * eine Instanz von der Klasse Cell ist
     * Methode wird in java.lang.Object überschrieben
     * @param o
     * @return 
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Cell)) {
            return false;
        }
        return col == ((Cell) o).col && row == ((Cell) o).row;
    }// equals
    /**
     * Gibt den Hashwert des Shapes zurück
     * Methode wird in java.lang.Object überschrieben
     * @return 
     */
    @Override
    public int hashCode() {
        return HASHFACTOR * row + col;
    }// hashCode
    /**
     * Gibt den Namen des Shapes zurück
     * Methode wird in java.lang.Object überschrieben
     * @return String "Zelle ( col + row ) mit X Nachbarn
     */
    @Override
    public String toString() {
        return "Zelle (" + col + ", " + row + ") mit " + neighbour + " Nachbarn" + (neighbour == 1 ? "" : "s");
    }// toString
}// 
