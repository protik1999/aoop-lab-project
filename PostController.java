package com.teamvoid.gocircle;

import com.teamvoid.gocircle.Timeline.Account;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class PostController implements Initializable {
    Connection connect;

    @FXML
    private Circle postCardPic;
    @FXML
    private Label caption;

    @FXML
    private ImageView commentclick;

    @FXML
    private Label date;

    @FXML
    private ImageView globe;

    @FXML
    private ImageView mypost;

    @FXML
    private Label postusrname;

    @FXML
    private ImageView profileimage;

    @FXML
    private ImageView save;

    @FXML
    private ImageView saveclick;

    @FXML
    private ImageView starclick;
    @FXML
    private Post post;

    public void setPost(Post post) {
        this.post = post;
    }

    @FXML
    void comment(MouseEvent event) {

    }

    @FXML
    void star(MouseEvent event) {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        saveclick.setVisible(false);
//        commentclick.setVisible(false);
//        saveclick.setVisible(false);

        Platform.runLater(()->{
            try {

                    postusrname.setText(post.getUsername());
                    caption.setText(post.getContent());
                DatabaseConnection connectNow= new DatabaseConnection();
                connect= connectNow.getConnect();

                Statement statement= connect.createStatement();
                String query=" SELECT * FROM `students_info` WHERE `Username` LIKE " +"\""+post.getUsername()+"\"";
                ResultSet resultSet= statement.executeQuery(query);
                Blob postProfile=null;
                while (resultSet.next()){
                    postProfile= resultSet.getBlob(7);
                }
                if(postProfile!=null){
                    String path = "temp/"+post.getUsername()+".png";
                    byte byteArray[] = postProfile.getBytes(1, (int) postProfile.length());
                    FileOutputStream outPutStream = new FileOutputStream(path);
                    outPutStream.write(byteArray);
                    outPutStream.close();
                    FileInputStream imgStream = new FileInputStream(path);
                    postCardPic.setFill(new ImagePattern(new Image(imgStream)));
                }
                else{
                    String path="temp/default-profile-photo.jpg";
                    FileInputStream imgStream = new FileInputStream(path);
                    postCardPic.setFill(new ImagePattern(new Image(imgStream)));
                }


                    String path = "temp/" + post.getPostID() + ".png";
                if(post.getBlob()!=null) {
                    byte byteArray[] = post.getBlob().getBytes(1, (int) post.getBlob().length());
                    FileOutputStream outPutStream = new FileOutputStream(path);
                    outPutStream.write(byteArray);
                    outPutStream.close();
                    FileInputStream imgStream = new FileInputStream(path);
                    mypost.setImage(new Image(imgStream));
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        });


    }

    public void imageUp(MouseEvent mouseEvent) {
    }
}
