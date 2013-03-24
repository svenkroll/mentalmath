package net.gibberfish.games.kopfrechnen.client;

import com.google.gwt.canvas.dom.client.Context2d;

public class MenueDrawable {

	protected boolean bDraw = false;
	protected int MenueSlot = -1;
	protected int MenueSlotHeight = 33;
	protected int MenueSlotWidth = 350;
	protected int MenuePosX = 30;
	protected int MenuePosY = 0;
	protected String Text = null;
	protected boolean strikeThrough = false;
	protected int count;
	
	public MenueDrawable(String text) {
		Text = text;
		bDraw = true;
	}

	public void draw(Context2d ctx_m) {
		ctx_m.setFillStyle("rgb(0,0,0)");
		ctx_m.setFont("20pt Arial");
		ctx_m.fillText(Text, 5, MenueSlot * MenueSlotHeight);   
    }
	
	public void setText(String text){
		Text = text;
		bDraw = true;
	}

	public int getMenueSlot() {
		return MenueSlot;
	}
}

