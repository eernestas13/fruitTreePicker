package Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;

import static javafx.application.Application.launch;

public class Main extends Application {
    public Main(){}

    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Application.fxml"));
        primaryStage.setTitle("Image Viewer");
        primaryStage.setScene(new Scene(root,500,500.0D));
        primaryStage.show();

    }
    public static void main(String[] args) {launch(args);}

}
