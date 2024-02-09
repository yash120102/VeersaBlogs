package com.veersablog.BlogApp.repositories;

import com.veersablog.BlogApp.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment , Integer> {
}
