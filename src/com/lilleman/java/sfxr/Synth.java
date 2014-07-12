package com.lilleman.java.sfxr;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.AudioDevice;

public class Synth extends Thread {
	private AudioDevice ad;
	private boolean running = false;
	
	/*
	 * Sound Parameters
	 */
	private Params params = new Params();
	
	private int numSamples = 2048;
	private float[] cachedWave = new float[numSamples];
	
	private Params original;
	
	/*
	 * Synth Variables
	 */
	private double masterVolume;
	
	private int waveType;
	
	private double envelopeVolume;
	private int envelopeStage;
	
	private double envelopeTime;
	private double envelopeLength;
	private double envelopeLength0;
	private double envelopeLength1;
	private double envelopeLength2;
	private double envelopeOverLength0;
	private double envelopeOverLength1;
	private double envelopeOverLength2;
	private double envelopeFullLength;
	
	private double sustainPunch;
	
	private int phase;
	
	private double period;
	private double maxPeriod;
	
	private double slide;
	private double deltaSlide;
	private double minFrequency;
	
	private double vibratoPhase;
	private double vibratoSpeed;
	private double vibratoAmplitude;
	
	private double changeAmount;
	private int changeTime;
	private int changeLimit;
	
	private double squareDuty;
	private double dutySweep;
	
	private int repeatTime;
	private int repeatLimit;
	
	private boolean phaser;
	private double phaserOffset;
	private double phaserDeltaOffset;
	private int phaserInt;
	private int phaserPos;
	private List<Double> phaserBuffer;
	
	private boolean filters;
	private double lpFilterPos;
	private double lpFilterOldPos;
	private double lpFilterDeltaPos;
	private double lpFilterCutoff;
	private double lpFilterDeltaCutoff;
	private double lpFilterDamping;
	private boolean lpFilterOn;
	
	private double hpFilterPos;
	private double hpFilterCutoff;
	private double hpFilterDeltaCutoff;
	
	private List<Double> noiseBuffer;
	
	public Synth() {
		ad = Gdx.audio.newAudioDevice(44100, false);
		this.start();
	}
	
	@Override
	public void run() {
		while (true) {
			boolean temp = false;
			synchronized (cachedWave) {
				if (running) {
					temp = true;
				}
			}
			if (temp) {
				onSampleData();
			}
		}
	}
	
	/*
	 *  Getters / Setters
	 */
	public Params getParams() {
		return params;
	}
	
	public void setParams(Params value) {
		this.params = value;
	}
	
	/*
	 * Sound Methods
	 */
	public void play(int sampleRate, int bitDepth) {
		stopPlaying();
		
		reset(true);
		
		synchronized (cachedWave) {
			running = true;
		}
	}
	
	public void stopPlaying() {
		synchronized (cachedWave) {
			running = false;
		}
		
		if (original != null) {
			params.copyFrom(original);
			original = null;
		}
	}
	
	private void onSampleData() {		
		if (synthWave(null, numSamples, true)) {
			synchronized(cachedWave) {
				running = false;
			}
		}
		
		float[] f = new float[numSamples * 2];
		float s;
		for (int i = 0; i < numSamples; i++) {
			s = cachedWave[i];
			f[i * 2] = s;
			f[i * 2 + 1] = s;
			cachedWave[i] = 0;
		}
		
		ad.writeSamples(f, 0, f.length);
	}
	
