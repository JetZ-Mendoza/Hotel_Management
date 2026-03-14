package sql.hotel_management;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DisplayBookingInformation extends Application {

    @Override
    public void start(Stage primaryStage) {
        
        TableView<BookingInfoRow> tableView = new TableView<>();

       
        TableColumn<BookingInfoRow, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<BookingInfoRow, String> checkInDateColumn = new TableColumn<>("Check-In Date");
        checkInDateColumn.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));

        TableColumn<BookingInfoRow, String> contactColumn = new TableColumn<>("Contact");
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));

        TableColumn<BookingInfoRow, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<BookingInfoRow, String> roomColumn = new TableColumn<>("Room");
        roomColumn.setCellValueFactory(new PropertyValueFactory<>("room"));

        TableColumn<BookingInfoRow, String> timeInColumn = new TableColumn<>("Time In");
        timeInColumn.setCellValueFactory(new PropertyValueFactory<>("timeIn"));

        TableColumn<BookingInfoRow, String> timeOutColumn = new TableColumn<>("Time Out");
        timeOutColumn.setCellValueFactory(new PropertyValueFactory<>("timeOut"));

        TableColumn<BookingInfoRow, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<BookingInfoRow, String> totalTimeColumn = new TableColumn<>("Total Time");
        totalTimeColumn.setCellValueFactory(new PropertyValueFactory<>("totalTime"));

        TableColumn<BookingInfoRow, String> paymentColumn = new TableColumn<>("Payment");
        paymentColumn.setCellValueFactory(new PropertyValueFactory<>("payment"));

       
        tableView.getColumns().addAll(nameColumn, checkInDateColumn, contactColumn, emailColumn,
                roomColumn, timeInColumn, timeOutColumn, statusColumn, totalTimeColumn, paymentColumn);

        
        List<BookingInfoRow> data = fetchDataFromDatabase();

        
        tableView.getItems().addAll(data);

       
        StackPane root = new StackPane();
        root.getChildren().add(tableView);

        
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Booking Information");
        primaryStage.show();
    }

   
    private List<BookingInfoRow> fetchDataFromDatabase() {
        List<BookingInfoRow> data = new ArrayList<>();
        try {
            
            Connection connection = Database_Booking.connect();

           
            String sql = "SELECT Name, Check_In_Date, Contact, Email, Room, Time_In, Time_Out, Status, Total_Time, Payment FROM booking_information";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

           
            ResultSet resultSet = preparedStatement.executeQuery();

           
            while (resultSet.next()) {
                String name = resultSet.getString("Name");
                String checkInDate = resultSet.getString("Check_In_Date");
                String contact = resultSet.getString("Contact");
                String email = resultSet.getString("Email");
                String room = resultSet.getString("Room");
                String timeIn = resultSet.getString("Time_In");
                String timeOut = resultSet.getString("Time_Out");
                String status = resultSet.getString("Status");
                String totalTime = resultSet.getString("Total_Time");
                String payment = resultSet.getString("Payment");
                data.add(new BookingInfoRow(name, checkInDate, contact, email, room, timeIn, timeOut, status, totalTime, payment));
            }

            
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void main(String[] args) {
        launch(args);
    }

  
    public static class BookingInfoRow {
        private final String name;
        private final String checkInDate;
        private final String contact;
        private final String email;
        private final String room;
        private final String timeIn;
        private final String timeOut;
        private final String status;
        private final String totalTime;
        private final String payment;

        public BookingInfoRow(String name, String checkInDate, String contact, String email,
                              String room, String timeIn, String timeOut, String status, String totalTime,
                              String payment) {
            this.name = name;
            this.checkInDate = checkInDate;
            this.contact = contact;
            this.email = email;
            this.room = room;
            this.timeIn = timeIn;
            this.timeOut = timeOut;
            this.status = status;
            this.totalTime = totalTime;
            this.payment = payment;
        }

        
        public String getName() {
            return name;
        }

        public String getCheckInDate() {
            return checkInDate;
        }

        public String getContact() {
            return contact;
        }

        public String getEmail() {
            return email;
        }

        public String getRoom() {
            return room;
        }

        public String getTimeIn() {
            return timeIn;
        }

        public String getTimeOut() {
            return timeOut;
        }

        public String getStatus() {
            return status;
        }

        public String getTotalTime() {
            return totalTime;
        }

        public String getPayment() {
            return payment;
        }
    }
}
