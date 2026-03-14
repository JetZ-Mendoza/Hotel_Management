package sql.hotel_management;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Controller_CustomerInformation {

    @FXML
    private AnchorPane Anchor;

    @FXML
    private TextField addON_ServiceBox;

    @FXML
    private TextField addON_priceBox;

    @FXML
    private Circle circleProfile_ID;

    @FXML
    private Button confirm_addON;

    @FXML
    private Text contactNumber_ID;

    @FXML
    private Text customerName_ID;

    @FXML
    private Rectangle customerRect_ID;

    @FXML
    private Text date_ID;

    @FXML
    private Text hour_ID;

    @FXML
    private Text indicator_ID;

    @FXML
    private ComboBox<String> modeOfPayment_ID;

    private final String[] payment = {"CASH", "GCASH", "DEBIT CARD", "CREDIT CARD", "PAYMAYA", "OTHER"};

    @FXML
    private Button paid_button;

    @FXML
    private Text room_ID;

    @FXML
    private Button view_receipt;

    public void initialize(){
        modeOfPayment_ID.getItems().addAll(payment);
        Image profile_img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/profile.png")));
        circleProfile_ID.setFill(new ImagePattern(profile_img));
        customerRect_ID.setFill(Color.web(Controller_RoomList.colorIdentifier));
        circleProfile_ID.setOnMouseClicked(event -> {
            try {
                new Switch_Scene(Anchor, "Main.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        try {
            Connection connection = Database_Booking.connect();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT Name, Check_In_Date, Contact, Email, Room, Time_In, Time_Out, Total_Time, Status FROM Booking_Information WHERE Room = ? AND Status = ?");
            preparedStatement.setString(1, Controller_RoomList.roomNumber);
            preparedStatement.setString(2, "ONGOING");
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("Name");
                String checkInDate = resultSet.getString("Check_In_Date");
                String contactNumber = resultSet.getString("Contact");
                String stay = resultSet.getString("Total_Time");
                room_ID.setText("Room: " + Controller_RoomList.roomNumber);
                customerName_ID.setText("Customer Name: " + name);
                date_ID.setText("Date in: " + checkInDate);
                contactNumber_ID.setText("Customer Contact Number: " +contactNumber);
                hour_ID.setText("Initial Number of Hour Stay: " + stay);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("No ongoing booking found for room " + Controller_RoomList.roomNumber);
                alert.showAndWait();
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to retrieve booking information: " + e.getMessage());
            alert.showAndWait();
        }


    }

    @FXML
    void addHours(ActionEvent event) {
        int number = Integer.parseInt(Controller_RoomList.roomNumber);
        int additionalHours = extraHours(number);
        try {
            String roomNumber = Controller_RoomList.roomNumber;
            Connection connection = Database_Booking.connect();

            String sql = "INSERT INTO R" + Controller_RoomList.roomNumber + " (ID, Name, Services, Price) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "");
            preparedStatement.setString(2, "");
            preparedStatement.setString(3, "Added 1 Hour");
            String priceString = String.valueOf(additionalHours);
            double price = Double.parseDouble(priceString.replace("₱", ""));

            preparedStatement.setDouble(4, price);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                indicator_ID.setText("1 Hour added for ₱" + price);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to add 1 hour to room " + roomNumber);
                alert.showAndWait();
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to add 1 hour: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void addPerson(ActionEvent event) {
        int number = Integer.parseInt(Controller_RoomList.roomNumber);
        int additionalPerson = extraPerson(number);
        try {
            String roomNumber = Controller_RoomList.roomNumber;

            Connection connection = Database_Booking.connect();

            String sql = "INSERT INTO R" + Controller_RoomList.roomNumber + " (ID, Name, Services, Price) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "");
            preparedStatement.setString(2, "");
            preparedStatement.setString(3, "Added 1 Person");
            String priceString = String.valueOf(additionalPerson);
            double price = Double.parseDouble(priceString.replace("₱", ""));

            preparedStatement.setDouble(4, price);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                indicator_ID.setText("1 Extra Person added for ₱" + price);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to add 1 extra person to room " + roomNumber);
                alert.showAndWait();
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to add 1 hour: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void confirmAddOn(ActionEvent event) {
        String add = addON_ServiceBox.getText();
        String price = addON_priceBox.getText();
        try {
            String roomNumber = Controller_RoomList.roomNumber;

            Connection connection = Database_Booking.connect();

            String sql = "INSERT INTO R" + Controller_RoomList.roomNumber + " (ID, Name, Services, Price) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "");
            preparedStatement.setString(2, "");
            preparedStatement.setString(3, add);
            preparedStatement.setDouble(4, Double.parseDouble(price));

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                indicator_ID.setText("Added " + add + " for " + price);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to add 1 extra person to room " + roomNumber);
                alert.showAndWait();
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to add 1 hour: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void mainPage(ActionEvent event) throws IOException{
        new Switch_Scene(Anchor, "Main.fxml");
    }

    @FXML
    void mainRoomList(ActionEvent event) throws IOException{
        new Switch_Scene(Anchor, "RoomList.fxml");
    }

    @FXML
    void markAsPaid(ActionEvent event) {
        String payment = modeOfPayment_ID.getValue();
        if (payment == null || payment.equals("null")){
            indicator_ID.setText("Please Enter Mode of Payment");
        }
        else
        {
            try {

                Connection connection = Database_Booking.connect();
                String sql = "UPDATE Booking_Information SET Status = 'Paid', Payment = ? WHERE Room = ? AND Status = 'ONGOING'";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, payment);
                preparedStatement.setString(2, Controller_RoomList.roomNumber);;

                int rowsUpdated = preparedStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    indicator_ID.setText("Status updated to PAID successfully! Returning you to Room List Page");
                    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
                        indicator_ID.setText("");
                        try {
                            new Switch_Scene(Anchor, "Main.fxml");
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }));
                    timeline.play();

                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText(null);
                    alert.setContentText("No ongoing booking found for room " + Controller_RoomList.roomNumber);
                    alert.showAndWait();
                }
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to update status: " + e.getMessage());
                alert.showAndWait();
            }
        }
    }

    @FXML
    void openReceiptPopup(ActionEvent event) {
        try {
            Connection connection = Database_Booking.connect();

            String sql = "SELECT Name, Services, Price FROM R" + Controller_RoomList.roomNumber;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            double totalExpense = 0;
            StringBuilder receiptMessage = new StringBuilder("Receipt for Room " + Controller_RoomList.roomNumber + ":\n\n");
            boolean startCollectingServices = false;

            while (resultSet.next()) {
                String service = resultSet.getString("Services");
                String priceString = resultSet.getString("Price");

                if (service != null && service.contains("Booked Room")) {
                    totalExpense = 0;
                    receiptMessage = new StringBuilder("Receipt for Room " + Controller_RoomList.roomNumber + ":\n\n");
                    startCollectingServices = true;
                }
                if (startCollectingServices) {
                    if (service == null) {
                        break;
                    }

                    priceString = priceString.replaceAll("[₱,]", "");

                    double price = Double.parseDouble(priceString);

                    receiptMessage.append(service).append(": ₱").append(price).append("\n");

                    totalExpense += price;
                }
            }

            receiptMessage.append("\nTotal Expense: ₱").append(totalExpense);

            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Receipt");

            Label businessDetails = new Label("MC Tolentino Hospitality Business Corp\n 9177980494 / 0270032581\n sitio_manuel@yahoo.com\n Blk 43 Lot 24 Regalado Ave. Brgy. North Fairview\n Quezon City Philippines");
            businessDetails.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            businessDetails.setTextFill(Color.DARKBLUE);
            businessDetails.setTextAlignment(TextAlignment.CENTER);
            businessDetails.setContentDisplay(ContentDisplay.TOP);

            Label receiptTitle = new Label("TOTAL INVOICE");
            receiptTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            receiptTitle.setTextFill(Color.BLACK);
            receiptTitle.setAlignment(Pos.CENTER_LEFT); 

            Label receipt = new Label(receiptMessage.toString());
            receipt.setTextFill(Color.BLACK);
            receipt.setAlignment(Pos.CENTER_LEFT); 


            VBox layout = new VBox(20);
            layout.getChildren().addAll(businessDetails, receiptTitle, receipt);
            layout.setAlignment(Pos.CENTER);
            layout.setPadding(new Insets(20));
            layout.setStyle("-fx-background-color: white; -fx-border-color: #336699; -fx-border-width: 2px;");

            Scene scene = new Scene(layout, 400, 700);
            popupStage.setScene(scene);
            popupStage.show();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to retrieve receipt: " + e.getMessage());
            alert.showAndWait();
        }




    }

    private int extraHours(int roomNumber){
        if (roomNumber == 101 || roomNumber == 102 || roomNumber == 105 || roomNumber == 106
                || roomNumber == 107 || roomNumber == 108 || roomNumber == 109 || roomNumber == 110
                || roomNumber == 111 || roomNumber == 112 || roomNumber == 114 || roomNumber == 115
                || roomNumber == 116){
            return 100;
        } else if (roomNumber == 103 || roomNumber == 104 || roomNumber == 117 || roomNumber == 307
                || roomNumber == 308 || roomNumber == 309) {
            return 150;
        } else if (roomNumber == 301 || roomNumber == 302 || roomNumber == 303 || roomNumber == 304) {
            return 200;
        } else if (roomNumber == 201 || roomNumber == 202 || roomNumber == 305 || roomNumber == 306) {
            return 250;
        }
        return roomNumber;
    }

    private int extraPerson(int roomNumber){
        if (roomNumber == 101 || roomNumber == 102 || roomNumber == 105 || roomNumber == 106
                || roomNumber == 107 || roomNumber == 108 || roomNumber == 109 || roomNumber == 110
                || roomNumber == 111 || roomNumber == 112 || roomNumber == 114 || roomNumber == 115
                || roomNumber == 116){
            return 150;
        } else if (roomNumber == 103 || roomNumber == 104 || roomNumber == 117 || roomNumber == 307
                || roomNumber == 308 || roomNumber == 309) {
            return 175;
        } else if (roomNumber == 301 || roomNumber == 302 || roomNumber == 303 || roomNumber == 304) {
            return 200;
        } else if (roomNumber == 201 || roomNumber == 202 || roomNumber == 305 || roomNumber == 306) {
            return 225;
        }
        return roomNumber;
    }

    @FXML
    void database(ActionEvent event) {
        databaseUniversal();
    }

    public static void databaseUniversal(){

        Stage stage = new Stage();
        stage.setTitle("Booking Information");

        TableView<BookingInfoRow> tableView = new TableView<>();


        TableColumn<BookingInfoRow, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<BookingInfoRow, String> servicesColumn = new TableColumn<>("Services");
        servicesColumn.setCellValueFactory(new PropertyValueFactory<>("services"));

        TableColumn<BookingInfoRow, String> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));


        tableView.getColumns().addAll(nameColumn, servicesColumn, priceColumn);

      
        List<BookingInfoRow> data = fetchDataFromDatabase();

       
        tableView.getItems().addAll(data);

     
        Scene scene = new Scene(tableView, 800, 600);
        stage.setScene(scene);
        stage.show();
    }


  
    private static List<BookingInfoRow> fetchDataFromDatabase() {
        List<BookingInfoRow> data = new ArrayList<>();
        try {
    
            Connection connection = DriverManager.getConnection(Controller_Login.url, Controller_Login.username, Controller_Login.password);

        
            String sql = "SELECT Name, Services, Price FROM R" + Controller_RoomList.roomNumber;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

           
            ResultSet resultSet = preparedStatement.executeQuery();

          
            while (resultSet.next()) {
                String name = resultSet.getString("Name");
                String services = resultSet.getString("Services");
                String price = resultSet.getString("Price");
                data.add(new BookingInfoRow(name, services, price));
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static class BookingInfoRow {
        private final String name;
        private final String services;
        private final String price;

        public BookingInfoRow(String name, String services, String price) {
            this.name = name;
            this.services = services;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public String getServices() {
            return services;
        }

        public String getPrice() {
            return price;
        }
    }

}
