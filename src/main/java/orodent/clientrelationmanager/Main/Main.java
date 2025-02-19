package orodent.clientrelationmanager.Main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import orodent.clientrelationmanager.model.App;
import orodent.clientrelationmanager.view.MainView;

import java.io.File;
import java.io.PrintStream;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        File file = new File("outStream.txt");
        PrintStream stream = new PrintStream(file);
        System.setOut(stream);

        App app = new App();

        MainView mainView = new MainView(app);
        primaryStage.setTitle("Orodent");
        primaryStage.setScene(new Scene(mainView, 780, 550));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}