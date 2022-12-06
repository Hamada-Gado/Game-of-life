package state;

public enum State {
	START_STATE("START"),
	GAME_STATE("GAME"),
	RULES_STATE("RULES");
	
	public String value;
	
	State(String value){
		this.value = value;	
	}
	
}