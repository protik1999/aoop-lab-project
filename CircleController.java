package com.teamvoid.gocircle;

import com.teamvoid.gocircle.circlemod.CircleModel;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CircleController implements Initializable {
    Connection connect;
    @FXML
    private GridPane grid;

    @FXML
    private TextField search;
    private String username;
    private StackPane changepane;

    public void setChangepane(StackPane changepane) {
        this.changepane = changepane;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @FXML
    void searching(KeyEvent event) {

    }

    private ArrayList<CircleModel> getCircleList() {
        ArrayList<CircleModel> circleModels = new ArrayList<>();
//        String sql = "SELECT * FROM circle WHERE userID = '" + username + "'";
        String sql = "SELECT * FROM circle";
        try {
            Statement statement = connect.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                circleModels.add(new CircleModel(resultSet.getString(1), resultSet.getString(2), resultSet.getBlob(3), resultSet.getString(4)));
            }
            System.out.println(circleModels.size());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return circleModels;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()->{
       DatabaseConnection connectNow= new DatabaseConnection();
            connect= connectNow.getConnect();
            try {
                ArrayList<CircleModel> circleModels = getCircleList();
                if(true) {
                    FadeTransition fadeTransition = new FadeTransition();
                    fadeTransition.setNode(grid);
                    fadeTransition.setDuration(Duration.seconds(1));
                    fadeTransition.setFromValue(0);
                    fadeTransition.setToValue(1);
                    fadeTransition.play();
                    int col = 0;
                    int row = 1;
                    for (CircleModel circleModel : circleModels) {
                        try {
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("/com/teamvoid/gocircle/fxml/circlecard.fxml"));
                            Parent root = loader.load();

                            CirclecardController controller = loader.getController();
                            controller.setChangepane(changepane);
                            controller.setUsername(username);
                            controller.setCircleModel(circleModel);
                            if (col == 4) {
                                col = 0;
                                ++row;
                            }
                            grid.add(root, col++, row);
                            GridPane.setMargin(root, new Insets(20));

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        });
  }
}

