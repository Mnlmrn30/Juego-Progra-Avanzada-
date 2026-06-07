package puppy.code;



public class EstrategiaRestaVida implements EstrategiaColision {
 
    public EstrategiaRestaVida() {
    }

    @Override
    public void ejecutarColision(MotoAcuatica moto) {
        moto.recibirImpacto(); 
        SoundManager.getInstance().reproducirImpacto();
    }
}
