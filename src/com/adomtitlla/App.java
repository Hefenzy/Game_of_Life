package com.adomtitlla;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
/*
* JavaFX App
* Game Of Life
* App tested on Ubuntu 19.10 with Gnome3 GUI.
* Maybe on Windows, macOS or other GUI the frame can be crooked.
* I don't know JavaFX good, so maybe I shitcoded.
*/
public class App extends Application {

    @Override
    public void start(Stage stage) {
        MainView mainView = new MainView();
        Scene scene = new Scene(mainView, 800, 826);    // New window with mainView
        stage.setTitle("Game Of Life");                       // Title
        stage.setResizable(false);                            // Can't resize
        stage.setScene(scene);                                // Set scene
        stage.show();

        mainView.draw();    // And draw. All simple
    }

    public static void main(String[] args) {
        launch();
    }
}
