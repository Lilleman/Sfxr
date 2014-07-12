package com.lilleman.java.sfxr;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.stoff.java.sfxr.ui.Button;
import com.stoff.java.sfxr.ui.Font;
import com.stoff.java.sfxr.ui.Label;
import com.stoff.java.sfxr.ui.Mouse;
import com.stoff.java.sfxr.ui.Slider;
import com.stoff.java.sfxr.ui.Button.ClickListener;
import com.stoff.java.sfxr.ui.Slider.ValueChangedListener;
import com.stoff.java.sfxr.ui.Utils;

public class SfxrApp implements ApplicationListener {
	/*
	 * Properties
	 */
	private Synth synth;
	private int sampleRate = 44100;
	private int bitDepth = 16;
	private Font font;
	
	private boolean playOnChange = true;
	private boolean mutePlayOnChange = false;
	
	private Dictionary<Slider, String> propLookup = new Hashtable<Slider, String>();
	private Map<String, Slider> sliderLookup = new HashMap<String, Slider>();
	private List<Button> waveformLookup = new ArrayList<Button>();
	private List<Slider> squareLookup = new ArrayList<Slider>();
	
	private List<Button> buttons = new ArrayList<Button>();
	private List<Label> labels = new ArrayList<Label>();
	
	private Button back;
	private Button forward;
	private List<Params> history = new ArrayList<Params>();
	private int historyPos;
	
	@Override
	public void create() {
		init();
	}
	
	@Override
	public void resume() {
	}
	
	@Override
	public void pause() {
	}
	
	@Override
	public void resize(int width, int height) {
	}
	
	@Override
	public void dispose() {
		synth.dispose();
		font.dispose();
		batch.dispose();
		history.clear();
	}
	
	/*
	 * Init Method
	 */
	private void init() {
		synth = new Synth();
		synth.getParams().randomize();
		
		font = new Font("data/font.png", 0, 0, 96, 8, 8);
		
		batch = new SpriteBatch();
		
		Gdx.input.setInputProcessor(new Mouse());
		
		history.add(synth.getParams());
		
		addButtons();
		addSliders();
		addLabels();
		
		updateSliders();
		updateButtons();
	}
	
	/*
	 * Button Methods
	 */
	private void addButtons() {
		addButton("PICKUP/COIN", clickPickupCoin, 5, 35, 300);
		addButton("LASER/SHOOT", clickLaserShoot, 5, 65, 301);
		addButton("EXPLOSION", clickExplosion, 5, 95, 302);
		addButton("POWERUP", clickPowerup, 5, 125, 303);
		addButton("HIT/HURT", clickHitHurt, 5, 155, 304);
		addButton("JUMP", clickJump, 5, 185, 305);
		addButton("BLIP/SELECT", clickBlipSelect, 5, 215, 306);
		addButton("MUTATE", clickMutate, 5, 322, 30);
		addButton("RANDOMIZE", clickRandomize, 5, 352, 40);
		
		back = addButton("BACK", clickBack, 5, 382, 102);
		forward = addButton("FORWARD", clickForward, 5, 412, 103);
		back.setEnabled(false);
		forward.setEnabled(false);
		
		addButton("SQUAREWAVE", clickSquarewave, 130, 30, 10, true);
		addButton("SAWTOOTH", clickSawtooth, 250, 30, 11, true);
		addButton("SINEWAVE", clickSinewave, 370, 30, 12, true);
		addButton("NOISE", clickNoise, 490, 30, 13, true);
		
		addButton("PLAY SOUND", clickPlaySound, 490, 200, 20);
		addButton("LOAD SOUND", clickLoadSound, 490, 290, 14);
		addButton("SAVE SOUND", clickSaveSound, 490, 320, 15);
		addButton("EXPORT .WAV", clickExportWav, 490, 380, 16);
		addButton("44100 HZ", clickSampleRate, 490, 410, 18);
		addButton("16-BIT", clickBitDepth, 490, 440, 19);
	}
	
	private Button addButton(String label, ClickListener listener, int x, int y, int id) {
		return addButton(label, listener, x, y, 100, 17, id, false);
	}
	
	private Button addButton(String label, ClickListener listener, int x, int y, int id, boolean highlight) {
		return addButton(label, listener, x, y, 100, 17, id, highlight);
	}
	
	private Button addButton(String label, ClickListener listener, int x, int y, int w, int h, int id, boolean highlight) {
		Button button = new Button(x, y, w, h, label, font, id, listener);
		buttons.add(button);
		
		if (highlight) {
			waveformLookup.add(button);
		}
		
		return button;
	}
	
