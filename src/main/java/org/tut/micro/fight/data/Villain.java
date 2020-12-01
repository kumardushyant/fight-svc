package org.tut.micro.fight.data;

import javax.validation.constraints.NotNull;

public class Villain {
    
    @NotNull
    public String id;
	@NotNull
    public String name;
    @NotNull
    public String otherName;
    @NotNull
    public int level;
    @NotNull
    public String picture;
    public String powers;

}
