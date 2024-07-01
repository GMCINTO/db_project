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

@WebServlet("/usesUpdate")
public class usesUpdateServlet extends HttpServlet {
    // JDBC URL, username, and password
    private static final String JDBC_URL = "jdbc:mysql://{redacted}";
    private static final String JDBC_USERNAME = "{redacted}";
    private static final String JDBC_PASSWORD = "{redacted}";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userID = Integer.parseInt(request.getParameter("userID"));
        int deviceID = Integer.parseInt(request.getParameter("deviceID"));
        String usageDate = request.getParameter("usageDate");
        String usageDuration = request.getParameter("usageDuration");

        Connection connection = null;
        PreparedStatement statement = null;
        String usesAddStatus = "Uses added successfully";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish database connection
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);

            // Prepare SQL statement
            String sql = "INSERT INTO Uses (UserID, DeviceID, UsageDate, UsageDuration) VALUES (?, ?, ?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, userID);
            statement.setInt(2, deviceID);
            statement.setString(3, usageDate);
            statement.setString(4, usageDuration);

            // Execute SQL statement
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted <= 0) {
                usesAddStatus = "Uses add fail: No rows inserted";
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            usesAddStatus = "Uses add fail: " + e.getMessage();
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
        String jsonResult = gson.toJson(usesAddStatus);

        // Send response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(jsonResult);
        out.flush();
    }
}