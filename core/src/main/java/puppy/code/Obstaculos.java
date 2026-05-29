package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Obstaculos {

	private Array<Rectangle> posicionesBarriles;
	private long tiempoUltimoBarril;

	private Texture texturaBarril;
	private Sound sonidoImpacto;

	private float velocidadBarril = 280f;

	public Obstaculos(Texture barril, Sound impacto) {
		this.texturaBarril = barril;
		this.sonidoImpacto = impacto;
	}

	public void crear() {
		posicionesBarriles = new Array<Rectangle>();
		spawnBarril();
	}

	private void spawnBarril() {
		Rectangle barril = new Rectangle();
		barril.x = MathUtils.random(0, 800 - 64);
		barril.y = 480;
		barril.width  = 60; // Tamaño ajustado del barril
		barril.height = 80;
		posicionesBarriles.add(barril);
		tiempoUltimoBarril = TimeUtils.nanoTime();
	}

	public void actualizar(MotoAcuatica moto) {
		// --- Dificultad Dinámica ---
		long tiempoSpawnBase = 1_200_000_000L;
		long reduccion = moto.getDistancia() * 200_000L;
		long tiempoSpawnActual = tiempoSpawnBase - reduccion;

		// Límite para que no se vuelva imposible
		if (tiempoSpawnActual < 250_000_000L) {
			tiempoSpawnActual = 250_000_000L;
		}

		// Aumenta la velocidad de caída según la distancia
		float velocidadActual = velocidadBarril + (moto.getDistancia() * 0.06f);

		if (TimeUtils.nanoTime() - tiempoUltimoBarril > tiempoSpawnActual) {
			spawnBarril();
		}

		for (int i = 0; i < posicionesBarriles.size; i++) {
			Rectangle barril = posicionesBarriles.get(i);

			barril.y -= velocidadActual * Gdx.graphics.getDeltaTime();

			if (barril.y + barril.height < 0) {
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

	public void dibujar(SpriteBatch batch) {
		for (Rectangle barril : posicionesBarriles) {
			batch.draw(texturaBarril, barril.x, barril.y, barril.width, barril.height);
		}
	}

	public void destruir() {
		// No destruir la textura aquí si la maneja GameEvasion, pero se mantiene por estructura básica
	}
}
