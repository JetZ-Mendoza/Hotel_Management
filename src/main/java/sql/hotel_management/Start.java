package sql.hotel_management;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.io.IOException;

public class Start extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("Login.fxml"));
        AnchorPane root = fxmlLoader.load();

        
        double initialWidth = 1550; 
        double initialHeight = 850; 
        double scaleX = 1;
        double scaleY = 1.03;
        Scale scale = new Scale(scaleX, scaleY);
        root.getTransforms().add(scale);

       
        Scene scene = new Scene(root, initialWidth, initialHeight);
        stage.setScene(scene);

       
        stage.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            double newScaleX = newWidth.doubleValue() / initialWidth;
            scale.setX(newScaleX);
        });

        stage.heightProperty().addListener((obs, oldHeight, newHeight) -> {
            double newScaleY = newHeight.doubleValue() / initialHeight;
            scale.setY(newScaleY);
        });

     
        stage.setResizable(true); 
        stage.setFullScreen(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}