package com.example.servelets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/addUser")
public class AddUserServlet extends HttpServlet {
    // JDBC URL, username, and password
    private static final String JDBC_URL = "jdbc:mysql://{redacted}";
    private static final String JDBC_USERNAME = "{redacted}";
    private static final String JDBC_PASSWORD = "{redacted}";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int newUserID = Integer.parseInt(request.getParameter("newUserID"));
        String newUserName = request.getParameter("newUserName");
        String newUserType = request.getParameter("newUserType");

        Connection connection = null;
        PreparedStatement statement = null;
        String userAddStatus = "User added successfully";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish database connection
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);

            // Prepare SQL statement
            String sql = "INSERT INTO Users (UserID, UserName, UserType) VALUES (?, ?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, newUserID);
            statement.setString(2, newUserName);
            statement.setString(3, newUserType);

            // Execute SQL statement
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted <= 0) {
                userAddStatus = "User add fail: No rows inserted";
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            userAddStatus = "User add fail: " + e.getMessage();
        } finally {
            // Close resources
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Prepare JSON response
        Gson gson = new Gson();
        String jsonResult = gson.toJson(userAddStatus);

        // Send response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(jsonResult);
        out.flush();
    }
}