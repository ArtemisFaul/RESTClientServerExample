package model;

public class BankAccount {

	private long id;
	private long number;
	private long summ;
	private String description;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getSumm() {
		return summ;
	}

	public void setSumm(long summ) {
		this.summ = summ;
	}

	public BankAccount(long id, long number, String description, long summ) {
		super();
		this.id = id;
		this.number = number;
		this.description = description;
		this.summ = summ;
	}

	@Override
	public String toString() {
		return Long.toString(number);
	}

}
