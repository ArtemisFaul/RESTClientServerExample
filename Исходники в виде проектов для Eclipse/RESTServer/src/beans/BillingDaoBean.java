package beans;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entities.BankAccount;
import entities.Billing;
import entities.Operation;
import entities.User;

/**
 * Session Bean implementation class BankAccountLinkBean
 */
@Stateless
@LocalBean
public class BillingDaoBean {
	@PersistenceContext
	private EntityManager entityManager;

	public List<Billing> getBillingInputList(Long accountId) {
		Query query = entityManager.createQuery(
				"Select bil from Billing bil " + "join bil.bankAccountTo opto " + "where opto.id = :accountId");
		query.setParameter("accountId", accountId);
		return query.getResultList();
	}

	public List<Billing> getBillingOutList(Long accountId) {
		Query query = entityManager.createQuery(
				"Select bil from Billing bil " + "join bil.bankAccountFrom opt " + "where opt.id = :accountId");
		query.setParameter("accountId", accountId);
		return query.getResultList();
	}

	public void saveNew(Long userId, Long bankAccountFromId, Long bankAccountToId, Long summ, String description) {
		User user = entityManager.find(User.class, userId);
		BankAccount bankAccountFrom = entityManager.find(BankAccount.class, bankAccountFromId);
		Long newSummFrom = bankAccountFrom.getSumm() - summ;
		bankAccountFrom.setSumm(newSummFrom);
		bankAccountFrom = entityManager.merge(bankAccountFrom);
		BankAccount bankAccountTo = entityManager.find(BankAccount.class, bankAccountToId);
		Long newSummTo = bankAccountTo.getSumm() + summ;
		bankAccountTo.setSumm(newSummTo);
		bankAccountTo = entityManager.merge(bankAccountTo);

		Operation operationFrom = entityManager.find(Operation.class, 3L);
		Operation operationTo = entityManager.find(Operation.class, 2L);

		Billing billing = new Billing();
		billing.setOperationFrom(operationFrom);
		billing.setOperationTo(operationTo);
		billing.setBankAccountFrom(bankAccountFrom);
		billing.setBankAccountTo(bankAccountTo);
		billing.setDescription(description);
		billing.setSumm(summ);
		billing.setUser(user);
		entityManager.persist(billing);
	}
	/*
	 * public void saveNew(Billing billing) { if (billing != null) { if
	 * (billing.getOperationFrom() != null && billing.getUser() != null &&
	 * billing.getBankAccountFrom() != null) { entityManager.persist(billing); } }
	 * 
	 * }
	 * 
	 * public void updateBilling(Billing billing) { if (billing != null) { if
	 * (billing.getOperationFrom() != null && billing.getUser() != null &&
	 * billing.getBankAccountFrom() != null) { entityManager.merge(billing); } } }
	 * 
	 * public void removeBilling(Billing billing) { Billing remBill = null; if
	 * (billing != null) { remBill = entityManager.merge(billing);
	 * entityManager.remove(remBill); } }
	 */
}
