package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class MotoAcuatica extends EntidadJuego {

	private Rectangle areaColision;
	private final Texture texturaMoto;

	private int vidas = 3;
	private int distancia = 0;

	private float velocidadActual = 0f;
	private float nivelGasolina = 100f;
	private float velocidadMaxima = 520f;
	private float aceleracion    = 1400f; 
	private boolean herido = false;
	private final int tiempoHeridoMax = 50;
	private int tiempoHerido = 0;

	public MotoAcuatica(Texture textura) {
		super(800 / 2f - 64 / 2f, 20, textura);
		this.texturaMoto   = textura;
		this.areaColision = new Rectangle(x, y, textura.getWidth(), textura.getHeight());
	}

	// Procesar las entradas del teclado y aceleración
	@Override
	protected void mover() {
		float delta = Gdx.graphics.getDeltaTime();
		boolean izq = Gdx.input.isKeyPressed(Input.Keys.LEFT);
		boolean der = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
		
		if (nivelGasolina <= 0) {
		    velocidadActual = 0;
		    return; 
		}
		
		float factorEficiencia = (nivelGasolina / 100f); 
		if (factorEficiencia < 0.2f) factorEficiencia = 0.2f; 

		float aceleracionEfectiva = aceleracion * factorEficiencia;
		
		if (izq && !der) {
		    velocidadActual -= aceleracionEfectiva * delta;
		    if (velocidadActual < -velocidadMaxima * factorEficiencia) 
		        velocidadActual = -velocidadMaxima * factorEficiencia;
		} else if (der && !izq) {
		    velocidadActual += aceleracionEfectiva * delta; 
		    if (velocidadActual > velocidadMaxima * factorEficiencia) 
		        velocidadActual = velocidadMaxima * factorEficiencia;
		}

		areaColision.x += velocidadActual * delta;
	}
	
	// Controlar los límites para que no se salga el personaje
	@Override
	protected void comprobarLimites() {
		if (areaColision.x < 0)        { areaColision.x = 0;         velocidadActual = 0; }
		if (areaColision.x > 800 - 64) { areaColision.x = 800 - 64;  velocidadActual = 0; }
	}
	
	// Incrementar puntaje y sincronizar variables
	@Override
	protected void actualizarTextura() {
		this.x = areaColision.x;
		this.y = areaColision.y;
		this.area.x = areaColision.x;
		this.area.y = areaColision.y;
		
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
	    if (texturaMoto != null) {
	        texturaMoto.dispose();
	    }
	}
	
	@Override
	public Rectangle getArea() {
		return new Rectangle(
			areaColision.x + 12, 
			areaColision.y + 4, 
			areaColision.width - 24, 
			areaColision.height - 8
		);
	}
	
	public void setVidas(int vidas) {
	    this.vidas = vidas;
	}

	public void setVelocidadMaxima(float velocidadMaxima) {
	    this.velocidadMaxima = velocidadMaxima;
	}

	public void setAceleracion(float aceleracion) {
	    this.aceleracion = aceleracion;
	}

	public void recibirImpacto() {
		vidas--;
		herido = true;
		tiempoHerido   = tiempoHeridoMax;
		velocidadActual *= 0.3f;
	}
	
	public void consumirGasolina(float delta) {
		if(nivelGasolina > 0) {
			nivelGasolina -= 5f * delta;
		}
	}
	
	public void recargarGasolina(float cantidad) {
		nivelGasolina += cantidad;
		if (nivelGasolina > 100f ) {
			nivelGasolina = 100f;
		}
	}
	
	public float getGasolina() {
		return nivelGasolina;
	}
	
	public void restarVida() {
		this.vidas--;
	}
	
	public void sumarGasolina() {
		this.nivelGasolina += 30;
		if (this.nivelGasolina > 100) {
			this.nivelGasolina = 100; 
		}
	}
	
	
	public int getVidas()           { return vidas; }
	public int getDistancia()       { return distancia; }
	public boolean estaHerido()     { return herido; }
	public boolean estaVivo()       { return vidas > 0; }
}