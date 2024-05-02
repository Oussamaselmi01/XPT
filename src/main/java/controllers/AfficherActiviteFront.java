package controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.esprit.models.Activite;
import tn.esprit.services.ActiviteService;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AfficherActiviteFront {
    @FXML
    private ImageView activitieimage;
    @FXML
    private VBox actionsContainer;
    String imagepath ;
    private Stage stage;
    private Scene scene;
    private Parent root;

    ActiviteService as = new ActiviteService();


    @FXML
    void initialize() {
        List<Activite> activites = as.getAll();
        for (Activite activite :activites) {
            Pane actionPane = createActionPane(activite);
            actionsContainer.getChildren().add(actionPane);
            imagepath = "C:/Users/HP/IdeaProjects/test/src/main/resources/img/"+activite.getImage();
            setImage(imagepath);
        }
    }

    private void setImage(String imagePath) {
        try {
            Image image = new Image(new File(imagePath).toURI().toString());
            activitieimage.setImage(image);
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());

        }
    }



    private Pane createActionPane(Activite activite) {
        Pane pane = new Pane();
        pane.setPrefWidth(740.0);
        pane.setPrefHeight(164.0);
        pane.setStyle("-fx-background-color: #ffff;");
        ImageView imageView = new ImageView();
        imageView.setFitWidth(200);
        imageView.setFitHeight(164.0);
        imageView.setLayoutX(10.0);
        imageView.setLayoutY(2.0);

        String imagePath = "C:/Users/HP/IdeaProjects/test/src/main/resources/img/" + activite.getImage();
        Image image = new Image(new File(imagePath).toURI().toString(), true);
        imageView.setImage(image);
        Text title = new Text(activite.getNom());
        title.setLayoutX(220.0);
        title.setLayoutY(50.0);
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");


        String partOfDescription = activite.getDescription().length() > 70 ? activite.getDescription().substring(0, 60) : activite.getDescription();
        Text description = new Text(partOfDescription + "...");
        description.setLayoutX(220.0);
        description.setLayoutY(108.0);
        description.setStyle("-fx-font-size: 13px;");


        pane.getChildren().addAll(imageView,description,title);




        return pane;
    }
    @FXML
    void back(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/Home.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }





}
