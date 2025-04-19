package com.lucas.tasktracker.enumerations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum JobsEnum {
    PROJECT_MANAGER("Project Manager"),
    SOFTWARE_ENGINEER("Software Engineer"),
    DESIGNER("Designer");

    @Getter
    private final String jobName;

    JobsEnum(String jobName) {
        this.jobName = jobName;
    }

    @JsonValue
    public String getJobName() {
        return jobName;
    }

    public static String getJobs() {
        String validNames = Arrays.stream(JobsEnum.values())
                .map(JobsEnum::getJobName)
                .collect(Collectors.joining(", "));

        return validNames;
    }

    @JsonCreator
    public static JobsEnum fromJobName(String text) {
        return Arrays.stream(JobsEnum.values())
                .filter(job -> job.getJobName().equalsIgnoreCase(text))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No se encuentra el trabajo indicado: " + text + ". Estos son los disponibles: " + getJobs()));
    }



}


