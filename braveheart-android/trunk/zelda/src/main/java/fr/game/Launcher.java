package fr.game;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import com.badlogic.gdx.backends.jogl.JoglApplication;

public class Launcher {
	public static void main(String[] args) throws IllegalArgumentException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException {
		// lance l'application Zelda
		new JoglApplication(new Zelda(480, 320), "Zelda", 480, 320, false);
	}
}