	private void updateButtons() {
		mutePlayOnChange = true;
		
		selectedSwitch(waveformLookup.get(synth.getParams().getWaveType()));
		
		mutePlayOnChange = false;
	}
	
	/*
	 * Generator Methods
	 */
	private ClickListener clickPickupCoin = new ClickListener() {
		@Override
		public void clicked(Button button) {
			addToHistory();
			synth.getParams().generatePickupCoin();
			updateSliders();
			updateButtons();
			synth.play(sampleRate, bitDepth);
		}
	};
	
	private ClickListener clickLaserShoot = new ClickListener() {
		@Override
		public void clicked(Button button) {
			addToHistory();
			synth.getParams().generateLaserShoot();
			updateSliders();
			updateButtons();
			synth.play(sampleRate, bitDepth);
		}
	};
	
	private ClickListener clickExplosion = new ClickListener() {
		@Override
		public void clicked(Button button) {
			addToHistory();
			synth.getParams().generateExplosion();
			updateSliders();
			updateButtons();
			synth.play(sampleRate, bitDepth);
		}
	};
	
	private ClickListener clickPowerup = new ClickListener() {
		@Override
		public void clicked(Button button) {
			addToHistory();
			synth.getParams().generatePowerup();
			updateSliders();
			updateButtons();
			synth.play(sampleRate, bitDepth);
		}
	};
	
	private ClickListener clickHitHurt = new ClickListener() {
		@Override
		public void clicked(Button button) {
			addToHistory();
			synth.getParams().generateHitHurt();
			updateSliders();
			updateButtons();
			synth.play(sampleRate, bitDepth);
		}
	};
	
	private ClickListener clickJump = new ClickListener() {
		@Override
		public void clicked(Button button) {
			addToHistory();
			synth.getParams().generateJump();
			updateSliders();
			updateButtons();
			synth.play(sampleRate, bitDepth);
		}
	};
	
	private ClickListener clickBlipSelect = new ClickListener() {
		@Override
		public void clicked(Button button) {
			addToHistory();
			synth.getParams().generateBlipSelect();
			updateSliders();
			updateButtons();
			synth.play(sampleRate, bitDepth);
		}
	};
	
	private ClickListener clickMutate = new ClickListener() {
		@Override
		public void clicked(Button button) {
			addToHistory();
			synth.getParams().mutate();
			updateSliders();
			updateButtons();
			synth.play(sampleRate, bitDepth);
		}
	};
	
	private ClickListener clickRandomize = new ClickListener() {
		@Override
		public void clicked(Button button) {
			addToHistory();
			synth.getParams().randomize();
			updateSliders();
			updateButtons();
			synth.play(sampleRate, bitDepth);
		}
	};
	
	/*
	 * History Methods
	 */
	private ClickListener clickBack = new ClickListener() {
		@Override
		public void clicked(Button button) {
			historyPos--;
			if (historyPos == 0) {
				back.setEnabled(false);
			}
			if (historyPos < history.size() - 1) {
				forward.setEnabled(true);
			}
			
			synth.stopPlaying();
			synth.setParams(history.get(historyPos));
			
			updateSliders();
			updateButtons();
			
			synth.play(sampleRate, bitDepth);
		}
	};
	
	private ClickListener clickForward = new ClickListener() {
		@Override
		public void clicked(Button button) {
			historyPos++;
			if (historyPos > 0) {
				back.setEnabled(true);
			}
			if (historyPos == history.size() - 1) {
				forward.setEnabled(false);
			}
			
			synth.stopPlaying();
			synth.setParams(history.get(historyPos));
			
			updateSliders();
			updateButtons();
			
			synth.play(sampleRate, bitDepth);
		}
	};
	
	private void addToHistory() {
		historyPos++;
		history = history.subList(0, historyPos);
		history.add(synth.getParams().clone());
		
		back.setEnabled(true);
		forward.setEnabled(false);
	}
	
	/*
	 * Waveform Methods
	 */
	private ClickListener clickSquarewave = new ClickListener() {
		@Override
		public void clicked(Button button) {
			synth.getParams().setWaveType(0);
			selectedSwitch(button);
			if (playOnChange && !mutePlayOnChange) {
				synth.play(sampleRate, bitDepth);
			}
		}
	};
	
	private ClickListener clickSawtooth = new ClickListener() {
		@Override
		public void clicked(Button button) {
			synth.getParams().setWaveType(1);
			selectedSwitch(button);
			if (playOnChange && !mutePlayOnChange) {
				synth.play(sampleRate, bitDepth);
			}
		}
	};
	
