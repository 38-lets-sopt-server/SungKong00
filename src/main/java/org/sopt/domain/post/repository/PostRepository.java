package org.sopt.domain.post.repository;

import org.sopt.domain.post.entity.BoardType;
import org.sopt.domain.post.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    Post save(Post post);

    List<Post> findAll(int page, int size);
    List<Post> findByBoardType(BoardType boardType, int page, int size);
    Optional<Post> findById(Long id);
    void delete(Post post);
}