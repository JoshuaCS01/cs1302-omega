package cs1302.game;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;

public class playerSnake extends ImageView {

    private Game game; // game containing this sprite
    public double dx; // change in x per update
    public double dy; // change in y per update

    public playerSnake(Game game) {
        super("file:resources/sprites/Snakehead.png"); // call parent constructon
        this.setRotate(this.getRotate() + 270); 
        this.setPreserveRatio(true);
        this.setFitWidth(35);
        this.game = game;
        this.dx = 2; // each update, add 2 to x (to start)
        this.dy = 2; // each update, add 0 to y (to start)
    } // IdleCat

    public void update() {
        Bounds catBounds = getBoundsInParent();
        Bounds gameBounds = game.getGameBounds();
        if (catBounds.getMaxX() > gameBounds.getMaxX()) {
            dx *= -.1;      // change x direction
            setScaleX(-1.0); // flip this image view horizontally
        } else if (catBounds.getMinX() < gameBounds.getMinX()) {
            dx *= -.1;      // change x direction
            setScaleX(1.0);  // flip this image view back
        } // if
        setX(getX() + dx);   // move this cat!


        
    } // update


}
