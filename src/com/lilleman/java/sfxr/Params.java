package com.lilleman.java.sfxr;

public class Params implements Cloneable {
	/*
	 * Properties
	 */
	private int waveType = 0;
	
	private float masterVolume = 0.5f;
	
	private float attackTime;
	private float sustainTime;
	private float sustainPunch;
	private float decayTime;
	
	private float startFrequency;
	private float minFrequency;
	
	private float slide;
	private float deltaSlide;
	
	private float vibratoDepth;
	private float vibratoSpeed;
	
	private float changeAmount;
	private float changeSpeed;
	
	private float squareDuty;
	
	private float dutySweep;
	
	private float repeatSpeed;
	
	private float phaserOffset;
	private float phaserSweep;
	
	private float lpFilterCutoff;
	private float lpFilterCutoffSweep;
	private float lpFilterResonance;
	
	private float hpFilterCutoff;
	private float hpFilterCutoffSweep;
	
	/*
	 * Getters / Setters
	 */
	public int getWaveType() {
		return waveType;
	}

	public void setWaveType(int value) {
		this.waveType = value > 3 ? 0 : value;
	}

	public float getMasterVolume() {
		return masterVolume;
	}

	public void setMasterVolume(float value) {
		this.masterVolume = clamp1(value);
	}

	public float getAttackTime() {
		return attackTime;
	}

	public void setAttackTime(float value) {
		this.attackTime = clamp1(value);
	}

	public float getSustainTime() {
		return sustainTime;
	}

	public void setSustainTime(float value) {
		this.sustainTime = clamp1(value);
	}

	public float getSustainPunch() {
		return sustainPunch;
	}

	public void setSustainPunch(float value) {
		this.sustainPunch = clamp1(value);
	}

	public float getDecayTime() {
		return decayTime;
	}

	public void setDecayTime(float value) {
		this.decayTime = clamp1(value);
	}

	public float getStartFrequency() {
		return startFrequency;
	}

	public void setStartFrequency(float value) {
		this.startFrequency = clamp1(value);
	}

	public float getMinFrequency() {
		return minFrequency;
	}

	public void setMinFrequency(float value) {
		this.minFrequency = clamp1(value);
	}

	public float getSlide() {
		return slide;
	}

	public void setSlide(float value) {
		this.slide = clamp2(value);
	}

	public float getDeltaSlide() {
		return deltaSlide;
	}

	public void setDeltaSlide(float value) {
		this.deltaSlide = clamp2(value);
	}

	public float getVibratoDepth() {
		return vibratoDepth;
	}

	public void setVibratoDepth(float value) {
		this.vibratoDepth = clamp1(value);
	}

	public float getVibratoSpeed() {
		return vibratoSpeed;
	}

	public void setVibratoSpeed(float value) {
		this.vibratoSpeed = clamp1(value);
	}

	public float getChangeAmount() {
		return changeAmount;
	}

	public void setChangeAmount(float value) {
		this.changeAmount = clamp2(value);
	}

	public float getChangeSpeed() {
		return changeSpeed;
	}

	public void setChangeSpeed(float value) {
		this.changeSpeed = clamp1(value);
	}

	public float getSquareDuty() {
		return squareDuty;
	}

	public void setSquareDuty(float value) {
		this.squareDuty = clamp1(value);
	}

	public float getDutySweep() {
		return dutySweep;
	}

	public void setDutySweep(float value) {
		this.dutySweep = clamp2(value);
	}

	public float getRepeatSpeed() {
		return repeatSpeed;
	}

	public void setRepeatSpeed(float value) {
		this.repeatSpeed = clamp1(value);
	}

	public float getPhaserOffset() {
		return phaserOffset;
	}

	public void setPhaserOffset(float value) {
		this.phaserOffset = clamp2(value);
	}

	public float getPhaserSweep() {
		return phaserSweep;
	}

	public void setPhaserSweep(float value) {
		this.phaserSweep = clamp2(value);
	}

	public float getLpFilterCutoff() {
		return lpFilterCutoff;
	}

	public void setLpFilterCutoff(float value) {
		this.lpFilterCutoff = clamp1(value);
	}

	public float getLpFilterCutoffSweep() {
		return lpFilterCutoffSweep;
	}

	public void setLpFilterCutoffSweep(float value) {
		this.lpFilterCutoffSweep = clamp2(value);
	}

	public float getLpFilterResonance() {
		return lpFilterResonance;
	}

