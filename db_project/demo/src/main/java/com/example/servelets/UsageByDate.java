/**
 * The `UsageByDate` servlet retrieves and displays device usage information for a specific user within
 * a specified date range by querying a database and returning the results in JSON format.
 */
package com.example.servelets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import com.example.model.AdvancedSearchResult;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



/*
In addition to the basic search, lookup for a user (by Id) and display devices used
and usage duration within specified date range (joining three tables).
*/

@WebServlet("/dateSearch")
public class UsageByDate extends HttpServlet {
    // JDBC URL, username, and password
    private static final String JDBC_URL = "jdbc:mysql://{redacted}";
    private static final String JDBC_USERNAME = "{redacted}";
    private static final String JDBC_PASSWORD = "{redacted}";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {

        int searchUserID = Integer.parseInt(request.getParameter("searchUserID"));
        String lbDate = request.getParameter("lbDate");
        String ubDate = request.getParameter("ubDate");

        List<AdvancedSearchResult> searchResult = new ArrayList<>();
        
        Connection connection = null;
        PreparedStatement statement = null;

        ResultSet resultSet = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            // Establish database connection
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);

            // Prepare SQL statement
            String sql = "CALL GetDevicesUsedBetweenDates6(?, ?, ?);";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, searchUserID);
            statement.setString(2, lbDate);
            statement.setString(3, ubDate);

            // Execute query
            resultSet = statement.executeQuery();

            // Process result set
            while (resultSet.next()) {
                String advUserName = resultSet.getString("UserName");
                int advDeviceId = resultSet.getInt("DeviceID");
                String advDeviceName = resultSet.getString("DeviceName");
                Date advUsageDate = resultSet.getDate("UsageDate");
                int advUsageDuration = resultSet.getInt("UsageDuration");
                System.out.println(" User Name: " + advUserName + "Device ID: " + advDeviceId + "\n Device Name: " + advDeviceName + "\n Usage Date: " + advUsageDate + "\n Usage Duration: " + advUsageDuration);

                // Create User object and add to search result
                AdvancedSearchResult result = new AdvancedSearchResult(advUserName, advDeviceId, advDeviceName, advUsageDate, advUsageDuration);
                searchResult.add(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (resultSet != null)
                    resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
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

        // Convert searchResult to JSON and send as response
        Gson gson = new Gson();
        String jsonResult = gson.toJson(searchResult);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(jsonResult);
        out.flush();
    }

}


