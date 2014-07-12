package com.lilleman.java.sfxr.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Font {
	private final Texture texture;
	private final int glyphWidth;
	private final int glyphHeight;
	private final TextureRegion[] glyphs = new TextureRegion[96];
	
	private int color = 0xFFFFFF;
	private float alpha = 1.0f;
	
	public Font(String fontName, int offsetX, int offsetY, 
			int glyphsPerRow, int glyphWidth, int glyphHeight) {
		this.texture = new Texture(fontName);
		this.glyphWidth = glyphWidth;
		this.glyphHeight = glyphHeight;
		int x = offsetX;
		int y = offsetY;
		for (int i = 0; i < 96; i++) {
			glyphs[i] = new TextureRegion(texture, x, y, glyphWidth, glyphHeight);
			x += glyphWidth;
			if (x == offsetX + glyphsPerRow * glyphWidth) {
				x = offsetX;
				y += glyphHeight;
			}
		}
	}
	
	public void setColor(int color, float alpha) {
		this.color = color;
		this.alpha = alpha;
	}
	
	public void drawText(SpriteBatch batch, String text, int xv, int yv) {
		int y = yv - 8;
		int x = xv;
		Color batchColor = batch.getColor();
		Color col = Utils.toColor(color);
		col.a = alpha;
		
		batch.setColor(col.r, col.g, col.b, col.a);
			
		int len = text.length();
		for (int i = 0; i < len; i++) {
			int c = text.charAt(i) - ' ';
			if (c < 0 || c > glyphs.length - 1) {
				continue;
			}
				
			TextureRegion glyph = glyphs[c];
			//gl.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_MODULATE);
			//batch.draw(glyph, x, y, glyphWidth, glyphHeight);
			Gdx.gl10.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_ADD);
			batch.draw(glyph, x, y, glyphWidth, glyphHeight);
			x += glyphWidth;
		}
		batch.setColor(batchColor);
	}
	
	public void dispose() {
		texture.dispose();
	}
}