	private ClickListener clickSinewave = new ClickListener() {
		@Override
		public void clicked(Button button) {
			synth.getParams().setWaveType(2);
			selectedSwitch(button);
			if (playOnChange && !mutePlayOnChange) {
				synth.play(sampleRate, bitDepth);
			}
		}
	};
	
	private ClickListener clickNoise = new ClickListener() {
		@Override
		public void clicked(Button button) {
			synth.getParams().setWaveType(3);
			selectedSwitch(button);
			if (playOnChange && !mutePlayOnChange) {
				synth.play(sampleRate, bitDepth);
			}
		}
	};
	
	private void selectedSwitch(Button select) {
		for (int i = 0, l = waveformLookup.size(); i < l; i++) {
			if (waveformLookup.get(i) != select) {
				waveformLookup.get(i).setHighlight(false);
			}
		}
		
		if (!select.getHighlight()) {
			select.setHighlight(true);
		}
	}
	
	/*
	 * Play/Save/Export Methods
	 */
	private ClickListener clickPlaySound = new ClickListener() {
		@Override
		public void clicked(Button button) {
			synth.play(sampleRate, bitDepth);
		}
	};
	
	private ClickListener clickLoadSound = new ClickListener() {
		@Override
		public void clicked(Button button) {
		}
	};
	
