package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class Barril extends EntidadJuego {
    private float velocidad = 280f;
    private EstrategiaColision estrategia; 

    public Barril(float x, float y, Texture textura) {
        super(x, y, textura);
        this.estrategia = new EstrategiaRestaVida();
    }

    @Override
    public void chocar(MotoAcuatica moto) {
        if (this.estrategia != null) {
            this.estrategia.ejecutarColision(moto);
        }
    }

    @Override
    public void actualizar() {
        this.y -= velocidad * Gdx.graphics.getDeltaTime();
        this.area.y = this.y;
    }
}