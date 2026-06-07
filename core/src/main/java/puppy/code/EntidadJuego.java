package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class EntidadJuego {
    protected float x;
    protected float y;
    protected Texture textura;
    protected Rectangle area;

    public EntidadJuego(float x, float y, Texture textura) {
        this.x = x;
        this.y = y;
        this.textura = textura;
        this.area = new Rectangle(x, y, textura.getWidth(), textura.getHeight());
    }

    public abstract void actualizar();

    public void dibujar(SpriteBatch batch) {
        batch.draw(textura, x, y);
    }

    public void destruir() {
        if (textura != null) {
            textura.dispose();
        }
    }

    public Rectangle getArea() {
        return area;
    }
}