package com.example.experiencetunisia.Controlleur;

import com.example.experiencetunisia.Entity.Reclammation;
import com.example.experiencetunisia.Service.ReclamtionResponseService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.SQLException;

public class CardReclamtionUser {
    @FXML
    private Label titre;
    @FXML
    private Label desciption;
    @FXML
    private Label date;
    @FXML
    private Label statut;
    @FXML
    private Label response;
    private Reclammation r;
    public void setData(Reclammation r){
        titre.setText(r.getTitre());
        desciption.setText(r.getDescription());
        date.setText(r.getDate_creation().toString());
        statut.setText(r.getStatut());
        if(r.getReponse()!=null){
            response.setText(r.getReponse().getContenu());
        }
        this.r=r;
    }
    public void modifier(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxll=new FXMLLoader();
        fxll.setLocation(getClass().getResource("/com/example/experiencetunisia/EditReclamtion.fxml"));
        Parent roott=fxll.load();
        EditReclamtion c=fxll.getController();
        c.SetData(this.r);
        statut.getScene().setRoot(roott);
    }

    public void supprimer(ActionEvent actionEvent) throws SQLException, IOException {
        ReclamtionResponseService reclamtionResponseService =new ReclamtionResponseService();
        int t=reclamtionResponseService.supprimerReclamtion(this.r.getId());
        if(t==1){
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/com/example/experiencetunisia/RecalamtionsFront.fxml"));
            Parent root=loader.load();
            statut.getScene().setRoot(root);
        }
    }
}
