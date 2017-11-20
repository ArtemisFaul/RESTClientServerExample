package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bankaccountlink")
public class BankAccountLink {
	@Id
	@Column(name = "ID")
	private long id;
	
	@OneToOne
	@JoinColumn(name = "UserID")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "BankAccauntID")
	private BankAccount bankAccount;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public BankAccount getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}

	
}
