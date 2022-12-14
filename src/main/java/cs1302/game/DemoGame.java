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
    private playerSnake player; // some rectangle to represent the player
    private IdleCat cat;      // the not so idle cat (see IdleCat.java)

    public Direction direction = Direction.RIGHT;
    double xPos;
    double yPos;
    double prevX;
    double prevY;
    private final Double snakeSize = 10.0;
    private AnchorPane anchorPane;
    private final List<Position> positions = new ArrayList<>();
    ImageView snakeTail_1;
    public int howManyApples;
    //List of all snake body parts
    private final ArrayList<ImageView> snakeBody = new ArrayList<>();

    //Game ticks is how many times the snake have moved


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
        this.cat  = new IdleCat(this);
        snakeTail_1 = new ImageView("file:resources/sprites/Body.png");
        snakeTail_1.setFitWidth(50);
        snakeTail_1.setFitHeight(50);


        // the not so idle cat (see IdleCat.java)
    } // DemoGame

    /** {@inheritDoc} */
    @Override
    protected void init() {
        // setup subgraph for this component
        snakeBody.add(player);
        snakeBody.add(snakeTail_1);
        snakeBody.get(1).setX(1);
        snakeBody.get(1).setY(60);
        //snakeTail_1.setX(15);
       // snakeTail_1.setY(65);
        getChildren().addAll(player, cat, snakeTail_1);         // add to main container
        // setup player
        player.setX(50);                           // 50px in the x direction (right)
        player.setY(58);                           // 50ps in the y direction (down)
        player.setOnMouseClicked(event -> handleClickPlayer(event));
        // setup the cat
        cat.setX(150);
        cat.setY(58);
        positions.add(new Position(player.getX() + xPos, player.getY() + yPos));
      //  snakeBody.add(player);
      //  snakeBody.add(snakeTail_1);

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
        if(!(direction == Direction.LEFT) && !(direction == Direction.RIGHT)){
            //player.setX(player.getX() - 10.0);
            if(direction == Direction.UP){
            player.setRotate(player.getRotate() - 90);
            } else {
              player.setRotate(player.getRotate() + 90);
            }
            direction = Direction.LEFT;
        }
            });

        isKeyPressed(KeyCode.RIGHT, () -> {
            if(!(direction == Direction.RIGHT) && !(direction == Direction.LEFT)){
            //player.setX(player.getX() + 10.0);
            if(direction == Direction.UP){
                player.setRotate(player.getRotate() + 90);
                } else {
                  player.setRotate(player.getRotate() - 90);
                }
            direction = Direction.RIGHT;
      }  });

        isKeyPressed( KeyCode.UP, () -> {
            if(!(direction == Direction.UP) && !(direction == Direction.DOWN)){
            //player.setY(player.getY() - 10.0);
            if(direction == Direction.LEFT){
                player.setRotate(player.getRotate() + 90);
                } else {
                  player.setRotate(player.getRotate() - 90);
                }
            direction = Direction.UP;
      }  });


        isKeyPressed( KeyCode.DOWN, () -> {
            if(!(direction == Direction.DOWN) && !(direction == Direction.UP)){
            //player.setY(player.getY() - 10.0);
            if(direction == Direction.LEFT){
                player.setRotate(player.getRotate() - 90);
                } else {
                  player.setRotate(player.getRotate() + 90);
                }
            direction = Direction.DOWN;
      }  });

      if(didILose(player) == true){
        stop();
    }
        // <--------------------------------------------------------------------
        // try adding the code to make the player move up and down!
        // <--------------------------------------------------------------------

        // update idle cat
        positions.add(new Position(player.getX() + xPos, player.getY() + yPos));
        if(cat.update(player) == true) {
            addSnakeTail();
            score++;
        }
        prevX = player.getX();
        prevY = player.getY();

        moveSnakeHead(player);

        for (int i = 1; i < snakeBody.size(); i++) {
            moveSnakeTail(snakeBody.get(i),i);
        }

        if(didILose(player) == true){
            stop();
        }

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
        Bounds snakeBounds = player.getBoundsInParent();
        Bounds gameBounds = getGameBounds();

        //if (snakeBounds.getMaxX() > gameBounds.getMaxX()+ 51) {
           // System.out.println("YOU FUCKING LOSE YOU DUMBASS BITCH LOLOLOLOLOLO");
           // stop();
       // } else if (snakeBounds.getMinX() < gameBounds.getMinX()-10) {
           // System.out.println("YOU FUCKING LOSE YOU DUMBASS BITCH XDDDDD");
           // stop();



         // if


        if(direction.equals(Direction.RIGHT)){
            player.setX(player.getX() + player.dx);
            snakeHead.setTranslateX(xPos);
        } else if(direction.equals(Direction.LEFT)) {
            player.setX(player.getX() - player.dx);
            snakeHead.setTranslateX(xPos);
        }else if(direction.equals(Direction.UP)) {
            player.setY(player.getY() - player.dy);
            snakeHead.setTranslateY(yPos);
        }else if(direction.equals(Direction.DOWN)) {
            player.setY(player.getY() + player.dy);
            snakeHead.setTranslateY(yPos);
        }
    }
    private void moveSnakeTail(ImageView snakeTail, int tailNumber){
 /*
    if(tailNumber == 1){
    double yPos = prevY -5;
    double xPos = prevX + 7;


        snakeTail.setX(xPos - 10);
        snakeTail.setY(yPos + 12);
        prevY = snakeTail.getY();
        prevX = snakeTail.getX();
        System.out.println(tailNumber);
    }else{
        if(tailNumber > 1){
    double yPos = prevY -5;
    double xPos = prevX + 7;

        snakeTail.setX((snakeBody.get(tailNumber - 1)).getX()-45);
        snakeTail.setY((snakeBody.get(tailNumber - 1)).getY()-45);

      prevY = snakeTail.getY();
      prevX = snakeTail.getX();
        System.out.println(tailNumber);
        }
        }
        */
        double yPos = positions.get(gameTicks + 1 - tailNumber + 1).getYPos() - snakeTail.getY();
        double xPos = positions.get(gameTicks + 1 - tailNumber + 1).getXPos() - snakeTail.getX();
        snakeTail.setTranslateX(xPos);
        snakeTail.setTranslateY(yPos);
    }

    private void addSnakeTail(){
        //Rectangle rectangle = snakeBody.get(snakeBody.size()-1);
        Image img = new Image("file:resources/sprites/Body.png");
        ImageView snakeTail = new ImageView(img);
        snakeTail.setFitWidth(50);
        snakeTail.setFitHeight(50);
        snakeBody.add(snakeTail);
        getChildren().add(snakeTail);
        if(direction == Direction.LEFT){
           snakeTail.setX(snakeBody.get(snakeBody.size()-2).getX()+ 35);
           snakeTail.setY(snakeBody.get(snakeBody.size()-2).getY());

        } else if(direction == Direction.RIGHT){
            snakeTail.setX((snakeBody.get(snakeBody.size()-2).xProperty()).intValue() - 35);
            snakeTail.setY((snakeBody.get(snakeBody.size()-2).yProperty()).intValue());

         }else if(direction == Direction.UP){
            snakeTail.setX(snakeBody.get(snakeBody.size()-2).getX());
            snakeTail.setY(snakeBody.get(snakeBody.size()-2).getY() + 35);

         } else if(direction == Direction.DOWN){
            snakeTail.setX(snakeBody.get(snakeBody.size()-2).getX());
            snakeTail.setY(snakeBody.get(snakeBody.size()-2).getY() -35);

         }




         for(int i = 0; i < snakeBody.size(); i ++){
//System.out.println(snakeBody.get(i).getX() + " : " + snakeBody.get(i).getY());
         }

        //getChildren().add(snakeTail);
        //anchorPane.getChildren().add(snakeTail);

 //       snakeTail = new Rectangle(
 //               snakeBody.get(1).getX() + xPos + snakeSize,
//                snakeBody.get(1).getY() + yPos - 5,
   //             snakeSize,snakeSize);
 ////       snakeBody.add(snakeTail);
   //     anchorPane.getChildren().add(snakeTail);

    }

    void addBodyPart(ActionEvent event) {
        addSnakeTail();
    }

    boolean didILose(ImageView snakeHead){
        if (player.getX() > 600 || player.getX() < 0 ||player.getY()  < -2 || player.getY() > 608) {
            System.out.println("Game_over");
            return true;
        } else if(snakeHitItSelf()){
            return true;
        }
        return false;
    }
    public boolean snakeHitItSelf(){
        int size = positions.size() - 1;
        if(size > 2){
            for (int i = size - snakeBody.size(); i < size; i++) {
                if(positions.get(size).getXPos() == (positions.get(i).getXPos())
                        && positions.get(size).getYPos() == (positions.get(i).getYPos())){
                    System.out.println("Hit");
                    return true;
                }
            }
        }
        return false;
    }

} // DemoGame
