package com.teamvoid.gocircle;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
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
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;


public class LoginregiController implements Initializable {

    @FXML
    private Circle Circle1;
    @FXML
    private Label loginPagego;
    @FXML
    private Label signupPagego;
    @FXML
    private JFXButton loginButton;
    @FXML
    private JFXButton signupButton;
    @FXML
    private PasswordField confirmPassword;

    @FXML
    private PasswordField createPassword;

    @FXML
    private TextField email;

    @FXML
    private Label haveaccLabel;

    @FXML
    private Pane layer1;

    @FXML
    private Pane layer2;

    @FXML
    private Label noaccLabel;

    @FXML
    private Pane pane1;
    @FXML
    private Pane pane2;

    @FXML
    private PasswordField password;

    @FXML
    private Label signinLabel;

    @FXML
    private Label signupLabel;

    @FXML
    private TextField userName;

    @FXML
    private TextField signupUsername;
    @FXML
    private Pane icon1;

    @FXML
    private Pane icon2;

    @FXML
    private Pane icon3;

    @FXML
    private Pane icon4;
    @FXML
    private ToggleButton icon51;

    @FXML
    private ToggleButton icon61;
    @FXML
    private Circle Circle;

    private Connection connect;

    private  static String verificationCode="";
    //from verification
    @FXML
    private Rectangle massagebox1;

    @FXML
    private TextField verify_code;

    @FXML
    private Label warnningMassage;
    @FXML
    public  Label user_email_show;
    static String username;
    static String versityMail;
    static String passWord;
    EmailVerification emailVerification;


    @FXML
    void close(MouseEvent event) {
        System.exit(0);
    }
    @FXML
    void loginPage(MouseEvent event) {
        TranslateTransition slide = new TranslateTransition();
        TranslateTransition slide2 = new TranslateTransition();

        slide.setDuration(Duration.seconds(1));
        slide.setNode(layer1);
        slide.setToX(0);
        slide.play();

        slide2.setDuration(Duration.seconds(1));
        slide2.setNode(layer2);
        slide2.setToX(0);
        slide2.play();

       // pane2.setVisible(false);
        signupLabel.setVisible(false);
        signupUsername.setVisible(false);
        icon3.setVisible(false);
        email.setVisible(false);
        icon4.setVisible(false);
        confirmPassword.setVisible(false);
        createPassword.setVisible(false);
        icon51.setVisible(false);
        icon61.setVisible(false);
        signupButton.setVisible(false);
        haveaccLabel.setVisible(false);
        loginPagego.setVisible(false);


        signinLabel.setVisible(true);
        userName.setVisible(true);

          password.setVisible(true);
        icon1.setVisible(true);
        icon2.setVisible(true);
        loginButton.setVisible(true);
        noaccLabel.setVisible(true);
        signupPagego.setVisible(true);

    }

    @FXML
    void loginbtn(ActionEvent event) throws SQLException, IOException {
        Statement statement = connect.createStatement();
        String verify = "SELECT count(1) FROM students_info WHERE Username = " + "\"" + userName.getText() + "\"" + " AND Password =  " + "\"" + password.getText() + "\"";


        ResultSet resultSet= statement.executeQuery(verify);
        while (resultSet.next()) {
            if (resultSet.getInt(1) == 1) {
                username= userName.getText();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/dboard.fxml"));

                Parent root = loader.load();
                DboardController dboardController =loader.getController();
                dboardController.setData(username);

                Scene scene3 = new Scene(root);


                Stage stage3 = (Stage) ((Node) event.getSource()).getScene().getWindow();

                stage3.setScene(scene3);
                stage3.show();

            } else System.out.println("Not successful");

        }

    }

    @FXML
    void minimize(MouseEvent event) {
        Main.stage.setIconified(true);

    }

    @FXML
    void signUpButton(ActionEvent event) {
        try {

            if(!signupUsername.getText().isBlank() && !email.getText().isBlank()) {
                if(createPassword.getText().equals(confirmPassword.getText())) {
                    String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";


                    if (email.getText().matches(emailRegex)) {
                        username=signupUsername.getText();
                        versityMail=email.getText();
                        passWord=createPassword.getText();
//                        verificationCode= generateCode();
//                        System.out.println("1:"+verificationCode);

                        emailVerification =new EmailVerification(versityMail,username);
                        verificationCode = emailVerification.verificationCode;


                        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/VarificationPopUp.fxml"));
                        Parent root = loader.load();
                        VerificationPopUpController verificationPopUpController = loader.getController();
                        verificationPopUpController.setData(username, versityMail, passWord, verificationCode);
                        Scene scene = new Scene(root);
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(scene);
//                        sendVerificationCode(email.getText(),verificationCode);
//                        System.out.println("Email send");
                        stage.show();




                    } else {
                        System.out.println("Invalid email address");
                    }
                } else {
                    System.out.println("Password miss match");
                }
            } else {
                System.out.println("Please enter values");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @FXML
    void icon5(ActionEvent event) {

    }

    @FXML
    void icon6(ActionEvent event) {

    }


    @FXML
    void singupPage(MouseEvent event) {
        TranslateTransition slide = new TranslateTransition();
        TranslateTransition slide2 = new TranslateTransition();

        slide.setDuration(Duration.seconds(1));
        slide.setNode(layer1);
        slide.setToX(586);
        slide.play();

        slide2.setDuration(Duration.seconds(1));
        slide2.setNode(layer2);
        slide2.setToX(-586);
        slide2.play();


        signinLabel.setVisible(false);
        userName.setVisible(false);
        password.setVisible(false);
        icon1.setVisible(false);
        icon2.setVisible(false);
        loginButton.setVisible(false);
        noaccLabel.setVisible(false);
        signupPagego.setVisible(false);

      //  pane2.setVisible(true);
        signupLabel.setVisible(true);
        signupUsername.setVisible(true);
        icon3.setVisible(true);
        email.setVisible(true);
        icon4.setVisible(true);
        confirmPassword.setVisible(true);
        createPassword.setVisible(true);
        icon51.setVisible(true);
        icon61.setVisible(true);
        signupButton.setVisible(true);
        haveaccLabel.setVisible(true);
        loginPagego.setVisible(true);



    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
          //  pane2.setVisible(false);
            signupLabel.setVisible(false);
            signupUsername.setVisible(false);
            icon3.setVisible(false);
            email.setVisible(false);
            icon4.setVisible(false);
            confirmPassword.setVisible(false);
            createPassword.setVisible(false);
            icon51.setVisible(false);
            icon61.setVisible(false);
            signupButton.setVisible(false);
            haveaccLabel.setVisible(false);
            loginPagego.setVisible(false);

        RotateTransition rotate = new RotateTransition();
        RotateTransition rotate2 = new RotateTransition();

        rotate.setAxis(Rotate.Z_AXIS);
        rotate.setByAngle(360);
        rotate.setCycleCount(10000);
        rotate.setDuration(Duration.millis(1000));
        rotate.setNode(Circle);
        rotate.play();

        rotate2.setAxis(Rotate.Z_AXIS);
        rotate2.setByAngle(360);
        rotate2.setCycleCount(10000);
        rotate2.setDuration(Duration.millis(1300));
        rotate2.setNode(Circle1);
        rotate2.play();

        DatabaseConnection connectNow= new DatabaseConnection();
        connect= connectNow.getConnect();
    }
}

