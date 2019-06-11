package by.clevertec.WebApplication.repository;

import by.clevertec.WebApplication.dataSets.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}