package com.example.LibraryManagementSystem;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestContainerTest extends AbstractTestContainer{
    @Test
    void canStartMysqlDB() {
        assertThat(mySQLContainer.isRunning()).isTrue();
        assertThat(mySQLContainer.isCreated()).isTrue();

    }


}

