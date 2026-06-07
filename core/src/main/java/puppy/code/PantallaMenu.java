package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class PantallaMenu implements Screen {

    private final GameEvasion juego;
    private final OrthographicCamera camara;
    private final SpriteBatch batch;
    private final BitmapFont fuenteTitulo;
    private final BitmapFont fuenteUI;
    private final GlyphLayout layout;
    private final Texture fondo;
    private final Texture overlay;      // negro semitransparente

    private float tiempoTotal = 0f;     // para animaciones con seno

    private static final String PREFS  = "evasion_maritima";
    private static final String HSCORE = "high_score";

    public PantallaMenu(GameEvasion juego) {
        this.juego = juego;
        camara = new OrthographicCamera();
        camara.setToOrtho(false, 800, 480);
        batch  = new SpriteBatch();
        layout = new GlyphLayout();

        fondo   = new Texture(Gdx.files.internal("mar.png"));
        overlay = new Texture(Gdx.files.internal("mar.png")); // 1x1 px negro o textura base

        // Fuente grande para el título
        fuenteTitulo = new BitmapFont();
        fuenteTitulo.getData().setScale(3.2f);
        fuenteTitulo.setColor(Color.WHITE);

        // Fuente normal para instrucciones
        fuenteUI = new BitmapFont();
        fuenteUI.getData().setScale(1.4f);
    }

    @Override
    public void render(float delta) {
        tiempoTotal += delta;

        // --- ENTRADAS DE TECLADO (Controles de Selección de Sesión) ---
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) GameEvasion.tipoMotoSeleccionada = "ORIGINAL";
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) GameEvasion.tipoMotoSeleccionada = "AZUL";
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) GameEvasion.tipoMotoSeleccionada = "ROSA";
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) GameEvasion.tipoMotoSeleccionada = "VERDE";

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            juego.setScreen(new PantallaJuego(juego));
            dispose();
            return;
        }

        ScreenUtils.clear(0f, 0.05f, 0.18f, 1f);
        camara.update();
        batch.setProjectionMatrix(camara.combined);

        batch.begin();

        // --- Fondo del mar ---
        batch.draw(fondo, 0, 0, 800, 480);

        // --- Overlay oscuro para legibilidad ---
        batch.setColor(0f, 0f, 0f, 0.52f);
        batch.draw(overlay, 0, 0, 800, 480);
        batch.setColor(Color.WHITE);

        // --- Separador decorativo superior ---
        drawLinea(batch, 260, 310, 540, 310, new Color(0.3f, 0.75f, 0.9f, 0.5f));

        // --- TÍTULO principal ---
        fuenteTitulo.setColor(Color.WHITE);
        String titulo = "EVASION MARITIMA";
        layout.setText(fuenteTitulo, titulo);
        fuenteTitulo.draw(batch, titulo, (800 - layout.width) / 2f, 290f);

        // --- Subtítulo ---
        fuenteUI.setColor(0.3f, 0.85f, 1f, 1f);
        String sub = "ALTA VELOCIDAD";
        layout.setText(fuenteUI, sub);
        fuenteUI.draw(batch, sub, (800 - layout.width) / 2f, 240f);

        // --- Separador inferior ---
        drawLinea(batch, 260, 225, 540, 225, new Color(0.3f, 0.75f, 0.9f, 0.5f));

        // --- Pulso animado en ENTER ---
        float alpha = 0.45f + 0.45f * (float) Math.sin(tiempoTotal * 3.0f);
        fuenteUI.setColor(0.85f, 0.92f, 1f, alpha);
        String prompt = "[ ENTER ]  COMENZAR";
        layout.setText(fuenteUI, prompt);
        fuenteUI.draw(batch, prompt, (800 - layout.width) / 2f, 185f);

        fuenteUI.getData().setScale(1.0f);
        
        // Encabezado del menú
        fuenteUI.setColor(Color.CYAN);
        String txtMenu = "PERSONALIZA TU VEHICULO (Presiona 1 - 4):";
        layout.setText(fuenteUI, txtMenu);
        fuenteUI.draw(batch, txtMenu, (800 - layout.width) / 2f, 140f);

        // Opción 1: Gris 
        if (GameEvasion.tipoMotoSeleccionada.equals("ORIGINAL")) fuenteUI.setColor(Color.GREEN);
        else fuenteUI.setColor(Color.WHITE);
        String opt1 = "1. Moto Gris (Equilibrada) " + (GameEvasion.tipoMotoSeleccionada.equals("ORIGINAL") ? "[X]" : "[ ]");
        layout.setText(fuenteUI, opt1);
        fuenteUI.draw(batch, opt1, (800 - layout.width) / 2f, 115f);

        // Opción 2: Azul
        if (GameEvasion.tipoMotoSeleccionada.equals("AZUL")) fuenteUI.setColor(Color.GREEN);
        else fuenteUI.setColor(Color.WHITE);
        String opt2 = "2. Moto Azul (Mas Vidas) " + (GameEvasion.tipoMotoSeleccionada.equals("AZUL") ? "[X]" : "[ ]");
        layout.setText(fuenteUI, opt2);
        fuenteUI.draw(batch, opt2, (800 - layout.width) / 2f, 90f);

        // Opción 3: Roja
        if (GameEvasion.tipoMotoSeleccionada.equals("ROJA")) fuenteUI.setColor(Color.GREEN);
        else fuenteUI.setColor(Color.WHITE);
        String opt3 = "3. Moto Roja (Veloz extrema) " + (GameEvasion.tipoMotoSeleccionada.equals("ROJA") ? "[X]" : "[ ]");
        layout.setText(fuenteUI, opt3);
        fuenteUI.draw(batch, opt3, (800 - layout.width) / 2f, 65f);

        // Opción 4: Verde
        if (GameEvasion.tipoMotoSeleccionada.equals("VERDE")) fuenteUI.setColor(Color.GREEN);
        else fuenteUI.setColor(Color.WHITE);
        String opt4 = "4. Moto Verde (Modo Agil) " + (GameEvasion.tipoMotoSeleccionada.equals("VERDE") ? "[X]" : "[ ]");
        layout.setText(fuenteUI, opt4);
        fuenteUI.draw(batch, opt4, (800 - layout.width) / 2f, 40f);
        // =========================================================================


        int hs = Gdx.app.getPreferences(PREFS).getInteger(HSCORE, 0);
        fuenteUI.getData().setScale(1.0f);
        fuenteUI.setColor(1f, 0.82f, 0.25f, 0.8f);
        fuenteUI.draw(batch, "RECORD  " + hs, 600f, 30f);
        
        fuenteUI.getData().setScale(1.4f); 

        batch.end();
    }

    // Dibuja una línea horizontal usando el overlay (1px de alto)
    private void drawLinea(SpriteBatch b, float x1, float y, float x2, float alto, Color c) {
        b.setColor(c);
        b.draw(overlay, x1, y, x2 - x1, 1f);
        b.setColor(Color.WHITE);
    }

    @Override public void show() {}
    @Override public void resize(int w, int h) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        fuenteTitulo.dispose();
        fuenteUI.dispose();
        fondo.dispose();
        overlay.dispose();
    }
}