	public void setLpFilterResonance(float value) {
		this.lpFilterResonance = clamp1(value);
	}

	public float getHpFilterCutoff() {
		return hpFilterCutoff;
	}

	public void setHpFilterCutoff(float value) {
		this.hpFilterCutoff = clamp1(value);
	}

	public float getHpFilterCutoffSweep() {
		return hpFilterCutoffSweep;
	}

	public void setHpFilterCutoffSweep(float value) {
		this.hpFilterCutoffSweep = clamp2(value);
	}
	
	public float get(String s) {
		if (s.equals("masterVolume"))
			return getMasterVolume();
		if (s.equals("attackTime"))
			return getAttackTime();
		if (s.equals("sustainTime"))
			return getSustainTime();
		if (s.equals("sustainPunch"))
			return getSustainPunch();
		if (s.equals("decayTime"))
			return getDecayTime();
		if (s.equals("startFrequency"))
			return getStartFrequency();
		if (s.equals("minFrequency"))
			return getMinFrequency();
		if (s.equals("slide"))
			return getSlide();
		if (s.equals("deltaSlide"))
			return getDeltaSlide();
		if (s.equals("vibratoDepth"))
			return getVibratoDepth();
		if (s.equals("vibratoSpeed"))
			return getVibratoSpeed();
		if (s.equals("changeAmount"))
			return getChangeAmount();
		if (s.equals("changeSpeed"))
			return getChangeSpeed();
		if (s.equals("squareDuty"))
			return getSquareDuty();
		if (s.equals("dutySweep"))
			return getDutySweep();
		if (s.equals("repeatSpeed"))
			return getRepeatSpeed();
		if (s.equals("phaserOffset"))
			return getPhaserOffset();
		if (s.equals("phaserSweep"))
			return getPhaserSweep();
		if (s.equals("lpFilterCutoff"))
			return getLpFilterCutoff();
		if (s.equals("lpFilterCutoffSweep"))
			return getLpFilterCutoffSweep();
		if (s.equals("lpFilterResonance"))
			return getLpFilterResonance();
		if (s.equals("hpFilterCutoff"))
			return getHpFilterCutoff();
		if (s.equals("hpFilterCutoffSweep"))
			return getHpFilterCutoffSweep();
		
		return 0.0f;
	}
	
	public void set(String s, float value) {
		if (s.equals("masterVolume"))
			setMasterVolume(value);
		if (s.equals("attackTime"))
			setAttackTime(value);
		if (s.equals("sustainTime"))
			setSustainTime(value);
		if (s.equals("sustainPunch"))
			setSustainPunch(value);
		if (s.equals("decayTime"))
			setDecayTime(value);
		if (s.equals("startFrequency"))
			setStartFrequency(value);
		if (s.equals("minFrequency"))
			setMinFrequency(value);
		if (s.equals("slide"))
			setSlide(value);
		if (s.equals("deltaSlide"))
			setDeltaSlide(value);
		if (s.equals("vibratoDepth"))
			setVibratoDepth(value);
		if (s.equals("vibratoSpeed"))
			setVibratoSpeed(value);
		if (s.equals("changeAmount"))
			setChangeAmount(value);
		if (s.equals("changeSpeed"))
			setChangeSpeed(value);
		if (s.equals("squareDuty"))
			setSquareDuty(value);
		if (s.equals("dutySweep"))
			setDutySweep(value);
		if (s.equals("repeatSpeed"))
			setRepeatSpeed(value);
		if (s.equals("phaserOffset"))
			setPhaserOffset(value);
		if (s.equals("phaserSweep"))
			setPhaserSweep(value);
		if (s.equals("lpFilterCutoff"))
			setLpFilterCutoff(value);
		if (s.equals("lpFilterCutoffSweep"))
			setLpFilterCutoffSweep(value);
		if (s.equals("lpFilterResonance"))
			setLpFilterResonance(value);
		if (s.equals("hpFilterCutoff"))
			setHpFilterCutoff(value);
		if (s.equals("hpFilterCutoffSweep"))
			setHpFilterCutoffSweep(value);
	}

	/*
	 * Generator methods
	 */
	public void generatePickupCoin() {
		resetParams();
		
		startFrequency = (float) (0.4 + Math.random() * 0.5);
				
		sustainTime = (float) (Math.random() * 0.1);
		decayTime = (float) (0.1 + Math.random() * 0.4);
		sustainPunch = (float) (0.3 + Math.random() * 0.3);
		
		if (Math.random() < 0.5) {
			changeSpeed = (float) (0.5 + Math.random() * 0.2);
			changeAmount = (float) (0.2 + Math.random() * 0.4);
		}
	}
	
