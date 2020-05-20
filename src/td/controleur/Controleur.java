package td.controleur;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.util.Duration;
import td.modele.*;
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

    private Partie partie;

    private VueMap vM;
    private VuePers vP;
    private vueTourelle vT;
    
    private Timeline gameLoop;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.partie = new Partie();
        vM = new VueMap(partie.getEnv().getMap(), tilePaneMap);
        initGame();
    }
    
    private void initGame() {
		gameLoop = new Timeline();
		gameLoop.setCycleCount(Timeline.INDEFINITE);
		
		KeyFrame kf = new KeyFrame(Duration.seconds(0.2),(ev ->{
			if(this.partie.estPerdu()){
				System.out.println("game over");
				gameLoop.stop();
			}
			else if(this.partie.niveauFini()) {
				gameLoop.stop();
			}
			else {
				this.partie.unTour();
			}
		}));
		gameLoop.getKeyFrames().add(kf);
	}
    
	@FXML
    void CreePers(ActionEvent event) {
		System.out.println("creer pers 1");
    	vP = new VuePers(panePers, this.partie.getEnv());
    	vP.affichPers();
    	creerTourelle(); // a sup
    }


    void creerTourelle () {
    	Tourelle t = new TourelleVitamine(partie.getEnv());
		System.out.println(t);
		vT = new vueTourelle(panePers, t.getX(), t.getY());
	}


    @FXML
    void action(ActionEvent event) {
    	gameLoop.play();
    }
}