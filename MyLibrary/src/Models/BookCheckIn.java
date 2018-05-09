package Models;

import java.util.ArrayList;

public class BookCheckIn {
	
	
	public ArrayList<String> isbn = new ArrayList<String>();
	public ArrayList<String> firstName= new ArrayList<String>();
	public ArrayList<String> lastName= new ArrayList<String>();
	public ArrayList<String> cardNum= new ArrayList<String>();
	public ArrayList<String> getIsbn() {
		return isbn;
	}
	public void setIsbn(ArrayList<String> isbn) {
		this.isbn = isbn;
	}
	public ArrayList<String> getFirstName() {
		return firstName;
	}
	public void setFirstName(ArrayList<String> firstName) {
		this.firstName = firstName;
	}
	public ArrayList<String> getLastName() {
		return lastName;
	}
	public void setLastName(ArrayList<String> lastName) {
		this.lastName = lastName;
	}
	public ArrayList<String> getCardNum() {
		return cardNum;
	}
	public void setCardNum(ArrayList<String> cardNum) {
		this.cardNum = cardNum;
	}
	
	
	

}
