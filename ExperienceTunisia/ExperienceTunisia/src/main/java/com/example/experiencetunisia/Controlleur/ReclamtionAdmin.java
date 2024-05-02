package com.example.experiencetunisia.Controlleur;

import com.example.experiencetunisia.Entity.Reclammation;
import com.example.experiencetunisia.Service.ReclamtionResponseService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class ReclamtionAdmin implements Initializable {
    @FXML
    private VBox vbox;
    @FXML
    private VBox cards;
    @FXML
    private PieChart pie;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader fxl=new FXMLLoader();
        fxl.setLocation(getClass().getResource("/com/example/experiencetunisia/SideBar.fxml"));
        Parent root= null;
        try {
            root = fxl.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        vbox.getChildren().add(root);
        ReclamtionResponseService reclamtionResponseService =new ReclamtionResponseService();
        try {
            List<Reclammation> reclammations=reclamtionResponseService.getAllReclamations();
            Map<String, Integer> countByType = new HashMap<>();
            int totalReclamations = reclammations.size();
            if(totalReclamations>0){
                for (Reclammation reclammation : reclammations) {
                    String type = reclammation.getTitre();
                    countByType.put(type, countByType.getOrDefault(type, 0) + 1);
                }
                ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
                for (Map.Entry<String, Integer> entry : countByType.entrySet()) {
                    String type = entry.getKey();
                    int count = entry.getValue();
                    double percentage = (double) count / totalReclamations * 100;
                    PieChart.Data data = new PieChart.Data(type, percentage);
                    pieChartData.add(data);
                }
                pie.setData(pieChartData);
            }
            for(int i=0;i<reclammations.size();i++){
                FXMLLoader fxll=new FXMLLoader();
                fxll.setLocation(getClass().getResource("/com/example/experiencetunisia/CardReclamtionAdmin.fxml"));
                Parent roott=fxll.load();
                CardReclamtionAdmin c=fxll.getController();
                c.SetData(reclammations.get(i));
                cards.getChildren().add(roott);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
