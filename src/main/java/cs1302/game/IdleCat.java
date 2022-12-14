package cs1302.game;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;

/**
 * A simple "sprite" of an idle apple.
 */
public class IdleCat extends ImageView {

    private Game game; // game containing this sprite
    private double dx; // change in x per update
    private double dy; // change in y per update

    /**
     * Construct an {@code IdleCat} object.
     * @param game parent game
     */
    public IdleCat(Game game) {
        super("file:resources/sprites/Apple.png"); // call parent constructor
        this.setPreserveRatio(true);
        this.setFitWidth(50);
        this.game = game;
        this.dx = 2; // each update, add 2 to x (to start)
        this.dy = 0; // each update, add 0 to y (to start)

    } // IdleCat

    /**
     * This method checks to see if the snakes head came
     * into contact with the apple. If it has then the apple
     * is spawned in a random location and true is returned.
     * If not then false is returned.
     *
     * @param player the  head of the snake.
     * @return boolean based on whether or not the
     * snakes head came into contact with the apple
     */
    public boolean update(PlayerSnake player) {
        if ((Math.abs(player.getX() - this.getX()) < 30) &&
            (Math.abs(player.getY() - this.getY()) < 30)) {
            double x =  ((Math.random() * (600 - 5)) + 5);
            double y =  ((Math.random() * (600 - 5)) + 5);
            setX(x);
            setY(y);
            return true;
        } else {
            return false;
        }

    } // update

} // IdleCat
