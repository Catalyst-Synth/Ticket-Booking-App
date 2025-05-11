package tiket;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.Map;

public class TicketSlipController {

    @FXML
    private Label filmTitleLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label ticketCountLabel;

    @FXML
    private Label seatLabel;

    public void setData(Map<String, String> data) {
        filmTitleLabel.setText(data.get("filmTitle"));
        dateLabel.setText(data.get("date"));
        timeLabel.setText(data.get("time"));
        emailLabel.setText(data.get("email"));
        ticketCountLabel.setText(data.get("ticketCount"));
        seatLabel.setText(data.get("seat"));
    }
}
