package fr.game.fight;

import java.util.List;

public class Army {
	private List<Fighter> fighters;
	private String name;

	public List<Fighter> getFighters() {
		return fighters;
	}
	public void setFighters(List<Fighter> fighters) {
		this.fighters = fighters;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
