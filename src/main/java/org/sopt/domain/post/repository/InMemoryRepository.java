package org.sopt.domain.post.repository;

import org.sopt.domain.post.entity.Post;
import org.sopt.domain.post.repository.memory.PostRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryRepository implements PostRepository {
    final List<Post> postList = new ArrayList<>();
    Long nextId = 1L;

    @Override
    public Post save(Post post) {
        postList.add(post);
        return post;
    }

    @Override
    public List<Post> findAll() {
        return postList;
    }

    @Override
    public Optional<Post> findById(Long id) {
        return postList.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    @Override
    public boolean deleteById(Long id) {
        return postList.removeIf(p -> p.getId().equals(id));
    }

    public Long generateId() {
        return nextId++;
    }
}