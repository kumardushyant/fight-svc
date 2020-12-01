/**
 * 
 */
package org.tut.micro.fight.data.dao;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity(name = "fight.fight")
@Table(name="fight")
public class Fight extends PanacheEntity {

	@JsonIgnore
	@NotNull
	public Instant fightDate;
	@NotNull
	public String winnerName;
	@NotNull
	public int winnerLevel;
	@NotNull
	public String winnerPicture;
	@NotNull
	public String loserName;
	@NotNull
	public int loserLevel;
	@NotNull
	public String loserPicture;
	@NotNull
	public String winnerTeam;
	@NotNull
	public String loserTeam;

	// toString method
}