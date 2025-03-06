package orodent.clientrelationmanager.Main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import orodent.clientrelationmanager.model.App;
import orodent.clientrelationmanager.view.mainview.MainView;

import java.io.File;
import java.io.PrintStream;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        File file = new File("outStream.txt");
        PrintStream stream = new PrintStream(file);
        System.setOut(stream);

        App app = new App();
        app.setPrimaryStage(primaryStage);

        MainView mainView = new MainView(app);
        Scene scene = new Scene(mainView, 350, 250);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/clientdetailview.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/hotbar.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/annotation.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/clientinfoview.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/filterview.css")).toExternalForm());

        primaryStage.setTitle("Orodent");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}