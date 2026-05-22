package org.sopt.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.sopt.global.common.entity.BaseTimeEntity;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "nickname")
})  // "user"는 SQL 예약어라 테이블명을 "users"로 변경
@Getter
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String email;

    // BCrypt로 암호화된 비밀번호
    @Column(nullable = false)
    private String password;

    protected User() {}

    public User(String nickname, String email, String password) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

}
