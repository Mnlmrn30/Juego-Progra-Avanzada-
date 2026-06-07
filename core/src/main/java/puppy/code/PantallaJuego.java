package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class PantallaJuego implements Screen {

    private final GameEvasion juego;
    private boolean pausado = false;
    private OrthographicCamera camara;
    private SpriteBatch batch;
    private BitmapFont fuenteHUD;
    private GlyphLayout layout;

    private Texture fondo;
    private Texture overlay;

    private MotoAcuatica moto;
    private Obstaculos   obstaculos;
    private Sound        sonidoImpacto;

    public PantallaJuego(GameEvasion juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        fondo   = new Texture(Gdx.files.internal("mar.png"));
        overlay = new Texture(Gdx.files.internal("mar.png"));

        Texture texturaMoto   = new Texture(Gdx.files.internal("moto.png"));
        Texture texturaBarril = new Texture(Gdx.files.internal("barril.png"));
        sonidoImpacto = Gdx.audio.newSound(Gdx.files.internal("impacto.wav"));

        moto       = new MotoAcuatica(texturaMoto, sonidoImpacto);
        obstaculos = new Obstaculos(texturaBarril, sonidoImpacto);
        obstaculos.setMoto(moto);

        camara = new OrthographicCamera();
        camara.setToOrtho(false, 800, 480);
        batch  = new SpriteBatch();
        layout = new GlyphLayout();

        fuenteHUD = new BitmapFont();
        fuenteHUD.getData().setScale(1.3f);

        moto.crear();
        obstaculos.crear();
    }

    @Override
    public void render(float delta) {
    	
    	if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
    		pausado = !pausado;
    	}
    	
        ScreenUtils.clear(0f, 0.05f, 0.18f, 1f);
        camara.update();
        batch.setProjectionMatrix(camara.combined);

        batch.begin();

        // Fondo mar
        batch.draw(fondo, 0, 0, 800, 480);

        // Overlay muy sutil para HUD legible
        batch.setColor(0f, 0f, 0f, 0.18f);
        batch.draw(overlay, 0, 0, 800, 480);
        batch.setColor(Color.WHITE);
        
        if (!pausado) {
        	// Logica
        	if (!moto.estaHerido()) {
                moto.actualizar();
                obstaculos.actualizar();
            }
        }

        // Sprites
        moto.dibujar(batch);
        obstaculos.dibujar(batch);

        // --- HUD superior ---
        // Barra oscura detrás del HUD
        batch.setColor(0f, 0f, 0.05f, 0.55f);
        batch.draw(overlay, 0, 450, 800, 30);
        batch.setColor(Color.WHITE);

        // Distancia
        fuenteHUD.setColor(0.5f, 0.9f, 1f, 1f);
        fuenteHUD.draw(batch, "DISTANCIA", 10, 475);
        fuenteHUD.setColor(Color.WHITE);
        fuenteHUD.draw(batch, String.valueOf(moto.getDistancia()), 60, 475);

        // Vidas como corazones
        fuenteHUD.setColor(1f, 0.4f, 0.55f, 1f);
        String corazones = corazonesStr(moto.getVidas());
        layout.setText(fuenteHUD, corazones);
        fuenteHUD.draw(batch, corazones, (800 - layout.width) / 2f, 475);

        // Record
        fuenteHUD.setColor(1f, 0.82f, 0.25f, 0.85f);
        int hs = Gdx.app.getPreferences("evasion_maritima").getInteger("high_score", 0);
        String recStr = "RECORD " + hs;
        layout.setText(fuenteHUD, recStr);
        fuenteHUD.draw(batch, recStr, 800 - layout.width - 10, 475);
        
        if (pausado) {
        	fuenteHUD.getData().setScale(2f);
        	fuenteHUD.draw(batch, "PAUSA", 340, 300);
        	fuenteHUD.getData().setScale(1f);
        	fuenteHUD.draw(batch, "Presiona P para continuar", 260, 240);
        }
        
        batch.end();

        if (!pausado && !moto.estaVivo()) {
            juego.setScreen(new PantallaGameOver(juego, moto.getDistancia()));
            dispose();
        }
    }

    private String corazonesStr(int vidas) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) sb.append(i < vidas ? "<3 " : "__ ");
        return sb.toString();
    }

    @Override public void resize(int w, int h) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        moto.destruir();
        obstaculos.destruir();
        sonidoImpacto.dispose();
        fondo.dispose();
        overlay.dispose();
        batch.dispose();
        fuenteHUD.dispose();
    }
    
    
}