	/*
	 * Synth Methods
	 */
	private void reset(boolean totalReset) {
		Params p = params;
		
		period = 100 / (p.getStartFrequency() * p.getStartFrequency() + 0.001);
		maxPeriod = 100.0 / (p.getMinFrequency() * p.getMinFrequency() + 0.001);
		
		slide = 1.0 - p.getSlide() * p.getSlide() * p.getSlide() * 0.01;
		deltaSlide = -p.getDeltaSlide() * p.getDeltaSlide() * p.getDeltaSlide() * 0.000001;
		
		if (p.getWaveType() == 0) {
			squareDuty = 0.5 - p.getSquareDuty() * 0.5;
			dutySweep = -p.getDutySweep() * 0.00005;
		}
		
		if (p.getChangeAmount() > 0.0) {
			changeAmount = 1.0 - p.getChangeAmount() * p.getChangeAmount() * 0.9;
		} else {
			changeAmount = 1.0 + p.getChangeAmount() * p.getChangeAmount() * 10.0;
		}
		
		changeTime = 0;
		
		if (p.getChangeSpeed() == 1.0) {
			changeLimit = 0;
		} else {
			changeLimit = (int) ((1.0 - p.getChangeSpeed()) * (1.0 - p.getChangeSpeed()) * 20000 + 32);
		}
		
		if (totalReset) {
			masterVolume = p.getMasterVolume() * p.getMasterVolume();
			
			waveType = p.getWaveType();
			
			if (p.getSustainTime() < 0.01) {
				p.setSustainTime(0.01f);
			}
			
			double totalTime = p.getAttackTime() + p.getSustainTime() + p.getDecayTime();
			if (totalTime < 0.18) {
				double multiplier = 0.18 / totalTime;
				p.setAttackTime((float) (p.getAttackTime() * multiplier));
				p.setSustainTime((float) (p.getSustainTime() * multiplier));
				p.setDecayTime((float) (p.getDecayTime() * multiplier));
			}
			
			sustainPunch = p.getSustainPunch();
			
			phase = 0;
			
			minFrequency = p.getMinFrequency();
			
			filters = p.getLpFilterCutoff() != 1.0 || p.getHpFilterCutoff() != 0.0;
			
			lpFilterPos = 0.0;
			lpFilterDeltaPos = 0.0;
			lpFilterCutoff = p.getLpFilterCutoff() * p.getLpFilterCutoff() * p.getLpFilterCutoff() * 0.1;
			lpFilterDeltaCutoff = 1.0 + p.getLpFilterCutoffSweep() * 0.0001;
			lpFilterDamping = 5.0 / (1.0 + p.getLpFilterResonance() * p.getLpFilterResonance() * 20) * (0.01 + lpFilterCutoff);
			if (lpFilterDamping > 0.8) {
				lpFilterDamping = 0.8;
			}
			lpFilterDamping = 1.0 - lpFilterDamping;
			lpFilterOn = p.getLpFilterCutoff() != 1.0;
			
			hpFilterPos = 0.0;
			hpFilterCutoff = p.getHpFilterCutoff() * p.getHpFilterCutoff() * 0.1;
			hpFilterDeltaCutoff = 1.0 + p.getHpFilterCutoffSweep() * 0.0003;
			
			vibratoPhase = 0.0;
			vibratoSpeed = p.getVibratoSpeed() * p.getVibratoSpeed() * 0.01;
			vibratoAmplitude = p.getVibratoDepth() * 0.5;
			
			envelopeVolume = 0.0;
			envelopeStage = 0;
			envelopeTime = 0;
			envelopeLength0 = p.getAttackTime() * p.getAttackTime() * 100000.0;
			envelopeLength1 = p.getSustainTime() * p.getSustainTime() * 100000.0;
			envelopeLength2 = p.getDecayTime() * p.getDecayTime() * 100000.0 + 10;
			envelopeLength = envelopeLength0;
			envelopeFullLength = envelopeLength0 + envelopeLength1 + envelopeLength2;
			
			envelopeOverLength0 = 1 / envelopeLength0;
			envelopeOverLength1 = 1 / envelopeLength1;
			envelopeOverLength2 = 1 / envelopeLength2;
			
			phaser = p.getPhaserOffset() != 0.0 || p.getPhaserSweep() != 0.0;
			
			phaserOffset = p.getPhaserOffset() * p.getPhaserOffset() * 1020.0;
			if (p.getPhaserOffset() < 0.0) {
				phaserOffset = -phaserOffset;
			}
			phaserDeltaOffset = p.getPhaserSweep() * p.getPhaserSweep() * p.getPhaserSweep() * 0.2;
			phaserPos = 0;
			
			if (phaserBuffer == null || phaserBuffer.size() == 0) {
				phaserBuffer = new ArrayList<Double>(1024);
				for (int i = 0; i < 1024; i++) {
					phaserBuffer.add(0.0);
				}
			}
			if (noiseBuffer == null || noiseBuffer.size() == 0) {
				noiseBuffer = new ArrayList<Double>(32);
				for (int i = 0; i < 32; i++) {
					noiseBuffer.add(0.0);
				}
			}
			
			for (int i = 0; i < 1024; i++) {
				phaserBuffer.set(i, 0.0);
			}
			for (int i = 0; i < 32; i++) {
				noiseBuffer.set(i, Math.random() * 2.0 - 1.0);
			}
			
			repeatTime = 0;
			
			if (p.getRepeatSpeed() == 0.0) {
				repeatLimit = 0;
			} else {
				repeatLimit = (int) ((1.0 - p.getRepeatSpeed()) * (1.0 - p.getRepeatSpeed()) * 20000) + 32;
			}
		}
	}
	
