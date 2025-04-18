module com.example.slanga {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.slanga to javafx.fxml;
    exports com.example.slanga;
}