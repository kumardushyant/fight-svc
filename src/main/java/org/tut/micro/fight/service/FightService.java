package org.tut.micro.fight.service;

import java.time.Instant;
import java.util.List;
import java.util.Random;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import org.tut.micro.fight.data.Fighter;
import org.tut.micro.fight.data.Hero;
import org.tut.micro.fight.data.Villain;
import org.tut.micro.fight.data.dao.Fight;



@ApplicationScoped
@Transactional(value = TxType.SUPPORTS)
public class FightService {

	private static final Logger LOGGER = Logger.getLogger(FightService.class);

	private final Random random = new Random();

	@Inject
	@RestClient
	HeroService heroService;

	@Inject
	@RestClient
	VillainService villainService;

	public List<Fight> findAllFights() {
		return Fight.listAll();
	}

	public Fight findFightById(Long id) {
		return Fight.findById(id);
	}

	public Fighter findRandomFighters() {
		// T1
		Hero hero = findRandomHero(); // 4s
		Villain villain = findRandomVillain(); // 6s
		Fighter fighters = new Fighter();
		fighters.hero = hero;
		fighters.villain = villain;
		return fighters;

	}

	@Fallback(fallbackMethod = "fallbackRandomHero")
	Hero findRandomHero() {
		try {
			return heroService.getRandomHero();
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}

	//@Fallback(fallbackMethod = "fallbackRandomVillain")
	Villain findRandomVillain() {
		return villainService.getRandomVillain();
	}
	
	
	public Hero fallbackRandomHero() {
	    LOGGER.warn("Falling back on Hero");
	    Hero hero = new Hero();
	    hero.name = "Fallback hero";
	    hero.picture = "https://dummyimage.com/280x380/1e8fff/ffffff&text=Fallback+Hero";
	    hero.powers = "Fallback hero powers";
	    hero.level = 1;
	    return hero;
	}
	
	public Villain fallbackRandomVillain() {
	    LOGGER.warn("Falling back on Villain");
	    Villain villain = new Villain();
	    villain.name = "Fallback villain";
	    villain.picture = "https://dummyimage.com/280x380/b22222/ffffff&text=Fallback+Villain";
	    villain.powers = "Fallback villain powers";
	    villain.level = 2;
	    return villain;
	}

	@Transactional(value = TxType.REQUIRED)
	public Fight persistFight(Fighter fighters) {
		// Amazingly fancy logic to determine the winner...
		Fight fight;

		int heroAdjust = random.nextInt(20);
		int villainAdjust = random.nextInt(20);

		if ((fighters.hero.level + heroAdjust) > (fighters.villain.level + villainAdjust)) {
			fight = heroWon(fighters);
		} else if (fighters.hero.level < fighters.villain.level) {
			fight = villainWon(fighters);
		} else {
			fight = random.nextBoolean() ? heroWon(fighters) : villainWon(fighters);
		}

		fight.fightDate = Instant.now();

		fight.persist();

		//

		return fight;

	}

	private Fight heroWon(Fighter fighters) {
		LOGGER.info("Yes, Hero won :o)");
		Fight fight = new Fight();
		fight.winnerName = fighters.hero.name;
		fight.winnerPicture = fighters.hero.picture;
		fight.winnerLevel = fighters.hero.level;
		fight.loserName = fighters.villain.name;
		fight.loserPicture = fighters.villain.picture;
		fight.loserLevel = fighters.villain.level;
		fight.winnerTeam = "heroes";
		fight.loserTeam = "villains";
		return fight;
	}

	private Fight villainWon(Fighter fighters) {
		LOGGER.info("Gee, Villain won :o(");
		Fight fight = new Fight();
		fight.winnerName = fighters.villain.name;
		fight.winnerPicture = fighters.villain.picture;
		fight.winnerLevel = fighters.villain.level;
		fight.loserName = fighters.hero.name;
		fight.loserPicture = fighters.hero.picture;
		fight.loserLevel = fighters.hero.level;
		fight.winnerTeam = "villains";
		fight.loserTeam = "heroes";
		return fight;
	}

}
