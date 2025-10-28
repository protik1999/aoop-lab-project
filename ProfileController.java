package com.teamvoid.gocircle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    @FXML
    private Circle Circle1;

    @FXML
    private Label Name;

    @FXML
    private Circle ball1;

    @FXML
    private Circle ball2;

    @FXML
    private Circle ball3;

    @FXML
    private Circle ball4;

    @FXML
    private Label circleName;

    @FXML
    private Label departmentnameshow;

    @FXML
    private ImageView profilepic;

    @FXML
    private StackPane stackpane;

    @FXML
    private Label uniNameshow;
    private String username;
    Connection connect;

    @FXML
    void createpost(MouseEvent event) {

    }

    @FXML
    void editbtn(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader =new FXMLLoader(getClass().getResource("fxml/edit.fxml"));
        Parent fxml = fxmlLoader.load();
        EditController editController=fxmlLoader.getController();
        editController.setData(username);
        stackpane.getChildren().removeAll();
        stackpane.getChildren().setAll(fxml);


    }
    @FXML
    void imageset(MouseEvent event) {

    }

    @FXML
    void send(ActionEvent event) {

    }

    public void setData(String username) {
        this.username=username;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()->{
            DatabaseConnection connectNow= new DatabaseConnection();
            connect= connectNow.getConnect();
            try {
                Statement statement= connect.createStatement();
                String query=" SELECT * FROM `students_info` WHERE `Username` LIKE " +"\""+username+"\"";
                ResultSet resultSet= statement.executeQuery(query);
                while (resultSet.next())
                {
                    String name=resultSet.getString(4);
                    String unversityname=resultSet.getString(5);
                    String department=resultSet.getString(6);
                    Blob profileBlob= resultSet.getBlob(7);
                    if(name!=null)
                    {
                        Name.setText(name);
                    } else Name.setText("Not Found");


                    if(unversityname!=null)
                    {
                        uniNameshow.setText(unversityname);
                    } else uniNameshow.setText("Not Found");

                    if(department!=null)
                    {
                        departmentnameshow.setText(department);
                    } else departmentnameshow.setText("Not Found");

                    if(profileBlob!=null){
                        String path = "temp/"+username+".png";
                        byte byteArray[] = profileBlob.getBytes(1, (int) profileBlob.length());
                        FileOutputStream outPutStream = new FileOutputStream(path);
                        outPutStream.write(byteArray);
                        outPutStream.close();
                        FileInputStream imgStream = new FileInputStream(path);
                        profilepic.setImage(new Image(imgStream));
                    }
                    else{
                        String path="temp/default-profile-photo.jpg";
                        FileInputStream imgStream = new FileInputStream(path);
                        profilepic.setImage(new Image(imgStream));
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

    public void goBack(MouseEvent mouseEvent) {
    }

    public void goback(MouseEvent mouseEvent) {
    }
}
