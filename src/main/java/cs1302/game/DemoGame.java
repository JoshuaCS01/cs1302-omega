package cs1302.game;

import javafx.animation.Timeline;
import java.util.Random;
import java.util.logging.Level;
import javafx.scene.control.Label;
import cs1302.omega.OmegaApp;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import javafx.geometry.Bounds;
import java.util.List;
import java.lang.Thread;
import java.nio.channels.UnresolvedAddressException;
import javafx.scene.shape.Rectangle;

/**
 * An example of a simple game in JavaFX. The play can move the rectangle left/right
 * with the arrow keys or teleport the rectangle by clicking it!
 */
public class DemoGame extends Game {

    private Random rng;       // random number generator
    private PlayerSnake player; // some rectangle to represent the player
    private IdleCat cat;      // the not so idle cat (see IdleCat.java)

    public Direction direction = Direction.RIGHT;
    double xPos;
    double yPos;
    double prevX;
    double prevY;
    private final Double snakeSize = 10.0;
    private AnchorPane anchorPane;
    private final List<Position> positions = new ArrayList<>();
    ImageView snakeTail;
    public int howManyApples;
    //List of all snake body parts
    private final ArrayList<ImageView> snakeBody = new ArrayList<>();

    /**
     * Construct a {@code DemoGame} object.
     * @param width scene width
     * @param height scene height
     */
    public DemoGame(int width, int height) {
        super(width, height, 60);            // call parent constructor
        setLogLevel(Level.INFO);             // enable logging
        this.rng = new Random();             // random number generator
        this.player = new PlayerSnake(this); // some rectangle to represent the player
        this.cat  = new IdleCat(this);
        snakeTail = new ImageView("file:resources/sprites/Body.png");
        snakeTail.setFitWidth(50);
        snakeTail.setFitHeight(50);


        // the not so idle cat (see IdleCat.java)
    } // DemoGame

    /** {@inheritDoc} */
    @Override
    protected void init() {
        // setup subgraph for this component
        snakeBody.add(player);
        snakeBody.add(snakeTail);
        snakeBody.get(1).setX(1);
        snakeBody.get(1).setY(60);
        getChildren().addAll(player, cat, snakeTail);         // add to main container

        player.setX(50);                           // 50px in the x direction (right)
        player.setY(58);                           // 50ps in the y direction (down)
        player.setOnMouseClicked(event -> handleClickPlayer(event));

        cat.setX(150);
        cat.setY(58);
        positions.add(new Position(player.getX() + xPos, player.getY() + yPos));



    } // init

    /** {@inheritDoc} */
    @Override
    protected void update() {
        isKeyPressed( KeyCode.LEFT, () -> {
            if (!(direction == Direction.LEFT) && !(direction == Direction.RIGHT)) {
                if (direction == Direction.UP) {
                    player.setRotate(player.getRotate() - 90);
                } else {
                    player.setRotate(player.getRotate() + 90);
                }
                direction = Direction.LEFT;
            }
        } );
        isKeyPressed(KeyCode.RIGHT, () -> {
            if (!(direction == Direction.RIGHT) && !(direction == Direction.LEFT)) {
                if (direction == Direction.UP) {
                    player.setRotate(player.getRotate() + 90);
                } else {
                    player.setRotate(player.getRotate() - 90);
                }
                direction = Direction.RIGHT;
            }
        } );
        isKeyPressed( KeyCode.UP, () -> {
            if (!(direction == Direction.UP) && !(direction == Direction.DOWN)) {
                if (direction == Direction.LEFT) {
                    player.setRotate(player.getRotate() + 90);
                } else {
                    player.setRotate(player.getRotate() - 90);
                }
                direction = Direction.UP;
            }
        } );
        isKeyPressed( KeyCode.DOWN, () -> {
            if (!(direction == Direction.DOWN) && !(direction == Direction.UP)) {
                if (direction == Direction.LEFT) {
                    player.setRotate(player.getRotate() - 90);
                } else {
                    player.setRotate(player.getRotate() + 90);
                }
                direction = Direction.DOWN;
            }
        } );
        if (didILose(player) == true) {
            stop();
        }
        positions.add(new Position(player.getX() + xPos, player.getY() + yPos));
        if (cat.update(player) == true) {
            addSnakeTail();
            score++;
        }
        prevX = player.getX();
        prevY = player.getY();
        moveSnakeHead(player);
        for (int i = 1; i < snakeBody.size(); i++) {
            moveSnakeTail(snakeBody.get(i),i);
        }

    } // update

