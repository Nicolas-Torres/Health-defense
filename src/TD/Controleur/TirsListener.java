package TD.Controleur;

import TD.Modele.Tir.Tir;
import TD.Modele.Tir.TirVitamine;
import TD.Vue.VueTir;
import TD.Vue.VueTirVitamine;
import javafx.collections.SetChangeListener;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;


public class TirsListener implements SetChangeListener<Tir> { // pourquoi Set
    Pane map;
    Map <Tir, VueTir> modelToView;

    public TirsListener(Pane map) {
        this.map = map;
        this.modelToView = new HashMap<>();
    }

    @Override
    public void onChanged(SetChangeListener.Change<? extends Tir> change) {
        // Si ajout
        if (change.wasAdded()) {
            Tir tir = change.getElementAdded();
            VueTir vT;
            if (tir instanceof TirVitamine) {
                vT = new VueTirVitamine(tir);
            } // else autres tir
            else {
                throw new IllegalArgumentException();
            }
            // garder en memoire association du tir avec son image
            modelToView.put(tir, vT);
            map.getChildren().add(vT);
        }

        if (change.wasRemoved()) {
            map.getChildren().remove(modelToView.get(change.getElementRemoved()));
        }
    }
}