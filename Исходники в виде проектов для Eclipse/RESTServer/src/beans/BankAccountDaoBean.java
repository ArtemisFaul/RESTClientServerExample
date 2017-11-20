package beans;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entities.BankAccount;
import entities.BankAccountLink;
import entities.User;

/**
 * Session Bean implementation class BankAccountLinkBean
 */
@Stateless
@LocalBean
public class BankAccountDaoBean {

	@EJB
	BankAccountLinkDaoBean bankAccountLinkDaoBean;

	@PersistenceContext
	private EntityManager entityManager;

	public List<BankAccount> getAccountListFree() {
		Query query = entityManager.createQuery("Select bac from BankAccount bac");
		return query.getResultList();
	}

	public List<BankAccount> getAccountList(Long userId) {
		Query query = entityManager.createQuery("" + "Select bl from BankAccountLink ubl " + "LEFT JOIN ubl.user ul "
				+ "LEFT JOIN ubl.bankAccount bl " + "WHERE ul.id = :userId");
		query.setParameter("userId", userId);
		return query.getResultList();
	}

	public void saveNew(BankAccount bankAccount, Long userId) {
		if (bankAccount != null && userId != null) {
			if (bankAccount.getNumber() > 0) {
				if (bankAccount.getSumm() > 0) {
					entityManager.persist(bankAccount);
					Query query = entityManager
							.createQuery("Select bac from BankAccount bac where bac.number = :finNum");
					query.setParameter("finNum", bankAccount.getNumber());
					if (query.getResultList().size() > 0) {
						BankAccount foundedBAccount = (BankAccount) query.getResultList().get(0);
						User foundedUser = entityManager.find(User.class, userId);
						BankAccountLink newBankAccountLink = new BankAccountLink();
						newBankAccountLink.setBankAccount(foundedBAccount);
						newBankAccountLink.setUser(foundedUser);
						bankAccountLinkDaoBean.saveNew(newBankAccountLink);
					}
				}
			}
		}
	}

	public List<BankAccount> searchAccount(Long accountNum) {
		List<BankAccount> result = null;
		if (accountNum != null) {
			Query query = entityManager.createQuery("Select bac from BankAccount bac where bac.number = :finNum");
			query.setParameter("finNum", accountNum);
			result = query.getResultList();
		}
		return result;
	}

	public List<BankAccount> searchAccountLike(Long accountNum) {
		List<BankAccount> result = null;
		if (accountNum != null) {
			Query query = entityManager
					.createQuery("Select bac from BankAccount bac where cast(bac.number as char) like :finNum");
			query.setParameter("finNum", "%" + accountNum + "%");
			result = query.getResultList();
		}
		return result;
	}

	public void updateAccount(BankAccount bankAccount) {
		if (bankAccount != null) {
			entityManager.merge(bankAccount);
		}
	}

	public void removeAccount(BankAccount bankAccount) {
		BankAccount remBankAc = null;
		if (bankAccount != null) {
			remBankAc = entityManager.merge(bankAccount);
			entityManager.remove(remBankAc);
		}
	}
}
