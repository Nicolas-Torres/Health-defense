package TD.Modele.Tir;

import TD.Modele.Environnement;
import TD.Utilitaire.Position;
import TD.Utilitaire.PositionProperty;
import TD.Utilitaire.Vecteur;
import javafx.beans.property.DoubleProperty;

import java.util.Objects;

public abstract class Tir {
    private static long idMax = 0;
    private long id;
    protected PositionProperty positionProperty;
    protected int pointAttaque;
    protected Vecteur direction;
    protected Environnement env;
    protected int hitbox;

    public Tir(Position p, int pointAttaque, int hitbox, Environnement env) {
        this.positionProperty = new PositionProperty(p.getX(), p.getY());
        this.pointAttaque = pointAttaque;
        direction = new Vecteur();
        this.env = env;
        this.hitbox = hitbox;
        this.id = idMax++;
    }

    public static boolean estDansMap(double positionX, double positionY) {
        return (positionX > 0 && positionX < 800 && positionY > 0 && positionY < 480);
    }

    public abstract void agit();

    /**** Getter et Setter ****/

    public double getX() {
        return positionProperty.getX();
    }

    public DoubleProperty xProperty() {
        return positionProperty.getXProperty();
    }

    public void setX(int x) {
        this.positionProperty.setX(x);
    }

    public double getY() {
        return positionProperty.getY();
    }

    public DoubleProperty yProperty() {
        return positionProperty.getYProperty();
    }

    public void setY(int y) {
        this.positionProperty.setY(y);
    }

    public PositionProperty getPosition() {
        return positionProperty;
    }

    // Verif l'égalité comme un ==
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tir tir = (Tir) o;
        return id == tir.id;
    }

    // fonction similaire au equals qui va verif l'égalité entre deux objects
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}