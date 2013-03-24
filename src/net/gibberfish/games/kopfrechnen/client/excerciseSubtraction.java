package net.gibberfish.games.kopfrechnen.client;

import com.google.gwt.user.client.Random;

public class excerciseSubtraction extends excercise {

	public excerciseSubtraction(int lvl){
		super(lvl);
		
		dFormat = "#"; 

		int rand1 = Random.nextInt(100 + (Level * Level * 100));
		int rand2 = Random.nextInt(100 + (Level * Level * 100));
		answer = rand1 - rand2;
		
		StringBuilder builder = new StringBuilder();
		builder
		   .append(rand1)
		   .append(" - ")
		   .append(rand2)
		   .append(" = ");
		problem = builder.toString();
	}
}
