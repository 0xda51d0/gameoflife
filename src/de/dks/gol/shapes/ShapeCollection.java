package de.dks.gol.shapes;

import de.dks.gol.exception.ShapeException;
import java.util.Random;

/**
 *
 * @author h4ckt00r
 */
public class ShapeCollection {
    private static final Shape CLEAR;
    private static final Shape GLIDER;
    private static final Shape SMALLEXPL;
    private static final Shape EXPLODER;
    private static final Shape CELL10;
    private static final Shape FISH;
    private static final Shape PUMP;
    private static final Shape SHOOTER;
    private static final Shape[] COLLECTION;

    /**
     * Gibt ein Array aus Shapes zurück
     *
     * @return die Collection mit den einzelnen Shapes
     */
    public static Shape[] getShapes() {
        return COLLECTION;
    }

    /**
     * Gibt das Shape zurück anhand seines Namens
     *
     * @param name Name des Shapes
     * @return shape object
     * @throws ShapeException wenn es nicht diese Namen als Shape gibt
     */
    public static Shape getShapeByName(String name) throws ShapeException {
        Shape[] shapes = getShapes();
        for (Shape shape : shapes) {
            if (shape.getName().equals(name)) {
                return shape;
            }
        }
        throw (new ShapeException("Unbekanntes Figur: " + name));
    }
    
    /**
     * Auswahl an vorgefertigten Shapes
     */
    static {
        CLEAR = new Shape("Sauber", new int[][]{});
        GLIDER = new Shape("Gleiter", new int[][]{{1, 0}, {2, 1}, {2, 2}, {1, 2}, {0, 2}});
        SMALLEXPL = new Shape("Kleine Explosion", new int[][]{{0, 1}, {0, 2}, {1, 0}, {1, 1}, {1, 3}, {2, 1}, {2, 2}});
        EXPLODER = new Shape("Explosion", new int[][]{{0, 0}, {0, 1}, {0, 2}, {0, 3}, {0, 4}, {2, 0}, {2, 4}, {4, 0}, {4, 1}, {4, 2}, {4, 3}, {4, 4}});
        CELL10 = new Shape("10 Zellen auf 1 Spalte", new int[][]{{0, 0}, {1, 0}, {2, 0}, {3, 0}, {4, 0}, {5, 0}, {6, 0}, {7, 0}, {8, 0}, {9, 0}});
        FISH = new Shape("Einfaches Raumschiff", new int[][]{{0, 1}, {0, 3}, {1, 0}, {2, 0}, {3, 0}, {3, 3}, {4, 0}, {4, 1}, {4, 2}});
        PUMP = new Shape("Stehaufmännchen", new int[][]{{0, 3}, {0, 4}, {0, 5}, {1, 0}, {1, 1}, {1, 5}, {2, 0}, {2, 1}, {2, 2}, {2, 3}, {2, 4}, {4, 0}, {4, 1}, {4, 2}, {4, 3}, {4, 4}, {5, 0}, {5, 1}, {5, 5}, {6, 3}, {6, 4}, {6, 5}});
        SHOOTER = new Shape("Gleiter Fabrik", new int[][]{{0, 2}, {0, 3}, {1, 2}, {1, 3}, {8, 3}, {8, 4}, {9, 2}, {9, 4}, {10, 2}, {10, 3}, {16, 4}, {16, 5}, {16, 6}, {17, 4}, {18, 5}, {22, 1}, {22, 2}, {23, 0}, {23, 2}, {24, 0}, {24, 1}, {24, 12}, {24, 13}, {25, 12}, {25, 14}, {26, 12}, {34, 0}, {34, 1}, {35, 0}, {35, 1}, {35, 7}, {35, 8}, {35, 9}, {36, 7}, {37, 8}});
        COLLECTION = new Shape[]{CLEAR, GLIDER, SMALLEXPL, EXPLODER, CELL10, FISH, PUMP, SHOOTER};
    }
}
