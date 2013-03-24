package net.gibberfish.games.kopfrechnen.client;


import com.google.gwt.user.client.Random;

public class excerciseMultiplication extends excercise{

	public excerciseMultiplication(int lvl){
		super(lvl);
		
		dFormat = "#";  
		
		int rand1 = Random.nextInt(10 + (Level * Level * 10));
		int rand2 = Random.nextInt(10 + (Level * Level * 10));
		answer = rand1 * rand2;
		
		StringBuilder builder = new StringBuilder();
		builder
		   .append(rand1)
		   .append(" * ")
		   .append(rand2)
		   .append(" = ");
		problem = builder.toString();
	}
}
