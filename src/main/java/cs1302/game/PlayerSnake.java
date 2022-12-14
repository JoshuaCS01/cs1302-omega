package cs1302.game;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;

/**
 * A simple "sprite" of a snake head.
 */
public class PlayerSnake extends ImageView {

    private Game game; // game containing this sprite
    public double dx; // change in x per update
    public double dy; // change in y per update

    /**
     * This constructor creates a snake head object and
     * sets the dx and dy values to 50 which determine
     * how far the snake head moves per movement.
     * @param game
     */
    public PlayerSnake(Game game) {
        super("file:resources/sprites/Snakehead.png"); // call parent constructon
        this.setRotate(this.getRotate() + 270);
        this.setPreserveRatio(true);
        this.setFitWidth(50);
        this.game = game;
        this.dx = 50; // each update, add 2 to x (to start)
        this.dy = 50; // each update, add 0 to y (to start)
    }


}
