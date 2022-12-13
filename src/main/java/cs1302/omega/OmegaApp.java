package cs1302.omega;

import cs1302.game.DemoGame;
import javafx.geometry.Insets;

import javafx.scene.layout.CornerRadii;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * REPLACE WITH NON-SHOUTING DESCRIPTION OF YOUR APP.
 */
public class OmegaApp extends Application {

    /**
     * Constructs an {@code OmegaApp} object. This default (i.e., no argument)
     * constructor is executed in Step 2 of the JavaFX Application Life-Cycle.
     */
    public OmegaApp() {}

    /** {@inheritDoc} */
    @Override
    public void start(Stage stage) {

        // demonstrate how to load local asset using "file:resources/"
        Image bannerImage = new Image("file:resources/sprites/A_black_image.jpg");
        ImageView banner = new ImageView(bannerImage);
        banner.setPreserveRatio(true);
        banner.setFitWidth(640);

        // some labels to display information
        Label notice = new Label("Modify the starter code to suit your needs.");
        Label instructions
            = new Label("Move left/right with arrow keys; click rectangle to teleport.");

        // demo game provided with the starter code
        DemoGame game = new DemoGame(640, 640);
        VBox gameWindow = new VBox();
        gameWindow.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        gameWindow.getChildren().addAll(game);
        // setup scene
        VBox root = new VBox(gameWindow, notice, instructions);
        Scene scene = new Scene(root);



        // setup stage
        stage.setTitle("Snake!");
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> Platform.exit());
        stage.sizeToScene();
        stage.show();

        // play the game
        game.play();

    } // start

} // OmegaApp
