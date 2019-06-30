package by.clevertec.WebApplication.repository;

import by.clevertec.WebApplication.dataSets.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    Page<User> findAllByOrderByAge(Pageable pageable);
}