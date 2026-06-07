package puppy.code;

import com.badlogic.gdx.graphics.Texture;

public class MotoAcuaticaBuilder {
    private Texture textura;
    private int vidas = 3;
    private float velocidadMaxima = 520f;
    private float aceleracion = 1400f;

    public MotoAcuaticaBuilder conTextura(Texture textura) {
        this.textura = textura;
        return this;
    }

    public MotoAcuaticaBuilder conVidas(int vidas) {
        this.vidas = vidas;
        return this;
    }

    public MotoAcuaticaBuilder conVelocidadMaxima(float velMax) {
        this.velocidadMaxima = velMax;
        return this;
    }

    public MotoAcuaticaBuilder conAceleracion(float aceleracion) {
        this.aceleracion = aceleracion;
        return this;
    }
    
   

    public MotoAcuatica build() {
        if (textura == null) {
            throw new IllegalStateException("No se puede construir la moto sin una textura.");
        }
        MotoAcuatica moto = new MotoAcuatica(textura);
        moto.setVidas(this.vidas);
        moto.setVelocidadMaxima(this.velocidadMaxima);
        moto.setAceleracion(this.aceleracion);
        return moto;
    }
}
