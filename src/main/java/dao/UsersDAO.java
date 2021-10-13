package dao;

import models.User;
import org.hibernate.Criteria;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

public class UsersDAO {

    private final Session session;

    public UsersDAO(Session session) {
        this.session = session;
    }

    public User getUserByLogin(String login) {
        return getUserByRestrictions(Restrictions.eq("login", login));
    }

    public User getUserBySessionId(String sessionId) {
        return getUserByRestrictions(Restrictions.eq("sessionId", sessionId));
    }

    private User getUserByRestrictions(SimpleExpression expression) {
        Criteria criteria = session.createCriteria(User.class);
        return (User) criteria.add(expression).uniqueResult();
    }

    public void insertOrUpdateUser(User user) {
        session.saveOrUpdate(user);
    }
}
