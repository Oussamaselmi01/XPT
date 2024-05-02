package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.esprit.models.Activite;
import tn.esprit.models.Categorie;
import tn.esprit.services.CategorieService;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

public class AddCategorie {
    @FXML
    private TextField NomCat;

    @FXML
    private TextArea descriptionCat;
    @FXML
    private Text errorLabel;

    private final CategorieService cs = new CategorieService();

    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    void AjouterCat(ActionEvent event) {
        try{ if (isValidInput()) {


        cs.addA(new Categorie(NomCat.getText(),descriptionCat.getText()));
        }

    }catch(Exception e){
        System.out.println(e.getMessage());
    }


    }

    private boolean isValidInput() {
        if (NomCat.getText().isEmpty()) {
            errorLabel.setText("Please enter a name.");
            return false;
        }
        if (descriptionCat.getText().isEmpty()) {
            errorLabel.setText("Please enter a description.");
            return false;
        }
        errorLabel.setText("");

        return true;
    }


    @FXML
    void Back(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/Home.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    @FXML
    void back(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/AfficherCategorie.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

}