	public void generateLaserShoot() {
		resetParams();
		
		waveType = (int) (Math.random() * 3);
		if (waveType == 2 && Math.random() < 0.5) {
			waveType = (int) (Math.random() * 2);
		}
		
		startFrequency = (float) (0.5 + Math.random() * 0.5);
		minFrequency = (float) (startFrequency - 0.2 - Math.random() * 0.6);
		if (minFrequency < 0.2) {
			minFrequency = 0.2f;
		}
		
		slide = (float) (-0.15 - Math.random() * 0.2);
		
		if (Math.random() < 0.33) {
			startFrequency = (float) (0.3 + Math.random() * 0.6);
			minFrequency = (float) (Math.random() * 0.1);
			slide = (float) (-0.35 - Math.random() * 0.3);
		}
		
		if (Math.random() < 0.5) {
			squareDuty = (float) (Math.random() * 0.5);
			dutySweep = (float) (Math.random() * 0.2);
		} else {
			squareDuty = (float) (0.4 + Math.random() * 0.5);
			dutySweep = (float) (Math.random() * 0.7);
		}
				
		sustainTime = (float) (0.1 + Math.random() * 0.2);
		decayTime = (float) (Math.random() * 0.4);
		if (Math.random() < 0.5) {
			sustainPunch = (float) (Math.random() * 0.3);
		}
		
		if (Math.random() < 0.33) {
			phaserOffset = (float) (Math.random() * 0.2);
			phaserSweep = (float) (-Math.random() * 0.2);
		}
		
		if (Math.random() < 0.5) {
			hpFilterCutoff = (float) (Math.random() * 0.3);
		}
	}
	
	public void generateExplosion() {
		resetParams();
		waveType = 3;
		
		if (Math.random() < 0.5) {
			startFrequency = (float) (0.1 + Math.random() * 0.4);
			slide = (float) (-0.1 + Math.random() * 0.4);
		} else {
			startFrequency = (float) (0.2 + Math.random() * 0.7);
			slide = (float) (-0.2 + Math.random() * 0.2);
		}
		
		startFrequency *= startFrequency;
		
		if (Math.random() < 0.2) {
			slide = 0.0f;
		}
		if (Math.random() < 0.33) {
			repeatSpeed = (float) (0.3 + Math.random() * 0.5);
		}
		
		sustainTime = (float) (0.1 + Math.random() * 0.3);
		decayTime = (float) (Math.random() * 0.5);
		sustainPunch = (float) (0.2 + Math.random() * 0.6);
		
		if (Math.random() < 0.5) {
			phaserOffset = (float) (-0.3 + Math.random() * 0.9);
			phaserSweep = (float) (-Math.random() * 0.3);
		}
		
		/**/
		if (Math.random() < 0.5) {
			vibratoDepth = (float) (Math.random() * 0.7);
			vibratoSpeed = (float) (Math.random() * 0.6);
		}
		/**/
		
		if (Math.random() < 0.33) {
			changeSpeed = (float) (0.6 + Math.random() * 0.3);
			changeAmount = (float) (0.8 - Math.random() * 1.6);
		}
	}
	
	public void generatePowerup() {
		resetParams();
		
		if (Math.random() < 0.5) {
			waveType = 1;
		} else {
			squareDuty = (float) (Math.random() * 0.6);
		}
		
		if (Math.random() < 0.5) {
			startFrequency = (float) (0.2 + Math.random() * 0.3);
			slide = (float) (0.1 + Math.random() * 0.4);
			repeatSpeed = (float) (0.4 + Math.random() * 0.4);
		} else {
			startFrequency = (float) (0.2 + Math.random() * 0.3);
			slide = (float) (0.05 + Math.random() * 0.2);
			
			if (Math.random() < 0.5) {
				vibratoDepth = (float) (Math.random() * 0.7);
				vibratoSpeed = (float) (Math.random() * 0.6);
			}
		}
				
		sustainTime = (float) (Math.random() * 0.4);
		decayTime = (float) (0.1 + Math.random() * 0.4);
	}
	
