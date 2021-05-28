package main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Stack;

public class FXMLHelper {
    private static final String pathToScreens = "../fx/fxml/";
    private static Stage primaryStage;
    private static Stack<Scene> chronologicalScenes = new Stack<>();

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    private static void createScreen(Parent root, Object controller) {
        chronologicalScenes.push(primaryStage.getScene());
        Scene scene = new Scene(root);
        scene.setUserData(controller);
        setScene(scene);
    }

    public static void loadScreen(String screen) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(FXMLHelper.class.getResource(pathToScreens + screen + ".fxml"));
            loader.load();
            Parent root = loader.getRoot();
            Object controller = loader.getController();
            createScreen(root, controller);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> T loadScreenReturnController(String screen) {
        T controller = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(FXMLHelper.class.getResource(pathToScreens + screen + ".fxml"));
            loader.load();
            Parent root = loader.getRoot();
            controller = loader.getController();
            createScreen(root, controller);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return controller;
    }

    public static void backScreen() {
        if (chronologicalScenes.size() > 1) {
            Scene pop = chronologicalScenes.pop();
            pop.getUserData();
            setScene(pop);
        }
    }

    public static <T> T backScreenReturnController() {
        T controller = null;
        if (chronologicalScenes.size() > 1) {
            Scene pop = chronologicalScenes.pop();
            controller =  (T)pop.getUserData();
            setScene(pop);
        }
        return controller;
    }

    private static void setScene(Scene scene) {
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();
        primaryStage.setHeight(primaryStage.getHeight());
        primaryStage.setWidth(primaryStage.getWidth());
    }
}
