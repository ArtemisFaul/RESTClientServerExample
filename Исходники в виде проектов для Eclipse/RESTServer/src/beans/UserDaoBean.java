package beans;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entities.User;

/**
 * Session Bean implementation class BillingDaoBean
 */
@Stateless
@LocalBean
public class UserDaoBean {

	@PersistenceContext
	private EntityManager entityManager;

	public List<User> getUserList() {
		Query query = entityManager.createQuery("Select u from User u");
		return query.getResultList();
	}

	public void saveNewUser(User user) {
		if (user != null) {
			if (user.getUser() != null) {
				entityManager.persist(user);
			}
		}
	}

	public User getUser(User user) {
		User findUsr = null;
		if (user != null) {
			findUsr = entityManager.find(user.getClass(), user.getId());
		}
		return findUsr;
	}

	public void updateUser(User user) {
		if (user != null) {
			if (user.getUser() != null) {
				entityManager.merge(user);
			}
		}
	}

	public void removeUser(User user) {
		User remUse = null;
		if (user != null) {
			remUse = entityManager.merge(user);
			entityManager.remove(remUse);
		}
	}
}
