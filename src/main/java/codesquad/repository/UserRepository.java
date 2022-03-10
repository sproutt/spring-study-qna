package codesquad.repository;

import codesquad.domain.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class UserRepository {

    private final EntityManager em;

    public UserRepository(EntityManager em) {
        this.em = em;
    }

    public Long save(User user) {
        em.persist(user);
        return user.getId();
    }

    public List<User> findAll() {
        return em.createQuery(
                "select u from User u", User.class)
                .getResultList();
    }

    public User findById(Long id) {
        return em.find(User.class, id);
    }
}
