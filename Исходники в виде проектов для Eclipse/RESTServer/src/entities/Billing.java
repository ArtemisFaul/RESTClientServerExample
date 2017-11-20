package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "billing")
public class Billing {
	@Id
	@Column(name = "ID")
	private long id;

	@ManyToOne
	@JoinColumn(name = "OperationFromID")
	private Operation operationFrom;

	@ManyToOne
	@JoinColumn(name = "OperationToID")
	private Operation operationTo;

	@ManyToOne
	@JoinColumn(name = "UserID")
	private User user;

	@ManyToOne
	@JoinColumn(name = "bankAccountFromID")
	private BankAccount bankAccountFrom;

	@ManyToOne
	@JoinColumn(name = "bankAccountToID")
	private BankAccount bankAccountTo;

	@Column(name = "Description")
	private String description;

	@Column(name = "Summ")
	private long Summ;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Operation getOperationFrom() {
		return operationFrom;
	}

	public void setOperationFrom(Operation operationFrom) {
		this.operationFrom = operationFrom;
	}

	public Operation getOperationTo() {
		return operationTo;
	}

	public void setOperationTo(Operation operationTo) {
		this.operationTo = operationTo;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public BankAccount getBankAccountFrom() {
		return bankAccountFrom;
	}

	public void setBankAccountFrom(BankAccount bankAccountFrom) {
		this.bankAccountFrom = bankAccountFrom;
	}

	public BankAccount getBankAccountTo() {
		return bankAccountTo;
	}

	public void setBankAccountTo(BankAccount bankAccountTo) {
		this.bankAccountTo = bankAccountTo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getSumm() {
		return Summ;
	}

	public void setSumm(long summ) {
		Summ = summ;
	}

}
