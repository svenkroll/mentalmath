package net.gibberfish.games.kopfrechnen.client;

import com.google.gwt.canvas.dom.client.Context2d;

public class MenueItem extends MenueDrawable{

	public MenueItem(String text, int i) {
		super(text);
		MenueSlot = i;
	}
	
	@Override
	public void draw(Context2d ctx_m) {	
		ctx_m.clearRect(MenuePosX-5, (MenueSlot * MenueSlotHeight) + 45 + MenuePosY, MenueSlotWidth+5, MenueSlotHeight+6);
		
		ctx_m.setTextBaseline("hanging");
    	ctx_m.setTextAlign("left");

		ctx_m.setShadowColor("rgb(190, 190, 190)");
		ctx_m.setShadowOffsetX(1);
		ctx_m.setShadowOffsetY(1);
		ctx_m.setShadowBlur(1);
		ctx_m.setFillStyle("rgb(255,255,255)");
		ctx_m.setGlobalAlpha(0.8);
		ctx_m.setFont("bold 24px Comic Sans MS");
		ctx_m.fillText(Text , MenuePosX, (MenueSlot * MenueSlotHeight) + 40 + MenuePosY);  		

		if (strikeThrough){
			ctx_m.setLineWidth(6);
		    ctx_m.beginPath();
		    ctx_m.setStrokeStyle("rgb(255,255,255)");
		    ctx_m.moveTo(MenuePosX, (MenueSlot * MenueSlotHeight) + 40 + MenuePosY);
		    ctx_m.lineTo(ctx_m.measureText(Text).getWidth() + MenuePosX + 4, (MenueSlot * MenueSlotHeight) + 42 + MenuePosY);
		    ctx_m.stroke();
		}
		ctx_m.setGlobalAlpha(1);
		ctx_m.setShadowOffsetX(0);
		ctx_m.setShadowOffsetY(0);
		ctx_m.setShadowBlur(0);
		
	    bDraw=false;
    }

	public boolean isText(String translation) {
		if (translation.equals(Text)){
			return true;
		}else{
			return false;
		}
	}

	public void isFound(boolean b) {
		strikeThrough = b;
		bDraw = true;
	}

}
