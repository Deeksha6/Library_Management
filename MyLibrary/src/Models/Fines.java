package Models;

import java.util.ArrayList;

public class Fines {

	public ArrayList<Float> fine_amt = new ArrayList<Float>();
	public ArrayList<String> first_name = new ArrayList<String>();
	public ArrayList<String> last_name = new ArrayList<String>();
	public ArrayList<String> card_id = new ArrayList<String>();
	public ArrayList<Float> getFine_amt() {
		return fine_amt;
	}
	public void setFine_amt(ArrayList<Float> fine_amt) {
		this.fine_amt = fine_amt;
	}
	public ArrayList<String> getFirst_name() {
		return first_name;
	}
	public void setFirst_name(ArrayList<String> first_name) {
		this.first_name = first_name;
	}
	public ArrayList<String> getLast_name() {
		return last_name;
	}
	public void setLast_name(ArrayList<String> last_name) {
		this.last_name = last_name;
	}
	public ArrayList<String> getCard_id() {
		return card_id;
	}
	public void setCard_id(ArrayList<String> card_id) {
		this.card_id = card_id;
	}
	
	
}