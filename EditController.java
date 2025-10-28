package com.teamvoid.gocircle;

import com.teamvoid.gocircle.DatabaseConnection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class EditController implements Initializable {

  //  FileInputStream fileInputStream;
  File file;
    @FXML
    public Circle profilePic;
    Connection connect;
    @FXML
    private Label Name;

    @FXML
    private TextField department;

    @FXML
    private Label givencirclename;

    @FXML
    private TextField uniMail;

    @FXML
    private TextField university;

    @FXML
    private TextField fullName;
    private String username;

    @FXML
    void send(ActionEvent event) {

    }

    @FXML
    void submitbtn(ActionEvent event) throws SQLException, IOException {
//        String query = "UPDATE students_info SET `Versity_mail` = ?, `Full Name` = ?, `University` = ?, `Department` = ?, `profile_pic` = ? WHERE Username = ?";
//
//        PreparedStatement statement = connect.prepareStatement(query);
//        statement.setString(1,uniMail.getText());
//        statement.setString(2,fullName.getText());
//        statement.setString(3,university.getText());
//        statement.setString(4,department.getText());
//        statement.setBlob(5,fileInputStream);
//        statement.setString(6,username);
//Statement statement= connect.createStatement();

//        String query = "Update students_info set Versity_mail=? Full Name=? University=? Department=? where Username=?";
//
//        PreparedStatement statement = connect.prepareStatement(query);
//        statement.setString(1, uniMail.getText());
//        statement.setString(2, fullName.getText());
//        statement.setString(3, university.getText());
//        statement.setString(4, department.getText());
//        statement.setString(5, username);
//        statement.executeUpdate();
        FileInputStream fis =new FileInputStream(file);
        String query = "Update students_info set profile_pic=? where Username=?";

        PreparedStatement statement = connect.prepareStatement(query);

        statement.setBinaryStream(1, fis);
        statement.setString(2, username);
        statement.executeUpdate();



        String query1 =  "UPDATE students_info SET `Versity_mail` = '" + uniMail.getText() + "', `Full Name` = '" + fullName.getText() + "', `University` = '" + university.getText() + "', `Department` = '" + department.getText() + "' WHERE `Username` = '" + username + "'";
        Statement statement1=connect.createStatement();
        statement1.executeUpdate(query1);



        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/dboard.fxml"));
        Parent fxml = loader.load();
        DboardController controller = loader.getController();
        controller.setData(username);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(fxml));
        stage.show();
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
                    String mail=resultSet.getString(2);
                    String name=resultSet.getString(4);
                    String unversityname=resultSet.getString(5);
                    String unidepartment=resultSet.getString(6);
                    Blob profileBlob= resultSet.getBlob(7);
                    if(name!=null)
                    {
                        fullName.setText(name);
                    } else fullName.setText("Not Found");


                    if(unversityname!=null)
                    {
                        university.setText(unversityname);
                    } else university.setText("Not Found");

                    if(unidepartment!=null)
                    {
                        department.setText(unidepartment);
                    } else department.setText("Not Found");
                    if(mail!=null)uniMail.setText(mail);

                    if(profileBlob!=null){
                        String path = "temp/"+username+".png";
                        byte byteArray[] = profileBlob.getBytes(1, (int) profileBlob.length());
                        FileOutputStream outPutStream = new FileOutputStream(path);
                        outPutStream.write(byteArray);
                        outPutStream.close();
                        FileInputStream imgStream = new FileInputStream(path);
                        profilePic.setFill(new ImagePattern(new Image(imgStream)));
                    }
                    else{
                        String path="temp/default-profile-photo.jpg";
                        FileInputStream imgStream = new FileInputStream(path);
                        profilePic.setFill(new ImagePattern(new Image(imgStream)));
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
    public void uploadPic(ActionEvent actionEvent) throws IOException, SQLException {
        FileChooser fileChooser = new FileChooser();
        Stage stage = new Stage();
        file = fileChooser.showOpenDialog(stage);
        stage.show();
        stage.close();
        FileInputStream  fileInputStream = new FileInputStream(file);
        profilePic.setFill(new ImagePattern(new Image(fileInputStream)));




}}
