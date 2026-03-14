package sql.hotel_management;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Controller_Booking {

    @FXML
    private AnchorPane CustomerInfo_Anchorpane;

    @FXML
    private TextField contactNumber_ID;

    @FXML
    private TextField customerName_ID;

    @FXML
    private DatePicker datePicker_ID;

    @FXML
    private TextField email_ID;

    @FXML
    private Text indicator_ID;

    @FXML
    private ComboBox<String> jestaHereBrotha_IN;

    @FXML
    private ComboBox<String> jestaHereBrotha_OUT;

    @FXML
    private Rectangle left_image;

    @FXML
    private ImageView proceedButton_ID;

    @FXML
    private Circle profileCircle_ID;

    @FXML
    private Rectangle right_image;

    @FXML
    private ComboBox<String> roomDawJet;

    @FXML
    private ComboBox<String> roomNumber_IDENTIFICATION;

    String dayOfWeekString;
    String formattedDate;

    private final String[] time = {
            "12:00 AM", "1:00 AM", "2:00 AM", "3:00 AM", "4:00 AM", "5:00 AM", "6:00 AM", "7:00 AM",
            "8:00 AM", "9:00 AM", "10:00 AM", "11:00 AM", "12:00 PM", "1:00 PM", "2:00 PM", "3:00 PM",
            "4:00 PM", "5:00 PM", "6:00 PM", "7:00 PM", "8:00 PM", "9:00 PM", "10:00 PM", "11:00 PM"
    };

    private final String[] room_type = {"Savers", "Standard", "Deluxe", "Superior"};
    private final String[] room_number_saver = {"101", "102", "105", "106", "107", "108", "109", "110", "111", "112", "114", "115", "116"};
    private final String[] room_number_standard = {"103", "104", "117", "307", "308", "309"};
    private final String[] room_number_deluxe = {"301", "302", "303", "304"};
    private final String[] room_number_superior = {"201", "202", "305", "306"};
    private boolean roomTypeSelected = false;

    public void initialize() {
        Image leftImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/433255915_263629616718396_6311808052561959757_n.jpg")));
        left_image.setFill(new ImagePattern(leftImage));
        Image rightImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/427770546_211256848745826_8486237434655466547_n.png")));
        right_image.setFill(new ImagePattern(rightImage));
        Image profile_Image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/profile.png")));
        profileCircle_ID.setFill(new ImagePattern(profile_Image));
        profileCircle_ID.setOnMouseClicked(event -> {
            try {
                new Switch_Scene(CustomerInfo_Anchorpane, "Main.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        proceedButton_ID.setOnMouseClicked(event -> {
            try {
                book();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        roomDawJet.setOnAction(event -> {
            if (!roomTypeSelected) {
                String selected = roomDawJet.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    switch (selected) {
                        case "Savers":
                            roomNumber_IDENTIFICATION.getItems().setAll(room_number_saver);
                            break;
                        case "Standard":
                            roomNumber_IDENTIFICATION.getItems().setAll(room_number_standard);
                            break;
                        case "Deluxe":
                            roomNumber_IDENTIFICATION.getItems().setAll(room_number_deluxe);
                            break;
                        case "Superior":
                            roomNumber_IDENTIFICATION.getItems().setAll(room_number_superior);
                            break;
                        default:
                            roomNumber_IDENTIFICATION.getItems().clear();
                            break;
                    }
                    roomNumber_IDENTIFICATION.show();
                    roomTypeSelected = true;
                    roomDawJet.toBack();
                }
            }
        });

        roomNumber_IDENTIFICATION.setOnMouseEntered(event -> {
            roomDawJet.toFront();
        });

        roomDawJet.setOnMouseExited(event -> {
            roomDawJet.toBack();
        });

        roomNumber_IDENTIFICATION.setOnAction(event -> {
            roomTypeSelected = false;
        });

        roomDawJet.getItems().addAll(room_type);

        jestaHereBrotha_IN.getItems().addAll(time);
        jestaHereBrotha_OUT.getItems().addAll(time);
    }

    @FXML
    void getDate(ActionEvent event) {
        LocalDate myDate = datePicker_ID.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG);
        formattedDate = myDate.format(formatter);
        DayOfWeek dayOfWeek = myDate.getDayOfWeek();
        dayOfWeekString = dayOfWeek.toString();
    }

    @FXML
    void mainPage(ActionEvent event) throws IOException {
        new Switch_Scene(CustomerInfo_Anchorpane, "Main.fxml");
    }

    public void book() throws IOException {
        String name = customerName_ID.getText();
        String date = formattedDate;
        String contact = contactNumber_ID.getText();
        String email = email_ID.getText();
        String room = roomNumber_IDENTIFICATION.getValue();
        String timeIn = jestaHereBrotha_IN.getValue();
        String timeOut = jestaHereBrotha_OUT.getValue();
        String status = "ONGOING";

        try {
            int timeTotal = calculateHoursWorked(timeIn, timeOut);
            if (customerName_ID == null || formattedDate == null || contactNumber_ID == null || room == null
                    || timeIn == null || timeOut == null) {
                indicator_ID.setText("Fill out all of the information");
            } else if (timeTotal == 3 || timeTotal == 8 || timeTotal == 12 || timeTotal == 24) {
                int roomNumber = Integer.parseInt(room);
                String rated = Rates(roomNumber, timeTotal + " Hours");

               
                if (isRoomOccupied(room) || isRoomStatusOngoing(room)) {
                    indicator_ID.setText("Room " + room + " is already occupied or has an ongoing booking.");
                } else if (isRoomTimeInterfered(room, date, timeIn, timeOut)) {
                    indicator_ID.setText("There is a time interference for Room " + room + " on " + formattedDate);
                } else {
                   
                    Database_Booking.uploadData(name, date, contact, email, room, timeIn, timeOut, status, timeTotal + " Hours");
                    Database_Room.uploadData(name, "Booked Room", rated, room);
                    new Switch_Scene(CustomerInfo_Anchorpane, "RoomList.fxml");
                }
            } else {
                indicator_ID.setText("Time is set to: " + timeTotal + " Hours. Please follow the 3, 8, 12, 24 hour timeline");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private boolean isRoomStatusOngoing(String roomNumber) {
        try {
            Connection connection = Database_Booking.connect();
            String sql = "SELECT * FROM Booking_Information WHERE Room = ? AND Status = 'ONGOING'";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, roomNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isRoomTimeInterfered(String roomNumber, String date, String startTime, String endTime) {
        try {
            Connection connection = Database_Booking.connect();
            String sql = "SELECT Time_In, Time_Out FROM Booking_Information WHERE Room = ? AND Check_In_Date = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, roomNumber);
            preparedStatement.setString(2, date);
            ResultSet resultSet = preparedStatement.executeQuery();

           
            while (resultSet.next()) {
                String bookedStartTime = resultSet.getString("Time_In");
                String bookedEndTime = resultSet.getString("Time_Out");
                if (isTimeOverlap(startTime, endTime, bookedStartTime, bookedEndTime) ||
                        isTimeOverlap24Hours(startTime, endTime, bookedStartTime, bookedEndTime)) {
                    return true; 
                }
            }
            return false; 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isTimeOverlap24Hours(String startTime, String endTime, String bookedStartTime, String bookedEndTime) {
        return startTime.compareTo(bookedStartTime) <= 0 && endTime.compareTo(bookedEndTime) >= 0;
    }


    private boolean isTimeOverlap(String startTime, String endTime, String bookedStartTime, String bookedEndTime) {
        LocalTime start = parseTime(startTime);
        LocalTime end = parseTime(endTime);
        LocalTime bookedStart = parseTime(bookedStartTime);
        LocalTime bookedEnd = parseTime(bookedEndTime);

        return (start.isBefore(bookedEnd) && end.isAfter(bookedStart)) ||
                (start.isAfter(bookedEnd) && end.isBefore(bookedStart));
    }

    private LocalTime parseTime(String timeStr) {
        String[] parts = timeStr.split(":");
        int hours = Integer.parseInt(parts[0]);
        String ampm = parts[1].split(" ")[1];
        if (ampm.equals("PM") && hours != 12) {
            hours += 12;
        } else if (ampm.equals("AM") && hours == 12) {
            hours = 0;
        }
        return LocalTime.of(hours, Integer.parseInt(parts[1].split(" ")[0]));
    }

    private boolean isRoomOccupied(String roomNumber) {
        try {
            Connection connection = Database_Booking.connect();
            String sql = "SELECT * FROM Booking_Information WHERE Room = ? AND Status = 'ONGOING'";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, roomNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int calculateHoursWorked(String timeIn, String timeOut) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
        Date dateIn = format.parse(timeIn);
        Date dateOut = format.parse(timeOut);

        if (dateOut.compareTo(dateIn) <= 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateOut);
            calendar.add(Calendar.DATE, 1);
            dateOut = calendar.getTime();
        }

        long difference = dateOut.getTime() - dateIn.getTime();
        return (int) (difference / (60 * 60 * 1000));
    }

    public String Rates(int roomNumber, String hour) {
        if (roomNumber == 101 || roomNumber == 102 || roomNumber == 105 || roomNumber == 106
                || roomNumber == 107 || roomNumber == 108 || roomNumber == 109 || roomNumber == 110
                || roomNumber == 111 || roomNumber == 112 || roomNumber == 114 || roomNumber == 115
                || roomNumber == 116) {
            if (hour.equals("3 Hours")) {
                return "₱349";
            } else if (hour.equals("8 Hours")) {
                return "₱449";
            } else if (hour.equals("12 Hours")) {
                if (dayOfWeekString.equals("MONDAY") || dayOfWeekString.equals("TUESDAY") || dayOfWeekString.equals("WEDNESDAY") ||
                        dayOfWeekString.equals("THURSDAY")) {
                    return "₱499";
                } else {
                    return "₱549";
                }
            } else if (hour.equals("24 Hours")) {
                if (dayOfWeekString.equals("MONDAY") || dayOfWeekString.equals("TUESDAY") || dayOfWeekString.equals("WEDNESDAY") ||
                        dayOfWeekString.equals("THURSDAY")) {
                    return "₱899";
                } else {
                    return "₱949";
                }
            }
        }
        if (roomNumber == 103 || roomNumber == 104 || roomNumber == 117 || roomNumber == 307
                || roomNumber == 308 || roomNumber == 309) {
            if (hour.equals("3 Hours")) {
                return "₱399";
            } else if (hour.equals("8 Hours")) {
                return "₱499";
            } else if (hour.equals("12 Hours")) {
                if (dayOfWeekString.equals("MONDAY") || dayOfWeekString.equals("TUESDAY") || dayOfWeekString.equals("WEDNESDAY") ||
                        dayOfWeekString.equals("THURSDAY")) {
                    return "₱549";
                } else {
                    return "₱649";
                }
            } else if (hour.equals("24 Hours")) {
                if (dayOfWeekString.equals("MONDAY") || dayOfWeekString.equals("TUESDAY") || dayOfWeekString.equals("WEDNESDAY") ||
                        dayOfWeekString.equals("THURSDAY")) {
                    return "₱949";
                } else {
                    return "₱1049";
                }
            }
        }
        if (roomNumber == 301 || roomNumber == 302 || roomNumber == 303 || roomNumber == 304) {
            if (hour.equals("3 Hours")) {
                return "₱449";
            } else if (hour.equals("8 Hours")) {
                return "₱549";
            } else if (hour.equals("12 Hours")) {
                if (dayOfWeekString.equals("MONDAY") || dayOfWeekString.equals("TUESDAY") || dayOfWeekString.equals("WEDNESDAY") ||
                        dayOfWeekString.equals("THURSDAY")) {
                    return "₱649";
                } else {
                    return "₱749";
                }
            } else if (hour.equals("24 Hours")) {
                if (dayOfWeekString.equals("MONDAY") || dayOfWeekString.equals("TUESDAY") || dayOfWeekString.equals("WEDNESDAY") ||
                        dayOfWeekString.equals("THURSDAY")) {
                    return "₱1149";
                } else {
                    return "₱1249";
                }
            }
        }
        if (roomNumber == 201 || roomNumber == 202 || roomNumber == 305 || roomNumber == 306) {
            if (hour.equals("3 Hours")) {
                return "₱499";
            } else if (hour.equals("8 Hours")) {
                return "₱649";
            } else if (hour.equals("12 Hours")) {
                if (dayOfWeekString.equals("MONDAY") || dayOfWeekString.equals("TUESDAY") || dayOfWeekString.equals("WEDNESDAY") ||
                        dayOfWeekString.equals("THURSDAY")) {
                    return "₱749";
                } else {
                    return "₱849";
                }
            } else if (hour.equals("24 Hours")) {
                if (dayOfWeekString.equals("MONDAY") || dayOfWeekString.equals("TUESDAY") || dayOfWeekString.equals("WEDNESDAY") ||
                        dayOfWeekString.equals("THURSDAY")) {
                    return "₱1249";
                } else {
                    return "₱1349";
                }
            }
        }
        return null;
    }
}
