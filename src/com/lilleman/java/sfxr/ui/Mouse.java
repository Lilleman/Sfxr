package com.lilleman.java.sfxr.ui;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;

public class Mouse extends InputAdapter {
	private static Vector2 mouse = new Vector2();
	private static Vector2 mouseP = new Vector2();
	private static boolean mouseLeft, mouseRight;
	private static boolean mouseLeftClick, mouseRightClick;
	
	private static int vCurrentButton = -1;
	private static float vSelected;
	
	private static boolean mouseUp;
	
	public static Vector2 getMouse() {
		return mouse;
	}

	public static void setMouse(Vector2 mouse) {
		Mouse.mouse = mouse;
	}

	public static Vector2 getMouseP() {
		return mouseP;
	}

	public static void setMouseP(Vector2 mouseP) {
		Mouse.mouseP = mouseP;
	}

	public static boolean isMouseLeft() {
		return mouseLeft;
	}

	public static void setMouseLeft(boolean mouseLeft) {
		Mouse.mouseLeft = mouseLeft;
	}

	public static boolean isMouseRight() {
		return mouseRight;
	}

	public static void setMouseRight(boolean mouseRight) {
		Mouse.mouseRight = mouseRight;
	}

	public static boolean isMouseLeftClick() {
		return mouseLeftClick;
	}

	public static void setMouseLeftClick(boolean mouseLeftClick) {
		Mouse.mouseLeftClick = mouseLeftClick;
	}

	public static boolean isMouseRightClick() {
		return mouseRightClick;
	}

	public static void setMouseRightClick(boolean mouseRightClick) {
		Mouse.mouseRightClick = mouseRightClick;
	}

	public static int getvCurrentButton() {
		return vCurrentButton;
	}

	public static void setvCurrentButton(int vCurrentButton) {
		Mouse.vCurrentButton = vCurrentButton;
	}

	public static float getvSelected() {
		return vSelected;
	}

	public static void setvSelected(float vSelected) {
		Mouse.vSelected = vSelected;
	}

	public static boolean isMouseUp() {
		return mouseUp;
	}

	public static void setMouseUp(boolean mouseUp) {
		Mouse.mouseUp = mouseUp;
	}
	
	private void setMouse(int x, int y) {
		mouseP = mouse;
		mouse.x = x;
		mouse.y = y;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		setMouse(x, y);
		if (button == Input.Buttons.RIGHT) {
			mouseRight = true;
			mouseRightClick = true;
			mouseLeft = false;
			mouseLeftClick = false;
		} else {
			mouseLeft = true;
			mouseLeftClick = true;
			mouseRight = false;
			mouseRightClick = false;
		}
		
		mouseUp = false;
		
		return true;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		setMouse(x, y);
		return true;
	}

	@Override
	public boolean mouseMoved(int x, int y) {
		setMouse(x, y);
		return true;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		setMouse(x, y);
		mouseLeft = false;
		mouseLeftClick = false;
		mouseRight = false;
		mouseRightClick = false;
		
		mouseUp = true;
		
		return true;
	}
	
	public static boolean mouseInBox(int x, int y, int w, int h) {
		if (mouse.x >= x && mouse.x < x + w && mouse.y >= y && mouse.y < y + h) {
			return true;
		}
		return false;
	}
}
