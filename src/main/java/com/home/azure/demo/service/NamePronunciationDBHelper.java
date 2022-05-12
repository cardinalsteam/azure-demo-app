package com.home.azure.demo.service;

import com.home.azure.demo.domain.Employee;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class NamePronunciationDBHelper {

    @Value("${azure.yburl}")
    private String yburl;
    @Value("${azure.ybdriver}")
    private String ybdriver;

    public  void insertEmpoyeeRecord(Employee employee) {

        try {
            Statement stmt = getStatement();
            System.out.println("Connected to the YugabyteDB Cluster successfully.");
            // stmt.execute("DROP TABLE IF EXISTS employee");
            /*stmt.execute("CREATE TABLE IF NOT EXISTS employee" +
                    "  (id int primary key, name varchar, age int, language text)");*/
            // System.out.println("Created table employee");

            String insertStr = "INSERT INTO employees.employees  VALUES ('"+employee.getUid()+"','"+employee.getEmail()+"','"+employee.getName()+"','"+employee.getBlob()+"')";

            stmt.execute(insertStr);
            System.out.println("EXEC: " + insertStr);

            ResultSet rs = stmt.executeQuery("select * from employees.employees");
            while (rs.next()) {
                System.out.println(String.format("Query returned: uid = %s, email = %s, name = %s, blob = %s",
                        rs.getString("uid"), rs.getString("email"), rs.getString("name"), rs.getString("audio")));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private Statement getStatement() throws SQLException, ClassNotFoundException {
        Connection conn = DriverManager.getConnection(yburl);
        Statement stmt = conn.createStatement();
        Class.forName(ybdriver);
        return stmt;
    }

    public Employee searchEmployeeByUid(String uid){
        Employee employee = null;
        try {
            System.out.println("Connected to the YugabyteDB Cluster successfully.");
            Statement stmt = getStatement();
            String selectStr = "SELECT uid,email,name,audio FROM employees.employees WHERE uid='"+uid+"'";

            stmt.execute(selectStr);
            System.out.println("EXEC: " + selectStr);

            ResultSet rs = stmt.executeQuery(selectStr);

            while (rs.next()) {
                employee = new Employee();
                employee.setUid(rs.getString("uid"));
                employee.setEmail(rs.getString("email"));
                employee.setName(rs.getString("name"));
                employee.setBlob(rs.getString("audio"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return  employee;
    }

    public Employee searchEmployeeByEmail(String email) {
        Employee employee = null;
        try {
            System.out.println("Connected to the YugabyteDB Cluster successfully.");
            Statement stmt = getStatement();
            String selectStr = "SELECT uid,email,name,audio FROM employees.employees WHERE email='"+email+"'";

            stmt.execute(selectStr);
            System.out.println("EXEC: " + selectStr);

            ResultSet rs = stmt.executeQuery(selectStr);

            while (rs.next()) {
                employee = new Employee();
                employee.setUid(rs.getString("uid"));
                employee.setEmail(rs.getString("email"));
                employee.setName(rs.getString("name"));
                employee.setBlob(rs.getString("audio"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return employee;
    }
}
