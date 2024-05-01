package View;

import Entities.User;
import Services.IService;
import Services.UserService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class IndexUserController {

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, String> cinColumn;

    @FXML
    private TableColumn<User, String> nomColumn;

    @FXML
    private TableColumn<User, String> prenomColumn;

    @FXML
    private TableColumn<User, String> emailColumn;

    @FXML
    private Label labelCIN;

    @FXML
    private Label labelNom;

    @FXML
    private Label labelPrenom;

    @FXML
    private Label labelEmail;

    private ObservableList<User> userService = FXCollections.observableArrayList();
    private IService<User> userIService = new UserService();

    @FXML
    private void initialize() {
        cinColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCin()));
        nomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
        prenomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrenom()));
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));

        showUserDetails(null);

        try {
            userService.addAll(userIService.readAll());
            userTable.setItems(userService);
        } catch (Exception e) {
            e.printStackTrace();
            showAlertDialog(Alert.AlertType.ERROR, "Error", "Failed to load users", e.getMessage());
        }

        userTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showUserDetails(newValue));
    }

    private void showUserDetails(User user) {
        if (user != null) {
            labelCIN.setText(user.getCin());
            labelNom.setText(user.getNom());
            labelPrenom.setText(user.getPrenom());
            labelEmail.setText(user.getEmail());
        } else {
            labelCIN.setText("");
            labelNom.setText("");
            labelPrenom.setText("");
            labelEmail.setText("");
        }
    }

    @FXML
    public void refreshTableView() {
        try {
            userService.clear();
            userService.addAll(userIService.readAll());
        } catch (Exception e) {
            e.printStackTrace();
            showAlertDialog(Alert.AlertType.ERROR, "Database Error", "Failed to fetch users", e.getMessage());
        }
    }

    @FXML
    private void createUser(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/add-user.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setTitle("Add User");
            stage.setScene(scene);

           View.AddUserController controller = loader.getController();
            controller.setIndexUserController(this);

            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteUser(ActionEvent event) {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            try {
                userIService.delete(selectedUser.getId());
                refreshTableView();
            } catch (Exception e) {
                e.printStackTrace();
                showAlertDialog(Alert.AlertType.ERROR, "Database Error", "Failed to delete user", e.getMessage());
            }
        } else {
            showAlertDialog(Alert.AlertType.WARNING, "No Selection", "No User Selected!", "Please select a user in the table!");
        }
    }

    @FXML
    private void updateUser(ActionEvent event) {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/update-user.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root);

                Stage stage = new Stage();
                stage.setTitle("Update User");
                stage.setScene(scene);

                UpdateUserController controller = loader.getController();
                controller.setIndexUserController(this);
                controller.setSelectedUser(selectedUser);

                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            showAlertDialog(Alert.AlertType.WARNING, "No Selection", "No User Selected!", "Please select a user in the table!");
        }
    }

    private void showAlertDialog(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