	public void generateHitHurt() {
		resetParams();
		
		waveType = (int) (Math.random() * 3);
		if (waveType == 2) {
			waveType = 3;
		} else if (waveType == 0) {
			squareDuty = (float) (Math.random() * 0.6);
		}
		
		startFrequency = (float) (0.2 + Math.random() * 0.6);
		slide = (float) (-0.3 - Math.random() * 0.4);
				
		sustainTime = (float) (Math.random() * 0.1);
		decayTime = (float) (0.1 + Math.random() * 0.2);
		
		if (Math.random() < 0.5) {
			hpFilterCutoff = (float) (Math.random() * 0.3);
		}
	}
	
	public void generateJump() {
		resetParams();
		
		waveType = 0;
		squareDuty = (float) (Math.random() * 0.6);
		startFrequency = (float) (0.3 + Math.random() * 0.3);
		slide = (float) (0.1 + Math.random() * 0.2);
				
		sustainTime = (float) (0.1 + Math.random() * 0.3);
		decayTime = (float) (0.1 + Math.random() * 0.2);
		
		if (Math.random() < 0.5) {
			hpFilterCutoff = (float) (Math.random() * 0.3);
		}
		if (Math.random() < 0.5) {
			lpFilterCutoff = (float) (1.0 - Math.random() * 0.6);
		}
	}
	
	public void generateBlipSelect() {
		resetParams();
		
		waveType = (int) (Math.random() * 2);
		if (waveType == 0) {
			squareDuty = (float) (Math.random() * 0.6);
		}
		
		startFrequency = (float) (0.2 + Math.random() * 0.4);
				
		sustainTime = (float) (0.1 + Math.random() * 0.1);
		decayTime = (float) (Math.random() * 0.2);
		hpFilterCutoff = 0.1f;		
	}
	
	protected void resetParams() {
		waveType = 0;
		startFrequency = 0.3f;
		minFrequency = 0.0f;
		slide = 0.0f;
		deltaSlide = 0.0f;
		squareDuty = 0.0f;
		dutySweep = 0.0f;
		
		vibratoDepth = 0.0f;
		vibratoSpeed = 0.0f;
		
		attackTime = 0.0f;
		sustainTime = 0.3f;
		decayTime = 0.4f;
		sustainPunch = 0.0f;
		
		lpFilterResonance = 0.0f;
		lpFilterCutoff = 1.0f;
		lpFilterCutoffSweep = 0.0f;
		hpFilterCutoff = 0.0f;
		hpFilterCutoffSweep = 0.0f;
		
		phaserOffset = 0.0f;
		phaserSweep = 0.0f;
		
		repeatSpeed = 0.0f;
		
		changeSpeed = 0.0f;
		changeAmount = 0.0f;
	}
	
	/*
	 * Randomize Methods
	 */
	public void mutate() {
		mutate(0.05);
	}
	
	public void mutate(double mutation) {
		if (Math.random() < 0.5)
			startFrequency +=		Math.random() * mutation * 2 - mutation;
		if (Math.random() < 0.5)
			minFrequency +=			Math.random() * mutation * 2 - mutation;
		if (Math.random() < 0.5)
			slide +=				Math.random() * mutation * 2 - mutation;
		if (Math.random() < 0.5)
			deltaSlide +=			Math.random() * mutation * 2 - mutation;
		if (Math.random() < 0.5)
			squareDuty +=			Math.random() * mutation * 2 - mutation;
		if (Math.random() < 0.5)
			dutySweep +=			Math.random() * mutation * 2 - mutation;
		if (Math.random() < 0.5)
			vibratoDepth +=			Math.random() * mutation * 2 - mutation;
		if (Math.random() < 0.5)
			vibratoSpeed +=			Math.random() * mutation * 2 - mutation;
		if (Math.random() < 0.5)
			attackTime +=			Math.random() * mutation * 2 - mutation;
		if (Math.random() < 0.5)
			sustainTime +=			Math.random() * mutation * 2 - mutation;
		if (Math.random() < 0.5)
			decayTime +=			Math.random() * mutation * 2 - mutation;
		if (Math.random() < 0.5)
			sustainPunch +=			Math.random() * mutation * 2 - mutation;
		if (Math.random() < 0.5)
			lpFilterCutoff +=		Math.random() * mutation * 2 - mutation;
		if (Math.random() < 0.5)
			lpFilterCutoffSweep +=	Math.random() * mutation * 2 - mutation;
		if (Math.random() < 0.5)
			lpFilterResonance +=	Math.random() * mutation * 2 - mutation;
		if (Math.random() < 0.5)
			hpFilterCutoff +=		Math.random() * mutation * 2 - mutation;
		if (Math.random() < 0.5)
			hpFilterCutoffSweep +=	Math.random() * mutation * 2 - mutation;
		if (Math.random() < 0.5)
			phaserOffset +=			Math.random() * mutation * 2 - mutation;
		if (Math.random() < 0.5)
			phaserSweep +=			Math.random() * mutation * 2 - mutation;
		if (Math.random() < 0.5)
			repeatSpeed +=			Math.random() * mutation * 2 - mutation;
		if (Math.random() < 0.5)
			changeSpeed +=			Math.random() * mutation * 2 - mutation;
		if (Math.random() < 0.5)
			changeAmount +=			Math.random() * mutation * 2 - mutation;
	}
	
