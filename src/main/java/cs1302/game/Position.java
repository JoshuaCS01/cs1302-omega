package cs1302.game;

/**
 * This class is used to save the xPos and yPos variables
 * for later use.
 */
public class Position {
    private final double xPos;
    private final double yPos;

    /**
     * Constructs a Position object and uses the two parameters
     * to set the xPos and yPos variables inside this clas.
     * @param xPos which is the x position of this object.
     * @param yPos which is the y position of this object.
     */
    public Position(double xPos, double yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    /**
     * This method returns the x position of this object
     * that was saved here when the constructor was called.
     *
     * @return xPos which is the x position of this object.
     */
    public double getXPos() {
        return xPos;
    }

     /**
     * This method returns the y position of this object
     * that was saved here when the constructor was called.
     *
     * @return yPos which is the y position of this object.
     */
    public double getYPos() {
        return yPos;
    }

    @Override
    public String toString() {
        return "Position{" +
                "xPos=" + xPos +
                ", yPos=" + yPos +
                '}';
    }

}
