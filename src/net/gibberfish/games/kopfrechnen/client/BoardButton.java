package net.gibberfish.games.kopfrechnen.client;

import com.google.gwt.canvas.dom.client.Context2d;

public class BoardButton {

	private int x;
	private int y;
	private int radius;
	private int height, width;
	private String text;
	private boolean hit, isRect, visible = false;

	public BoardButton(int ix, int iy, int radius, String text) {
		setX(ix);
		setY(iy);
		setRadius(radius);
		this.text = text;
	}
	
	public BoardButton(int ix, int iy, int width, int height, String text) {
		setX(ix);
		setY(iy);
		this.height = height;
		this.width = width;
		setRadius(5);
		isRect = true;
		this.text = text;
	}

	public void setVisible(boolean b){
		visible = b;
	}
	
	public void draw(Context2d context){  
		if (visible){
			if (!isRect){
			  context.beginPath();
		      context.arc(x, y, radius, 0, 360, false);
		      context.closePath();
		      
			}else{
				context.beginPath();
				context.moveTo(x-(width/2)+radius, y-(height/2));
				context.arcTo(x+(width/2), y-(height/2), x+(width/2), y+(height/2), radius);
				context.arcTo(x+(width/2), y+(height/2), x-(width/2), y+(height/2), radius);
				context.arcTo(x-(width/2), y+(height/2), x-(width/2), y-(height/2), radius);
				context.arcTo(x-(width/2), y-(height/2), x+(width/2), y-(height/2), radius);
				context.closePath();
			}
			
			context.setLineWidth(5);
		    context.setFillStyle("#FFFFFF");
			
		    if (hit){
				context.setGlobalAlpha(1);
			}else{
				context.setGlobalAlpha(0.5);
			}
			
			context.fill();	      
			context.setStrokeStyle("#FFFFFF");	      
			context.stroke();  
		  
			context.setGlobalAlpha(1);
			context.setFont("28pt Comic Sans MS"); 
			context.setTextAlign("center");
			context.setTextBaseline("middle");
			context.setFillStyle("rgb(0,0,0)");
			context.fillText(text, x, y); 
		}
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int rad) {
		this.radius = rad;
	}

	public boolean isMouseOver(){
		return hit;
	}
	
	public void setMouseOver(boolean b) {
		hit = b;
	}
	
	public boolean checkMouseOver(int click_x, int click_y){
		boolean ret = false;
		if (visible){
			if (!isRect){
				int a = click_x - x;
		        int b = click_y - y;
		        int c = (int) Math.sqrt(a * a + b * b);
		        
		        if (c <= radius){
		        	ret = true;
		        }
			}else{
				if ((click_x > x-(width/2) && (click_x < (x + (width/2))))){
	                if ((click_y > y-(height/2)) && (click_y < (y + (height/2)))){
	                	ret = true;
	                }
	            }
			}
		}
		return ret;
		
	}
	
}
