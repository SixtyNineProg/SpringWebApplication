package by.clevertec.WebApplication.repository;

import by.clevertec.WebApplication.datasets.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    Page<User> findAllByOrderByAge(Pageable pageable);
}