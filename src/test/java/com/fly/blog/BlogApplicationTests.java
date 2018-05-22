package com.fly.blog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogApplicationTests {

    @Test
    public void contextLoads() throws ClassNotFoundException {

        Class.forName("com.mysql.jdbc.Driver");
        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useSSL=false", "root", "123456");
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM user");
                ResultSet set = statement.executeQuery()

        ) {
            while (set.next()) {
                String name = set.getString("name");
                String address = set.getString("address");
                Integer age = set.getInt("age");
                System.out.println(name + address + age);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
