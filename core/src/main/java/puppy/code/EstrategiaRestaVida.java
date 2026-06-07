package puppy.code;


import com.badlogic.gdx.audio.Sound;

public class EstrategiaRestaVida implements EstrategiaColision {
    private Sound sonidoImpacto;

    public EstrategiaRestaVida(Sound sonidoImpacto) {
        this.sonidoImpacto = sonidoImpacto;
    }

    @Override
    public void ejecutarColision(MotoAcuatica moto) {
        moto.recibirImpacto(); 
        

        if (sonidoImpacto != null) {
            sonidoImpacto.play();
        }
    }
}
