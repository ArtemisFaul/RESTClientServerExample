package model;

public class BillingList {

	private long id;

	private String operation;

	private long bankAccountFrom;

	private long bankAccountTo;

	private String description;

	private long summ;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
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

	public long getBankAccountFrom() {
		return bankAccountFrom;
	}

	public void setBankAccountFrom(long bankAccountFrom) {
		this.bankAccountFrom = bankAccountFrom;
	}

	public long getBankAccountTo() {
		return bankAccountTo;
	}

	public void setBankAccountTo(long bankAccountTo) {
		this.bankAccountTo = bankAccountTo;
	}

	public BillingList(long id, String operation, long bankAccountFrom, long bankAccountTo, String description,
			long summ) {
		super();
		this.id = id;
		this.operation = operation;
		this.bankAccountFrom = bankAccountFrom;
		this.bankAccountTo = bankAccountTo;
		this.description = description;
		this.summ = summ;
	}

}
