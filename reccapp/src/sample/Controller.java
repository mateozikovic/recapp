package sample;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    //Polje polja objekta Plovilo
    Plovilo[] ploviloObject = new Plovilo[100];
    // ploviloCounter koristimo za dodavanje novih objekata Plovilo u array
    int ploviloCounter = 0;
    ObservableList list = FXCollections.observableArrayList();
    // Kreiranje polja objekta Racun
    Racun[] racunObject = new Racun[100];
    // Broj odabira u listi
    int currentlySelectedListItem = 0;
    double ukupnoRac = 0;

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();

    Document document = new Document();

    @FXML
    private TextField imePlovila;
    @FXML
    private TextField duljinaPlovila;
    @FXML
    private TextField imeVlasnika;
    @FXML
    private TextField vez;
    @FXML
    private ListView listView;
    @FXML
    private ChoiceBox<String> vrstaPlovila;
    @FXML
    private Text iznosPoNocenju;
    @FXML
    private Text ukupanIznos;
    @FXML
    public Button potvrdaButton;
    @FXML
    public Button naplataButton;
    @FXML
    public TextField brojNocenjaField;
    @FXML
    public Button stornoButton;
    @FXML
    public AnchorPane secPane;

    // kreiranje dropdown izbornika za vrstu plovila
    private void loadList(){
        list.removeAll(list);
        String a="Motorno plovilo";
        String b="Plovilo na jedra";
        String c="Katamaran";
        list.addAll(a,b,c);
        vrstaPlovila.getItems().addAll(list);
    }

    // metoda potvrdaPress kreira novo Plovilo i Racun i popunjava listView
    public void potvrdaPress() {
        ploviloObject[ploviloCounter] = new Plovilo();
        ploviloObject[ploviloCounter].setImePlovila(imePlovila.getText());
        ploviloObject[ploviloCounter].setDuljinaPlovila(Integer.parseInt(duljinaPlovila.getText()));
        ploviloObject[ploviloCounter].setImeIPrezimeVlasnika(imeVlasnika.getText());
        ploviloObject[ploviloCounter].setVez(vez.getText());
        ploviloObject[ploviloCounter].setVrstaPlovila(vrstaPlovila.getValue());

        listView.getItems().add(
                "Vez: " + ploviloObject[ploviloCounter].getVez() + "   " +
                "Ime: " + ploviloObject[ploviloCounter].getImePlovila() + "   " +
                "Duljina: " + ploviloObject[ploviloCounter].getDuljinaPlovila() + "   " +
                ploviloObject[ploviloCounter].getVrstaPlovila() + "   " +
                ploviloObject[ploviloCounter].getImeIPrezimeVlasnika()
        );

        racunObject[ploviloCounter] = new Racun();

        ploviloCounter++;

        imePlovila.clear();
        duljinaPlovila.clear();
        vrstaPlovila.setValue("Motorno plovilo");
        imeVlasnika.clear();
        vez.clear();
    }


    // sa initialize popunjavamo dropdown izbornik
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadList();
    }

    // metoda handleMouseClick odabire item sa listViewa i postavlja iznosPoNocenju
    public void handleMouseClick(MouseEvent arg0) {
        int listIndex = listView.getSelectionModel().getSelectedIndex();
        currentlySelectedListItem = listView.getSelectionModel().getSelectedIndex();
        double iznosNoc = racunObject[listIndex].obracunaj();

        iznosPoNocenju.setText(String.valueOf(iznosNoc) + "0");
    }

    // metoda brojNocenjaEnter uzima vrijednost iz fielda iznosPoNocenju te mnozi sa vrijednosti iz brojNocenjaField
    // te ju upisuje u ukupanIznos
    public void brojNocenjaEnter(ActionEvent actionEvent) {
        double iznosNoc = Double.parseDouble(iznosPoNocenju.getText());
        double brojDana = Double.parseDouble(brojNocenjaField.getText());
        double ukupno = iznosNoc * brojDana;

        ukupanIznos.setText(String.valueOf(ukupno) + "0");

        ukupnoRac = ukupnoRac + ukupno;
    }

    // metoda naplataPress brise sa liste trenutno odabran item te izbacuje pop-up da je racun izdan.
    public void naplataPress(ActionEvent actionEvent) {
        listView.getItems().remove(currentlySelectedListItem);
        ukupanIznos.setText("0,00");
        iznosPoNocenju.setText("0,00");
        brojNocenjaField.clear();

        // generiraj pdf racuna
        try{
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(currentlySelectedListItem +"racun.pdf"));
            document.open();
            document.add(new Paragraph("Racun:  " + currentlySelectedListItem + "(" + now + ")"));
            document.add(new Paragraph("Plovilo:  " + ploviloObject[currentlySelectedListItem].getImePlovila()));
            document.add(new Paragraph("Vlasnik: "+ ploviloObject[currentlySelectedListItem].getImeIPrezimeVlasnika()));
            document.add(new Paragraph("Duljina: "+ ploviloObject[currentlySelectedListItem].getDuljinaPlovila() + " metara"));
            document.add(new Paragraph("Iznos racuna: " + ukupnoRac + " EUR"));

            document.close();
            writer.close();
        }catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }


        // Popup
        try{
            Stage window2 = new Stage();
            window2.initModality(Modality.APPLICATION_MODAL);
            window2.setTitle("Racun");
            Label label = new Label();
            label.setText("Racun je uspjesno izdan!");

            Button yesButton = new Button("Yes");
            yesButton.setOnAction(e -> {
                window2.close();
            });

            VBox layout = new VBox(10);

            layout.getChildren().addAll(label,yesButton);
            layout.setAlignment(Pos.CENTER);
            Scene scene = new Scene(layout, 250,200 );
            window2.setScene(scene);
            window2.showAndWait();
        } catch (Exception e){
            System.out.println("Can't load new window");
        }
    }

    //metoda stornoPress brise odabran item sa liste
    public void stornoPress(ActionEvent actionEvent) {
        listView.getItems().remove(currentlySelectedListItem);
        ukupanIznos.setText("0,00");
        iznosPoNocenju.setText("0,00");
        brojNocenjaField.clear();
    }
}



