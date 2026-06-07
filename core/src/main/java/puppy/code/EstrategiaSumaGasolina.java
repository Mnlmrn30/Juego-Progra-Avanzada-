package puppy.code;

public class EstrategiaSumaGasolina implements EstrategiaColision {
    @Override
    public void ejecutarColision(MotoAcuatica moto) {
        moto.recargarGasolina(20f); 
    }
}