	public void randomize() {
		waveType = (int) (Math.random() * 4);
		
		attackTime =			pow(Math.random() * 2 - 1, 4);
		sustainTime =			pow(Math.random() * 2 - 1, 2);
		sustainPunch =			pow(Math.random() * 0.8, 2);
		decayTime =				(float) Math.random();
		
		startFrequency =		(float) ((Math.random() < 0.5) ? pow(Math.random() * 2 - 1, 2) : (pow(Math.random() * 0.5, 3) + 0.5));
		minFrequency =			0.0f;
		
		slide =					pow(Math.random() * 2 - 1, 5);
		deltaSlide =			pow(Math.random() * 2 - 1, 3);
		
		vibratoDepth =			pow(Math.random() * 2 - 1, 3);
		vibratoSpeed =			(float) (Math.random() * 2 - 1);
		
		changeAmount =			(float) (Math.random() * 2 - 1);
		changeSpeed =			(float) (Math.random() * 2 - 1);
		
		squareDuty =			(float) (Math.random() * 2 - 1);
		dutySweep =				pow(Math.random() * 2 - 1, 3);
		
		repeatSpeed =			(float) (Math.random() * 2 - 1);
		
		phaserOffset =			pow(Math.random() * 2 - 1, 3);
		phaserSweep =			pow(Math.random() * 2 - 1, 3);
		
		lpFilterCutoff =		1 - pow(Math.random(), 3);
		lpFilterCutoffSweep =	pow(Math.random() * 2 - 1, 3);
		lpFilterResonance =		(float) (Math.random() * 2 - 1);
		
		hpFilterCutoff =		pow(Math.random(), 5);
		hpFilterCutoffSweep =	pow(Math.random() * 2 - 1, 5);
		
		if (attackTime + sustainTime + decayTime < 0.2) {
			sustainTime = (float) (0.2 + Math.random() * 0.3);
			decayTime = (float) (0.2 + Math.random() * 0.3);
		}
		
		if ((startFrequency > 0.7 && slide > 0.2) || (startFrequency < 0.2 && slide < -0.05)) {
			slide = -slide;
		}
		
		if (lpFilterCutoff < 0.2 && lpFilterCutoffSweep < -0.05) {
			lpFilterCutoffSweep = -lpFilterCutoffSweep;
		}
	}
	
	/*
	 * Settings String Methods
	 */
	public String getSettingsString() {
		String string = String.valueOf(waveType);
		string += ",";
		string += to4DP(attackTime);
		string += ",";
		string += to4DP(sustainTime);
		string += ",";
		string += to4DP(sustainPunch);
		string += ",";
		string += to4DP(decayTime);
		string += ",";
		string += to4DP(startFrequency);
		string += ",";
		string += to4DP(minFrequency);
		string += ",";
		string += to4DP(slide);
		string += ",";
		string += to4DP(deltaSlide);
		string += ",";
		string += to4DP(vibratoDepth);
		string += ",";
		string += to4DP(vibratoSpeed);
		string += ",";
		string += to4DP(changeAmount);
		string += ",";
		string += to4DP(changeSpeed);
		string += ",";
		string += to4DP(squareDuty);
		string += ",";
		string += to4DP(dutySweep);
		string += ",";
		string += to4DP(repeatSpeed);
		string += ",";
		string += to4DP(phaserOffset);
		string += ",";
		string += to4DP(phaserSweep);
		string += ",";
		string += to4DP(lpFilterCutoff);
		string += ",";
		string += to4DP(lpFilterCutoffSweep);
		string += ",";
		string += to4DP(lpFilterResonance);
		string += ",";
		string += to4DP(hpFilterCutoff);
		string += ",";
		string += to4DP(hpFilterCutoffSweep);
		string += ",";
		string += to4DP(masterVolume);
		
		return string;
	}
	
