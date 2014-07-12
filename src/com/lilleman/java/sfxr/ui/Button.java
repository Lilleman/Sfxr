package com.lilleman.java.sfxr.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Button {
	final class Rect {
		private int x, y;
		private int width, height;
		
		private Rect(int x, int y, int width, int height) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}
	}
	
	final class Shape {
		private int color1, color2, color3;
		
		private Shape(int color1, int color2, int color3) {
			this.color1 = color1;
			this.color2 = color2;
			this.color3 = color3;
		}
	}
	
	private Shape normal = new Shape(0x000000, 0xA09088, 0x000000);
	private Shape highlighted = new Shape(0x000000, 0x988070, 0xFFF0E0);
	private Shape down = new Shape(0xA09088, 0xFFF0E0, 0xA09088);
	
	private Rect rect;
	private Shape shape = normal;
	private Label label;
	private int id;
	private boolean highlight = false;
	
	private boolean enabled = true;
	
	private ClickListener listener = null;
	
	private float alpha = 1.0f;
	
	public Button(int x, int y, String text, Font font, int id) {
		this(x, y, 100, 17, text, font, id);
	}
	
	public Button(int x, int y, String text, Font font, int id, ClickListener listener) {
		this(x, y, text, font, id);
		setListener(listener);
	}
	
	public Button(int x, int y, int w, int h, String text, Font font, int id) {
		rect = new Rect(x, y, w, h);
		this.id = id;
		
		label = new Label(font, x + 5, y + 5, text);
	}
	
	public Button(int x, int y, int w, int h, String text, Font font, int id, ClickListener listener) {
		this(x, y, w, h, text, font, id);
		setListener(listener);
	}
	
	public boolean getHighlight() {
		return highlight;
	}
	
	public void setHighlight(boolean highlight) {
		this.highlight = highlight;
	}
	
	public final void setListener(ClickListener listener) {
		this.listener = listener;
	}
	
	public void setText(String text) {
		label.setText(text);
	}
	
	public void setEnabled(boolean value) {
		if (value) {
			alpha = 1.0f;
		} else {
			alpha = 0.3f;
		}
		
		this.enabled = value;
	}
	
	public void update() {
		if (enabled) {
			if (Mouse.mouseInBox(rect.x, rect.y, 100, 17)) {
				if (Mouse.isMouseLeftClick()) {
					Mouse.setvCurrentButton(id);
				}
			
				if (Mouse.getvCurrentButton() == id) {
					shape = down;
				}
			}
			
			if (Mouse.isMouseUp()) {
				if (highlight) {
					shape = highlighted;
				} else {
					shape = normal;
				}
				
				if (listener != null && Mouse.getvCurrentButton() == id) {
					listener.clicked(this);
				}
			}
		}
	}
	
	public void render(SpriteBatch batch) {
		update();
		
		Utils.setColor(shape.color1, alpha);
		Utils.drawBar(rect.x - 1, rect.y - 1, rect.width + 2, rect.height + 2);
		Utils.setColor(shape.color2, alpha);
		Utils.drawBar(rect.x, rect.y, rect.width, rect.height);
		label.draw(batch, shape.color3, alpha);
	}
	
	public interface ClickListener {
		void clicked(Button button);
	}
}
