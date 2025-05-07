package com.lucas.tasktracker.utils;

import com.lucas.tasktracker.entities.UserEntity;
import com.lucas.tasktracker.enumerations.JobsEnum;

public class TestUserFactory {

    // Metodos staticos para evitar codigo boilerplate en los tests.

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
}
