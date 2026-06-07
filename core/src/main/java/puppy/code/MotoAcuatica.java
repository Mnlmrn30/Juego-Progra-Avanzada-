package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class MotoAcuatica extends EntidadJuego {

	private Rectangle areaColision;
	private final Texture texturaMoto;
	private final Sound sonidoImpacto;

	private int vidas = 3;
	private int distancia = 0;

	private float velocidadActual = 0f;
	private final float velocidadMaxima = 520f;
	private final float aceleracion    = 1400f;
	private final float friccion       = 900f;

	private boolean herido = false;
	private final int tiempoHeridoMax = 50;
	private int tiempoHerido = 0;

	public MotoAcuatica(Texture textura, Sound sonido) {
		super(800 / 2f - 64 / 2f, 20, textura);
		this.texturaMoto   = textura;
		this.sonidoImpacto = sonido;
	}


	@Override
	public void actualizar() {
		float delta = Gdx.graphics.getDeltaTime();
		boolean izq = Gdx.input.isKeyPressed(Input.Keys.LEFT);
		boolean der = Gdx.input.isKeyPressed(Input.Keys.RIGHT);

		if (izq && !der) {
			velocidadActual -= aceleracion * delta;
			if (velocidadActual < -velocidadMaxima) velocidadActual = -velocidadMaxima;
		} else if (der && !izq) {
			velocidadActual += aceleracion * delta;
			if (velocidadActual > velocidadMaxima) velocidadActual = velocidadMaxima;
		} else {
			if (velocidadActual > 0) {
				velocidadActual -= friccion * delta;
				if (velocidadActual < 0) velocidadActual = 0;
			} else if (velocidadActual < 0) {
				velocidadActual += friccion * delta;
				if (velocidadActual > 0) velocidadActual = 0;
			}
		}

		areaColision.x += velocidadActual * delta;
		if (areaColision.x < 0)        { areaColision.x = 0;         velocidadActual = 0; }
		if (areaColision.x > 800 - 64) { areaColision.x = 800 - 64;  velocidadActual = 0; }

		distancia++;
	}

	@Override
	public void dibujar(SpriteBatch batch) {
		if (!herido) {
			batch.draw(texturaMoto, areaColision.x, areaColision.y);
		} else {
			batch.draw(texturaMoto,
					areaColision.x + MathUtils.random(-5, 5),
					areaColision.y + MathUtils.random(-3, 3));
			tiempoHerido--;
			if (tiempoHerido <= 0) herido = false;
		}
	}

	@Override
	public void destruir() {
		texturaMoto.dispose();
	}

	public void recibirImpacto() {
		vidas--;
		herido = true;
		tiempoHerido   = tiempoHeridoMax;
		velocidadActual *= 0.3f;
		sonidoImpacto.play();
	}

	public Rectangle getArea()      { return areaColision; }
	public int getVidas()           { return vidas; }
	public int getDistancia()       { return distancia; }
	public boolean estaHerido()     { return herido; }
	public boolean estaVivo()       { return vidas > 0; }
}