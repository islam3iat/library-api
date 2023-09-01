package com.example.LibraryManagementSystem;


import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import com.github.javafaker.Faker;

import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


import javax.sql.DataSource;
@Testcontainers
public class AbstractTestContainer {


    @Container
    protected static MySQLContainer<?> mySQLContainer=new MySQLContainer<>("mysql:latest").
            withDatabaseName("start_dao_test").
            withUsername("dark").
            withPassword("1234");
    @DynamicPropertySource
    private static void registerDataSourceProperties(DynamicPropertyRegistry register){
        register.add("spring.datasource.url",
                mySQLContainer::getJdbcUrl);
        register.add("spring.datasource.username",
                mySQLContainer::getUsername);
        register.add("spring.datasource.password",
                mySQLContainer::getPassword);
    }
    private static DataSource getDataSource(){
        return  DataSourceBuilder.create().
                driverClassName(mySQLContainer.getDriverClassName()).
                url(mySQLContainer.getJdbcUrl()).
                username(mySQLContainer.getUsername()).
                password(mySQLContainer.getPassword()).
                build();

    }

    protected static final Faker FAKER=new Faker();}
