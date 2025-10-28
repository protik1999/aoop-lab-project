package com.teamvoid.gocircle;

import com.jfoenix.controls.JFXButton;
import com.sun.mail.imap.protocol.UID;
import com.teamvoid.gocircle.Todo.TodoController;
import com.teamvoid.gocircle.chat.chat.controller.ClientFormController;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Date;

public class DboardController implements Initializable {
    @FXML
    private Circle postShowPic;
    FileInputStream  postInputStream;
    public TextField search;
    @FXML
    private TextField captionwrite;

    @FXML
    private VBox poatContrainer;
    @FXML
    private Circle profilePic;
    Connection connect;
    @FXML
    private StackPane changepane;

    @FXML
    private JFXButton circlebtn;

    @FXML
    private JFXButton homebtn;

    @FXML
    private JFXButton keepbtn;

    @FXML
    private VBox pane1;

    @FXML
    private VBox pane2;

    @FXML
    private ImageView menueicon2;

    @FXML
    private JFXButton profilebtn;

    @FXML
    private JFXButton studybtn;
    @FXML
    private ImageView menueicon;
    private String username;
    @FXML
    void logout(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/dfg.fxml"));
        Scene scene1 = new Scene(root);
        Stage stage1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage1.setScene(scene1);
        stage1.show();

    }
    @FXML
    void chaticon(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("fxml/chatbox.fxml"));
        Parent fxml = fxmlLoader.load();
        ClientFormController clientFormController =fxmlLoader.getController();
        clientFormController.setUsername(username);


        changepane.getChildren().removeAll();
        changepane.getChildren().setAll(fxml);

    }

    @FXML
    void createpost(MouseEvent event) {

    }
    @FXML
    void circlebutton(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("fxml/circle.fxml"));
        Parent root=fxmlLoader.load();
      CircleController circleController=  fxmlLoader.getController();
      circleController.setUsername(username);
      circleController.setChangepane(changepane);

        changepane.getChildren().removeAll();
        changepane.getChildren().setAll(root);

    }

    @FXML
    void homebutton(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/dboard.fxml"));
        Parent fxml = loader.load();
        DboardController controller = loader.getController();
        controller.setData(username);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(fxml));
        stage.show();

    }

    @FXML
    void keepbutton(MouseEvent event) {

    }


    @FXML
    void profilebutton(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/profile-page.fxml"));
        Parent fxml = loader.load();
        ProfileController profileController =loader.getController();

        profileController.setData(username);


        changepane.getChildren().removeAll();
        changepane.getChildren().setAll(fxml);

    }

    @FXML
    void studybutton(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("fxml/todo.fxml"));
        Parent root =  fxmlLoader.load();
        TodoController todoController= fxmlLoader.getController();
//        System.out.println("k="+username);
        todoController.setData(username);
        changepane.getChildren().removeAll();
        changepane.getChildren().setAll(root);

    }
    @FXML
    void close(MouseEvent event) {
        System.exit(0);
    }
    @FXML
    void minimize(MouseEvent event) {
        Main.stage.setIconified(true);
    }

    @FXML
    void menue(MouseEvent event) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.5));
        slide.setNode(pane2);
        slide.setToX(68);
        slide.play();

        pane2.setVisible(true);
        homebtn.setVisible(true);
        circlebtn.setVisible(true);
        profilebtn.setVisible(true);
        studybtn.setVisible(true);
        keepbtn.setVisible(true);

        menueicon.setVisible(false);
        menueicon2.setVisible(true);
        DatabaseConnection connectNow= new DatabaseConnection();
        connect= connectNow.getConnect();


    }

    @FXML
    void menue2(MouseEvent event) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.5));
        slide.setNode(pane2);
        slide.setToX(-68);
        slide.play();

        pane2.setVisible(false);
        homebtn.setVisible(false);
        circlebtn.setVisible(false);
        profilebtn.setVisible(false);
        studybtn.setVisible(false);
        keepbtn.setVisible(false);

        menueicon.setVisible(true);
        menueicon2.setVisible(false);

    }


    ArrayList<Post> getPosts() throws SQLException {
        ArrayList<Post> posts = new ArrayList<>();
        String query = "SELECT * FROM `posts` ORDER BY `posts`.`PostDate` DESC";
        Statement statement = connect.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next())
        {
            posts.add(new Post(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3), resultSet.getBlob(4),resultSet.getString(5)));
        }
        //Collections.sort(posts);
        return posts;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        pane2.setVisible(false);
        homebtn.setVisible(false);
        circlebtn.setVisible(false);
        profilebtn.setVisible(false);
        studybtn.setVisible(false);
        keepbtn.setVisible(false);
        menueicon2.setVisible(false);



        Platform.runLater(()->{

            DatabaseConnection connectNow= new DatabaseConnection();
            connect= connectNow.getConnect();
            try {
                for(Post post:getPosts())
                {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/teamvoid/gocircle/fxml/post.fxml"));
                        Parent root = loader.load();
                        PostController controller = loader.getController();
                        controller.setPost(post);
                        poatContrainer.getChildren().add(root);
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


            try {
                Statement statement= connect.createStatement();
                String query=" SELECT * FROM `students_info` WHERE `Username` LIKE " +"\""+username+"\"";
                ResultSet resultSet= statement.executeQuery(query);
                while (resultSet.next())
                {
                    Blob profileBlob= resultSet.getBlob(7);
                    if(profileBlob!=null){
                        String path = "temp/"+username+".png";
                        //System.out.print(path);
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

    public void setData(String username) {
        this.username=username;
    }

    public void genreatePost(ActionEvent event) throws SQLException {
        if(!captionwrite.getText().equals(""))
        {
            String sql ="INSERT INTO `posts` (`postID`, `content`, `Username`, `pic`, `PostDate`) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connect.prepareStatement(sql);
            statement.setString(1, UUID.randomUUID().toString());
            statement.setString(2,captionwrite.getText());
            statement.setString(3,username);
            statement.setBinaryStream(4,postInputStream);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
            String timestamp = LocalDateTime.now().format(formatter);
            statement.setString(5,timestamp);
            statement.executeUpdate();
            captionwrite.setText("");
            postShowPic.setFill(null);

            DatabaseConnection connectNow= new DatabaseConnection();
            connect= connectNow.getConnect();
            try {
                poatContrainer.getChildren().clear();
                for(Post post:getPosts())
                {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/teamvoid/gocircle/fxml/post.fxml"));
                        Parent root = loader.load();
                        PostController controller = loader.getController();
                        controller.setPost(post);
                        poatContrainer.getChildren().add(root);
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void searching(KeyEvent keyEvent) {

    }

    public void imageChoose(MouseEvent mouseEvent) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        Stage stage = new Stage();
       File file = fileChooser.showOpenDialog(stage);
        stage.show();
        stage.close();
        if(file!=null){

        postInputStream = new FileInputStream(file);
        FileInputStream postShowInputStream=new FileInputStream(file);
        postShowPic.setFill(new ImagePattern(new Image(postShowInputStream)));}


    }
}


