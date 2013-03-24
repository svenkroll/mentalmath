package net.gibberfish.games.kopfrechnen.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class SplashScreen {
	
	Canvas canvasSplash;
	Context2d ctxSplash;
	
	int canvasHeight = MentalMath.AppHeight;
	int canvasWidth = MentalMath.AppWidth;
	
	BoardButton btnAddition, btnDivision, btnMultiplication, btnSubtraction;
	MentalMath parent;
	
	public SplashScreen(MentalMath wortsuche, int height, int width){
		
		parent = wortsuche;
		canvasHeight = height;
		canvasWidth = width;
		
		canvasSplash = Canvas.createIfSupported();
		canvasSplash.setVisible(true);
		if (canvasSplash == null) {
			RootPanel
					.get(MentalMath.holderId)
					.add(new Label(
							"Sorry, your browser doesn't support the HTML5 Canvas element"));
			return;
		}
		canvasSplash.setStyleName("splashCanvas");
		canvasSplash.setWidth(canvasWidth+"px");
		canvasSplash.setCoordinateSpaceWidth(canvasWidth);

		canvasSplash.setHeight(canvasHeight+"px");
		canvasSplash.setCoordinateSpaceHeight(canvasHeight);

		RootPanel.get(MentalMath.holderId).add(canvasSplash);
		ctxSplash = canvasSplash.getContext2d();
		
		//btnEasy = new BoardButton(200, 320, 80, "Leicht");
		btnAddition = new BoardButton(450, 250, 300, 60, "Addition");
		btnAddition.setVisible(true);
		btnMultiplication = new BoardButton(450, 330, 300, 60, "Multiplication");
		btnMultiplication.setVisible(true);
		btnDivision = new BoardButton(450, 410, 300, 60, "Division");
		btnDivision.setVisible(true);
		btnSubtraction = new BoardButton(450, 490, 300, 60, "Subtraction");
		btnSubtraction.setVisible(true);

		
		canvasSplash.addClickHandler(new ClickHandler() {
        	@Override
			public void onClick(ClickEvent event) {

	            checkButtonClicked(event);	
			}
		});   
		
		canvasSplash.addMouseMoveHandler(new MouseMoveHandler(){
            public void onMouseMove(MouseMoveEvent e) {
            	checkMouseOver(e.getX(),e.getY());
            }
		});

	}
	

	private void checkMouseOver(int x, int y) {
        if (btnAddition.checkMouseOver(x, y)){
        	if (!btnAddition.isMouseOver()){
        		btnAddition.setMouseOver(true);
        		parent.soundSelect.play();
        	}
        }else{
        	btnAddition.setMouseOver(false);
        }

        if (btnDivision.checkMouseOver(x, y)){
        	if (!btnDivision.isMouseOver()){
        		btnDivision.setMouseOver(true);
        		parent.soundSelect.play();
        	}
        }else{
        	btnDivision.setMouseOver(false);
        }
        
        if (btnSubtraction.checkMouseOver(x, y)){
        	if (!btnSubtraction.isMouseOver()){
        		btnSubtraction.setMouseOver(true);
        		parent.soundSelect.play();
        	}
        }else{
        	btnSubtraction.setMouseOver(false);
        }
        
        if (btnMultiplication.checkMouseOver(x, y)){
        	if (!btnMultiplication.isMouseOver()){
        		btnMultiplication.setMouseOver(true);
        		parent.soundSelect.play();
        	}
        }else{
        	btnMultiplication.setMouseOver(false);
        }
        
        draw();
	}
	
	public void draw(){
		ctxSplash.clearRect(0,0, canvasWidth, canvasHeight);

		ctxSplash.setStrokeStyle("#A0522D");
		ctxSplash.setLineWidth(10);
		ctxSplash.strokeRect(0,0, canvasWidth, canvasHeight);

		
		ctxSplash.setTextAlign("left");
		ctxSplash.setTextBaseline("middle");
		ctxSplash.setFont("bold 40pt Comic Sans MS"); 
		ctxSplash.setShadowColor("rgb(190, 190, 190)");
		ctxSplash.setShadowOffsetX(5);
		ctxSplash.setShadowOffsetY(5);
		ctxSplash.setShadowBlur(10);
		ctxSplash.setFillStyle("rgb(255,255,255)");
		ctxSplash.setGlobalAlpha(0.8);
		ctxSplash.fillText("Mental Math", 30, 80);
		ctxSplash.setFont("bold 30pt Comic Sans MS"); 
		ctxSplash.fillText("Choose your operation of choice:", 70, 150);
		ctxSplash.setGlobalAlpha(1);

		ctxSplash.setShadowOffsetX(0);
		ctxSplash.setShadowOffsetY(0);
		ctxSplash.setShadowBlur(0);
		
	    //Draw Buttons
		btnMultiplication.draw(ctxSplash);
		btnAddition.draw(ctxSplash);
		btnSubtraction.draw(ctxSplash);
		btnDivision.draw(ctxSplash);

	}

	public void hide() {
		canvasSplash.setVisible(false);
	}

	public void show() {
		canvasSplash.setVisible(true);
		draw();
	}
	
	private void checkButtonClicked(ClickEvent event) {

        if (btnAddition.isMouseOver()){
        	parent.soundClick.play();
        	start(0);
        }

        if (btnMultiplication.isMouseOver()){
        	parent.soundClick.play();
        	start(1);
        }
        
        if (btnDivision.isMouseOver()){
        	parent.soundClick.play();
        	start(2);
        }
        
        if (btnSubtraction.isMouseOver()){
        	parent.soundClick.play();
        	start(3);
        }
	}

	private void start(int i) {
		canvasSplash.setVisible(false);
		parent.start(i,0);		
	}


}