    /**
     * Move the player rectangle to a random position.
     * @param event associated mouse event
     */
    private void handleClickPlayer(MouseEvent event) {
        logger.info(event.toString());

    } // handleClickPlayer


    /**
     * This method moves the snake's head in whatever direction it is facing.
     * the amount of pixels the head moves by is in the snakePlayer dx and dy
     * variables.
     * @param snakeHead The imageView of the snake head that will be moved
     */
    private void moveSnakeHead(ImageView snakeHead) {
        Bounds snakeBounds = player.getBoundsInParent();
        Bounds gameBounds = getGameBounds();

        if (direction.equals(Direction.RIGHT)) {
            player.setX(player.getX() + player.dx);
            snakeHead.setTranslateX(xPos);
        } else if (direction.equals(Direction.LEFT)) {
            player.setX(player.getX() - player.dx);
            snakeHead.setTranslateX(xPos);
        } else if (direction.equals(Direction.UP)) {
            player.setY(player.getY() - player.dy);
            snakeHead.setTranslateY(yPos);
        } else if (direction.equals(Direction.DOWN)) {
            player.setY(player.getY() + player.dy);
            snakeHead.setTranslateY(yPos);
        }
    }

    /**
     * This method moves the snakeTail parameter to one of the previous
     * locations of the head making the snake tail move like the arcade.
     *
     * @param snakeTail The part of the tail that will be moved
     * @param tailNumber the number of the tail that will be moved
     */
    private void moveSnakeTail(ImageView snakeTail, int tailNumber) {
        double yPos = positions.get(gameTicks + 1 - tailNumber + 1).getYPos() - snakeTail.getY();
        double xPos = positions.get(gameTicks + 1 - tailNumber + 1).getXPos() - snakeTail.getX();
        snakeTail.setTranslateX(xPos);
        snakeTail.setTranslateY(yPos);
    }

   /**
    * This method adds a new square to the snake when an apple is eaten.
    * Where the square is added depends on where the snake is facing.
    */
    private void addSnakeTail() {

        Image img = new Image("file:resources/sprites/Body.png");
        ImageView snakeTail = new ImageView(img);
        snakeTail.setFitWidth(50);
        snakeTail.setFitHeight(50);
        snakeBody.add(snakeTail);
        getChildren().add(snakeTail);
        if (direction == Direction.LEFT) {
            snakeTail.setX(snakeBody.get(snakeBody.size() - 2).getX() + 35);
            snakeTail.setY(snakeBody.get(snakeBody.size() - 2).getY());

        } else if (direction == Direction.RIGHT) {
            snakeTail.setX((snakeBody.get(snakeBody.size() - 2).xProperty()).intValue() - 35);
            snakeTail.setY((snakeBody.get(snakeBody.size() - 2).yProperty()).intValue());

        } else if (direction == Direction.UP) {
            snakeTail.setX(snakeBody.get(snakeBody.size() - 2).getX());
            snakeTail.setY(snakeBody.get(snakeBody.size() - 2).getY() + 35);

        } else if (direction == Direction.DOWN) {
            snakeTail.setX(snakeBody.get(snakeBody.size() - 2).getX());
            snakeTail.setY(snakeBody.get(snakeBody.size() - 2).getY() - 35);

        }

    }

    /**
     * This method calls the {@code addsnakeTail} Method.
     * @param event an event that occurs after an action has been performed.
     */
    void addBodyPart(ActionEvent event) {
        addSnakeTail();
    }

    /**
     * This method checks to see if the snake head reached out
     * of bounds.
     *
     * @param snakeHead the head of the snake
     * @return boolean value depending on if snake head
     * reached out of bounds
     */
    boolean didILose(ImageView snakeHead) {
        if (player.getX() > 600 || player.getX() < 0
            || player.getY()  < - 2 || player.getY() > 608) {

            System.out.println("Game_over");
            return true;
        } else if (snakeHitItSelf()) {
            return true;
        }
        return false;
    }

    /**
     * This method checks to see if the snake head hits
     * any part of the snake body. If it has the method returns
     * true, if not it returns false.
     * @return boolean value depending on whether snake head hit body.
     */
    public boolean snakeHitItSelf() {
        int size = positions.size() - 1;
        if (size > 2) {
            for (int i = size - snakeBody.size(); i < size; i++) {
                if (positions.get(size).getXPos() == (positions.get(i).getXPos())
                    && positions.get(size).getYPos() == (positions.get(i).getYPos())) {
                    System.out.println("Hit");
                    return true;
                }
            }
        }
        return false;
    }

} // DemoGame
