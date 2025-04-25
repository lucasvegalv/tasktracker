package com.lucas.tasktracker.entities;

import com.lucas.tasktracker.dtos.TaskDTO;
import com.lucas.tasktracker.dtos.responses.ResponseUserDTO;
import com.lucas.tasktracker.mappers.TaskListMapper;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "projects")
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(length = 255, unique = true, nullable = false)
    private String title;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TaskListEntity> taskLists;

    @ManyToMany(mappedBy = "projects", fetch = FetchType.LAZY)
    private Set<UserEntity> members;


}
