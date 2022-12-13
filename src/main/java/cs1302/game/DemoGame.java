package cs1302.game;

import javafx.animation.Timeline;
import java.util.Random;
import java.util.logging.Level;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.input.KeyEvent;
import java.util.concurrent.TimeUnit;
import javafx.geometry.Bounds;
import java.lang.Thread;
import javafx.scene.shape.Rectangle;

/**
 * An example of a simple game in JavaFX. The play can move the rectangle left/right
 * with the arrow keys or teleport the rectangle by clicking it!
 */
public class DemoGame extends Game {

    private Random rng;       // random number generator
    private  playerSnake player; // some rectangle to represent the player
    private IdleCat cat;      // the not so idle cat (see IdleCat.java)

    public Direction direction = Direction.RIGHT;
    double xPos;
    double yPos;
    private final Double snakeSize = 50.;

    /**
     * Construct a {@code DemoGame} object.
     * @param width scene width
     * @param height scene height
     */
    public DemoGame(int width, int height) {
        super(width, height, 60);            // call parent constructor
        setLogLevel(Level.INFO);             // enable logging
        this.rng = new Random();             // random number generator
        this.player = new playerSnake(this); // some rectangle to represent the player
        this.cat  = new IdleCat(this);       // the not so idle cat (see IdleCat.java)
    } // DemoGame

    /** {@inheritDoc} */
    @Override
    protected void init() {
        // setup subgraph for this component
        getChildren().addAll(player, cat);         // add to main container
        // setup player
        player.setX(50);                           // 50px in the x direction (right)
        player.setY(50);                           // 50ps in the y direction (down)
        player.setOnMouseClicked(event -> handleClickPlayer(event));
        // setup the cat
        cat.setX(0);
        cat.setY(0);
    } // init

    /** {@inheritDoc} */
    @Override
    protected void update() {

        // (x, y)         In computer graphics, coordinates along an x-axis and
        // (0, 0) -x--->  y-axis are used. When compared to the standard
        // |              Cartesian plane that most students are familiar with,
        // y              the x-axis behaves the same, but the y-axis increases
        // |              in the downward direction! Keep this in mind when
        // v              adjusting the x and y positions of child nodes.


        // update player position
       // isKeyPressed( KeyCode.LEFT, () -> player.setX(player.getX() - 10.0));
       // isKeyPressed(KeyCode.RIGHT, () -> player.setX(player.getX() + 10.0));
       // isKeyPressed( KeyCode.UP, () -> player.setY(player.getY() - 10.0));
       // isKeyPressed( KeyCode.DOWN, () -> player.setY(player.getY() + 10.0));

       isKeyPressed( KeyCode.LEFT, () -> {
            //player.setX(player.getX() - 10.0);
            direction = Direction.LEFT;
            });

        isKeyPressed( KeyCode.RIGHT, () -> {
            //player.setX(player.getX() + 10.0);
            direction = Direction.RIGHT;
            });

        isKeyPressed( KeyCode.UP, () -> {
            //player.setY(player.getY() - 10.0);
            direction = Direction.UP;
            });
      
        isKeyPressed( KeyCode.DOWN, () -> {
            //player.setY(player.getY() - 10.0);
            direction = Direction.DOWN;
            });


        // <--------------------------------------------------------------------
        // try adding the code to make the player move up and down!
        // <--------------------------------------------------------------------

        // update idle cat
        cat.update();
        moveSnakeHead(player);
        
    

    } // update

    /**
     * Move the player rectangle to a random position.
     * @param event associated mouse event
     */
    private void handleClickPlayer(MouseEvent event) {
        logger.info(event.toString());
       // player.setX(rng.nextDouble() * (getWidth() - player.getWidth()));
     //   player.setY(rng.nextDouble() * (getHeight() - player.getHeight()));
    } // handleClickPlayer

    

    //Snake head is moved in the direction specified
    private void moveSnakeHead(ImageView snakeHead){
        Bounds snakeBounds = getBoundsInParent();
        Bounds gameBounds = getGameBounds();

        if (snakeBounds.getMaxX() > gameBounds.getMaxX()) {
            System.out.println("YOU FUCKING LOSE YOU DUMBASS BITCH XDDDDD");
        } else if (snakeBounds.getMinX() < gameBounds.getMinX()) {
            System.out.println("YOU FUCKING LOSE YOU DUMBASS BITCH XDDDDD");
        } // if

        
        if(direction.equals(Direction.RIGHT)){
            player.setX(player.getX() + player.dx);
            snakeHead.setTranslateX(xPos);
        } else if(direction.equals(Direction.LEFT)) {
            player.setX(player.getX() - player.dx);
            setRotate(this.getRotate() + 270);
            snakeHead.setTranslateX(xPos);
        }else if(direction.equals(Direction.UP)) {
            player.setY(player.getY() - player.dy);
            setRotate(this.getRotate() + 270);
            snakeHead.setTranslateY(yPos);
        }else if(direction.equals(Direction.DOWN)) {
            player.setY(player.getY() + player.dy);
            setRotate(this.getRotate() + 270);
            snakeHead.setTranslateY(yPos);
        }
    }

} // DemoGame
