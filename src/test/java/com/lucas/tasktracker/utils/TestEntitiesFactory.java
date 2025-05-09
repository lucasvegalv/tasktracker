package com.lucas.tasktracker.utils;

import com.lucas.tasktracker.entities.TaskListEntity;
import com.lucas.tasktracker.entities.UserEntity;
import com.lucas.tasktracker.enumerations.JobsEnum;

public class TestEntitiesFactory {

    // Metodos staticos para evitar codigo boilerplate en los tests.


    // USER
    public static UserEntity getValidUser1() {
        UserEntity validUser = UserEntity.builder()
                .username("Elon")
                .lastname("Gates")
                .job(JobsEnum.SOFTWARE_ENGINEER)
                .build();

        return validUser;
    }

    public static UserEntity getValidUser2() {
        UserEntity validUser = UserEntity.builder()
                .username("Javier")
                .lastname("Messi")
                .job(JobsEnum.DESIGNER)
                .build();

        return validUser;
    }

    public static UserEntity getInvalidUser() {
        UserEntity invalidUser = UserEntity.builder()
                .username("Jorge")
                .lastname("Novalid")
                .build();

        return invalidUser;
    }


    // TASK LIST
    public static TaskListEntity getValidTaskList1() {
        TaskListEntity taskListEntity = TaskListEntity.builder()
                .name("Desarrollar tests")
                .description("Desarrollar tests unitarios y de integracion para la capa de servicio")
                .build();

        return taskListEntity;
    }

    public static TaskListEntity getValidTaskList2() {
        TaskListEntity taskListEntity = TaskListEntity.builder()
                .name("Migracion version antigua de Spring")
                .description("Migrar el servicio a la ultima version del framework")
                .build();

        return taskListEntity;
    }


}
