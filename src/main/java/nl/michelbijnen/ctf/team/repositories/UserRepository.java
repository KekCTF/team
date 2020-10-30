package nl.michelbijnen.ctf.team.repositories;

import nl.michelbijnen.ctf.team.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
