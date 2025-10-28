package com.teamvoid.gocircle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ChatboxController {

    @FXML
    private ImageView chatNamepic;

    @FXML
    private ImageView chatProfilepic;

    @FXML
    private ImageView chatboxpic;

    @FXML
    private Label chatname;

    @FXML
    private Label profilename;

    @FXML
    private TextField search;

    @FXML
    private TextArea textarea;
    private String username;

    @FXML
    void attach(MouseEvent event) {

    }

    @FXML
    void send(ActionEvent event) {

    }

    public void setData(String username) {
        this.username=username;
    }
    private void loadChat() throws IOException {

    }
}
