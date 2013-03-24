package net.gibberfish.games.kopfrechnen.client;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.user.client.Timer;

public class CountDownClock extends Drawable{
	
	int CountdownTime = 0;
	long startTime, nowTime, lastBeepTime;
	String timeString = "0.00 s";
	Timer clockTimer;
	MentalMath parent;
	
	public CountDownClock(MentalMath wordsearch){
		fPosX = 530;
		fPosY = 500;
		parent = wordsearch;
	}

	@Override
	public void draw(Context2d ctx) {
		
		ctx.clearRect(fPosX-2, fPosY-2, 252, 82);
		
		ctx.setTextBaseline("hanging");
		ctx.setTextAlign("left");		
		ctx.setShadowColor("rgb(190, 190, 190)");
		ctx.setShadowOffsetX(1);
		ctx.setShadowOffsetY(1);
		ctx.setShadowBlur(1);
		ctx.setFillStyle("rgb(255,255,255)");
		ctx.setGlobalAlpha(0.8);
		ctx.setFont("bold 24px Comic Sans MS");
		ctx.fillText("Time remaining:" , fPosX, fPosY);
		if (lastBeepTime != 0){
			ctx.setFillStyle("rgb(255,0,0)");
			ctx.setShadowColor("rgb(255, 110, 110)");
		}
		ctx.fillText(timeString, fPosX, fPosY + 30);
		ctx.setGlobalAlpha(1);

		ctx.setShadowOffsetX(0);
		ctx.setShadowOffsetY(0);
		ctx.setShadowBlur(0);
	    
	    bDraw=false;
	}

	public void setTime(int i) {
		CountdownTime = i;
	}
	
	public static String getTimeAsString(long time)
    {
		int seconds = (int) (time / 1000);
		int millis = (int) (time % 1000);
		
        StringBuilder sb = new StringBuilder(64);
        sb.append(seconds);
        sb.append(".");
        sb.append(String.valueOf(millis).substring(0,1));
        sb.append(" seconds");

        return(sb.toString());
    }
	
	public void tick(){
		nowTime = System.currentTimeMillis();
		long diffTime = nowTime - startTime;
		if (diffTime < CountdownTime){
			timeString = getTimeAsString(CountdownTime - diffTime);
			//last 10 seconds, beep every second
			if (((CountdownTime - diffTime) / 1000) < 11 && (nowTime - lastBeepTime) > 1000){
				lastBeepTime = System.currentTimeMillis();
				parent.soundBeep.play();
			}
			bDraw = true;
		}else{
			clockTimer.cancel();
			parent.timeOver();
		}
	}

	public void start() {
		bDraw = true;
		startTime = System.currentTimeMillis();
		lastBeepTime = 0;
		clockTimer = new Timer() {
			@Override
			public void run() {
				tick();
			}
		};
		clockTimer.scheduleRepeating(100);
		
	}
}
