package com.teamvoid.gocircle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {
    public static Stage stage = null;
    @Override
    public void start(Stage stage) throws IOException {

        this.stage = stage;
      FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/dfg.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);


        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}