package com.teamvoid.gocircle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;


public class VerificationController implements Initializable {

    private Connection connect;
    static String verificationCode="";
    @FXML
    private TextField verify_code;
    @FXML
    private Label warnningMassage;
    private String username;
    private String email;
    @FXML
    private String password;
    EmailVerification emailVerification;

    public void setData(String username, String email, String password, String verificationCode) {
        this.username = username;
        this.email = email;
        this.password = password;
        VerificationController.verificationCode = verificationCode;
    }

    public void varify(MouseEvent mouseEvent) {
        try{
            if(verify_code.getText().equals(verificationCode))
            {
                Statement statement = connect.createStatement();
                String inputInfo = "INSERT INTO `students_info` (`Username`, `Versity_mail`, `Password`) VALUES ('" + username + "', '" + email + "', '" + password + "')";
                statement.executeUpdate(inputInfo);
                System.out.println("Successful Insert");
                Parent root = FXMLLoader.load(getClass().getResource("fxml/dfg.fxml"));
                Scene scene1 = new Scene(root);
                Stage stage1 = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
                stage1.setScene(scene1);
                stage1.show();

            }
            else{
                warnningMassage.setText("Wrong Verification Code");
            }


        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("From verify mouse event");
        }


    }

    public void resend(MouseEvent mouseEvent) {
        emailVerification =new EmailVerification(email,username);
        verificationCode= emailVerification.verificationCode;
        warnningMassage.setText("Code resend");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatabaseConnection connectNow= new DatabaseConnection();
        connect = connectNow.getConnect();

    }
}
