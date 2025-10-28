package com.teamvoid.gocircle;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;

public class VerificationPopUpController implements Initializable {

    Connection connect;
    @FXML
    private Circle Circle;

    @FXML
    private Circle Circle1;

    @FXML
    private Label Goback;

    @FXML
    private Label Resend;

    @FXML
    private JFXButton Verify;

    @FXML
    private Pane icon2;

    @FXML
    private Pane layer1;

    @FXML
    private Pane layer2;

    @FXML
    private Label noaccLabel;

    @FXML
    private PasswordField otp;

    @FXML
    private Label waringmassge;
    private String username;
    private String email;
    private String password;
    private  static String verificationCode="";
    EmailVerification emailVerification;

    public void setData(String username, String email, String password, String verificationCode) {
        this.username = username;
        this.email = email;
        this.password = password;
        VerificationPopUpController.verificationCode = verificationCode;
    }
    @FXML
    void close(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void goBack(MouseEvent event) throws IOException {
FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("fxml/dfg.fxml"));
Parent root =fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void minimize(MouseEvent event) {
        Main.stage.setIconified(true);

    }

    @FXML
    void resend(MouseEvent event) {
        emailVerification =new EmailVerification(email,username);
        verificationCode= emailVerification.verificationCode;
        waringmassge.setText("Code resend");
    }

    @FXML
    void varify(ActionEvent event) {
        try{
            System.out.println("otp="+otp.getText()+"verfi="+verificationCode);
            if(otp.getText().equals(verificationCode))
            {
                Statement statement = connect.createStatement();
                String inputInfo = "INSERT INTO `students_info` (`Username`, `Versity_mail`, `Password`) VALUES ('" + username + "', '" + email + "', '" + password + "')";
                statement.executeUpdate(inputInfo);
                System.out.println("Successful Insert");
                Parent root = FXMLLoader.load(getClass().getResource("fxml/dfg.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();

            }
            else{
              waringmassge.setText("Wrong Verification Code");
            }


        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("From verify mouse event");
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatabaseConnection connectNow= new DatabaseConnection();
        connect = connectNow.getConnect();
    }
}
