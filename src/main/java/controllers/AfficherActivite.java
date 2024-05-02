package controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.models.Activite;
import tn.esprit.services.ActiviteService;
import tn.esprit.services.CategorieService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AfficherActivite {

    @FXML
    private TableColumn<Activite, String> descriptioncol;

    @FXML
    private TableColumn<Activite, String> nomcol;

    @FXML
    private TableColumn<Activite, Date> date_debutCol;

    @FXML
    private TableColumn<Activite, Date> date_finCol;

    @FXML
    private TableView<Activite> tableview;

    @FXML
    private TableColumn<Activite, String> categoriecol;



    private Stage stage;
    private Scene scene;
    private Parent root;
    int myIndex;
    int id;
    int[] ids;
    String[] names;


    private final ActiviteService as = new ActiviteService();
    @FXML
    void initialize() {

        List<Activite> activites = as.getAll();


        ObservableList<Activite> observableList = FXCollections.observableList(activites);
        tableview.setItems(observableList);

        ids = new int[activites.size()];
         for(int i = 0; i < activites.size(); i++){
             ids[i] = activites.get(i).getId();
         }
        nomcol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        descriptioncol.setCellValueFactory(new PropertyValueFactory<>("description"));
        date_debutCol.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
        date_finCol.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
        //categoriecol.setCellValueFactory(new PropertyValueFactory<>("categorie"));

        tableview.setRowFactory( tv -> {
            TableRow<Activite> myRow = new TableRow<>();
            myRow.setOnMouseClicked (event ->
            {
                if (event.getClickCount() == 1 && (!myRow.isEmpty()))
                {
                    myIndex =  tableview.getSelectionModel().getSelectedIndex();

                    id = Integer.parseInt(String.valueOf(tableview.getItems().get(myIndex).getId()));
                }
            });
            System.out.println();

            return myRow;
        });
    }

    @FXML
    void AjouterActivite(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/AddActivite.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void naviguer1(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/AfficherActivite.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void naviguer2(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/AfficherCategorie.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void naviguer3(ActionEvent event) throws IOException {
        root =FXMLLoader.load(getClass().getResource("/AfficherActiviteFront.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void ModifierActivite(ActionEvent event) throws IOException{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Updateactivite.fxml"));
        Parent root = loader.load();
        Updateactivite controller = loader.getController();
        myIndex = tableview.getSelectionModel().getSelectedIndex();
        id = ids[myIndex];
        System.out.println("id activite :"+ id);
        controller.initData(id);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void SupprimerActvite(ActionEvent event) {
        myIndex = tableview.getSelectionModel().getSelectedIndex();

        id = Integer.parseInt(String.valueOf(tableview.getItems().get(myIndex).getId()));

        as.deleteA(id);
    }
    //public void initData(Activite act) {
       // activite = act;
       // categorie= CategorieService.getById(activie.getId_categorie());
   // }

}
