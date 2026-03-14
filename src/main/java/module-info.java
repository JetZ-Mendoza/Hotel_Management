module sql.hotel_management {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.poi.ooxml;


    opens sql.hotel_management to javafx.fxml;
    exports sql.hotel_management;
}