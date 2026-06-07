package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Obstaculos { // Ya no extiende EntidadJuego
    private Array<Barril> barriles = new Array<>(); // Lista de objetos Barril
    private long tiempoUltimoBarril;
    private final Texture texturaBarril;
    private final Sound sonidoImpacto;
    private MotoAcuatica moto;

    public Obstaculos(Texture barril, Sound impacto) {
        this.texturaBarril = barril;
        this.sonidoImpacto = impacto;
    }

    public void setMoto(MotoAcuatica moto) { this.moto = moto; }

    public void crear() { spawnBarril(); }

    private void spawnBarril() {
        Barril b = new Barril(MathUtils.random(0, 800 - 64), 480, texturaBarril);
        barriles.add(b);
        tiempoUltimoBarril = TimeUtils.nanoTime();
    }

    public void actualizar() {
        if (moto == null) return;
        if (TimeUtils.nanoTime() - tiempoUltimoBarril > 1_200_000_000L) spawnBarril();

        for (int i = 0; i < barriles.size; i++) {
            Barril b = barriles.get(i);
            b.actualizar(); // Polimorfismo: el barril sabe moverse solo

            if (b.y < -64) {
                barriles.removeIndex(i);
                i--;
            } else if (b.getArea().overlaps(moto.getArea())) {
                moto.recibirImpacto();
                sonidoImpacto.play();
                barriles.removeIndex(i);
                i--;
            }
        }
    }

    public void dibujar(SpriteBatch batch) {
        for (Barril b : barriles) {
            b.dibujar(batch); // Herencia: el barril sabe dibujarse solo
        }
    }

    public void destruir() { texturaBarril.dispose(); }
}