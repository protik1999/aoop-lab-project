package com.teamvoid.gocircle;

import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TestController implements Initializable {

    @FXML
    private Circle Circle;
    @FXML
    private AnchorPane rootpane;

    @FXML
    void close(MouseEvent event) {
        System.exit(0);

    }

    @FXML
    void minimize(MouseEvent event) {
        Main.stage.setIconified(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new SplashScreen().start();

        RotateTransition rotateTransition = new RotateTransition();
        rotateTransition.setAxis(Rotate.Z_AXIS);
        rotateTransition.setByAngle(360);
        rotateTransition.setCycleCount(1000);
        rotateTransition.setDuration(Duration.millis(1300));
        rotateTransition.setNode(Circle);
        rotateTransition.play();

    }

    class SplashScreen extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(9800);

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Parent root = null;
                        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/dfg.fxml"));
                        Scene scene = null;
                        try {
                            scene = new Scene(fxmlLoader.load());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Stage stage = new Stage();

                        stage.setScene(scene);
                        stage.initStyle(StageStyle.UNDECORATED);
                        stage.show();

                        rootpane.getScene().getWindow().hide();
                    }
                });

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
