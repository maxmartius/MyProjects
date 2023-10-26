package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class KochKurvenController implements Initializable {

	ObservableList<String> initAuswahlList = FXCollections.observableArrayList("custom", "Linie",
			"Quadrat (Kuven innen)", "Quadrat (Kurven auﬂen)", "gleichseitiges Dreieck", "Max");
	ObservableList<String> genAuswahlList = FXCollections.observableArrayList("custom", "Generator 1", "Generator 2",
			"Generator 3");

	@FXML private Canvas canvas;

	@FXML private ChoiceBox<String> InitAuswahl;
	@FXML private TextField InitX1;
	@FXML private TextField InitY1;
	@FXML private TextField InitX2;
	@FXML private TextField InitY2;
	@FXML private Button InitNextLine;
	@FXML private Button InitFertig;

	@FXML private ChoiceBox<String> GenAuswahl;
	@FXML private TextField GenX1;
	@FXML private TextField GenY1;
	@FXML private TextField GenX2;
	@FXML private TextField GenY2;
	@FXML private Button GenNextLine;
	@FXML private Button GenFertig;

	@FXML private TextField MinLaenge;
	@FXML private Button MinLaengeFestlegen;
	@FXML private Label MinLaengeLabel;

	@FXML private Button clear;
	@FXML private Button run;
	@FXML private Button runOnce;
	@FXML private Button refactor;

	@FXML private Slider slider;
	@FXML private Button export;
	@FXML private Button close;

	private List<Linie> initiator = new ArrayList<Linie>();
	private List<Linie> generator = new ArrayList<Linie>();
	private Initiator init;
	private Generator gen;
	private double minLaenge;
	private boolean initiatorExistiert;
	private boolean generatorExistiert;
	private boolean minLaengeExistiert;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		InitAuswahl.setValue("custom");
		InitAuswahl.setItems(initAuswahlList);
		GenAuswahl.setValue("custom");
		GenAuswahl.setItems(genAuswahlList);
		initiatorExistiert = false;
		generatorExistiert = false;
		minLaengeExistiert = false;
	}

	@FXML
	public void handleInitNextLine() {

		if (InitX1.getText().matches("-?\\d+([.]{1}\\d+)?") && InitY1.getText().matches("-?\\d+([.]{1}\\d+)?")
				&& InitX2.getText().matches("-?\\d+([.]{1}\\d+)?") && InitY2.getText().matches("-?\\d+([.]{1}\\d+)?")) {

			double x1 = Double.parseDouble(InitX1.getText());
			double y1 = Double.parseDouble(InitY1.getText());
			Punkt uP = new Punkt(x1, y1);

			double x2 = Double.parseDouble(InitX2.getText());
			double y2 = Double.parseDouble(InitY2.getText());
			Punkt eP = new Punkt(x2, y2);

			Linie l = new Linie(uP, eP);

			initiator.add(l);

			InitX1.clear();
			InitY1.clear();
			InitX2.clear();
			InitY2.clear();

		}
	}

	public void handleInitFertig() {
		handleInitNextLine();
		if (InitAuswahl.getValue() == "custom" && !initiator.isEmpty()) {
			Initiator init = new Initiator(initiator);
			this.init = init;
		} else if (InitAuswahl.getValue() == "Linie") {
			Initiator init = new Initiator(1);
			this.init = init;
		} else if (InitAuswahl.getValue() == "Quadrat (Kuven innen)") {
			Initiator init = new Initiator(2);
			this.init = init;
		} else if (InitAuswahl.getValue() == "Quadrat (Kurven auﬂen)") {
			Initiator init = new Initiator(3);
			this.init = init;
		} else if (InitAuswahl.getValue() == "gleichseitiges Dreieck") {
			Initiator init = new Initiator(4);
			this.init = init;
		} else if (InitAuswahl.getValue() == "Max") {
			Initiator init = new Initiator(5);
			this.init = init;
		} else {
			
		}
		initiatorExistiert = true;

	}

	public void handleGenNextLine() {

		if (GenX1.getText().matches("-?\\d+([.]{1}\\d+)?") && GenY1.getText().matches("-?\\d+([.]{1}\\d+)?")
				&& GenX2.getText().matches("-?\\d+([.]{1}\\d+)?") && GenY2.getText().matches("-?\\d+([.]{1}\\d+)?")) {

			double x1 = Double.parseDouble(GenX1.getText());
			double y1 = Double.parseDouble(GenY1.getText());
			Punkt uP = new Punkt(x1, y1);

			double x2 = Double.parseDouble(GenX2.getText());
			double y2 = Double.parseDouble(GenY2.getText());
			Punkt eP = new Punkt(x2, y2);

			Linie l = new Linie(uP, eP);
			generator.add(l);

			GenX1.clear();
			GenY1.clear();
			GenX2.clear();
			GenY2.clear();
		}
	}

	public void handleGenFertig() {
		handleGenNextLine();
		if (GenAuswahl.getValue() == "custom" && !generator.isEmpty()) {
			Generator gen = new Generator(this.init, generator);
			this.gen = gen;
		} else if (GenAuswahl.getValue() == "Generator 1") {
			Generator gen = new Generator(this.init, 1);
			this.gen = gen;
		} else if (GenAuswahl.getValue() == "Generator 2") {
			Generator gen = new Generator(this.init, 2);
			this.gen = gen;
		} else if (GenAuswahl.getValue() == "Generator 3") {
			Generator gen = new Generator(this.init, 3);
			this.gen = gen;
		} else {

		}
		generatorExistiert = true;
	}

	public void handleMinLaengeFestlegen() {
		if (MinLaenge.getText().matches("-?\\d+([.]{1}\\d+)?")) {
			this.minLaenge = Double.parseDouble(MinLaenge.getText());
			String s = String.valueOf(this.minLaenge);
			MinLaengeLabel.setText(s);
			MinLaenge.clear();
			minLaengeExistiert = true;
		}
	}

	public void handleClear() {
		initiator.clear();
		generator.clear();
		MinLaenge.clear();
		InitX1.clear();
		InitY1.clear();
		InitX2.clear();
		InitY2.clear();
		GenX1.clear();
		GenY1.clear();
		GenX2.clear();
		GenY2.clear();
		MinLaengeLabel.setText("");
		initiatorExistiert = false;
		generatorExistiert = false;
		minLaengeExistiert = false;
		ImageTools.clearCanvas(canvas);
	}

	public void handleRefactor() {
		double d = slider.getValue();
		if (initiatorExistiert && generatorExistiert) {
			ImageTools.clearCanvas(canvas);
			canvas = ImageTools.displayLines(canvas, gen.getInitiator().getLinien(), d);
		} else {

		}
	}

	public void handleRun() {
		double d = slider.getValue();
		if (initiatorExistiert && generatorExistiert && minLaengeExistiert) {
			ImageTools.clearCanvas(canvas);
			gen.generiereMinLaenge(this.minLaenge);
			canvas = ImageTools.displayLines(canvas, gen.getInitiator().getLinien(), d);
		} else {

		}

	}

	public void handleRunOnce() {
		double d = slider.getValue();
		if (initiatorExistiert && generatorExistiert) {
			ImageTools.clearCanvas(canvas);
			gen.generiereSchrittweise();
			canvas = ImageTools.displayLines(canvas, gen.getInitiator().getLinien(), d);
		} else {

		}
	}
	
	public void handleExport() {
		ImageTools.exportImage(canvas);
	}
	
	public void handleClose() {
		
	}

}