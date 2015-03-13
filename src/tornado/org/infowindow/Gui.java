package tornado.org.infowindow;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tornado.org.infowindow.fx.Controller;

public class Gui extends Application {

    public void start(Stage stage) throws Exception {
        Platform.runLater(new Runnable() {
            public void run() {
                try {
                    final Parent root = FXMLLoader.load(Controller.class.getResource("dgui.fxml"));
                    final Stage stage = new Stage() {{
                        setScene(new Scene(root, 600, 400));
                        setTitle("GUI");
                        setResizable(false);
                        show();
                    }};
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
