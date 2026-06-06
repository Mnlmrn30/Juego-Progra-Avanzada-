package puppy.code;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface EntidadJuego {
    void crear();
    void actualizar();
    void dibujar(SpriteBatch batch);
    void destruir();
}
