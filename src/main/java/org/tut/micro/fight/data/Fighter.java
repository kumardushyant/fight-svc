package org.tut.micro.fight.data;

import javax.validation.constraints.NotNull;

public class Fighter {
	
	@NotNull
	public Hero hero;
	@NotNull
	public Villain villain;
	

}
