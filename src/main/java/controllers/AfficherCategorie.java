package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.models.Activite;
import tn.esprit.models.Categorie;
import tn.esprit.services.ActiviteService;
import tn.esprit.services.CategorieService;

import java.io.IOException;
import java.util.List;

public class AfficherCategorie {
    @FXML
    private TableColumn<Categorie, String> descriptioncat;

    @FXML
    private TableColumn<Categorie, String> nomcat;

    @FXML
    private TableView<Categorie> tableview;
    @FXML
    private TextField cattxt;




    private Stage stage;
    private Scene scene;
    private Parent root;


    int myIndex;
    int id;
    int[] ids;
    private final CategorieService as = new CategorieService();
    @FXML
    void initialize() {

        List<Categorie> categorie = as.getAll();
        ObservableList<Categorie> observableList = FXCollections.observableList(categorie);
        tableview.setItems(observableList);
        ids = new int[categorie.size()];
        for(int i = 0; i < categorie.size(); i++){
            ids[i] = categorie.get(i).getId();
        }
        nomcat.setCellValueFactory(new PropertyValueFactory<>("nom"));
        descriptioncat.setCellValueFactory(new PropertyValueFactory<>("description"));

        FilteredList<Categorie> filteredData = new FilteredList<>(observableList, c -> true);
        cattxt.textProperty().addListener((observable ,olValue,newValue)->{
            filteredData.setPredicate(c -> {
                if (newValue.isEmpty() || newValue.isBlank()||newValue==null) {
                    return true;
                }
                String searchKeyword= newValue.toLowerCase();
                if (c.getNom().toLowerCase().indexOf(searchKeyword) >-1) {
                    return true;
                }else
                    return false;
            });
        });
        SortedList<Categorie> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableview.comparatorProperty());
        tableview.setItems(sortedData);



        tableview.setRowFactory( tv -> {
            TableRow<Categorie> myRow = new TableRow<>();
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
    void addcat(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/AddCategorie.fxml"));
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
        root =FXMLLoader.load(getClass().getResource("/AfficherCategorie.fxml"));
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
    void Back(ActionEvent event) throws IOException {
        root =FXMLLoader.load(getClass().getResource("/Home.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    @FXML
    void SupprimerCategorie(ActionEvent event) {
        myIndex = tableview.getSelectionModel().getSelectedIndex();

        id = Integer.parseInt(String.valueOf(tableview.getItems().get(myIndex).getId()));
        System.out.println("id :" + id);
        as.deleteA(id);
    }
    @FXML
    void ModifierCategorie(ActionEvent event) throws IOException{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateCategorie.fxml"));
        Parent root = loader.load();
        UpdateCategorie controller = loader.getController();
        myIndex = tableview.getSelectionModel().getSelectedIndex();
        id = ids[myIndex];
        System.out.println("id categorie :"+ id);
        controller.initData(id);


        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
