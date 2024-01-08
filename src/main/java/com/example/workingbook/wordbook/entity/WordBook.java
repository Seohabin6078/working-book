package com.example.workingbook.wordbook.entity;

import com.example.workingbook.audit.Auditable;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class WordBook extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wordBookId;

    @Column(nullable = false)
    private String title;

    // 0 = 비공개, 1 = 공유, 2 = 공개
    @Column(nullable = false)
    private Integer accessRange;

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeAccessRange(Integer accessRange) {
        this.accessRange = accessRange;
    }
}
