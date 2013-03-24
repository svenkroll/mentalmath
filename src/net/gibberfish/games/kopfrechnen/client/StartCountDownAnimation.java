package net.gibberfish.games.kopfrechnen.client;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.user.client.Timer;

public class StartCountDownAnimation extends Drawable {

	int fWidth;
	int fHeight;
	
	MentalMath parent;
	final int countDownFrom = 5;
	int countDownNumberNow;
	String message;
	String numberFont;
	long animationSpeed = 10;
	final int countDownNumberStartSize = 350;
	int countDownNumberSizeNow;
	long startTime, nowTime, lastAnimationTime;
	Timer animationTimer;
	
	public StartCountDownAnimation(MentalMath mentalMath) {
		parent = mentalMath;
		fPosX = MentalMath.AppWidth/2;
		fPosY = MentalMath.AppHeight/2;
		fWidth = 250;
		fHeight = 400;
		bDraw = false;
	}
	
	public void start(){
		countDownNumberSizeNow = countDownNumberStartSize;
		countDownNumberNow = countDownFrom;

		startTime = System.currentTimeMillis();
		lastAnimationTime = startTime;
		animationTimer = new Timer() {
			@Override
			public void run() {
				tick();
			}
		};
		animationTimer.scheduleRepeating(10);
	}
	
	public void tick(){
		if (countDownNumberSizeNow == countDownNumberStartSize){
			parent.soundBeep.play();
		}
		nowTime = System.currentTimeMillis();
		long diffTime = nowTime - lastAnimationTime;
		if (diffTime > animationSpeed){
			lastAnimationTime = nowTime;
			bDraw = true;
			if (countDownNumberSizeNow < 25){
				if (countDownNumberNow != 1){
					//next Number
					countDownNumberNow = countDownNumberNow - 1;
					countDownNumberSizeNow = countDownNumberStartSize;
				}else{
					//animation end
					bDraw = false;
					animationTimer.cancel();
					parent.countDownAnimationFinished();
				}
			}else{
				//decrease number size
				countDownNumberSizeNow = countDownNumberSizeNow - 8;
			}
			numberFont = "bold " + countDownNumberSizeNow + "px Comic Sans MS";
		}
	}
	
	public void draw(Context2d ctx){
		ctx.clearRect(fPosX-fWidth/2, fPosY-fHeight/2, fWidth, fHeight);
		
		ctx.setGlobalAlpha(0.8);
		ctx.setShadowColor("rgb(190, 190, 190)");
		ctx.setShadowOffsetX(1);
		ctx.setShadowOffsetY(1);
		ctx.setShadowBlur(1);
		
		ctx.setFillStyle("rgb(255,255,255)");
		ctx.setTextBaseline("middle");
		ctx.setTextAlign("center");	
		ctx.setFont(numberFont);
		ctx.fillText(String.valueOf(countDownNumberNow), fPosX, fPosY);
		
		ctx.setGlobalAlpha(1);
		ctx.setShadowOffsetX(0);
		ctx.setShadowOffsetY(0);
		ctx.setShadowBlur(0);
	    
	    bDraw=false;
	}

}
