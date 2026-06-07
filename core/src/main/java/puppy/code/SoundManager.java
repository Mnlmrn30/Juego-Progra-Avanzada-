package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {
   
    private static SoundManager instancia;

    // Assets de audio encapsulados
    private Sound sonidoImpacto;
    private Sound sonidoGasolina;
    private Music musicaFondo;
    private Music sonidoMotor;

    //  nadie fuera de esta clase puede hacer "new SoundManager()"
    private SoundManager() {
        cargarAudio();
    }

    // Método estático público de acceso global para obtener la instancia única
    public static SoundManager getInstance() {
        if (instancia == null) {
            instancia = new SoundManager();
        }
        return instancia;
    }

    private void cargarAudio() {
        sonidoImpacto = Gdx.audio.newSound(Gdx.files.internal("impacto.wav"));
        sonidoGasolina = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        musicaFondo = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        sonidoMotor = Gdx.audio.newMusic(Gdx.files.internal("motor.mp3"));

        musicaFondo.setLooping(true);
        musicaFondo.setVolume(0.3f);
        sonidoMotor.setLooping(true);
        sonidoMotor.setVolume(0.4f);
    }

    // Métodos globales de reproducción
    public void reproducirImpacto() {
        if (sonidoImpacto != null) sonidoImpacto.play();
    }

    public void reproducirGasolina() {
        if (sonidoGasolina != null) sonidoGasolina.play();
    }

    public void iniciarMusicaAmbiente() {
        if (musicaFondo != null && !musicaFondo.isPlaying()) {
            musicaFondo.play();
        }
    }

    public void detenerMusicaAmbiente() {
        if (musicaFondo != null && musicaFondo.isPlaying()) {
            musicaFondo.stop();
        }
    }

    public void iniciarMotor() {
        if (sonidoMotor != null && !sonidoMotor.isPlaying()) {
            sonidoMotor.play();
        }
    }

    public void liberarRecursos() {
        if (sonidoImpacto != null) sonidoImpacto.dispose();
        if (sonidoGasolina != null) sonidoGasolina.dispose();
        if (musicaFondo != null) musicaFondo.dispose();
        if (sonidoMotor != null) sonidoMotor.dispose();
    }
}