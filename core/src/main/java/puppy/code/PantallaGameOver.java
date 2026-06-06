package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class PantallaGameOver implements Screen {

    private final GameEvasion juego;
    private final int distanciaFinal;
    private final int highScore;
    private final boolean nuevoRecord;

    private OrthographicCamera camara;
    private SpriteBatch batch;
    private BitmapFont fuenteTitulo;
    private BitmapFont fuenteUI;
    private GlyphLayout layout;
    private Texture fondo;
    private Texture overlay;

    private float tiempoTotal = 0f;

    private static final String PREFS  = "evasion_maritima";
    private static final String HSCORE = "high_score";

    public PantallaGameOver(GameEvasion juego, int distancia) {
        this.juego         = juego;
        this.distanciaFinal = distancia;

        Preferences prefs = Gdx.app.getPreferences(PREFS);
        int anterior = prefs.getInteger(HSCORE, 0);
        nuevoRecord  = distancia > anterior;
        if (nuevoRecord) { prefs.putInteger(HSCORE, distancia); prefs.flush(); }
        this.highScore = prefs.getInteger(HSCORE, 0);
    }

    @Override
    public void show() {
        camara = new OrthographicCamera();
        camara.setToOrtho(false, 800, 480);
        batch  = new SpriteBatch();
        layout = new GlyphLayout();

        fondo   = new Texture(Gdx.files.internal("mar.png"));
        overlay = new Texture(Gdx.files.internal("mar.png"));

        fuenteTitulo = new BitmapFont();
        fuenteTitulo.getData().setScale(3.5f);

        fuenteUI = new BitmapFont();
        fuenteUI.getData().setScale(1.35f);
    }

    @Override
    public void render(float delta) {
        tiempoTotal += delta;

        ScreenUtils.clear(0f, 0f, 0.08f, 1f);
        camara.update();
        batch.setProjectionMatrix(camara.combined);

        batch.begin();

        // Fondo mar oscurecido fuerte
        batch.draw(fondo, 0, 0, 800, 480);
        batch.setColor(0.12f, 0f, 0f, 0.72f);
        batch.draw(overlay, 0, 0, 800, 480);
        batch.setColor(Color.WHITE);

        // Separador decorativo
        batch.setColor(0.9f, 0.25f, 0.25f, 0.45f);
        batch.draw(overlay, 240, 315, 320, 1f);
        batch.setColor(Color.WHITE);

        // Título GAME OVER
        fuenteTitulo.setColor(1f, 0.22f, 0.22f, 1f);
        String titulo = "GAME OVER";
        layout.setText(fuenteTitulo, titulo);
        fuenteTitulo.draw(batch, titulo, (800 - layout.width) / 2f, 308f);

        // Separador inferior
        batch.setColor(0.9f, 0.25f, 0.25f, 0.45f);
        batch.draw(overlay, 240, 258, 320, 1f);
        batch.setColor(Color.WHITE);

        // Stats: Distancia y Record
        fuenteUI.getData().setScale(1.2f);
        fuenteUI.setColor(0.7f, 0.75f, 0.85f, 0.85f);
        fuenteUI.draw(batch, "DISTANCIA", 230f, 240f);

        fuenteUI.setColor(Color.WHITE);
        fuenteUI.getData().setScale(1.9f);
        layout.setText(fuenteUI, String.valueOf(distanciaFinal));
        fuenteUI.draw(batch, String.valueOf(distanciaFinal), 230f, 218f);

        fuenteUI.getData().setScale(1.2f);
        fuenteUI.setColor(0.7f, 0.75f, 0.85f, 0.85f);
        fuenteUI.draw(batch, "RECORD", 490f, 240f);

        fuenteUI.getData().setScale(1.9f);
        fuenteUI.setColor(nuevoRecord ? new Color(1f, 0.9f, 0.2f, 1f) : new Color(1f, 0.82f, 0.25f, 0.75f));
        layout.setText(fuenteUI, String.valueOf(highScore));
        fuenteUI.draw(batch, String.valueOf(highScore), 490f, 218f);

        // Badge "NUEVO RECORD" animado
        if (nuevoRecord) {
            float alpha = 0.5f + 0.5f * (float) Math.sin(tiempoTotal * 4f);
            fuenteUI.getData().setScale(1.05f);
            fuenteUI.setColor(1f, 0.9f, 0.2f, alpha);
            fuenteUI.draw(batch, "*** NUEVO RECORD ***", 272f, 186f);
        }

        // Hint pulsante
        float alpha = 0.35f + 0.35f * (float) Math.sin(tiempoTotal * 2.5f);
        fuenteUI.getData().setScale(1.1f);
        fuenteUI.setColor(0.75f, 0.8f, 0.9f, alpha);
        fuenteUI.draw(batch, "[ ENTER ] reiniciar    [ ESC ] menu", 190f, 55f);

        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            juego.setScreen(new PantallaJuego(juego));
            dispose();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            juego.setScreen(new PantallaMenu(juego));
            dispose();
        }
    }

    @Override public void resize(int w, int h) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        fondo.dispose();
        overlay.dispose();
        batch.dispose();
        fuenteTitulo.dispose();
        fuenteUI.dispose();
    }
}
