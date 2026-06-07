package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class BarrilGasolina extends EntidadJuego {
    private float velocidad = 150f;
    private EstrategiaColision estrategia; 

    public BarrilGasolina(float x, float y, Texture textura) {
        super(x, y, textura);
        this.estrategia = new EstrategiaSumaGasolina();
    }

    @Override
    public void chocar(MotoAcuatica moto) {
        if (this.estrategia != null) {
            this.estrategia.ejecutarColision(moto);
        }
    }

    @Override
    protected void mover() {
        this.y -= velocidad * Gdx.graphics.getDeltaTime();
    }

    @Override
    protected void comprobarLimites() {
    }

    @Override
    protected void actualizarTextura() {
        this.area.y = this.y;
    }
}