	private boolean synthWave(ByteBuffer buffer, int length, boolean waveData) {
		return synthWave(buffer, length, waveData, 44100, 16);
	}
	
	private boolean synthWave(ByteBuffer buffer, int length, boolean waveData, int sampleRate, int bitDepth) {
		boolean finished = false;
		
		int sampleCount = 0;
		double bufferSample = 0.0;
		
		for (int i = 0; i < length; i++) {
			if (finished) {
				return true;
			}
			
			if (repeatLimit != 0 && ++repeatTime >= repeatLimit) {
				repeatTime = 0;
				reset(false);
			}
			
			if (changeLimit != 0 && ++changeTime >= changeLimit) {
				changeLimit = 0;
				period *= changeAmount;
			}
			
			slide += deltaSlide;
			period *= slide;
			
			if (period > maxPeriod) {
				period = maxPeriod;
				if (minFrequency > 0.0) {
					finished = true;
				}
			}
			
			double periodTemp = period;
			
			if (vibratoAmplitude > 0.0) {
				vibratoPhase += vibratoSpeed;
				periodTemp = period * (1.0 + Math.sin(vibratoPhase) * vibratoAmplitude);
			}
			
			periodTemp = (int) (periodTemp);
			if (periodTemp < 8) {
				periodTemp = 8;
			}
			
			if (waveType == 0) {
				squareDuty += dutySweep;
				if (squareDuty < 0.0) {
					squareDuty = 0.0;
				} else if (squareDuty > 0.5) {
					squareDuty = 0.5;
				}
			}
			
			if (++envelopeTime > envelopeLength) {
				envelopeTime = 0;
				
				switch(++envelopeStage) {
				case 1:
					envelopeLength = envelopeLength1;
					break;
				case 2:
					envelopeLength = envelopeLength2;
					break;
				}
			}
			
			switch(envelopeStage) {
			case 0:
				envelopeVolume = envelopeTime * envelopeOverLength0;
				break;
			case 1:
				envelopeVolume = 1.0 + (1.0 - envelopeTime * envelopeOverLength1) * 2.0 * sustainPunch;
				break;
			case 2:
				envelopeVolume = 1.0 - envelopeTime * envelopeOverLength2;
				break;
			case 3:
				envelopeVolume = 0.0;
				finished = true;
				break;
			}
			
			if (phaser) {
				phaserOffset += phaserDeltaOffset;
				phaserInt = (int) (phaserOffset);
				if (phaserInt < 0) {
					phaserInt = -phaserInt;
				} else if (phaserInt > 1023) {
					phaserInt = 1023;
				}
			}
			
			if (filters && hpFilterDeltaCutoff != 0.0) {
				hpFilterCutoff *= hpFilterDeltaCutoff;
				if (hpFilterCutoff < 0.00001) {
					hpFilterCutoff = 0.00001;
				} else if (hpFilterCutoff > 0.1) {
					hpFilterCutoff = 0.1;
				}
			}
			
			double superSample = 0.0;
			double sample = 0.0;
			for (int j = 0; j < 8; j++) {
				phase++;
				if (phase >= periodTemp) {
					phase %= periodTemp;
					if (waveType == 3) {
						for (int n = 0; n < 32; n++) {
							noiseBuffer.set(n, Math.random() * 2.0 - 1.0);
						}
					}
				}
				
				switch(waveType) {
				case 0:
					sample = ((phase / periodTemp) < squareDuty) ? 0.5 : -0.5;
					break;
				case 1:
					sample = 1.0 - (phase / periodTemp) * 2.0;
					break;
				case 2:
					double pos = phase / periodTemp;
					pos = pos > 0.5 ? (pos - 1.0) * 6.28318531 : pos * 6.28318531;
					sample = pos < 0 ? 1.27323954 * pos + 0.405284735 * pos * pos : 1.27323954 * pos - 0.405284735 * pos * pos;
					sample = sample < 0 ? (sample * -sample - sample) + sample : 0.225 * (sample * sample - sample) + sample;
					break;
				case 3:
					sample = noiseBuffer.get((int) (phase * 32 / (int) periodTemp));
					break;
				}
				
				if (filters) {
					lpFilterOldPos = lpFilterPos;
					lpFilterCutoff *= lpFilterDeltaCutoff;
					if (lpFilterCutoff < 0.0) {
						lpFilterCutoff = 0.0;
					} else if (lpFilterCutoff > 0.1) {
						lpFilterCutoff = 0.1;
					}
					
					if (lpFilterOn) {
						lpFilterDeltaPos += (sample - lpFilterPos) * lpFilterCutoff;
						lpFilterDeltaPos *= lpFilterDamping;
					} else {
						lpFilterPos = sample;
						lpFilterDeltaPos = 0.0;
					}
					
					lpFilterPos += lpFilterDeltaPos;
					
					hpFilterPos += lpFilterPos - lpFilterOldPos;
					hpFilterPos *= 1.0 - hpFilterCutoff;
					sample = hpFilterPos;
				}
				
				if (phaser) {
					phaserBuffer.set(phaserPos & 1023, sample);
					sample += phaserBuffer.get((phaserPos - phaserInt + 1024) & 1023);
					phaserPos = (phaserPos + 1) & 1023;
				}
				
				superSample += sample;
			}
			
			superSample = masterVolume * envelopeVolume * superSample * 0.125;
			
			if (superSample > 1.0) {
				superSample = 1.0;
			} else if (superSample < -1.0) {
				superSample = -1.0;
			}
			
			if (waveData) {
				synchronized (cachedWave) {
					cachedWave[i] = (float) superSample;
				}
			} else {
				bufferSample += superSample;
				
				sampleCount++;
				
				if (sampleRate == 44100 || sampleCount == 2) {
					bufferSample /= sampleCount;
					sampleCount = 0;
					
					if (bitDepth == 16) {
						buffer.putShort((short) ((int) (32000.0 * bufferSample)));
					} else {
						buffer.put((byte) (bufferSample * 127 + 128));
					}
					
					bufferSample = 0.0;
				}
			}
		}
		
		return false;
	}
	
