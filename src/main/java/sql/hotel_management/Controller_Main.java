package sql.hotel_management;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Controller_Main {
    //Removed credentials for security purposes
    public static String address = ;
    public static String port = ;
    public static String database = ;
    public static String url = ;
    public static String username = "";
    public static String password = "";

    @FXML
    private AnchorPane Anchor_1;

    @FXML
    private Rectangle backgroundImage;

    @FXML
    private ImageView exitImage_ID;

    @FXML
    private Text indicator_ID;

    @FXML
    private Circle profile_Circle;

    public void initialize(){
        Image profile_img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/profile.png")));
        profile_Circle.setFill(new ImagePattern(profile_img));
        Image background_img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/yesdalogo.jpg")));
        backgroundImage.setFill(new ImagePattern(background_img));
        exitImage_ID.setOnMouseClicked(event -> {
            try {
                exitApplication(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FXML
    void booking_Button(ActionEvent event) throws IOException{
        new Switch_Scene(Anchor_1, "Booking.fxml");
    }

    @FXML
    void roomList_Button(ActionEvent event) throws IOException{
        new Switch_Scene(Anchor_1, "RoomList.fxml");
    }

    @FXML
    void exportdata_Button(ActionEvent event) {
        exportDataToExcel();
    }

    private void exitApplication(MouseEvent event) throws IOException {
        new Switch_Scene(Anchor_1, "Login.fxml");
    }

    private void exportDataToExcel() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Excel File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));

        Stage stage = (Stage) Anchor_1.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            String excelFilePath = file.getAbsolutePath();

            try (Workbook workbook = new XSSFWorkbook()) {
                exportBookingData(workbook);
                exportRoomData(workbook);

                try (FileOutputStream fileOut = new FileOutputStream(excelFilePath)) {
                    workbook.write(fileOut);
                }
            } catch (IOException | SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to export data to Excel: " + e.getMessage());
                alert.showAndWait();
            }
        }
    }

    private void exportBookingData(Workbook workbook) throws SQLException {
        Sheet bookingSheet = workbook.createSheet("Booking Data");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(url, username, password);
            preparedStatement = connection.prepareStatement("SELECT * FROM Booking_Information");
            resultSet = preparedStatement.executeQuery();

            Row headerRow = bookingSheet.createRow(0);
            for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                String columnName = resultSet.getMetaData().getColumnName(i);
                Cell cell = headerRow.createCell(i - 1);
                cell.setCellValue(columnName);
            }

            int rowNum = 1;
            while (resultSet.next()) {
                Row row = bookingSheet.createRow(rowNum++);
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    String columnValue = resultSet.getString(i);
                    Cell cell = row.createCell(i - 1);
                    cell.setCellValue(columnValue);
                }
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        }
    }

    private void exportRoomData(Workbook workbook) throws SQLException {
        int[] roomNumbers = {101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 114, 115, 116, 117, 201, 202, 301, 302, 303, 304, 305, 306, 307, 308, 309};

        for (int roomNumber : roomNumbers) {
            String tableName = "R" + roomNumber;
            String sheetName = "Room " + roomNumber + " Data";
            Sheet roomSheet = workbook.createSheet(sheetName);
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;

            try {
                connection = DriverManager.getConnection(url, username, password);
                preparedStatement = connection.prepareStatement("SELECT * FROM " + tableName);
                resultSet = preparedStatement.executeQuery();

                Row headerRow = roomSheet.createRow(0);
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    String columnName = resultSet.getMetaData().getColumnName(i);
                    Cell cell = headerRow.createCell(i - 1);
                    cell.setCellValue(columnName);
                }

                int rowNum = 1;
                while (resultSet.next()) {
                    Row row = roomSheet.createRow(rowNum++);
                    for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                        String columnValue = resultSet.getString(i);
                        Cell cell = row.createCell(i - 1);
                        cell.setCellValue(columnValue);
                    }
                }
            } finally {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            }
        }
    }
}
