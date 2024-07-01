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

@WebServlet("/delete")
public class DeleteUserServlet extends HttpServlet {

    private static final String JDBC_URL = "jdbc:mysql://{redacted}";
    private static final String JDBC_USERNAME = "{redacted}";
    private static final String JDBC_PASSWORD = "{redacted}";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int deleteUserID = Integer.parseInt(request.getParameter("deleteUserID"));

        Connection connection = null;
        PreparedStatement statement = null;
        String userDeleteStatus = "User deleted successfully";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish database connection
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);

            String sql = "DELETE FROM Uses WHERE UserID = ?";

            statement = connection.prepareStatement(sql);
            statement.setInt(1, deleteUserID);

            // Execute SQL statement
            int rowsDeleted = statement.executeUpdate();

            // Prepare SQL statement
            sql = "DELETE FROM Users WHERE UserID = ?";

            statement = connection.prepareStatement(sql);
            statement.setInt(1, deleteUserID);

            // Execute SQL statement
            rowsDeleted = statement.executeUpdate();
            if (rowsDeleted <= 0) {
                userDeleteStatus = "User delete fail: No rows deleted";
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            userDeleteStatus = "User delete fail: " + e.getMessage();
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
        String jsonResult = gson.toJson(userDeleteStatus);

        // Send response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(jsonResult);
        out.flush();
    }

}
