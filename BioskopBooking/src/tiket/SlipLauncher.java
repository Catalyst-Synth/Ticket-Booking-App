package tiket;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Map;

public class SlipLauncher {

    public static void launchSlip(Map<String, String> data) {
        final JFXPanel fxPanel = new JFXPanel();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(SlipLauncher.class.getResource("/resources/Slip.fxml"));
                    Parent root = loader.load();

                    TicketSlipController controller = loader.getController();
                    controller.setData(data);

                    Stage stage = new Stage();
                    stage.setTitle("TIKET.IN");
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
