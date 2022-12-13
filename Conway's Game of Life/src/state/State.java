package state;

public enum State {
	START_STATE("START"),
	GAME_STATE("GAME"),
	RULES_STATE("RULES");
	
	private String value;
	
	State(String value){
		this.value = value;	
	}

	public String getValue() {
		return value;
	}
	
}