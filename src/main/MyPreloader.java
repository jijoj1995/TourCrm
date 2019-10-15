package main;

import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.InputStream;

public class MyPreloader extends Preloader {

    private static final double WIDTH = 450;
    private static final double HEIGHT = 330;

    private Stage preloaderStage;
    private Scene scene;

    private Label progress;

    public MyPreloader() {
        // Constructor is called before everything.
        System.out.println(Main.STEP() + "MyPreloader constructor called, thread: " + Thread.currentThread().getName());
    }

    @Override
    public void init() throws Exception {
        System.out.println(Main.STEP() + "MyPreloader#init (could be used to initialize preloader view), thread: " + Thread.currentThread().getName());

        // If preloader has complex UI it's initialization can be done in MyPreloader#init
        Platform.runLater(() -> {
            try {
                progress = new Label("0%");
                                    //show video loader
                Media media = new Media(this.getClass().getResource("/resource/images/logovideo.mp4").toURI().toString());


                MediaPlayer player = new MediaPlayer(media);
                MediaView view = new MediaView(player);

                Group full = new Group();
                full.getChildren().addAll(view);
                scene = new Scene (full,640,235);
                player.play();
            }
            catch (Exception e){
                            //if video loader not working show simple loader
                Label title = new Label("Welcome to Something Awesome!\nLoading, please wait...");
                title.setTextAlignment(TextAlignment.CENTER);
                progress = new Label("0%");
                Image image1=null;
                try {
                    image1 = new Image(this.getClass().getResourceAsStream("/resource/images/loader1.gif"));
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
                final ImageView selectedImage = new ImageView();
                selectedImage.setImage(image1);
                VBox root = new VBox(selectedImage);
                root.setAlignment(Pos.CENTER);

                scene = new Scene(root, WIDTH, HEIGHT);
            }
        });
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println(Main.STEP() + "MyPreloader#start (showing preloader stage), thread: " + Thread.currentThread().getName());
        primaryStage.setScene(scene);
        this.preloaderStage = primaryStage;
        // Set preloader scene and show stage.
        primaryStage.initStyle(StageStyle.UNDECORATED);
        preloaderStage.setScene(scene);
        preloaderStage.show();

    }

    @Override
    public void handleApplicationNotification(PreloaderNotification info) {
        // Handle application notification in this point (see MyApplication#init).
        if (info instanceof ProgressNotification) {
            progress.setText(((ProgressNotification) info).getProgress() + "%");
        }
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification info) {
        // Handle state change notifications.
        StateChangeNotification.Type type = info.getType();
        switch (type) {
            case BEFORE_LOAD:
                // Called after MyPreloader#start is called.
                System.out.println(Main.STEP() + "BEFORE_LOAD");
                break;
            case BEFORE_INIT:
                // Called before MyApplication#init is called.
                System.out.println(Main.STEP() + "BEFORE_INIT");
                break;
            case BEFORE_START:
                // Called after MyApplication#init and before MyApplication#start is called.
                System.out.println(Main.STEP() + "BEFORE_START");

                preloaderStage.hide();
                break;
        }
    }
}
