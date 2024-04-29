package org.rplbo.nilaimhs.nilaimahasiswa;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;

public class NilaiMhsApplication extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        primaryStage.setTitle("Data Nilai Mahasiswa");
        primaryStage.setScene(new Scene(loadFXML("login-view")));
        primaryStage.show();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(NilaiMhsApplication.class
                .getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    public static void setRoot(String fxml, boolean isResizeable)
            throws IOException {
        primaryStage.getScene().setRoot(loadFXML(fxml));
        primaryStage.sizeToScene();
        primaryStage.setResizable(isResizeable);
    }

    public static void openViewWithModal(String fxml, boolean isResizeable)
            throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(loadFXML("nyoba-thok")));
        stage.sizeToScene();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage);
        stage.showAndWait();
//        Stage stage = new Stage();
//        stage.setScene(new Scene(loadFXML(fxml)));
//        stage.sizeToScene();
//        stage.setResizable(isResizeable);
//        stage.initOwner(primaryStage);
//        stage.initModality(Modality.WINDOW_MODAL);
//        stage.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }
}