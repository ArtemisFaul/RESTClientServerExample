package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bankaccount")
public class BankAccount {
	

	@Id
	@Column(name = "ID")
	private long id;
	
	@Column(name = "Number")
	private long number;
	
	@Column(name = "Summ")
	private long summ;


	@Column(name = "Description")
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
	
	
}
