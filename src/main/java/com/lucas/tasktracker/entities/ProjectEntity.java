package com.lucas.tasktracker.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "projects")
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255, unique = true, nullable = false)
    private String title;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TaskListEntity> taskLists;

    @ManyToMany(mappedBy = "projects", fetch = FetchType.LAZY)
    private Set<UserEntity> members;
}
