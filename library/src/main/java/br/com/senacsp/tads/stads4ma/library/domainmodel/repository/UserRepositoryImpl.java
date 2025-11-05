package br.com.senacsp.tads.stads4ma.library.domainmodel.repository;

import br.com.senacsp.tads.stads4ma.library.domainmodel.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserRepositoryImpl {

    @PersistenceContext
    private EntityManager entityManager;

//    private final QUser user = Quser

    public Optional<User> findByIdWithPrifileAndPostsCriteria(UUID id){
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);

        Fetch<User,?> profileFetch = root.fetch("profile", JoinType.LEFT);
        Fetch<User,?> postFetch = root.fetch("posts", JoinType.LEFT);

        criteriaQuery.select(root).distinct(true).where(builder.equal(root.get("id"), id));
        TypedQuery querry = entityManager.createQuery(criteriaQuery);
        List<User> resultset = querry.getResultList();
        return resultset.stream().findFirst();
    }

    public List<User> findByMinPostsAndNameCriteria(int minPosts, String namePart){
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);


        criteriaQuery.select(root).where(builder.and(
                builder.greaterThanOrEqualTo(
                        builder.size(root.get("posts")), minPosts),
                (builder.like(
                        builder.lower(root.get("name")),
                                "%" + namePart.toLowerCase() + "%"))
                    )
                )
                .orderBy(builder.asc(root.get("name")));

        return entityManager.createQuery(criteriaQuery).getResultList();

    }

    private JPAQueryFactory qf(){
        return new JPAQueryFactory(this.entityManager);
    }

    public Optional<User> findByIdWithPrifileAndPostsQueryDsl(UUID id){
        QUser u = QUser.user;
        QProfile p = QProfile.profile;
        QPost post = QPost.posts;
        return this.qf().selectFrom(u).leftJoin(u.profile, p).fetchJoin()
                .leftJoin(u.posts, post).fetchJoin()
                .where(
                        u.id.eq(id)
                ).distinct()
                .fetch()
                .stream().findFirst();
    }

    public List<User> findByMinPostsAndNameQueryDsl(int minPosts, String namePart){
        return qf()
                .select(user)
                .from(user)
                .leftJoin(user.posts, posts)
                .where(user.name.containsIgnoreCase(namePart))
                .groupBy(user.id)
                .having(post.id.count().goes(minPosts))
                .orderBy(user.name.asc)
                .fetch;
    }
}
