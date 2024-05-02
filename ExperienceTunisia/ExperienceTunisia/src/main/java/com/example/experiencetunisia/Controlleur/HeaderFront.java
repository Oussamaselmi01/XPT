package com.example.experiencetunisia.Controlleur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class HeaderFront {
    @FXML
    private Button btnrec;
    public void gotoreclamtions(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/com/example/experiencetunisia/RecalamtionsFront.fxml"));
        Parent root=loader.load();
        btnrec.getScene().setRoot(root);
    }
}
