package com.lilleman.java.sfxr.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Label {
	private int x, y;
	private String text;
	private Font font;
	private int color = 0x000000;
	
	public Label(Font font, int x, int y, String text) {
		this.x = x;
		this.y = y;
		this.text = text;
		this.font = font;
	}
	
	public Label(Font font, int x, int y, int color, String text) {
		this(font, x, y, text);
		this.color = color;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public void draw(SpriteBatch batch, float alpha) {
		Utils.drawText(batch, font, text, x, y, color, alpha);
	}
	
	public void draw(SpriteBatch batch, int color, float alpha) {
		Utils.drawText(batch, font, text, x, y, color, alpha);
	}
}
