package cs1302.omega;

import cs1302.game.DemoGame;
import javafx.scene.layout.BackgroundSize;
import cs1302.game.Game;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.CornerRadii;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Font;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * REPLACE WITH NON-SHOUTING DESCRIPTION OF YOUR APP.
 */
public class OmegaApp extends Application {
    static Label notice;
    static Label noticeImage1;


    public static int scores;
    static int num = 0;
    static DemoGame game;
    static Image bannerImage;
    static Background backImage;

    static Image noticeImage;
    static ImageView banner;
   
    static Background backnoticeImage;
    static BackgroundImage backnoticeFirst;
    static VBox gameWindow;
    static VBox root;
    static Scene scene;
    static BackgroundImage backimageFirst;
    static Stage test;

    /**
     * Constructs an {@code OmegaApp} object. This default (i.e., no argument)
     * constructor is executed in Step 2 of the JavaFX Application Life-Cycle.
     */
    public OmegaApp() {}

    /** {@inheritDoc} */
    @Override
    public void start(Stage stage) {
        test = stage;
        SetupScene(0);


        // setup stage
        stage.setTitle("Snake!");
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> Platform.exit());
        stage.sizeToScene();
        stage.show();

        // play the game
        game.play();
    
    } // start

    public static void Restart(int highScore){  
        System.out.println(scores + " " + highScore);  
        if (scores < highScore){
            scores = highScore;
        }  
System.out.println(scores + " " + highScore);
SetupScene(scores);
        num = 0;
    }

    static void clearScene(){
        gameWindow.getChildren().clear();
        
    }

   static  void SetupScene(int scores){
        bannerImage = new Image("file:resources/sprites/A_black_image.jpg");
        backimageFirst = new BackgroundImage(bannerImage, null, null, null, null);
        backImage = new Background(backimageFirst);
      
      

        noticeImage = new Image("file:resources/sprites/White_full.png");
        backnoticeFirst = new BackgroundImage(noticeImage, null, null, null, null);
        backnoticeImage = new Background(backnoticeFirst);

        notice = new Label("After game over press Enter to restart");
        notice.setBackground(backnoticeImage);
        notice.setPrefWidth(650);
        notice.setAlignment(Pos.CENTER);

        game = new DemoGame(600, 600);
        gameWindow = new VBox();
        gameWindow.setBackground(backImage);


        Label labelhighScore = new Label ("High Score : " + scores); 
        labelhighScore.setPrefWidth(650);
        labelhighScore.setPrefHeight(0);
        labelhighScore.setTextFill(Color.CHARTREUSE);
        labelhighScore.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
        labelhighScore.setAlignment(Pos.BOTTOM_LEFT);
        labelhighScore.setAlignment(Pos.BOTTOM_LEFT);
       
        gameWindow.getChildren().addAll(game, labelhighScore, notice);
       
        root = new VBox(gameWindow);
        scene = new Scene(root);
        test.setScene(scene);
        
        // play the game
        game.play();

    }

    





} // OmegaApp
