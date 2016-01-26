package de.dks.gol.shapes;

import java.awt.Dimension;
import java.util.Enumeration;

/**
 *
 * @author David Kunschke alias @dks
 * @create 20.01.2016 17:17
 */
public class Shape {
    
    private final String name;
    private final int[][] shape;

    /**
     * Konstruktor von Shape bekommt den Namen als String und der Aufbau als 2Dimensionales int Array mitgegeben
     * @param name
     * @param shape 
     */
    public Shape(String name, int[][] shape) {
        this.name = name;
        this.shape = shape;
    }// Shape
    /**
     * Gibt die Größe des Shape als Dimension zurück.
     * @return 
     */
    public Dimension getDimension() {
        int shapeWidth = 0;
        int shapeHeight = 0;
        for (int[] shape1 : shape) {
            if (shape1[0] > shapeWidth) {
                shapeWidth = shape1[0];
            }
            if (shape1[1] > shapeHeight) {
                shapeHeight = shape1[1];
            }
        }
        shapeWidth++;
        shapeHeight++;
        return new Dimension(shapeWidth, shapeHeight);
    }// getDimension
    /**
     * Getter gibt den Name des Shapes zurück
     * @return 
     */
    public String getName() {
        return name;
    }// getName
    /**
     * Gibt die Zellen als Enumeration wieder
     * Und überschreibt @hasMoreElements() und nextElement()
     * @return 
     */
    public Enumeration getCells() {
        return new Enumeration() {
            private int index = 0;

            @Override
            public boolean hasMoreElements() {
                return index < shape.length;
            }

            @Override
            public Object nextElement() {
                return shape[index++];
            }
        };
    }// getCells
    /**
     * Größe Name des Shapes wird von java.lang.Object überschrieben
     * @return 
     */
    @Override
    public String toString() {
        return name + " (" + shape.length + " Zellen " + (shape.length == 1 ? "" : "s") + ")";
    }// toString
    /**
     * Kleine Name von dem Shape
     * @return name
     */    
    public String toStringSmall() {
        return name;
    }// toStringSmall
}
