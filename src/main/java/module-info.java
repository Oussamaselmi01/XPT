module com.example.experiencetunisia {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
    requires java.sql;
    requires java.mail;

    opens com.example.experiencetunisia to javafx.fxml;
    exports com.example.experiencetunisia;
    exports com.example.experiencetunisia.Controlleur;
    opens com.example.experiencetunisia.Controlleur  to javafx.fxml;
}