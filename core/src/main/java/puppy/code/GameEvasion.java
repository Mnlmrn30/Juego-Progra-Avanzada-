package puppy.code;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameEvasion extends ApplicationAdapter {

	private OrthographicCamera camara;
	private SpriteBatch batch;
	private BitmapFont fuente;

	private MotoAcuatica moto;
	private Obstaculos obstaculos;
	private Sound sonidoImpacto;

	private Texture texturaFondo;
	private Music sonidoMotor;

	@Override
	public void create() {
		fuente = new BitmapFont();

		Texture texturaMoto = new Texture(Gdx.files.internal("moto.png"));
		Texture texturaBarril = new Texture(Gdx.files.internal("barril.png"));
		sonidoImpacto = Gdx.audio.newSound(Gdx.files.internal("impacto.wav"));

		texturaFondo = new Texture(Gdx.files.internal("mar.png"));
		sonidoMotor = Gdx.audio.newMusic(Gdx.files.internal("motor.mp3"));

		sonidoMotor.setLooping(true);
		sonidoMotor.setVolume(0.4f); // Volumen al 40%
		sonidoMotor.play();

		moto = new MotoAcuatica(texturaMoto, sonidoImpacto);
		obstaculos = new Obstaculos(texturaBarril, sonidoImpacto);

		camara = new OrthographicCamera();
		camara.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();

		moto.crear();
		obstaculos.crear();
	}

	@Override
	public void render() {
		ScreenUtils.clear(0f, 0.0f, 0.0f, 1f); // Limpia la pantalla
		camara.update();
		batch.setProjectionMatrix(camara.combined);

		batch.begin();

		// Dibujar el fondo (Detrás de todo)
		batch.draw(texturaFondo, 0, 0, 800, 480);

		fuente.draw(batch, "Vidas: " + moto.getVidas(), 5, 475);
		fuente.draw(batch, "Distancia: " + moto.getDistancia() + " m", 640, 475);

		if (!moto.estaHerido()) {
			moto.actualizarMovimiento();
			obstaculos.actualizar(moto);

			// La distancia aumenta sola mientras estés vivo
			moto.sumarDistancia(1);
		}

		moto.dibujar(batch);
		obstaculos.dibujar(batch);

		batch.end();
	}

	@Override
	public void dispose() {
		moto.destruir();
		obstaculos.destruir();
		sonidoImpacto.dispose();
		texturaFondo.dispose();
		sonidoMotor.dispose();
		batch.dispose();
		fuente.dispose();
	}
}