	private ClickListener clickSaveSound = new ClickListener() {
		@Override
		public void clicked(Button button) {
			ByteBuffer file = getSettingsFile();
			
			try {
				OutputStream out = Gdx.files.getFileHandle("sfx.sfs", FileType.External).write(false);
				out.write(file.array());
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};
	
	private ClickListener clickExportWav = new ClickListener() {
		@Override
		public void clicked(Button button) {
			ByteBuffer file = synth.getWavFile(sampleRate, bitDepth);
			
        	try {
        		OutputStream out = Gdx.files.getFileHandle("sfx.wav", FileType.External).write(false);
				out.write(file.array());
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};
	
	private ClickListener clickSampleRate = new ClickListener() {
		@Override
		public void clicked(Button button) {
			if (sampleRate == 44100) {
				sampleRate = 22050;
			} else {
				sampleRate = 44100;
			}
			button.setText(sampleRate + " HZ");
		}
	};
	
	private ClickListener clickBitDepth = new ClickListener() {
		@Override
		public void clicked(Button button) {
			if (bitDepth == 16) {
				bitDepth = 8;
			} else {
				bitDepth = 16;
			}
			button.setText(bitDepth + "-BIT");
		}
	};
	
	/*
	 * Settings File Methods
	 */
	public ByteBuffer getSettingsFile() {
		ByteBuffer file = ByteBuffer.allocate(1000000);
		file.order(ByteOrder.LITTLE_ENDIAN);
    	
    	file.putInt(102);
    	file.putInt(synth.getParams().getWaveType());
    	file.putFloat(synth.getParams().getMasterVolume());
    	
    	file.putFloat(synth.getParams().getStartFrequency());
    	file.putFloat(synth.getParams().getMinFrequency());
    	file.putFloat(synth.getParams().getSlide());
    	file.putFloat(synth.getParams().getDeltaSlide());
    	file.putFloat(synth.getParams().getSquareDuty());
    	file.putFloat(synth.getParams().getDutySweep());
    	
    	file.putFloat(synth.getParams().getVibratoDepth());
    	file.putFloat(synth.getParams().getVibratoSpeed());
    	file.putFloat(0);
    	
    	file.putFloat(synth.getParams().getAttackTime());
    	file.putFloat(synth.getParams().getSustainTime());
    	file.putFloat(synth.getParams().getDecayTime());
    	file.putFloat(synth.getParams().getSustainPunch());
    	
    	file.put((byte) 0);
    	file.putFloat(synth.getParams().getLpFilterResonance());
    	file.putFloat(synth.getParams().getLpFilterCutoff());
    	file.putFloat(synth.getParams().getLpFilterCutoffSweep());
    	file.putFloat(synth.getParams().getHpFilterCutoff());
    	file.putFloat(synth.getParams().getHpFilterCutoffSweep());
    	
    	file.putFloat(synth.getParams().getPhaserOffset());
    	file.putFloat(synth.getParams().getPhaserSweep());
    	
    	file.putFloat(synth.getParams().getRepeatSpeed());
    	
    	file.putFloat(synth.getParams().getChangeSpeed());
    	file.putFloat(synth.getParams().getChangeAmount());
    	
    	return file;
	}
	
	public void setSettingsFile(ByteBuffer file) {
		file.position(0);
		file.order(ByteOrder.LITTLE_ENDIAN);
        
        int version = file.getInt();
        
        if (version != 100 && version != 101 && version != 102) {
        	return;
        }
        
        synth.getParams().setWaveType(file.getInt());
        synth.getParams().setMasterVolume((version == 102) ? file.getFloat() : 0.5f);
        
        synth.getParams().setStartFrequency(file.getFloat());
        synth.getParams().setMinFrequency(file.getFloat());
        synth.getParams().setSlide(file.getFloat());
        synth.getParams().setDeltaSlide((version >= 101) ? file.getFloat() : 0.0f);
        
        synth.getParams().setSquareDuty(file.getFloat());
        synth.getParams().setDutySweep(file.getFloat());
        
        synth.getParams().setVibratoDepth(file.getFloat());
        synth.getParams().setVibratoSpeed(file.getFloat());
        file.getFloat();
        
        synth.getParams().setAttackTime(file.getFloat());
        synth.getParams().setSustainTime(file.getFloat());
        synth.getParams().setDecayTime(file.getFloat());
        synth.getParams().setSustainPunch(file.getFloat());
        
        file.get();
        synth.getParams().setLpFilterResonance(file.getFloat());
        synth.getParams().setLpFilterCutoff(file.getFloat());
        synth.getParams().setLpFilterCutoffSweep(file.getFloat());
        synth.getParams().setHpFilterCutoff(file.getFloat());
        synth.getParams().setHpFilterCutoffSweep(file.getFloat());
        
        synth.getParams().setPhaserOffset(file.getFloat());
        synth.getParams().setPhaserSweep(file.getFloat());
        
        synth.getParams().setRepeatSpeed(file.getFloat());
        
        synth.getParams().setChangeSpeed((version >= 101) ? file.getFloat() : 0.0f);
        synth.getParams().setChangeAmount((version >= 101) ? file.getFloat() : 0.0f);
	}
	
	/*
	 * Slider Methods
	 */
	private void addSliders() {
		addSlider("ATTACK TIME", "attackTime", 350, 72);
		addSlider("SUSTAIN TIME", "sustainTime", 350, 90);
		addSlider("SUSTAIN PUNCH", "sustainPunch", 350, 108);
		addSlider("DECAY TIME", "decayTime", 350, 126);
		
		addSlider("START FREQUENCY", "startFrequency", 350, 144);
		addSlider("MIN FREQUENCY", "minFrequency", 350, 162);
		addSlider("SLIDE", "slide", 350, 180, true);
		addSlider("DELTA SLIDE", "deltaSlide", 350, 198, true);
		
		addSlider("VIBRATO DEPTH", "vibratoDepth", 350, 216);
		addSlider("VIBRATO SPEED", "vibratoSpeed", 350, 234);
		
		addSlider("CHANGE AMOUNT", "changeAmount", 350, 252, true);
		addSlider("CHANGE SPEED", "changeSpeed", 350, 270);
		
		addSlider("SQUARE DUTY", "squareDuty", 350, 288, false, true);
		addSlider("DUTY SWEEP", "dutySweep", 350, 306, true, true);
		
		addSlider("REPEAT SPEED", "repeatSpeed", 350, 324);
		
		addSlider("PHASER OFFSET", "phaserOffset", 350, 342, true);
		addSlider("PHASER SWEEP", "phaserSweep", 350, 360, true);
		
		addSlider("LP FILTER CUTOFF", "lpFilterCutoff", 350, 378);
		addSlider("LP FILTER CUTOFF SWEEP", "lpFilterCutoffSweep", 350, 396, true);
		addSlider("LP FILTER RESONANCE", "lpFilterResonance", 350, 414);
		addSlider("HP FILTER CUTOFF", "hpFilterCutoff", 350, 432);
		addSlider("HP FILTER CUTOFF SWEEP", "hpFilterCutoffSweep", 350, 450, true);
		
		addSlider("", "masterVolume", 490, 180);
	}
	
	private void addSlider(String label, String property, int x, int y) {
		addSlider(label, property, x, y, false, false);
	}
	
	private void addSlider(String label, String property, int x, int y, boolean bipol) {
		addSlider(label, property, x, y, bipol, false);
	}
	
	private void addSlider(String label, String property, int x, int y, boolean bipol, boolean square) {
		Slider slider = new Slider(x, y, onSliderChange, bipol, label, font);
		
		propLookup.put(slider, property);
		sliderLookup.put(property, slider);
		
		if (square) {
			squareLookup.add(slider);
		}
	}
	
	private ValueChangedListener onSliderChange = new ValueChangedListener() {
		@Override
		public void changed(Slider slider) {
			synth.getParams().set(propLookup.get(slider), slider.getValue());
			if (playOnChange && !mutePlayOnChange) {
				synth.play(sampleRate, bitDepth);
			}
		}
	};
	
	private void updateSliders() {
		mutePlayOnChange = true;
		
		for (Map.Entry<String, Slider> entry : sliderLookup.entrySet()) {
			sliderLookup.get(entry.getKey()).setValue((float) synth.getParams().get(entry.getKey()));
		}
		
		mutePlayOnChange = false;
	}
	
	/*
	 * Graphics Methods
	 */
	private SpriteBatch batch;
	private boolean firstFrame = true;
	private int refreshCounter = 0;
	private int drawCount = 0;
	
	@Override
	public void render() {
		boolean redraw = true;
		if (!firstFrame && Mouse.getMouse().x - Mouse.getMouseP().x == 0 && Mouse.getMouse().y - Mouse.getMouseP().y == 0 && !Mouse.isMouseLeft() && !Mouse.isMouseRight()) {
			redraw = false;
		}
		if (!Mouse.isMouseLeft()) {
			if (Mouse.getvSelected() != 0.0f || Mouse.getvCurrentButton() > -1) {
				redraw = true;
				refreshCounter = 2;
			}
			Mouse.setvSelected(0.0f);
		}
		if (refreshCounter > 0) {
			refreshCounter--;
			redraw = true;
		}

		if (drawCount++ > 20) {
			redraw = true;
			drawCount = 0;
		}

		if (!redraw) {
			return;
		}

		firstFrame = false;
		
		Gdx.gl.glClearColor(192f / 255f, 176f / 255f, 144f / 255f, 1f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		Utils.setColor(0x000000);
		Utils.drawBar(110, 0, 2, 480);
		Utils.drawBar(5 - 1 - 1, 352 - 1 - 1, 102 + 2, 19 + 2);
		Utils.drawBar(490 - 1 - 1 + 60, 180 - 1 + 5, 70, 2);
		Utils.drawBar(490 - 1 - 1 + 60 + 68, 180 - 1 + 5, 2, 205);
		
		Utils.setColor(0xFF0000);
		Utils.drawBar(490 - 1 - 1 + 60, 180 - 1, 42 + 2, 10 + 2);
		
		Utils.setColor(0x000000);
		Utils.drawBar(490 - 1 - 1 + 60, 380 - 1 + 9, 70, 2);
		Utils.drawBar(490 - 1 - 2, 380 - 1 - 2, 102 + 4, 19 + 4);
		Utils.drawBar(350 - 190, 4 * 18 - 5, 300, 2);
		Utils.drawBar(350 - 190, 8 * 18 - 5, 300, 2);
		Utils.drawBar(350 - 190, 14 * 18 - 5, 300, 2);
		Utils.drawBar(350 - 190, 16 * 18 - 5, 300, 2);
		Utils.drawBar(350 - 190, 18 * 18 - 5, 300, 2);		
		Utils.drawBar(350 - 190, 19 * 18 - 5, 300, 2);
		Utils.drawBar(350 - 190, 21 * 18 - 5, 300, 2);
		Utils.drawBar(350 - 190, 26 * 18 - 5, 300, 2);
		Utils.drawBar(350 - 190, 4 * 18 - 5, 1, (26 - 4) * 18);
		Utils.drawBar(350 - 190 + 299, 4 * 18 - 5, 1, (26 - 4) * 18);
		
		for (Map.Entry<String, Slider> entry : sliderLookup.entrySet()) {
			entry.getValue().render(batch, synth.getParams().getWaveType());
		}
		
		for (Button b : buttons) {
			b.render(batch);
		}
		
		for (Label l : labels) {
			l.draw(batch, 1.0f);
		}
		
		if (!Mouse.isMouseLeft()) {
			Mouse.setvCurrentButton(-1);
		}
	}
	
	private void addLabels() {
		addLabel(10, 10, 0x504030, "GENERATOR");
		addLabel(120, 10, 0x504030, "MANUAL SETTINGS");
		addLabel(515, 170, 0x000000, "VOLUME");
	}
	
	private void addLabel(int x, int y, int color, String text) {
		labels.add(new Label(font, x, y, color, text));
	}
}
