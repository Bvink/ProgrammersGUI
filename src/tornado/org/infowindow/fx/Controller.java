package tornado.org.infowindow.fx;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tornado.org.infowindow.constants.Constants;
import tornado.org.infowindow.objects.Pioneer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML ListView<String> lst;
    @FXML Label nameLbl;
    @FXML Label lifeTimeLbl;
    @FXML Label knownForLbl;
    @FXML ImageView imageView;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        final List<Pioneer> collection = importCSV();
        ObservableList<String> items = FXCollections.observableArrayList(getNames(collection));
        lst.setItems(items);

        lst.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                nameLbl.setText((newValue != null) ? newValue : "");
                int loc = lst.getSelectionModel().getSelectedIndex();
                lifeTimeLbl.setText(collection.get(loc).getLifeTime());
                knownForLbl.setText(collection.get(loc).getKnownFor());
                Image image = new Image(collection.get(loc).getImage());
                imageView.setImage(image);

            }
        });
    }

    private List<Pioneer> importCSV() {
        List<Pioneer> collection = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + Constants.CSV_LOCATION));
            String stringRead = br.readLine();
            int i = 0;
            while (stringRead != null) {
                Pioneer p = new Pioneer();
                String[] elements = stringRead.split(",");
                p.setName(" " + elements[0]);
                p.setLifeTime((elements[1].length() > 7) ? elements[1] : elements[1].substring(0, 6) + "now");
                p.setKnownFor((elements.length > 3) ? elements[2] + "," + elements[3] : elements[2]);
                p.setImage(( i < Constants.IMAGE_URLS.length) ? Constants.IMAGE_URLS[i] : Constants.NO_IMAGE);
                collection.add(p);
                stringRead = br.readLine();
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return collection;
    }

    private List<String> getNames(List<Pioneer> collection) {
        List<String> ls = new ArrayList<>();
        for (Pioneer p : collection) {
            ls.add(p.getName());
        }
        return ls;
    }

}
