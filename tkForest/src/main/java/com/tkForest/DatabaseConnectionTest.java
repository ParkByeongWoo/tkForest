package com.tkForest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseConnectionTest {

    public static void main(String[] args) {
        // MySQL 연결 정보
        String jdbcUrl = "jdbc:mysql://34.170.249.250:3306/tkForest"; // DB URL
        String username = "root"; // 사용자 이름
        String password = "tkforest"; // 비밀번호
        
        try {
            // JDBC 드라이버 로드 (MySQL 8.0 이상에서는 생략 가능)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 연결 설정
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

            if (connection != null) {
                System.out.println("DB에 성공적으로 연결되었습니다!");

                // 간단한 쿼리 실행
                Statement statement = connection.createStatement();
                String sql = "SELECT NOW() AS currentTime";  // 현재 시간을 가져오는 쿼리
                ResultSet resultSet = statement.executeQuery(sql);

                while (resultSet.next()) {
                    System.out.println("DB 시간: " + resultSet.getString("currentTime"));
                }

                // 리소스 해제
                resultSet.close();
                statement.close();
                connection.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}