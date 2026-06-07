package puppy.code;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Obstaculos {
    private Array<EntidadJuego> entidades = new Array<>();
    private long tiempoUltimoBarril;
    private final Texture texturaBarril;
    private final Texture texturaGasolina; 
    private final Sound sonidoImpacto;
    private MotoAcuatica moto;

    public Obstaculos(Texture barril, Texture gasolina, Sound impacto) {
        this.texturaBarril = barril;
        this.texturaGasolina = gasolina;
        this.sonidoImpacto = impacto;
    }

    public void setMoto(MotoAcuatica moto) { this.moto = moto; }

    public void actualizar() {
        if (moto == null) return;
        if (TimeUtils.nanoTime() - tiempoUltimoBarril > 1_000_000_000L) spawnEntidad();

        for (int i = 0; i < entidades.size; i++) {
            EntidadJuego e = entidades.get(i);
            e.actualizar();

            if (e.y < -64) {
                entidades.removeIndex(i); i--;
            } else if (e.getArea().overlaps(moto.getArea())) {
                
                
                entidades.removeIndex(i); i--;
            }
        }
    }

    private void spawnEntidad() {
        float x = MathUtils.random(0, 800 - 64);
        if (MathUtils.random(1, 5) == 1) {
            entidades.add(new BarrilGasolina(x, 480, texturaGasolina));
        } else {
        	entidades.add(new Barril(x, 480, texturaBarril, sonidoImpacto));
        }
        tiempoUltimoBarril = TimeUtils.nanoTime();
    }

    public void dibujar(SpriteBatch batch) {
        for (EntidadJuego e : entidades) {
            e.dibujar(batch);
        }
    }
    
    public void destruir() {
        texturaBarril.dispose();
        texturaGasolina.dispose(); 
        for (EntidadJuego e : entidades) {
            e.destruir();
        }
    }
}