package org.sopt.domain.post.repository;

import org.sopt.domain.post.entity.BoardType;
import org.sopt.domain.post.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    Post save(Post post);

    List<Post> findAll();
    List<Post> findByBoardType(BoardType boardType);
    Optional<Post> findById(Long id);
    boolean deleteById(Long id);
}