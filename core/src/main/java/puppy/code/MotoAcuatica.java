package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class MotoAcuatica {

	private Rectangle areaColision;
	private Texture texturaMoto;
	private Sound sonidoImpacto;

	private int vidas = 3;
	private int distancia = 0;

	// --- Sistema de inercia ---
	private float velocidadActual = 0f;       // velocidad horizontal actual (px/s)
	private final float velocidadMaxima = 520f; // límite de velocidad
	private final float aceleracion = 1400f;   // qué tan rápido acelera al presionar
	private final float friccion = 900f;       // qué tan rápido desacelera al soltar

	// --- Estado de daño ---
	private boolean herido = false;
	private int tiempoHeridoMax = 50;
	private int tiempoHerido = 0;

	public MotoAcuatica(Texture textura, Sound sonido) {
		this.texturaMoto = textura;
		this.sonidoImpacto = sonido;
	}

	public void crear() {
		areaColision = new Rectangle();
		areaColision.x = 800 / 2f - 64 / 2f;
		areaColision.y = 20;
		areaColision.width = 64;
		areaColision.height = 64;
	}

	// ----------------------------------------------------------------
	// Movimiento con inercia: acelera rápido, frena con peso
	// ----------------------------------------------------------------
	public void actualizarMovimiento() {
		float delta = Gdx.graphics.getDeltaTime();
		boolean izquierda = Gdx.input.isKeyPressed(Input.Keys.LEFT);
		boolean derecha   = Gdx.input.isKeyPressed(Input.Keys.RIGHT);

		if (izquierda && !derecha) {
			// Acelerar hacia la izquierda
			velocidadActual -= aceleracion * delta;
			if (velocidadActual < -velocidadMaxima) velocidadActual = -velocidadMaxima;

		} else if (derecha && !izquierda) {
			// Acelerar hacia la derecha
			velocidadActual += aceleracion * delta;
			if (velocidadActual > velocidadMaxima) velocidadActual = velocidadMaxima;

		} else {
			// Sin input: aplicar fricción (desaceleración gradual)
			if (velocidadActual > 0) {
				velocidadActual -= friccion * delta;
				if (velocidadActual < 0) velocidadActual = 0;
			} else if (velocidadActual < 0) {
				velocidadActual += friccion * delta;
				if (velocidadActual > 0) velocidadActual = 0;
			}
		}

		areaColision.x += velocidadActual * delta;

		// Límites de pantalla
		if (areaColision.x < 0) {
			areaColision.x = 0;
			velocidadActual = 0;
		}
		if (areaColision.x > 800 - 64) {
			areaColision.x = 800 - 64;
			velocidadActual = 0;
		}
	}

	public void recibirImpacto() {
		vidas--;
		herido = true;
		tiempoHerido = tiempoHeridoMax;
		velocidadActual *= 0.3f; // el golpe frena la moto bruscamente
		sonidoImpacto.play();
	}

	public void dibujar(SpriteBatch batch) {
		if (!herido) {
			batch.draw(texturaMoto, areaColision.x, areaColision.y);
		} else {
			// Efecto de vibración al estar herido
			batch.draw(texturaMoto,
					areaColision.x + MathUtils.random(-5, 5),
					areaColision.y + MathUtils.random(-3, 3));
			tiempoHerido--;
			if (tiempoHerido <= 0) herido = false;
		}
	}

	public void destruir() {
		texturaMoto.dispose();
	}

	// --- Getters ---
	public Rectangle getArea()     { return areaColision; }
	public int getVidas()          { return vidas; }
	public int getDistancia()      { return distancia; }
	public boolean estaHerido()    { return herido; }
	public void sumarDistancia(int d) { distancia += d; }
}