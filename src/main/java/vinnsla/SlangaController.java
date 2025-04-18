package vinnsla;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;
import java.util.List;

public class SlangaController {

    @FXML private GridPane fxBord;
    @FXML private Label fxSkilabod1;
    @FXML private Label fxSkilabod2;
    @FXML private Button fxTeningur;
    @FXML private Button fxNyrLeikur;
    @FXML private ImageView fxTeningurMynd;

    private Leikur leikur;
    private List<javafx.scene.Node> reitir;
    private final Point2D[] gridmap = new Point2D[25];

    @FXML
    private void initialize() throws FileNotFoundException {
        setjaUppGridMap();
        leikur = new Leikur(4, 6);
        reitir = fxBord.getChildren();

        buaTilReiti();
        bindaViðLeik();
        setjaUpplýsingar();
        uppfaeraBord();

        fxTeningurMynd.setImage(
                new Image(getClass().getResource("/vinnsla/css/Myndir/dice-one.png").toExternalForm())
        );
    }

    private void setjaUppGridMap() {
        int counter = 1;
        for (int i = fxBord.getRowCount() - 1; i >= 0; i--) {
            if (i % 2 == 1) {
                for (int j = 0; j < fxBord.getColumnCount(); j++) {
                    gridmap[counter++] = new Point2D(i, j);
                }
            } else {
                for (int j = fxBord.getColumnCount() - 1; j >= 0; j--) {
                    gridmap[counter++] = new Point2D(i, j);
                }
            }
        }
    }

    private void bindaViðLeik() {
        fxNyrLeikur.disableProperty().bind(leikur.leikLokidProperty().not());
        fxTeningur.disableProperty().bind(leikur.leikLokidProperty());
        fxTeningur.textProperty().bind(leikur.getTeningur().getTalaProperty().asString());
    }

    private void setjaUpplýsingar() {
        fxSkilabod1.textProperty().bind(
                leikur.getLeikmadur(0).reiturProperty().asString("Reitur: %d")
        );

        fxSkilabod2.textProperty().bind(
                Bindings.when(leikur.leikLokidProperty())
                        .then(leikur.sigurvegariProperty())
                        .otherwise(
                                Bindings.createStringBinding(
                                        () -> "Næstur: " +
                                                leikur.getLeikmadur(leikur.nuverandiLeikmadurProperty().get()).getNafn(),
                                        leikur.nuverandiLeikmadurProperty()
                                )
                        )
        );

        leikur.getLeikmadur(0).reiturProperty().addListener((obs, oldVal, newVal) -> uppfaeraBord());
        leikur.getLeikmadur(1).reiturProperty().addListener((obs, oldVal, newVal) -> uppfaeraBord());
    }

    private void buaTilReiti() {
        for (int i = fxBord.getRowCount() - 1; i >= 0; i--) {
            for (int j = 0; j < fxBord.getColumnCount(); j++) {
                Label reiturLabel = new Label("");
                reiturLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                reiturLabel.setStyle("-fx-background-color: green");
                fxBord.setConstraints(reiturLabel, j, i);
                fxBord.getChildren().add(reiturLabel);
            }
        }
    }

    private String talaIStreng(int tala) {
        return switch (tala) {
            case 1 -> "one";
            case 2 -> "two";
            case 3 -> "three";
            case 4 -> "four";
            case 5 -> "five";
            case 6 -> "six";
            default -> "one";
        };
    }

    @FXML
    private void teningurHandler() {
        leikur.leikaLeik();
        int gildi = leikur.getTeningur().getTala();
        String myndPath = String.format("/vinnsla/css/Myndir/dice-%s.png", talaIStreng(gildi));
        fxTeningurMynd.setImage(new Image(getClass().getResource(myndPath).toExternalForm()));
        uppfaeraBord();
    }

    @FXML
    private void nyrLeikurHandler() {
        leikur.nyrLeikur();
        fxTeningurMynd.setImage(new Image(getClass().getResource("/vinnsla/css/Myndir/dice-one.png").toExternalForm()));
        uppfaeraBord();
    }

    /**
     * Uppfærir stöðu reita og leikmanna á borði.
     */
    private void uppfaeraBord() {
        hreinsaReiti();

        for (int reiturNr = 1; reiturNr < 25; reiturNr++) {
            Point2D pos = gridmap[reiturNr];
            Label label = (Label) getNodeByRowColumnIndex((int) pos.getX(), (int) pos.getY(), fxBord);

            if (leikur.getSlongurStigar().erStigi(reiturNr)) {
                ImageView stigiMynd = new ImageView(new Image(getClass().getResource("/vinnsla/css/Myndir/greenLadder.png").toExternalForm()));
                stigiMynd.setFitWidth(60);
                stigiMynd.setFitHeight(60);
                label.setGraphic(stigiMynd);
            } else if (leikur.getSlongurStigar().erSlanga(reiturNr)) {
                ImageView slangaMynd = new ImageView(new Image(getClass().getResource("/vinnsla/css/Myndir/snake.png").toExternalForm()));
                slangaMynd.setFitWidth(60);
                slangaMynd.setFitHeight(60);
                label.setGraphic(slangaMynd);
            }
        }

        teiknaLeikmenn();
    }

    private void hreinsaReiti() {
        for (javafx.scene.Node reitur : reitir) {
            if (reitur instanceof Label label) {
                label.setStyle("-fx-background-color: white;");
                label.setGraphic(null);
            }
        }
    }

    private void teiknaLeikmenn() {
        int reitur1 = leikur.getLeikmadur(0).getReitur();
        int reitur2 = leikur.getLeikmadur(1).getReitur();

        if (reitur1 >= 0) {
            Point2D pos = gridmap[reitur1];
            Label label = (Label) getNodeByRowColumnIndex((int) pos.getX(), (int) pos.getY(), fxBord);
            label.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
        }

        if (reitur2 >= 0) {
            Point2D pos = gridmap[reitur2];
            Label label = (Label) getNodeByRowColumnIndex((int) pos.getX(), (int) pos.getY(), fxBord);
            label.setStyle("-fx-background-color: red; -fx-text-fill: black;");
        }
    }

    private javafx.scene.Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        for (javafx.scene.Node node : gridPane.getChildren()) {
            if (node instanceof Label &&
                    GridPane.getRowIndex(node) == row &&
                    GridPane.getColumnIndex(node) == column) {
                return node;
            }
        }
        return null;
    }
}
