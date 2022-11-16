package com.backend.repositories;

import com.backend.entities.users.DBAccount;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface AccountsRepo extends MongoRepository<com.backend.entities.users.DBAccount, String> {
    @Query("{'accountID': ?0}")
    DBAccount findByAccountID(String accountID);
    @Query("{'sessionID': ?0}")
    DBAccount findBySessionID(String sessionID);
    @Query("{'sessionID': ?0}")
    DBAccount findAccountID(String sessionID);
    DBAccount findByUsername(String username);
}
