package org.sopt.domain.post.repository.memory;

import org.sopt.domain.post.entity.BoardType; // 추가된 import
import org.sopt.domain.post.entity.Post;
import org.sopt.domain.post.repository.PostRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryRepository implements PostRepository {
    // 접근 제어자 private 추가 (외부 접근 완벽 차단)
    private final List<Post> postList = new ArrayList<>();
    private Long nextId = 1L;

    // Save
    @Override
    public Post save(Post post) {

        // Post엔티티와 Service가 InMemory의 예외적인 패턴에 영향 받지 않도록 ReflectionUtils를 사용하여 id 필드에 접근
        Field idField = ReflectionUtils.findField(Post.class, "id");    // 1. Post 클래스에서 "id" 필드 찾기
        ReflectionUtils.makeAccessible(idField);    // 2. 해당 필드에 접근할 수 있도록 설정 (private 필드도 접근 가능하게)
        ReflectionUtils.setField(idField, post, nextId++);  // 3. 찾은 필드에 post 객체의 id 값을 nextId로 설정하고, 이후 nextId를 1 증가

        postList.add(post);
        return post;
    }

    // Read All
    @Override
    public List<Post> findAll(int page, int size) {
        return postList.stream()
                .skip((long) page * size)
                .limit(size)
                .toList();
    }

    // Read by ID
    @Override
    public Optional<Post> findById(Long id) {
        return postList.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    // Read by BoardType
    @Override
    public List<Post> findByBoardType(BoardType boardType, int page, int size) {
        return postList.stream()
                .filter(p->p.getBoardType() == boardType)
                .skip((long) page * size)
                .limit(size)
                .toList();
    }

    // Delete by ID
    @Override
    public boolean deleteById(Long id) {
        return postList.removeIf(p -> p.getId().equals(id));
    }
}