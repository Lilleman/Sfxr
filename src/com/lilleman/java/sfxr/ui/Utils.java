package com.lilleman.java.sfxr.ui;

import java.nio.FloatBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.BufferUtils;

public class Utils {
	public static void drawText(SpriteBatch batch, Font font, String text, int x, int yv, int color, float alpha) {
		int y = flipY(yv);
		font.setColor(color, alpha);
		batch.begin();
		font.drawText(batch, text, x, y);
		batch.end();
	}
	
	public static void drawBar(int x, int yv, int w, int h) {
		int y = flipY(yv);
		y -= h;
		
		drawRect(Gdx.graphics.getGL10(), x, y, w, h);
	}
	
	public static void setColor(int col) {
		Color c = toColor(col);
		Gdx.gl10.glColor4f(c.r, c.g, c.b, c.a);
	}
	
	public static void setColor(int col, float alpha) {
		Color c = toColor(col);
		float backgroundAlpha = 1 - alpha;
		c.r = backgroundAlpha * (192f / 255f) + alpha * c.r;
		c.g = backgroundAlpha * (176f / 255f) + alpha * c.g;
		c.b = backgroundAlpha * (144f / 255f) + alpha * c.b;
		Gdx.gl10.glColor4f(c.r, c.g, c.b, 1);
	}
	
	public static Color toColor(int color) {
		Color c = new Color();
		Color.rgb888ToColor(c, color);
		return c;
	}
	
	public static int flipY(int y) {
		return 480 - y;
	}
	
	private static FloatBuffer vertices = BufferUtils.newFloatBuffer(2 * 4);
	public static void drawRect(GL10 gl, int x, int y, int w, int h) {
        vertices.put(x).put(y);
		vertices.put(x + w).put(y);
		vertices.put(x + w).put(y + h);
		vertices.put(x).put(y + h);
        vertices.position(0);
        
        gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertices);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        
        gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 4);
        
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        
        vertices.clear();
	}
	
	/*
	 * The code below is allocating a new FloatBuffer every 
	 * draw call. Allocating only one big FloatBuffer and 
	 * clearing it every draw call would increase the performance
	 * and drastically lower the garbage.
	 * 
	 * FIX:
	 * Allocate only one big FloatBuffer instead of
	 * one every draw call.
	 */
	
	/*
	public static void drawPoint(GL10 gl, float x, float y) {
        FloatBuffer vertices = BufferUtils.newFloatBuffer(2);
        
        vertices.put(x).put(y);
        vertices.position(0);
        
        gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertices);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        
        gl.glDrawArrays(GL10.GL_POINTS, 0, 1);
        
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}
	
	public static void drawPoints(GL10 gl, Vector2[] points) {
        FloatBuffer vertices = BufferUtils.newFloatBuffer(2 * points.length);
        
        for (int i = 0; i < points.length; i++) {
        	vertices.put(points[i].x).put(points[i].y);
        }
        vertices.position(0);
        
        gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertices);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        
        gl.glDrawArrays(GL10.GL_POINTS, 0, points.length);
        
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}
	
	public static void drawLine(GL10 gl, Vector2 origin, Vector2 destination) {
        FloatBuffer vertices = BufferUtils.newFloatBuffer(2 * 2);
        
        vertices.put(origin.x).put(origin.y);
        vertices.put(destination.x).put(destination.y);
        vertices.position(0);
        
        gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertices);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        
        gl.glDrawArrays(GL10.GL_LINES, 0, 2);
        
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }
	
	public static void drawRect(GL10 gl, Rectangle rect, boolean fill) {
		Vector2[] poly = new Vector2[4];
		
		poly[0] = new Vector2(rect.x, rect.y);
		poly[1] = new Vector2(rect.x + rect.width, rect.y);
		poly[2] = new Vector2(rect.x + rect.width, rect.y + rect.height);
		poly[3] = new Vector2(rect.x, rect.y + rect.height);
		
		drawPolygon(gl, poly, true);
	}
	
	public static void drawPolygon(GL10 gl, Vector2[] poly, boolean fillPolygon) {
        FloatBuffer vertices = BufferUtils.newFloatBuffer(2 * poly.length);
        
        for (int i = 0; i < poly.length; i++) {
        	vertices.put(poly[i].x).put(poly[i].y);
        }
        vertices.position(0);
        
        gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertices);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        
        if (fillPolygon) {
        	gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, poly.length);
        } else {
        	gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, poly.length);
        }
        
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}
	
	public static void drawCircle(GL10 gl, Circle c, int segments, boolean fill) {
        FloatBuffer vertices = BufferUtils.newFloatBuffer(2 * (segments + 2));
        
        final float coef = 2.0f * (float) Math.PI / segments;
        
        for (int i = 0; i <= segments; i++) {
        	float rads = i * coef;
        	float j = (float) (c.radius * Math.cos(rads) + c.x);
        	float k = (float) (c.radius * Math.sin(rads) + c.y);
        	
        	vertices.put(j).put(k);
        }
        vertices.put(c.x).put(c.y);
        vertices.position(0);
        
        gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertices);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        
        if (fill) {
        	gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, segments + 1);
        } else {
        	gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, segments + 1);
        }
        
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}
	
	public static void drawQuadBezier(GL10 gl, Vector2 origin, Vector2 control, Vector2 destination, int segments) {
        FloatBuffer vertices = BufferUtils.newFloatBuffer(2 * (segments + 1));
        
        float t = 0.0f;
        for (int i = 0; i < segments; i++) {
        	float x = (float)Math.pow(1 - t, 2) * origin.x + 2.0f * (1 - t) * t * control.x + t * t * destination.x;
        	float y = (float)Math.pow(1 - t, 2) * origin.y + 2.0f * (1 - t) * t * control.y + t * t * destination.y;
        	vertices.put(x).put(y);
        	t += 1.0f / segments;
        }
        vertices.put(destination.x).put(destination.y);
        vertices.position(0);
        
        gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertices);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        
        gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, segments + 1);
        
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}
	
	public static void drawCubicBezier(GL10 gl, Vector2 origin, Vector2 control1, Vector2 control2, Vector2 destination, int segments) {
        FloatBuffer vertices = BufferUtils.newFloatBuffer(2 * (segments + 1));
        
        float t = 0.0f;
        for (int i = 0; i < segments; i++) {
        	float x = (float)Math.pow(1 - t, 3) * origin.x + 3.0f * (float)Math.pow(1 - t, 2) * t * control1.x + 3.0f * (1 - t) * t * t * control2.x + t * t * t * destination.x;
            float y = (float)Math.pow(1 - t, 3) * origin.y + 3.0f * (float)Math.pow(1 - t, 2) * t * control1.y + 3.0f * (1 - t) * t * t * control2.y + t * t * t * destination.y;
        	vertices.put(x).put(y);
        	t += 1.0f / segments;
        }
        vertices.put(destination.x).put(destination.y);
        vertices.position(0);
        
        gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertices);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        
        gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, segments + 1);
        
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}
	*/
}