	/*
	 * .wav File Methods
	 */
	public ByteBuffer getWavFile() {
		return getWavFile(44100, 16);
	}
	
	public ByteBuffer getWavFile(int sampleRate) {
		return getWavFile(sampleRate, 16);
	}
	
	public ByteBuffer getWavFile(int sampleRate, int bitDepth) {
		stopPlaying();
		
		reset(true);
		
		if (sampleRate != 44100) {
			sampleRate = 22050;
		}
		if (bitDepth != 16) {
			bitDepth = 8;
		}
		
		int soundLength = (int) envelopeFullLength;
		if (bitDepth == 16) {
			soundLength *= 2;
		}
		if (sampleRate == 22050) {
			soundLength /= 2;
		}
		
		int filesize = 36 + soundLength;
		int blockAlign = bitDepth / 8;
		int bytesPerSec = sampleRate * blockAlign;
		
		ByteBuffer wav = ByteBuffer.allocate(filesize + 32);
		
		wav.order(ByteOrder.BIG_ENDIAN);
		wav.putInt(0x52494646);
		wav.order(ByteOrder.LITTLE_ENDIAN);
		wav.putInt(filesize);
		wav.order(ByteOrder.BIG_ENDIAN);
		wav.putInt(0x57415645);
		
		wav.order(ByteOrder.BIG_ENDIAN);
		wav.putInt(0x666D7420);
		wav.order(ByteOrder.LITTLE_ENDIAN);
		wav.putInt(6);
		wav.putShort((short) 1);
		wav.putShort((short) 1);
		wav.putInt(sampleRate);
		wav.putInt(bytesPerSec);
		wav.putShort((short) blockAlign);
		wav.putShort((short) bitDepth);
		
		wav.order(ByteOrder.BIG_ENDIAN);
		wav.putInt(0x64617461);
        wav.order(ByteOrder.LITTLE_ENDIAN);
        wav.putInt(soundLength);
        
        synthWave(wav, (int) envelopeFullLength, false, sampleRate, bitDepth);
        
        wav.position(0);
        
        return wav;
	}
	
	public void dispose() {
		running = false;
		ad.dispose();
	}
}
