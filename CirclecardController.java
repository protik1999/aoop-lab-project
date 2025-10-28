package com.teamvoid.gocircle;

import com.teamvoid.gocircle.chat.chat.controller.ClientFormController;
import com.teamvoid.gocircle.circlemod.CircleModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CirclecardController implements Initializable {

    @FXML
    private ImageView circleimage;

    @FXML
    private Label circlename;

    @FXML
    private ImageView otherspic1;

    @FXML
    private ImageView otherspic2;
    private CircleModel circleModel;
    private StackPane changepane;
    private String username;

    public void setChangepane(StackPane changepane) {
        this.changepane = changepane;
    }

    public void setCircleModel(CircleModel circleModel) {
        this.circleModel = circleModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()->{
            circlename.setText(circleModel.getCircleName());
            try {
               byte byteArray[] = circleModel.getCirclePicBlob().getBytes(1, (int) circleModel.getCirclePicBlob().length());
                String path="temp/"+circleModel.getCircleName()+".png";
                FileOutputStream outPutStream = new FileOutputStream(path);
                outPutStream.write(byteArray);
                outPutStream.close();
                FileInputStream imgStream = new FileInputStream(path);
                circleimage.setImage(new Image(imgStream));

            }
            catch (SQLException | FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

);
    }

    public void circleChat(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("fxml/chatbox.fxml"));
        Parent fxml = fxmlLoader.load();
        ClientFormController clientFormController =fxmlLoader.getController();
        clientFormController.setUsername(username);

        changepane.getChildren().removeAll();
        changepane.getChildren().setAll(fxml);
    }

    public void setUsername(String username) {
        this.username=username;
    }
}
