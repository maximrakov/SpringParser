package org.itmo.backend.entity;

import lombok.Getter;

import javax.persistence.*;
import java.util.Collection;

@Entity(name = "roles")
@Getter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private Collection<User> users;
}
