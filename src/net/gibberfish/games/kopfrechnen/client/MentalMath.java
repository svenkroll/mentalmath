package net.gibberfish.games.kopfrechnen.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import com.allen_sauer.gwt.voices.client.Sound;
import com.allen_sauer.gwt.voices.client.SoundController;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MentalMath implements EntryPoint {
	
	int Level = 0;
	int CountDownTime = 60000;
	
	int operation;
	int answersCorrect, answersWrong, excerciseNumber = 0;
	
	int excerciseArraySize = 5;
	
	
	Canvas canvas;
	Context2d ctx;

	static final int AppHeight = 600;
	static final int AppWidth = 800;
	
	excercise ex;
	Queue<excercise> newExcercises = new LinkedList<excercise>();
	Queue<excercise> oldExcercises = new LinkedList<excercise>();

	
	Timer loopTimer, loadTimer;

	ArrayList<MenueDrawable> menueDrawStack = new ArrayList<MenueDrawable>();

	
	SplashScreen splash;
	StartCountDownAnimation startAnimation;
	EndScreen endScreen;
	
	SoundController soundController;
	Sound soundFound, soundWrong, soundClick, soundSelect, soundBeep;
	
	Image bgImg;

	static final String holderId = "canvasholder";
	boolean bShowEndScreen,bReset,updateMenue, startAnimationRunning = false;
	
	CountDownClock cdClock = new CountDownClock(this);
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {	
		
		Analytics.gaEventTracker("MentalMath", "AppStart", "onModuleLoad");
		Analytics.gaPageTracker("Kopfrechnen.html");
		
		splash = new SplashScreen(this, AppHeight, AppWidth);
		endScreen = new EndScreen(this, AppHeight, AppWidth);
		startAnimation = new StartCountDownAnimation(this);
		splash.show();
		bReset = true;
		initializeSounds();
		initializeMainCanvas();
	}

	private void initializeSounds() {
		soundController = new SoundController();
		soundFound = soundController.createSound(Sound.MIME_TYPE_AUDIO_WAV_PCM,
	        "./sounds/found.wav");	
		soundWrong = soundController.createSound(Sound.MIME_TYPE_AUDIO_WAV_PCM,
		        "./sounds/wrong.wav");	
		soundSelect = soundController.createSound(Sound.MIME_TYPE_AUDIO_WAV_PCM,
		        "./sounds/select.wav");	
		soundBeep = soundController.createSound(Sound.MIME_TYPE_AUDIO_WAV_PCM,
		        "./sounds/beep.wav");	
		soundClick = soundController.createSound(Sound.MIME_TYPE_AUDIO_MPEG_MP3,
		        "./sounds/click.mp3");	
	}
	
	

	public void replay(){
		bReset = true;
		bShowEndScreen = false;
		menueDrawStack.clear();
		ex = null;
		oldExcercises.clear();
		newExcercises.clear();
		excerciseNumber = 0;
		answersCorrect = 0;
		answersWrong = 0;
		draw();
		start(operation, Level);
	}
	
	public void reset(){

	}
	
	public void nextLevel(){
		bReset = true;
		bShowEndScreen = false;
		menueDrawStack.clear();
		oldExcercises.clear();
		newExcercises.clear();
		ex = null;
		excerciseNumber = 0;
		answersCorrect = 0;
		answersWrong = 0;
		draw();
		start(operation, Level+1);
	}
	
	private void initializeMainCanvas() {
		canvas = Canvas.createIfSupported();
		canvas.setVisible(false);
		if (canvas == null) {
			RootPanel
					.get(holderId)
					.add(new Label(
							"Sorry, your browser doesn't support the HTML5 Canvas element"));
			return;
		}
		canvas.setStyleName("mainCanvas");
		canvas.setWidth(AppWidth + "px");
		canvas.setCoordinateSpaceWidth(AppWidth);

		canvas.setHeight(AppHeight + "px");
		canvas.setCoordinateSpaceHeight(AppHeight);
		
		canvas.addKeyPressHandler(new KeyPressHandler() {
			
			@Override
			public void onKeyPress(KeyPressEvent event) {
				int key = event.getUnicodeCharCode();
				if (key == 0) {
			        // Probably Firefox
			        key = event.getNativeEvent().getKeyCode();
			    }
				char realkey = (char)key;
				if (realkey == '.' || realkey == '-' || realkey == '0' || realkey == '1' || realkey == '2' || realkey == '3' || realkey == '4' || realkey == '5' || realkey == '6' || realkey == '7' || realkey == '8' ||realkey == '9'){
					ex.addAnswerNumber(realkey);
					answerCheck();
				}
			}
		});
	
		RootPanel.get(holderId).add(canvas);
		ctx = canvas.getContext2d();
			
	}
	
	private void nextExcercise(){
		
		if (ex != null){
			ex.stopCursorBlink();
			//rotate
			if (oldExcercises.size() == excerciseArraySize){
				oldExcercises.poll();
			}
			for (Iterator<excercise> i = oldExcercises.iterator(); i.hasNext();){
				excercise e = i.next();
				e.setPosition(e.indexPosition - 1);
			}
			ex.setPosition(-1);
			oldExcercises.add(ex);
		}
		ex = newExcercises.poll();
		ex.setPosition(0);
		ex.startCoursorBlink();
		excercise tmpEx = generateQuestion(operation, Level);
		tmpEx.setPosition(excerciseArraySize);
		
		for (Iterator<excercise> i = newExcercises.iterator(); i.hasNext();){
			excercise e = i.next();
			e.setPosition(e.indexPosition - 1);
		}
		newExcercises.add(tmpEx);
		excerciseNumber++;
	}

	private void answerCheck() {
		switch(ex.isFound()){
		case 1:
			soundFound.play();
			answersCorrect++;
			nextExcercise();
			updateMenue = true;
			break;
		case 2:
			break;
		case 0:
			soundWrong.play();
			answersWrong++;
			nextExcercise();
			updateMenue = true;
			break;
		}
	}

	public void timeOver(){
		ex.stopCursorBlink();
		bShowEndScreen = true;
		canvas.setVisible(false);
		endScreen.setStats(answersCorrect,answersWrong,operation, Level);
		endScreen.show();
	}

	public excercise generateQuestion(int operation, int Level){
		excercise ex;

		switch (operation) {
		case 1:
			ex = new excerciseMultiplication(Level);
			return ex;
		case 2:
			ex = new excerciseDivision(Level);
			return ex;		
		case 3:
			ex = new excerciseSubtraction(Level);
			return ex;		
		case 0:
			ex = new excerciseAddition(Level);
			return ex;		
		}
		return null;
	}
	
	public void start(int artithOp, int level){
		
		Level = level;
		
		switch (artithOp) {
		case 1:
			//Multiplication
			Analytics.gaEventTracker("MentalMath", "LevelStart", "Multiplication");
			operation = 1;
			break;
		case 2:
			//Division
			Analytics.gaEventTracker("MentalMath", "LevelStart", "Division");
			operation = 2;
			break;
		case 3:
			//Subtraction
			Analytics.gaEventTracker("MentalMath", "LevelStart", "Subtraction");
			operation = 3;
			break;
		default:
			//Addition
			Analytics.gaEventTracker("MentalMath", "LevelStart", "Addition");
			operation = 0;
			break;
		}
		
		cdClock.setTime(CountDownTime);
		
		ex = generateQuestion(operation, Level);
		ex.setPosition(0);

		ex.startCoursorBlink();
		
		excerciseNumber++;

		for (int i = 1; i<(excerciseArraySize+1); i++){
			excercise tmpEx = generateQuestion(operation, level);
			tmpEx.setPosition(i);
			newExcercises.add(tmpEx);
		}
		
		menueDrawStack.add(new MenueItem("Level: " + Level,0));
		menueDrawStack.add(new MenueItem("Exercise: " + excerciseNumber,1));

		startAnimationRunning = true;
		
		loopTimer = new Timer() {
			@Override
			public void run() {
				loop();
			}
		};
		loopTimer.scheduleRepeating(10);
		
		splash.hide();
		
		canvas.setVisible(true);
		canvas.setFocus(true);
		
		startAnimation.start();
	}
	
	public void countDownAnimationFinished() {
		startAnimationRunning = false;
		cdClock.start();		
	}
	
	public void draw() {
		if (bReset){
			ctx.clearRect(0, 0, AppWidth, AppHeight);
			//ctx.setStrokeStyle("#A0522D");
			//ctx.setLineWidth(10);
			//ctx.strokeRect(0,0, AppWidth, AppWidth);
			bReset = false;
		}
		if (!bShowEndScreen){
			for (MenueDrawable obj : menueDrawStack) {
				if (obj.bDraw){
					obj.draw(ctx);
				}
			}
			if (cdClock.bDraw){
				cdClock.draw(ctx);
			}
			if (!startAnimationRunning){
				if (ex != null && ex.bDraw){
					ex.draw(ctx);
				}
				for (Iterator<excercise> i = newExcercises.iterator(); i.hasNext();){
					excercise e = i.next();
					if (e.bDraw){
						e.draw(ctx);
					}
				}
				for (Iterator<excercise> i = oldExcercises.iterator(); i.hasNext();){
					excercise e = i.next();
					if (e.bDraw){
						e.draw(ctx);
					}
				}
			}else{
				if (startAnimation.bDraw){
					startAnimation.draw(ctx);
				}
			}
		}
	}
	
	public void loop() {
		if (updateMenue){
			for (MenueDrawable obj : menueDrawStack) {
				switch(obj.getMenueSlot()){
				case 0:
					obj.setText("Level: " + Level);
					break;
				case 1:
					obj.setText("Aufgabe: " + excerciseNumber);
					break;
				}
			}
			updateMenue = false;
		}
		draw();
	}
}

	