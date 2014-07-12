package com.lilleman.java.sfxr.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Slider {
	private int x, y;
	private float value;
	private boolean bipolar;
	
	private Label label;
	
	private ValueChangedListener listener = null;
	
	public Slider(int x, int y, boolean bipolar, String text, Font font) {
		this.x = x;
		this.y = y;
		this.bipolar = bipolar;
		
		label = new Label(font, x - 4 - text.length() * 8, y + 1, text);
	}
	
	public Slider(int x, int y, ValueChangedListener listener, boolean bipolar, String text, Font font) {
		this(x, y, bipolar, text, font);
		setListener(listener);
	}
	
	public final void setListener(ValueChangedListener listener) {
		this.listener = listener;
	}

	public float getValue() {
		return value;
	}
	
	public void setValue(float value) {
		this.value = value;
	}
	
	public void render(SpriteBatch batch, int waveType) {
		float startValue = value;
		
		if (Mouse.mouseInBox(x, y, 100, 10)) {
			if (Mouse.isMouseRightClick()) {
				value = 0.0f;
			}
			if (Mouse.isMouseLeftClick()) {
				if (bipolar) {
					value = (Mouse.getMouse().x - x) / 50.0f - 1.0f;
				} else {
					value = (Mouse.getMouse().x - x) / 100.0f;
				}
			}
		}
		float mv = (float) (Mouse.getMouse().x - Mouse.getMouseP().x);
		if (Mouse.getvSelected() != value) {
			mv = 0.0f;
		}
		if (bipolar) {
			value += mv * 0.005f;
			if (value < -1.0f) {
				value = -1.0f;
			}
			if (value > 1.0f) {
				value = 1.0f;
			}
		} else {
			value += mv * 0.0025f;
			if (value < 0.0f) {
				value = 0.0f;
			}
			if (value > 1.0f) {
				value = 1.0f;
			}
		}
		
		Utils.setColor(0x000000);
		Utils.drawBar(x - 1, y, 102, 10);
		int ival = (int) (value * 99);
		if (bipolar) {
			ival = (int) (value * 49.5f + 49.5f);
		}
		Utils.setColor(0xF0C090);
		Utils.drawBar(x, y + 1, ival, 8);
		Utils.setColor(0x807060);
		Utils.drawBar(x + ival, y + 1, 100 - ival, 8);
		Utils.setColor(0xFFFFFF);
		Utils.drawBar(x + ival, y + 1, 1, 8);
		if (bipolar) {
			Utils.setColor(0x000000);
			Utils.drawBar(x + 50, y - 1, 1, 3);
			Utils.drawBar(x + 50, y + 8, 1, 3);
		}
		int tcol = 0x000000;
		if (waveType != 0 && (label.getText().equals("squareDuty") || label.getText().equals("dutySweep"))) {
			tcol = 0x808080;
		}
		label.draw(batch, tcol, 1.0f);
		
		if (listener != null && value != startValue && (Mouse.isMouseLeft() || Mouse.isMouseRight())) {
			listener.changed(this);
		}
	}
	
	public interface ValueChangedListener {
		void changed(Slider slider);
	}
}
