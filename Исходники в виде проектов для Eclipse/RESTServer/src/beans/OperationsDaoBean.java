package beans;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entities.Operation;

/**
 * Session Bean implementation class BankAccountLinkBean
 */
@Stateless
@LocalBean
public class OperationsDaoBean {
	@PersistenceContext
	private EntityManager entityManager;

	public List<Operation> getOperationList() {
		Query query = entityManager.createQuery("Select oper from Operations oper");
		return query.getResultList();
	}

	public Operation getOperation(Operation operation) {
		Operation findOper = null;
		if (operation != null) {
			findOper = entityManager.find(operation.getClass(), operation.getId());
		}
		return findOper;
	}
}
