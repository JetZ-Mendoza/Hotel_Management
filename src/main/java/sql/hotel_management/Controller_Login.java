package sql.hotel_management;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Objects;

public class Controller_Login {
    @FXML
    private ImageView exitImage_ID;

    @FXML
    private AnchorPane Anchor;

    @FXML
    private PasswordField password_box;

    @FXML
    private Circle profileCircle;

    @FXML
    private TextField username_box;

    @FXML
    private Text wrongPassword_ID;
    
    //Removed credentials for security purposes
    public static String address = "";
    public static String port = "";
    public static String database = "";
    public static String url = "";
    public static String username = "";
    public static String password = "";

   
    public void initialize() {
        InputStream inputStream = getClass().getResourceAsStream("/images/profile.png");
        if (inputStream != null) {
            Image profile_img = new Image(inputStream);
            profileCircle.setFill(new ImagePattern(profile_img));
        } else {
            System.err.println("Failed to load profile image.");
        }
        exitImage_ID.setOnMouseClicked(event -> {
            try {
                System.exit(0);
            } catch (Exception e){
                e.printStackTrace();
            }
        });
    }


    @FXML
    void proceed(ActionEvent event) throws IOException {
        String enteredUsername = username_box.getText();
        String enteredPassword = password_box.getText();
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM UserAccount WHERE Username = ? AND Password = ?");
            preparedStatement.setString(1, enteredUsername);
            preparedStatement.setString(2, enteredPassword);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                wrongPassword_ID.setText("Connected");
                change();
            } else {
                wrongPassword_ID.setText("Incorrect Credentials");
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            wrongPassword_ID.setText("Failed to connect to database");
        }

    }

    private void change() throws IOException {
        new Switch_Scene(Anchor, "Main.fxml");
    }
}

