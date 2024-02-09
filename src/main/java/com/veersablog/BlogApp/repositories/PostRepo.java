package com.veersablog.BlogApp.repositories;

import com.veersablog.BlogApp.entities.Category;
import com.veersablog.BlogApp.entities.Post;
import com.veersablog.BlogApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post , Integer> {

    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);
    List<Post> findByTitleContaining(String title);
}
