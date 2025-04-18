package com.example.slanga;


import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import Vinnsla.Leikur;
import java.io.FileNotFoundException;
import java.util.List;

public class SlangaController {
    @FXML
    private GridPane fxBord;  // Borðið (4x6 reitir)
    @FXML
    private Label fxSkilabod1; // Skilaboð um slöngur/stiga
    @FXML
    private Label fxSkilabod2; // Hver á leikinn / Sigurvegari
    @FXML
    private Button fxTeningur; // Kasta teningi hnappur
    @FXML
    private Button fxNyrLeikur; // Nýr leikur hnappur
    @FXML
    private ImageView fxTeningurMynd;


    private Leikur leikur; // Leikurinn
    private List<javafx.scene.Node> reitir; // Listi af reitum í GridPane

    private Point2D gridmap[];

    @FXML
    private void initialize() throws FileNotFoundException {

        fxTeningurMynd.setImage(new Image(getClass().getResource("/com/example/slanga/css/Myndir/dice-one.png").toExternalForm()));
        gridmap = new Point2D[25];
        int counter = 1;
        for (int i = fxBord.getRowCount()-1; i >= 0 ; i-- ) {
            System.out.println( i % 2 );
            if ( ( i % 2 ) == 1 ) {
                for (int j = 0; j < fxBord.getColumnCount(); j++) {
                    gridmap[counter] = new Point2D(i, j);
                    counter++;
                }
            }
            else{
                for (int j = fxBord.getColumnCount()-1; j >= 0; j--) {
                    gridmap[counter]= new Point2D(i,j);
                    counter++;
                }
            }
        }

        leikur = new Leikur(4, 6); // Búa til nýjan leik
        reitir = fxBord.getChildren(); // Ná í reitina á borðinu
        for (int i = fxBord.getRowCount()-1; i >= 0 ; i-- ) {
          for (int j = 0; j < fxBord.getColumnCount(); j++ ){
              Label ble = new Label("");
              ble.setMaxHeight(Double.MAX_VALUE);
              ble.setMaxWidth(Double.MAX_VALUE);
              ble.setStyle("-fx-background-color: green" );
              fxBord.setConstraints (ble, j, i);
              fxBord.getChildren().add(ble);
          }
        }
        // Binda hnappa við leikstöðu
        fxNyrLeikur.disableProperty().bind(leikur.leikLokidProperty().not());
        fxTeningur.disableProperty().bind(leikur.leikLokidProperty());
        fxTeningur.textProperty().bind(leikur.getTeningur().getTalaProperty().asString());


        // Binda skilaboðasvæði við vinnslu
        fxSkilabod1.textProperty().bind(leikur.getLeikmadur(0).reiturProperty().asString("Reitur: %d"));
        fxSkilabod2.textProperty().bind(
                Bindings.when(leikur.leikLokidProperty())
                        .then(leikur.sigurvegariProperty())
                        .otherwise(Bindings.concat("Næstur: ", leikur.getLeikmadur(leikur.nuverandiLeikmadurProperty().get()).nafnProperty()))
        );

        // Setja upp listener fyrir leikmannahreyfingar
        leikur.getLeikmadur(0).reiturProperty().addListener((obs, oldValue, newValue) -> uppfaeraBord());
        leikur.getLeikmadur(1).reiturProperty().addListener((obs, oldValue, newValue) -> uppfaeraBord());
        uppfaeraBord();
    }
    private String talaIStreng(int tala) {
        switch (tala) {
            case 1: return "one";
            case 2: return "two";
            case 3: return "three";
            case 4: return "four";
            case 5: return "five";
            case 6: return "six";
            default: return "one";
        }
    }

    @FXML
    private void teningurHandler() {
        leikur.leikaLeik();
        int gildi = leikur.getTeningur().getTala();
        String myndPath = String.format("/com/example/slanga/css/Myndir/dice-%s.png", talaIStreng(gildi));
        fxTeningurMynd.setImage(new Image(getClass().getResource(myndPath).toExternalForm()));
        uppfaeraBord();
    }

    @FXML
    private void nyrLeikurHandler() {
        leikur.nyrLeikur();
        uppfaeraBord();
    }

    /**
     * Uppfærir borðið með stöðu leikmanna.
     */
    private void uppfaeraBord() {
        for (javafx.scene.Node reitur : reitir) {
            if (reitur instanceof Label label) {
                label.setStyle("-fx-background-color: white;");
                label.setGraphic(null); // Fjarlægja eldri mynd ef til staðar
            }
        }

        for (int reiturNr = 1; reiturNr < 25; reiturNr++) {
            Point2D inGridPoint = gridmap[reiturNr];
            Label label = (Label) getNodeByRowColumnIndex((int) inGridPoint.getX(), (int) inGridPoint.getY(), fxBord);

            if (leikur.getSlongurStigar().erStigi(reiturNr)) {
                System.out.println(String.format("Er að teikna stiga í reit : %d", reiturNr));
                ImageView stigiMynd = new ImageView(new Image(getClass().getResource("/com/example/slanga/css/Myndir/greenLadder.png").toExternalForm()));
                stigiMynd.setFitWidth(60);
                stigiMynd.setFitHeight(60);
                label.setGraphic(stigiMynd);
            } else if (leikur.getSlongurStigar().erSlanga(reiturNr)) {
                System.out.println(String.format("Er að teikna slöngu í reit : %d", reiturNr));
                ImageView slonguMynd = new ImageView(new Image(getClass().getResource("/com/example/slanga/css/Myndir/snake.png").toExternalForm()));
                slonguMynd.setFitWidth(60);
                slonguMynd.setFitHeight(60);
                label.setGraphic(slonguMynd);
            }
        }

        int reitur1 = leikur.getLeikmadur(0).getReitur();
        int reitur2 = leikur.getLeikmadur(1).getReitur();

        if (reitur1 >= 0) {
            System.out.println(String.format("Leikmaður 1 er í reit : %d", reitur1));
            Point2D inGridPoint = gridmap[reitur1];
            Label label = (Label) getNodeByRowColumnIndex((int) inGridPoint.getX(), (int) inGridPoint.getY(), fxBord);
            label.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
        }
        if (reitur2 >= 0) {
            System.out.println(String.format("Leikmaður 2 er í reit : %d", reitur2));
            Point2D inGridPoint = gridmap[reitur2];
            Label label = (Label) getNodeByRowColumnIndex((int) inGridPoint.getX(), (int) inGridPoint.getY(), fxBord);
            label.setStyle("-fx-background-color: red; -fx-text-fill: black;");
        }
    }


    private javafx.scene.Node getNodeByRowColumnIndex (final int row, final int column, GridPane gridPane) {
        javafx.scene.Node result = null;
        javafx.collections.ObservableList<javafx.scene.Node> childrens = gridPane.getChildren();

        for (javafx.scene.Node node : childrens) {
            if ( node.getClass() != Label.class ) {
                continue;
            }
            if(gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
    }
}