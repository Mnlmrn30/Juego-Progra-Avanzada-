package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Barril extends EntidadJuego {
    private float velocidad = 280f;

    public Barril(float x, float y, Texture textura) {
        super(x, y, textura);
    }

    @Override
    public void actualizar() {
        this.y -= velocidad * com.badlogic.gdx.Gdx.graphics.getDeltaTime();
        this.area.y = this.y;
    }
}