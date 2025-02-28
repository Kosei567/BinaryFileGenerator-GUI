package org.example.unko;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BFGApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(BFGApplication.class.getResource("bfg-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 650, 425);
        stage.setTitle("BinaryFileGenerator");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {launch();}
}