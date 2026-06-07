package puppy.code;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Game;

public class GameEvasion extends Game {
	public static String tipoMotoSeleccionada = "ORIGINAL";

	@Override
	public void create() {

		setScreen(new PantallaMenu(this));
	}
}