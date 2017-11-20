package beans;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entities.BankAccountLink;

/**
 * Session Bean implementation class BankAccountLinkBean
 */
@Stateless
@LocalBean
public class BankAccountLinkDaoBean {
	@PersistenceContext
	private EntityManager entityManager;

	public List<BankAccountLink> getLinkList() {
		Query query = entityManager.createQuery("Select bal from BankAccountLink bal");
		return query.getResultList();
	}

	public void saveNew(BankAccountLink bankAccountLink) {
		if (bankAccountLink != null) {
			if (bankAccountLink.getBankAccount() != null && bankAccountLink.getUser() != null) {
				entityManager.persist(bankAccountLink);
			}
		}

	}

	public void updateLink(BankAccountLink bankAccountLink) {
		if (bankAccountLink != null) {
			if (bankAccountLink.getBankAccount() != null && bankAccountLink.getUser() != null) {
				entityManager.merge(bankAccountLink);
			}
		}
	}

	public void removeLink(BankAccountLink bankAccountLink) {
		BankAccountLink remBankAcLink = null;
		if (bankAccountLink != null) {
			remBankAcLink = entityManager.merge(bankAccountLink);
			entityManager.remove(remBankAcLink);
		}
	}
}
