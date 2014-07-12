package com.lilleman.java.sfxr;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class DesktopStarter {
	private DesktopStarter() {
	}
	
	public static void main(String[] args) {
		new LwjglApplication(new SfxrApp(), 
							"SFXR", 
							640, 480, false);
	}
}
