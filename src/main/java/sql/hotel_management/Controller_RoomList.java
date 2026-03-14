package sql.hotel_management;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class Controller_RoomList {

    @FXML
    private AnchorPane Anchor_2;

    @FXML
    private Rectangle DE301_ID;

    @FXML
    private Rectangle DE302_ID;

    @FXML
    private Rectangle DE303_ID;

    @FXML
    private Rectangle DE304_ID;

    @FXML
    private Rectangle SA101_ID;

    @FXML
    private Rectangle SA102_ID;

    @FXML
    private Rectangle SA105_ID;

    @FXML
    private Rectangle SA106_ID;

    @FXML
    private Rectangle SA107_ID;

    @FXML
    private Rectangle SA108_ID;

    @FXML
    private Rectangle SA109_ID;

    @FXML
    private Rectangle SA110_ID;

    @FXML
    private Rectangle SA111_ID;

    @FXML
    private Rectangle SA112_ID;

    @FXML
    private Rectangle SA114_ID;

    @FXML
    private Rectangle SA115_ID;

    @FXML
    private Rectangle SA116_ID;

    @FXML
    private Rectangle ST103_ID;

    @FXML
    private Rectangle ST104_ID;

    @FXML
    private Rectangle ST117_ID;

    @FXML
    private Rectangle ST307_ID;

    @FXML
    private Rectangle ST308_ID;

    @FXML
    private Rectangle ST309_ID;

    @FXML
    private Rectangle SU201_ID;

    @FXML
    private Rectangle SU202_ID;

    @FXML
    private Rectangle SU305_ID;

    @FXML
    private Rectangle SU306_ID;

    @FXML
    private Circle cirlceProfile_ID;

    public static String roomNumber;

    public static String colorIdentifier;

    private final Map<String, Rectangle> roomRectangles = new HashMap<>();

    private final Map<String, String> roomStatusMap = new HashMap<>();


    public void initialize(){
        SA101_ID.setFill(Color.LIMEGREEN);
        SA102_ID.setFill(Color.LIMEGREEN);
        SA105_ID.setFill(Color.LIMEGREEN);
        SA106_ID.setFill(Color.LIMEGREEN);
        SA107_ID.setFill(Color.LIMEGREEN);
        SA108_ID.setFill(Color.LIMEGREEN);
        SA109_ID.setFill(Color.LIMEGREEN);
        SA110_ID.setFill(Color.LIMEGREEN);
        SA111_ID.setFill(Color.LIMEGREEN);
        SA112_ID.setFill(Color.LIMEGREEN);
        SA114_ID.setFill(Color.LIMEGREEN);
        SA115_ID.setFill(Color.LIMEGREEN);
        SA116_ID.setFill(Color.LIMEGREEN);
        ST103_ID.setFill(Color.LIMEGREEN);
        ST104_ID.setFill(Color.LIMEGREEN);
        ST117_ID.setFill(Color.LIMEGREEN);
        ST307_ID.setFill(Color.LIMEGREEN);
        ST308_ID.setFill(Color.LIMEGREEN);
        ST309_ID.setFill(Color.LIMEGREEN);
        DE301_ID.setFill(Color.LIMEGREEN);
        DE302_ID.setFill(Color.LIMEGREEN);
        DE303_ID.setFill(Color.LIMEGREEN);
        DE304_ID.setFill(Color.LIMEGREEN);
        SU201_ID.setFill(Color.LIMEGREEN);
        SU202_ID.setFill(Color.LIMEGREEN);
        SU305_ID.setFill(Color.LIMEGREEN);
        SU306_ID.setFill(Color.LIMEGREEN);
        populateRoomRectanglesMap();
        Image circleProfile = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/profile.png")));
        cirlceProfile_ID.setFill(new ImagePattern(circleProfile));
        cirlceProfile_ID.setOnMouseClicked(event -> {
            try {
                Main(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        loadRoomStatusesFromDatabase();
    }

    private void loadRoomStatusesFromDatabase() {
        try {
            Connection connection = Database_Booking.connect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT Room, Status FROM Booking_Information WHERE Status = 'ONGOING'");


            while (resultSet.next()) {
                String roomNumber = resultSet.getString("Room");
                String status = resultSet.getString("Status");
                roomStatusMap.put(roomNumber, status);
            }

            updateRoomRectangleColors();

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateRoomRectangleColors() {
        for (Map.Entry<String, Rectangle> entry : roomRectangles.entrySet()) {
            String roomNumber = entry.getKey();
            Rectangle rectangle = entry.getValue();
            String status = roomStatusMap.getOrDefault(roomNumber, "");

            if ("ONGOING".equals(status)) {
                rectangle.setFill(Color.RED);
            } else {
                rectangle.setFill(Color.LIMEGREEN);
            }
        }
    }
    private void Main(MouseEvent event) throws IOException {
        new Switch_Scene(Anchor_2, "Main.fxml");
    }


    @FXML
    void mainPage(ActionEvent event) throws IOException{
        new Switch_Scene(Anchor_2, "Main.fxml");
    }

    @FXML
    void DE301_BUTTON(ActionEvent event) throws IOException {
        if (DE301_ID.getFill() == Color.RED) {
            roomNumber = "301";
            colorIdentifier = "#4D94FF";
            new Switch_Scene(Anchor_2, "CustomerInformation.fxml");
        } else {
            roomNumber = "301";
            Controller_CustomerInformation.databaseUniversal();
        }
    }

    @FXML
    void DE302_BUTTON(ActionEvent event) throws IOException {
        if (DE302_ID.getFill() == Color.RED) {
            roomNumber = "302";
            colorIdentifier = "#4D94FF";
            new Switch_Scene(Anchor_2, "CustomerInformation.fxml");
        } else {
            roomNumber = "302";
            Controller_CustomerInformation.databaseUniversal();
        }
    }

    @FXML
    void DE303_BUTTON(ActionEvent event) throws IOException {
        if (DE303_ID.getFill() == Color.RED) {
            roomNumber = "303";
            colorIdentifier = "#4D94FF";
            new Switch_Scene(Anchor_2, "CustomerInformation.fxml");
        } else {
            roomNumber = "303";
            Controller_CustomerInformation.databaseUniversal();
        }
    }

    @FXML
    void DE304_BUTTON(ActionEvent event) throws IOException{
        if (DE304_ID.getFill() == Color.RED) {
            roomNumber = "304";
            colorIdentifier = "#4D94FF";
            new Switch_Scene(Anchor_2, "CustomerInformation.fxml");
        } else {
            roomNumber = "304";
            Controller_CustomerInformation.databaseUniversal();
        }
    }

    @FXML
    void SA101_BUTTON(ActionEvent event) throws IOException {
        if (SA101_ID.getFill() == Color.RED) {
            roomNumber = "101";
            colorIdentifier = "#9C6D15";
            new Switch_Scene(Anchor_2, "CustomerInformation.fxml");
        }
        else {
            roomNumber = "101";
            Controller_CustomerInformation.databaseUniversal();
        }
    }

    @FXML
    void SA102_BUTTON(ActionEvent event) throws IOException {
        if (SA102_ID.getFill() == Color.RED) {
            roomNumber = "102";
            colorIdentifier = "#9C6D15";
            new Switch_Scene(Anchor_2, "CustomerInformation.fxml");
        } else {
            roomNumber = "102";
            Controller_CustomerInformation.databaseUniversal();
        }
    }

    @FXML
    void SA105_BUTTON(ActionEvent event) throws IOException {
        if (SA105_ID.getFill() == Color.RED) {
            roomNumber = "105";
            colorIdentifier = "#9C6D15";
            new Switch_Scene(Anchor_2, "CustomerInformation.fxml");
        } else {
            roomNumber = "105";
            Controller_CustomerInformation.databaseUniversal();
        }
    }

    @FXML
    void SA106_BUTTON(ActionEvent event) throws IOException {
        if (SA106_ID.getFill() == Color.RED) {
            roomNumber = "106";
            colorIdentifier = "#9C6D15";
            new Switch_Scene(Anchor_2, "CustomerInformation.fxml");
        } else {
            roomNumber = "106";
            Controller_CustomerInformation.databaseUniversal();
        }
    }

    @FXML
    void SA107_BUTTON(ActionEvent event) throws IOException {
        if (SA107_ID.getFill() == Color.RED) {
            roomNumber = "107";
            colorIdentifier = "#9C6D15";
            new Switch_Scene(Anchor_2, "CustomerInformation.fxml");
        } else {
            roomNumber = "107";
            Controller_CustomerInformation.databaseUniversal();
        }
    }

    @FXML
    void SA108_BUTTON(ActionEvent event)  throws IOException {
        if (SA108_ID.getFill() == Color.RED) {
            roomNumber = "108";
            colorIdentifier = "#9C6D15";
            new Switch_Scene(Anchor_2, "CustomerInformation.fxml");
        } else {
            roomNumber = "108";
            Controller_CustomerInformation.databaseUniversal();
        }
    }

    @FXML
    void SA109_BUTTON(ActionEvent event) throws IOException {
        if (SA109_ID.getFill() == Color.RED) {
            roomNumber = "109";
            colorIdentifier = "#9C6D15";
            new Switch_Scene(Anchor_2, "CustomerInformation.fxml");
        } else {
            roomNumber = "109";
            Controller_CustomerInformation.databaseUniversal();
        }
    }

    @FXML
    void SA110_BUTTON(ActionEvent event) throws IOException {
        if (SA110_ID.getFill() == Color.RED) {
            roomNumber = "110";
            colorIdentifier = "#9C6D15";
            new Switch_Scene(Anchor_2, "CustomerInformation.fxml");
        } else {
            roomNumber = "110";
            Controller_CustomerInformation.databaseUniversal();
        }
    }

    @FXML
    void SA111_BUTTON(ActionEvent event) throws IOException {
        if (SA111_ID.getFill() == Color.RED) {
            roomNumber = "111";
            colorIdentifier = "#9C6D15";
            new Switch_Scene(Anchor_2, "CustomerInformation.fxml");
        } else {
            roomNumber = "111";
            Controller_CustomerInformation.databaseUniversal();
        }
    }

    @FXML
    void SA112_BUTTON(ActionEvent event) throws IOException {
        if (SA112_ID.getFill() == Color.RED) {
            roomNumber = "112";
            colorIdentifier = "#9C6D15";
            new Switch_Scene(Anchor_2, "CustomerInformation.fxml");
        } else {
            roomNumber = "112";
            Controller_CustomerInformation.databaseUniversal();
        }
    }

    @FXML
    void SA114_BUTTON(ActionEvent event) throws IOException {
        if (SA114_ID.getFill() == Color.RED) {
            roomNumber = "114";
            colorIdentifier = "#9C6D15";
            new Switch_Scene(Anchor_2, "CustomerInformation.fxml");
        } else {
            roomNumber = "114";
            Controller_CustomerInformation.databaseUniversal();
        }
    }

    @FXML
    void SA115_BUTTON(ActionEvent event) throws IOException {
        if (SA115_ID.getFill() == Color.RED) {
            roomNumber = "115";
            colorIdentifier = "#9C6D15";
            new Switch_Scene(Anchor_2, "CustomerInformation.fxml");
        } else {
            roomNumber = "115";
            Controller_CustomerInformation.databaseUniversal();
        }
    }

    @FXML
    void SA116_BUTTON(ActionEvent event) throws IOException {
        if (SA116_ID.getFill() == Color.RED) {
            roomNumber = "116";
            colorIdentifier = "#9C6D15";
            new Switch_Scene(Anchor_2, "CustomerInformation.fxml");
        } else {
            roomNumber = "116";
            Controller_CustomerInformation.databaseUniversal();
        }
    }

    @FXML
    void ST103_BUTTON(ActionEvent event) throws IOException {
        if (ST103_ID.getFill() == Color.RED) {
            roomNumber = "103";
            colorIdentifier = "#255608";
            new Switch_Scene(Anchor_2, "CustomerInformation.fxml");
        } else {
            roomNumber = "103";
            Controller_CustomerInformation.databaseUniversal();
        }
    }

    @FXML
    void ST104_BUTTON(ActionEvent event) throws IOException {
        if (ST104_ID.getFill() == Color.RED) {
            roomNumber = "104";
            colorIdentifier = "#255608";
            new Switch_Scene(Anchor_2, "CustomerInformation.fxml");
        } else {
            roomNumber = "104";
            Controller_CustomerInformation.databaseUniversal();
        }
    }

    @FXML
    void ST117_BUTTON(ActionEvent event) throws IOException {
        if (ST117_ID.getFill() == Color.RED) {
            roomNumber = "117";
            colorIdentifier = "#255608";
            new Switch_Scene(Anchor_2, "CustomerInformation.fxml");
        } else {
            roomNumber = "117";
            Controller_CustomerInformation.databaseUniversal();
        }
    }

    @FXML
    void ST307_BUTTON(ActionEvent event) throws IOException {
        if (ST307_ID.getFill() == Color.RED) {
            roomNumber = "307";
            colorIdentifier = "#255608";
            new Switch_Scene(Anchor_2, "CustomerInformation.fxml");
        } else {
            roomNumber = "307";
            Controller_CustomerInformation.databaseUniversal();
        }
    }

    @FXML
    void ST308_BUTTON(ActionEvent event) throws IOException {
        if (ST308_ID.getFill() == Color.RED) {
            roomNumber = "308";
            colorIdentifier = "#255608";
            new Switch_Scene(Anchor_2, "CustomerInformation.fxml");
        } else {
            roomNumber = "308";
            Controller_CustomerInformation.databaseUniversal();
        }
    }

    @FXML
    void ST309_BUTTON(ActionEvent event) throws IOException {
        if (ST309_ID.getFill() == Color.RED) {
            roomNumber = "309";
            colorIdentifier = "#255608";
            new Switch_Scene(Anchor_2, "CustomerInformation.fxml");
        } else {
            roomNumber = "309";
            Controller_CustomerInformation.databaseUniversal();
        }
    }

    @FXML
    void SU201_BUTTON(ActionEvent event) throws IOException {
        if (SU201_ID.getFill() == Color.RED) {
            roomNumber = "201";
            colorIdentifier = "#4200FF";
            new Switch_Scene(Anchor_2, "CustomerInformation.fxml");
        } else {
            roomNumber = "201";
            Controller_CustomerInformation.databaseUniversal();
        }
    }

    @FXML
    void SU202_BUTTON(ActionEvent event) throws IOException {
        if (SU202_ID.getFill() == Color.RED) {
            roomNumber = "202";
            colorIdentifier = "#4200FF";
            new Switch_Scene(Anchor_2, "CustomerInformation.fxml");
        } else {
            roomNumber = "202";
            Controller_CustomerInformation.databaseUniversal();
        }
    }

    @FXML
    void SU305_BUTTON(ActionEvent event) throws IOException{
        if (SU305_ID.getFill() == Color.RED) {
            roomNumber = "305";
            colorIdentifier = "#4200FF";
            new Switch_Scene(Anchor_2, "CustomerInformation.fxml");
        } else {
            roomNumber = "305";
            Controller_CustomerInformation.databaseUniversal();
        }
    }

    @FXML
    void SU306_BUTTON(ActionEvent event) throws IOException {
        if (SU306_ID.getFill() == Color.RED) {
            roomNumber = "306";
            colorIdentifier = "#4200FF";
            new Switch_Scene(Anchor_2, "CustomerInformation.fxml");
        } else {
            roomNumber = "306";
            Controller_CustomerInformation.databaseUniversal();
        }
    }

    private void populateRoomRectanglesMap() {
        roomRectangles.put("101", SA101_ID);
        roomRectangles.put("102", SA102_ID);
        roomRectangles.put("105", SA105_ID);
        roomRectangles.put("106", SA106_ID);
        roomRectangles.put("107", SA107_ID);
        roomRectangles.put("108", SA108_ID);
        roomRectangles.put("109", SA109_ID);
        roomRectangles.put("110", SA110_ID);
        roomRectangles.put("111", SA111_ID);
        roomRectangles.put("112", SA112_ID);
        roomRectangles.put("114", SA114_ID);
        roomRectangles.put("115", SA115_ID);
        roomRectangles.put("116", SA116_ID);
        roomRectangles.put("103", ST103_ID);
        roomRectangles.put("104", ST104_ID);
        roomRectangles.put("117", ST117_ID);
        roomRectangles.put("307", ST307_ID);
        roomRectangles.put("308", ST308_ID);
        roomRectangles.put("309", ST309_ID);
        roomRectangles.put("301", DE301_ID);
        roomRectangles.put("302", DE302_ID);
        roomRectangles.put("303", DE303_ID);
        roomRectangles.put("304", DE304_ID);
        roomRectangles.put("201", SU201_ID);
        roomRectangles.put("202", SU202_ID);
        roomRectangles.put("305", SU305_ID);
        roomRectangles.put("306", SU306_ID);

    }

    @FXML
    void database(ActionEvent event) {

        Stage stage = new Stage();
        stage.setTitle("Booking Information");


        TableView<Controller_RoomList.BookingInfoRow> tableView = new TableView<>();


        TableColumn<Controller_RoomList.BookingInfoRow, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Controller_RoomList.BookingInfoRow, String> checkInDateColumn = new TableColumn<>("Check-In Date");
        checkInDateColumn.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));

        TableColumn<Controller_RoomList.BookingInfoRow, String> contactColumn = new TableColumn<>("Contact");
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));

        TableColumn<Controller_RoomList.BookingInfoRow, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Controller_RoomList.BookingInfoRow, String> roomColumn = new TableColumn<>("Room");
        roomColumn.setCellValueFactory(new PropertyValueFactory<>("room"));

        TableColumn<Controller_RoomList.BookingInfoRow, String> timeInColumn = new TableColumn<>("Time In");
        timeInColumn.setCellValueFactory(new PropertyValueFactory<>("timeIn"));

        TableColumn<Controller_RoomList.BookingInfoRow, String> timeOutColumn = new TableColumn<>("Time Out");
        timeOutColumn.setCellValueFactory(new PropertyValueFactory<>("timeOut"));

        TableColumn<Controller_RoomList.BookingInfoRow, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<Controller_RoomList.BookingInfoRow, String> totalTimeColumn = new TableColumn<>("Total Time");
        totalTimeColumn.setCellValueFactory(new PropertyValueFactory<>("totalTime"));

        TableColumn<Controller_RoomList.BookingInfoRow, String> paymentColumn = new TableColumn<>("Payment");
        paymentColumn.setCellValueFactory(new PropertyValueFactory<>("payment"));


        tableView.getColumns().addAll(nameColumn, checkInDateColumn, contactColumn, emailColumn,
                roomColumn, timeInColumn, timeOutColumn, statusColumn, totalTimeColumn, paymentColumn);


        List<Controller_RoomList.BookingInfoRow> data = fetchDataFromDatabase();


        tableView.getItems().addAll(data);

        Scene scene = new Scene(tableView, 800, 600);
        stage.setScene(scene);
        stage.show();
    }




    private List<Controller_RoomList.BookingInfoRow> fetchDataFromDatabase() {
        List<Controller_RoomList.BookingInfoRow> data = new ArrayList<>();
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
                data.add(new Controller_RoomList.BookingInfoRow(name, checkInDate, contact, email, room, timeIn, timeOut, status, totalTime, payment));
            }


            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public class BookingInfoRow {
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

