package com.micutne.odik.domain.user;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;


}
