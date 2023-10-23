package com.icia.book.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter(AccessLevel.PRIVATE)
@Table(name="recent_address_table")
@Entity
public class RecentAddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, nullable = false)
    private String addressName;

    @Column(length = 10, nullable = false)
    private String postCode;

    @Column(length = 50, nullable = false)
    private String address;

    @Column(length = 50)
    private String addressDetail;

    @CreationTimestamp
    @Column()
    private LocalDateTime createdAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;
}
