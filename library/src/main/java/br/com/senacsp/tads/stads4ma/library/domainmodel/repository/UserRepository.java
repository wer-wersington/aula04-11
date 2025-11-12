package br.com.senacsp.tads.stads4ma.library.domainmodel.repository;

import br.com.senacsp.tads.stads4ma.library.domainmodel.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    List<User> findByEmail(String email);
    List<User> findByNameAndEmail(String name, String email);
    List<User> findByPosts(List<Post> posts);
    List<User> findByName(List<Post> posts);


    @Query("SELECT u FROM User u WHERE u.name = :name")
    Optional<User> findByName(String nome);
   @Query("SELECT u FROM User u Where size(u.posts) >= :minPosts AND lower(u.name) like lower(concat('%', :namePart, '%'))" +
           "ORDER BY u.name asc" +

           "")
    List<User> findByMinPostsAndNameLike(@Param("minPosts") int minPosts, @Param("namePart")String namePart);
}
