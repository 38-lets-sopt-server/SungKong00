package org.sopt.domain.post.repository.jpa;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.sopt.domain.post.entity.BoardType;
import org.sopt.domain.post.entity.Post;
import org.sopt.domain.post.repository.PostRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jpa")
@RequiredArgsConstructor
public class PostJpaRepositoryAdapter implements PostRepository {

    private final SpringDataJpaPostRepository jpaRepository;

    @Override
    public Post save(Post post) {
        return jpaRepository.save(post);
    }

    @Override
    public List<Post> findAll(int page, int size) {
        return jpaRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    @Override
    public List<Post> findByBoardType(BoardType boardType, int page, int size) {
        return jpaRepository.findByBoardType(boardType, PageRequest.of(page, size)).getContent();
    }

    @Override
    public Optional<Post> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public void delete(Post post) {
        jpaRepository.delete(post);
    }
}
