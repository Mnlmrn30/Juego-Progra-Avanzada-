package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class BarrilGasolina extends EntidadJuego {
    private float velocidad = 150f;

    public BarrilGasolina(float x, float y, Texture textura) {
        super(x, y, textura);
    }

    @Override
    public void actualizar() {
        this.y -= velocidad * Gdx.graphics.getDeltaTime();
        this.area.y = this.y;
    }
}
