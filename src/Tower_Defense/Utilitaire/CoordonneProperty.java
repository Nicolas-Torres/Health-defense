package Tower_Defense.Utilitaire;

import javafx.beans.property.SimpleDoubleProperty;

public interface CoordonneProperty {
    SimpleDoubleProperty getXProperty();
    SimpleDoubleProperty getYProperty();

    double getX();
    double getY();
}