	public boolean setSettingsString(String string) {
		String[] values = string.split(",");
		
		if (values.length != 24) {
			return false;
		}
		
		waveType =				Int(values[0]);
		attackTime =			Number(values[1]);
		sustainTime =			Number(values[2]);
		sustainPunch =			Number(values[3]);
		decayTime =				Number(values[4]);
		startFrequency =		Number(values[5]);
		minFrequency =			Number(values[6]);
		slide =					Number(values[7]);
		deltaSlide =			Number(values[8]);
		vibratoDepth =			Number(values[9]);
		vibratoSpeed =			Number(values[10]);
		changeAmount =			Number(values[11]);
		changeSpeed =			Number(values[12]);
		squareDuty =			Number(values[13]);
		dutySweep =				Number(values[14]);
		repeatSpeed =			Number(values[15]);
		phaserOffset =			Number(values[16]);
		phaserSweep =			Number(values[17]);
		lpFilterCutoff =		Number(values[18]);
		lpFilterCutoffSweep =	Number(values[19]);
		lpFilterResonance =		Number(values[20]);
		hpFilterCutoff =		Number(values[21]);
		hpFilterCutoffSweep =	Number(values[22]);
		masterVolume =			Number(values[23]);
		
		return true;
	}
	
	//Helper methods for setSettingsString
	private int Int(String str) {
		try {
			int i = Integer.parseInt(str);
			return i;
		} catch (NumberFormatException e) {
			return 0;
		}
	}
	
	private float Number(String str) {
		try {
			float f = Float.parseFloat(str);
			return f;
		} catch (NumberFormatException e) {
			return 0.0f;
		}
	}
	
	/*
	 * Copying Methods
	 */
	public Params clone() {
		Params out = new Params();
		out.copyFrom(this);
		
		return out;
	}
	
	public void copyFrom(Params params) {
		waveType =				params.waveType;
		attackTime =			params.attackTime;
		sustainTime =			params.sustainTime;
		sustainPunch =			params.sustainPunch;
		decayTime =				params.decayTime;
		startFrequency =		params.startFrequency;
		minFrequency =			params.minFrequency;
		slide =					params.slide;
		deltaSlide =			params.deltaSlide;
		vibratoDepth =			params.vibratoDepth;
		vibratoSpeed =			params.vibratoSpeed;
		changeAmount =			params.changeAmount;
		changeSpeed =			params.changeSpeed;
		squareDuty =			params.squareDuty;
		dutySweep =				params.dutySweep;
		repeatSpeed =			params.repeatSpeed;
		phaserOffset =			params.phaserOffset;
		phaserSweep =			params.phaserSweep;
		lpFilterCutoff =		params.lpFilterCutoff;
		lpFilterCutoffSweep =	params.lpFilterCutoffSweep;
		lpFilterResonance =		params.lpFilterResonance;
		hpFilterCutoff =		params.hpFilterCutoff;
		hpFilterCutoffSweep =	params.hpFilterCutoffSweep;
		masterVolume =			params.masterVolume;
	}
	
	/*
	 * Util Methods
	 */
	private float clamp1(float value) {
		return (value > 1.0f) ? 1.0f : ((value < 0.0f) ? 0.0f : value);
	}
	
	private float clamp2(float value) {
		return (value > 1.0f) ? 1.0f : ((value < -1.0f) ? -1.0f : value);
	}
	
	private float pow(double base, int power) {
		switch(power) {
		case 2:
			return (float) (base * base);
		case 3:
			return (float) (base * base * base);
		case 4:
			return (float) (base * base * base * base);
		case 5:
			return (float) (base * base * base * base * base);
		}
		
		return 1.0f;
	}
	
	private String to4DP(double value) {
		if (value < 0.0001 && value > -0.0001) {
			return "";
		}
		
		String string = String.valueOf(value);
		String[] split = string.split(".");
		if (split.length == 1) {
			return string;
		} else {
			String out = split[0] + "." + split[1].substring(0, 4);
			while (out.substring(out.length() - 1, 1).equals("0")) {
				out = out.substring(0, out.length() - 1);
			}
			
			return out;
		}
	}
}
