package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Obstaculos implements EntidadJuego {

	private Array<Rectangle> posicionesBarriles;
	private long tiempoUltimoBarril;

	private final Texture texturaBarril;
	private final Sound   sonidoImpacto;

	private final float velocidadBarril = 280f;

	// La moto se inyecta antes de llamar actualizar()
	private MotoAcuatica moto;

	public Obstaculos(Texture barril, Sound impacto) {
		this.texturaBarril = barril;
		this.sonidoImpacto  = impacto;
	}

	public void setMoto(MotoAcuatica moto) {
		this.moto = moto;
	}

	@Override
	public void crear() {
		posicionesBarriles = new Array<>();
		spawnBarril();
	}

	private void spawnBarril() {
		Rectangle barril = new Rectangle();
		barril.x = MathUtils.random(0, 800 - 64);
		barril.y = 480;
		barril.width  = 64;
		barril.height = 64;
		posicionesBarriles.add(barril);
		tiempoUltimoBarril = TimeUtils.nanoTime();
	}

	@Override
	public void actualizar() {
		if (moto == null) return;

		if (TimeUtils.nanoTime() - tiempoUltimoBarril > 1_200_000_000L) spawnBarril();

		for (int i = 0; i < posicionesBarriles.size; i++) {
			Rectangle barril = posicionesBarriles.get(i);
			barril.y -= velocidadBarril * Gdx.graphics.getDeltaTime();

			if (barril.y + 64 < 0) {
				posicionesBarriles.removeIndex(i);
				i--;
				continue;
			}

			if (barril.overlaps(moto.getArea())) {
				moto.recibirImpacto();
				posicionesBarriles.removeIndex(i);
				i--;
			}
		}
	}

	@Override
	public void dibujar(SpriteBatch batch) {
		for (Rectangle barril : posicionesBarriles) {
			batch.draw(texturaBarril, barril.x, barril.y);
		}
	}

	@Override
	public void destruir() {
		texturaBarril.dispose();

	}
}
