package com.example.servelets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/updateUser")
public class UpdateUserServlet extends HttpServlet {
    // JDBC URL, username, and password
    private static final String JDBC_URL = "jdbc:mysql://{redacted}";
    private static final String JDBC_USERNAME = "{redacted}";
    private static final String JDBC_PASSWORD = "{redacted}";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int oldUserID = Integer.parseInt(request.getParameter("oldUserID"));
        String updateUserName = request.getParameter("updateUserName");
        String updateUserType = request.getParameter("updateUserType");

        Connection connection = null;
        PreparedStatement statement = null;
        String userUpdateStatus = "User updated successfully";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish database connection
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);

            // Prepare SQL statement
            String sql = "SELECT UserName, UserType FROM Users WHERE UserID = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, oldUserID);

            ResultSet resultSet = statement.executeQuery();

            // Check if user exists
            if (resultSet.next()) {
                String currentUserName = resultSet.getString("UserName");
                String currentUserType = resultSet.getString("UserType");

                // If updateUserName is not provided, use the current user name
                if (updateUserName.isEmpty()) {
                    updateUserName = currentUserName;
                }

                // If updateUserType is not provided, use the current user type
                if (updateUserType.isEmpty()) {
                    updateUserType = currentUserType;
                }

                // Prepare SQL statement for update
                sql = "UPDATE Users SET UserName = ?, UserType = ? WHERE UserID = ?";
                statement = connection.prepareStatement(sql);
                statement.setString(1, updateUserName);
                statement.setString(2, updateUserType);
                statement.setInt(3, oldUserID);

                // Execute SQL statement
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated <= 0) {
                    userUpdateStatus = "User update failed: No rows updated";
                }
            } else {
                userUpdateStatus = "User with ID " + oldUserID + " not found.";
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            userUpdateStatus = "User update failed: " + e.getMessage();
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
        String jsonResult = gson.toJson(userUpdateStatus);

        // Send response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(jsonResult);
        out.flush();
    }
}
