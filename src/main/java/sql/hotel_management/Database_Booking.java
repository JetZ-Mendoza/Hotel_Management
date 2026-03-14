package sql.hotel_management;

import javafx.scene.control.Alert;
import java.sql.*;

public class Database_Booking {
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(Controller_Login.url, Controller_Login.username, Controller_Login.password);
    }


    public static void uploadData(String name, String Check_In_Date, String Contact_Number, String Email, String Room, String Time_In, String Time_Out, String Status, String Time) {
        try (Connection connection = connect()) {
            String bookingUpdate = "INSERT INTO Booking_Information (Name, Check_In_Date, Contact, Email, Room, Time_In, Time_Out, Status, Total_Time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement bookupdate = connection.prepareStatement(bookingUpdate);
            bookupdate.setString(1, name);
            bookupdate.setString(2, Check_In_Date);
            bookupdate.setString(3, Contact_Number);
            bookupdate.setString(4, Email);
            bookupdate.setString(5, Room);
            bookupdate.setString(6, Time_In);
            bookupdate.setString(7, Time_Out);
            bookupdate.setString(8, Status);
            bookupdate.setString(9, Time);


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
