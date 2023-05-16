package deliverySystem.moacall.repository;


import deliverySystem.moacall.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m" , Member.class)
                .getResultList();
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

    public List<Member> findByUserId(String userId) {
        return em.createQuery("select m from Member m where m.userId = :userId", Member.class)
                .setParameter("userId" ,userId)
                .getResultList();
    }

    public List<Member> loginPermission(String userId, String password) {
        return em.createQuery("select m from Member m where m.userId = :userId and m.password = :password")
                .setParameter("userId", userId)
                .setParameter("password", password)
                .getResultList();
    }
}
