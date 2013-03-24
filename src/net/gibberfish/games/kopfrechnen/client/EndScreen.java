package net.gibberfish.games.kopfrechnen.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class EndScreen {
	
	Canvas endCanvas;
	Context2d ctxWon;
	
	int canvasHeight = MentalMath.AppHeight;
	int canvasWidth = MentalMath.AppWidth;
	
	BoardButton btnRestart, btnNext;
	MentalMath parent;
	String Level,nextLevel,excercises,sPercent;
	int iLevel;
	int percentCorrect;
	
	public EndScreen(MentalMath wortsuche, int height, int width){
		
		parent = wortsuche;
		canvasHeight = height;
		canvasWidth = width;
		
		endCanvas = Canvas.createIfSupported();
		endCanvas.setVisible(false);
		if (endCanvas == null) {
			RootPanel
					.get(MentalMath.holderId)
					.add(new Label(
							"Sorry, your browser doesn't support the HTML5 Canvas element"));
			return;
		}
		endCanvas.setStyleName("endCanvas");
		endCanvas.setWidth(canvasWidth+"px");
		endCanvas.setCoordinateSpaceWidth(canvasWidth);

		endCanvas.setHeight(canvasHeight+"px");
		endCanvas.setCoordinateSpaceHeight(canvasHeight);

		RootPanel.get(MentalMath.holderId).add(endCanvas);
		
		ctxWon = endCanvas.getContext2d();
		
		btnRestart = new BoardButton(400, 430, 300, 60, "Repeat");
		btnNext = new BoardButton(400, 500, 300, 60, "Next level");

		
		endCanvas.addClickHandler(new ClickHandler() {
        	@Override
			public void onClick(ClickEvent event) {

	            checkButtonClicked(event);	
			}
		});   
		
		endCanvas.addMouseMoveHandler(new MouseMoveHandler(){
            public void onMouseMove(MouseMoveEvent e) {
            	checkMouseOver(e.getX(),e.getY());
            }
		});
	}

	private void checkMouseOver(int x, int y) {
		
		if (btnRestart.checkMouseOver(x, y)){
			if (!btnRestart.isMouseOver()){
				btnRestart.setMouseOver(true);
				parent.soundSelect.play();
			}
        }else{
        	btnRestart.setMouseOver(false);
        }
		if (btnNext.checkMouseOver(x, y)){
			if (!btnNext.isMouseOver()){
				btnNext.setMouseOver(true);
				parent.soundSelect.play();
			}
        }else{
        	btnNext.setMouseOver(false);
        }

        draw();
	}
	
	public void draw(){
		ctxWon.clearRect(0,0, canvasWidth, canvasHeight);

		ctxWon.setStrokeStyle("#A0522D");
		ctxWon.setLineWidth(5);
		ctxWon.strokeRect(0,0, canvasWidth, canvasHeight);

		ctxWon.setFont("bold 30pt Comic Sans MS"); 
		ctxWon.setShadowColor("rgb(190, 190, 190)");
		ctxWon.setShadowOffsetX(2);
		ctxWon.setShadowOffsetY(2);
		ctxWon.setShadowBlur(2);
		ctxWon.setFillStyle("rgb(255,255,255)");
		ctxWon.setGlobalAlpha(0.8);
		
		
		ctxWon.setTextAlign("left");
		ctxWon.setTextBaseline("middle");
		ctxWon.fillText("Level:", 50, 80); 
		ctxWon.fillText("Answered:" , 50, 150);
		ctxWon.fillText("Answered correctly:", 50, 230); 
 
		ctxWon.fillText(Level, 600, 80); 
		ctxWon.fillText(excercises, 600, 150);
		if (percentCorrect > 49){
			ctxWon.setFillStyle("rgb(0,230,57)");
		}else{
			ctxWon.setFillStyle("rgb(250,0,0)");
		}
		
		ctxWon.fillText(sPercent + "%" , 600, 230); 
		
		ctxWon.setGlobalAlpha(1);
		ctxWon.setShadowOffsetX(0);
		ctxWon.setShadowOffsetY(0);
		ctxWon.setShadowBlur(0);
		
	    //Draw Buttons
		btnRestart.draw(ctxWon);
		btnNext.draw(ctxWon);

	}

	public void hide() {
		endCanvas.setVisible(false);
	}
	
	public void show() {
		endCanvas.setVisible(true);
		draw();
	}
	
	private void checkButtonClicked(ClickEvent event) {

        if (btnRestart.isMouseOver()){
        	parent.soundClick.play();
        	parent.replay();
        	endCanvas.setVisible(false);
        }
        if (btnNext.isMouseOver()){
        	parent.soundClick.play();
        	parent.nextLevel();
        	endCanvas.setVisible(false);
        }
	}

	public void setStats(int answersCorrect, int answersWrong,int operation, int level) {
		excercises = String.valueOf((answersCorrect + answersWrong));
		int answercount = answersCorrect + answersWrong;
		if (answercount > 0){
			percentCorrect = (int)(100.0f / answercount) * answersCorrect;
			sPercent = String.valueOf(percentCorrect);
		}else{
			percentCorrect = 0;
			sPercent = "0";
		}
		Level = String.valueOf(level);
		iLevel = level;
		nextLevel = String.valueOf(level+1);
		if (percentCorrect > 49){
			btnRestart.setVisible(false);
			btnNext.setVisible(true);
		}else{
			btnRestart.setVisible(true);
			btnNext.setVisible(false);
		}
		Analytics.gaEventTracker("MentalMath", "LevelEnd", sPercent);
	}
}
