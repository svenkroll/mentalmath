package net.gibberfish.games.kopfrechnen.client;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Timer;

public class excercise{
	
	protected float fPosX;
	protected float fPosY;
	
	protected float fWidth;
	protected float fHeight;
	
	protected boolean bDraw, bBlink;
	protected String sBlink = "";
	long nowTime, lastBlinkTime;
	Timer blinkTimer;
	final int blinkFrequence = 1000;
	protected boolean correct, answered;
	int Level;
	int textFontSize;
	String textFont = "bold "+ textFontSize + "px Comic Sans MS";
	int indexPosition;
	
	double answer = 0;
	String dFormat; 
	
	String input = "";
	String problem = "";
	
	public excercise(int lvl){
		Level = lvl;
	}

	public void draw(Context2d ctx) {
		ctx.clearRect(fPosX, fPosY-fHeight/2, fWidth, fHeight);
		
		ctx.setTextBaseline("middle");
    	ctx.setTextAlign("left");

		ctx.setShadowColor("rgb(190, 190, 190)");
		
		if (indexPosition != 0){
			ctx.setGlobalAlpha(0.5);
			ctx.setShadowOffsetX(1);
			ctx.setShadowOffsetY(1);
			ctx.setShadowBlur(1);
		}else{
			ctx.setGlobalAlpha(0.8);
			ctx.setShadowOffsetX(4);
			ctx.setShadowOffsetY(4);
			ctx.setShadowBlur(10);
		}
		
		ctx.setFont(textFont);
		
		if (answered){
			if (!correct){
				ctx.setFillStyle("rgb(255,255,255)");
				ctx.fillText(problem, fPosX, fPosY);  
				ctx.setFillStyle("rgb(255,0,0)");
				ctx.fillText(input, fPosX + ctx.measureText(problem).getWidth(), fPosY);  
				ctx.setFillStyle("rgb(255,255,255)");
				ctx.fillText(" (" + NumberFormat.getFormat(dFormat).format(answer) + ")", fPosX + ctx.measureText(problem + input).getWidth(), fPosY);
			}else{
				ctx.setFillStyle("rgb(255,255,255)");
				ctx.fillText(problem, fPosX, fPosY); 
				ctx.setFillStyle("rgb(0,230,57)");
				ctx.fillText(input, fPosX + ctx.measureText(problem).getWidth(), fPosY);  
			}
			
		}else{
			ctx.setFillStyle("rgb(255,255,255)");
			ctx.fillText(problem + input + sBlink, fPosX, fPosY);  
		}
	
		
		ctx.setGlobalAlpha(1);
		ctx.setShadowOffsetX(0);
		ctx.setShadowOffsetY(0);
		ctx.setShadowBlur(0);   
		
		bDraw = false;
    }

	public int isFound(){
		String sAnswer = NumberFormat.getFormat(dFormat).format(answer);
		if (input.equals(sAnswer)){
			//hit
			answered = true;
			correct = true;
			return 1;
		}else if (input.length() < sAnswer.length() && sAnswer.substring(0, input.length()).equals(input)){
			//this part is correct but not completed
			answered = false;
			return 2;
		}else{
			//fail
			answered = true;
			correct = false;
			return 0;
		}
	}
	
	public void addAnswerNumber(char key) {
		StringBuilder builder = new StringBuilder();
		builder
		   .append(input)
		   .append(key);
		input = builder.toString();
		bDraw = true;
	}
	
	public void tick(){
		nowTime = System.currentTimeMillis();
		long diffTime = nowTime - lastBlinkTime;
		if (diffTime > blinkFrequence){
			lastBlinkTime = nowTime;
			if (bBlink){
				bBlink = false;
				sBlink = "";
			}else{
				bBlink = true;
				sBlink = "_";
			}
			bDraw = true;
		}
	}
	
	public void stopCursorBlink(){
		blinkTimer.cancel();
		bBlink = false;
		sBlink = "";
	}
	
	public void startCoursorBlink() {
		bDraw = true;
		lastBlinkTime = System.currentTimeMillis();
		blinkTimer = new Timer() {
			@Override
			public void run() {
				tick();
			}
		};
		blinkTimer.scheduleRepeating(100);	
	}
	
	public void setPosition(int position){
		indexPosition = position;
		if (position != 0){
			if (position > 0){
				fPosY = 300 + (position * 40) + 40;
				fPosX = 350 - (position * 40);
			}else{
				fPosY = 300 + (position * 40) - 40;
				fPosX = 350 - (position * 40);
			}
			fHeight = 44;
			fWidth = 400;
			textFontSize = 32;
		}else{
			fPosY = 300;
			fPosX = 250;
			fWidth = 500;
			fHeight = 85;
			textFontSize = 60;
		}
		textFont = "bold "+ textFontSize + "px Comic Sans MS";
		bDraw=true;
	}

}


