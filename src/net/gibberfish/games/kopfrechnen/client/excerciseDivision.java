package net.gibberfish.games.kopfrechnen.client;

import com.google.gwt.user.client.Random;

public class excerciseDivision extends excercise {
	
	public excerciseDivision(int lvl){
		super(lvl);
		
		dFormat = "#.#"; 
 
		double rand1,rand2;
		do{
			rand1 = Random.nextInt(10 + (Level * Level * 10));
		}while (rand1 == 0);
		
		do{
			rand2 = Random.nextInt(10 + (Level * Level * 10));
		}while (rand2 == 0);
		
		answer = rand1 / rand2;
		
		StringBuilder builder = new StringBuilder();
		builder
		   .append(rand1)
		   .append(" / ")
		   .append(rand2)
		   .append(" = ");
		problem = builder.toString();
	}
}
