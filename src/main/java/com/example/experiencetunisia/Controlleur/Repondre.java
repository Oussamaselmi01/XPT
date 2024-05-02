package com.example.experiencetunisia.Controlleur;

import com.example.experiencetunisia.Entity.Reponse;
import com.example.experiencetunisia.Service.ReclamtionResponseService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Repondre {
    @FXML
    private Label ccok;
    @FXML
    private Label cccontenu;
    @FXML
    private TextArea contenu;
    @FXML
    private VBox vbox;
    private int id;
    private String userEmail="Montassar.Mtar@esprit.tn"; // Assuming you have the user's email address

    public void setId(int id){
        this.id=id;
        FXMLLoader fxl=new FXMLLoader();
        fxl.setLocation(getClass().getResource("/com/example/experiencetunisia/SideBar.fxml"));
        Parent root= null;
        try {
            root = fxl.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        vbox.getChildren().add(root);
    }

    public void ok(ActionEvent actionEvent) throws SQLException, IOException {
        ReclamtionResponseService reclamtionResponseService=new ReclamtionResponseService();
        int t=0;
        if(contenu.getText().isEmpty()){
            t = 1;
            this.cccontenu.setText("Vous devez saisir la Reponse");
        } else {
            this.cccontenu.setText("");
        }
        if(t==0){
            Reponse r=new Reponse();
            r.setContenu(contenu.getText());
            r.setDate_creation(new Date());
            t=reclamtionResponseService.addResponse(r,this.id);
            if(t==0){
                this.ccok.setText("Erreur lors d'ajout s'il vous plait essayer une autre fois");
            }else {
                sendEmailToUser(userEmail, "Réponse à votre réclamation", contenu.getText()); // Send email to user
                FXMLLoader loader=new FXMLLoader(getClass().getResource("/com/example/experiencetunisia/ReclamtionAdmin.fxml"));
                Parent root=loader.load();
                contenu.getScene().setRoot(root);
            }
        }
    }

    private void sendEmailToUser(String userEmail, String subject, String content) {
        String host = "smtp.gmail.com"; // Change to your email provider's SMTP server
        String from = "montassarmtar@gmail.com"; // Change to your email address
        String password = "Montamtar2012"; // Change to your email password

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(from, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail));
            message.setSubject(subject);
            message.setText(content);
            Transport.send(message);
            System.out.println("Email sent to " + userEmail);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
