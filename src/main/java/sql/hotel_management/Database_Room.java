package sql.hotel_management;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database_Room {
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(Controller_Login.url, Controller_Login.username, Controller_Login.password);
    }


    public static void uploadData(String Name, String Services, String Price, String room) {
        try (Connection connection = connect()) {
            String bookingUpdate = "INSERT INTO R" + room +  "(ID, Name, Services, Price ) VALUES (?, ?, ?, ?)";
            PreparedStatement bookupdate = connection.prepareStatement(bookingUpdate);
            bookupdate.setString(1, room);
            bookupdate.setString(2, Name);
            bookupdate.setString(3, Services);
            bookupdate.setString(4, Price);

            int rowsAffected = bookupdate.executeUpdate();
            if (rowsAffected > 0) {
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to upload data!");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to upload data: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
