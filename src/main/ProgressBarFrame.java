package main;
        import javafx.application.Application;
        import javafx.geometry.Pos;
        import javafx.scene.Group;
        import javafx.scene.Scene;
        import javafx.scene.control.ChoiceDialog;
        import javafx.scene.control.Label;
        import javafx.scene.control.ProgressBar;
        import javafx.scene.image.Image;
        import javafx.scene.image.ImageView;
        import javafx.scene.layout.VBox;
        import javafx.scene.text.TextAlignment;
        import javafx.stage.Stage;
        import javafx.stage.StageStyle;

public class ProgressBarFrame extends Application

{
    private static final double WIDTH = 350;
    private static final double HEIGHT = 130;
    private boolean              isVisible        = false;

    Scene scene;
    Stage stage;
    public ProgressBarFrame()
    {


    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage=primaryStage;
        Label title = new Label("Welcome to Something Awesome!\nLoading, please wait...");
        title.setTextAlignment(TextAlignment.CENTER);
        Image image1=null;
        try {
            image1 = new Image(this.getClass().getResourceAsStream("/resource/images/loader1.gif"));
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        Label label=new Label("Loading please wait");
        final ImageView selectedImage = new ImageView();
        selectedImage.setImage(image1);
        VBox root = new VBox(selectedImage);
        root.setAlignment(Pos.CENTER);
        scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void showProgressBar()
    {

        isVisible=true;
    }

    public void hideProgressBar()
    {
        stage.close();
        isVisible = false;
    }

    public boolean isPBVisible()
    {
        return isVisible;
    }


}
