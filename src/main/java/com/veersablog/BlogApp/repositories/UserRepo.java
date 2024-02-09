package com.veersablog.BlogApp.repositories;

import com.veersablog.BlogApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User , Integer> {

}
