package td.controleur;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.util.Duration;
import td.modele.*;
import td.modele.personnage.InfecteSansSymp;
import td.modele.tir.TirVitamine;
import td.modele.tourelle.Tourelle;
import td.modele.tourelle.TourelleVitamine;
import td.vue.VueMap;
import td.vue.VuePers;
import td.vue.vueTourelle;

import java.net.URL;
import java.util.ResourceBundle;

public class Controleur implements Initializable {

    @FXML
    private TilePane tilePaneMap;
    @FXML
    private Pane panePers;
    @FXML
    private Label labelPV;
    @FXML
    private Label labelVague;
    @FXML
    private Label labelScore;
    @FXML
    private Label labelMoney;

    private Partie partie;
    private VueMap vM;
    private VuePers vP;
    private vueTourelle vT;
    private Timeline gameLoop;
    
    IntegerProperty nbTour;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.partie = new Partie();
        vM = new VueMap(partie.getEnv().getMap(), tilePaneMap);
        this.partie.getEnv().getTirs().addListener(new TirsListener(panePers));
        initGame();
        this.partie.getEnv().creerArbre();
        this.nbTour = new SimpleIntegerProperty();
        this.nbTour.set(0);
        this.partie.vagueProperty().addListener((obs,old,nouv) -> this.labelVague.textProperty().setValue(nouv.toString()));
        this.partie.scoreProperty().addListener((obs,old,nouv) -> this.labelScore.textProperty().setValue(nouv.toString()));
        this.partie.moneyProperty().addListener((obs,old,nouv) -> this.labelMoney.textProperty().setValue(nouv.toString()));
        this.partie.pvProperty().addListener((obs,old,nouv) -> this.labelPV.textProperty().setValue(nouv.toString()));
    }
    
    private void initGame() {
		gameLoop = new Timeline();
		gameLoop.setCycleCount(Timeline.INDEFINITE);
		
		KeyFrame kf = new KeyFrame(Duration.seconds(0.15),(ev ->{
			if(this.partie.estPerdu()){
				System.out.println("perdu");
				System.out.println("game over");
				gameLoop.stop();
			}
			else if(this.partie.niveauFini()) {
				System.out.println("Niveau fini");
				gameLoop.stop();
			}
			else {
				this.partie.unTour();
				this.nbTour.set(this.nbTour.getValue() + 1);
			}
		}));
		gameLoop.getKeyFrames().add(kf);
	}
    
	@FXML
    void CreePers(ActionEvent event) {
		this.partie.getEnv().ajouterPers(new InfecteSansSymp(0, 22, this.partie.getEnv()));
    	vP = new VuePers();
    	vP.translateXProperty().bind(this.partie.getEnv().getPersos().get(0).getXProperty());
    	vP.translateYProperty().bind(this.partie.getEnv().getPersos().get(0).getYProperty());
    	this.nbTour.addListener(e -> vP.changerSprite(nbTour.getValue()));
    	this.panePers.getChildren().add(vP);
    }

	@FXML
    void creerTourelle(ActionEvent event) {
    	Tourelle t = new TourelleVitamine(0, 0, partie.getEnv());
		new TirVitamine(t.getX(), t.getY(), 20,  555, partie.getEnv());
		panePers.getChildren().add(new vueTourelle(t));
	}


    @FXML
    void action(ActionEvent event) {
    	gameLoop.play();
    }
}