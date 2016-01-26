package de.dks.gol.exception;

/**
 *
 * @author David Kunschke
 * @create 20.01.2016 17:16
 */
public class ShapeException extends Exception {

    /**
     * Konstruiert eine ShapeException
     */
    public ShapeException() {
        super();
    }

    /**
     * Konstruiert eine ShapeException mit Beschreibung
     * @param s "Beschreibung"
     */
    public ShapeException(String s) {
        super(s);
    }
}
