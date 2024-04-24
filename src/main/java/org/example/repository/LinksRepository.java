package org.example.repository;

import org.example.exception.UnknownShortLinkException;
import org.example.jdbc.JdbcUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LinksRepository {
    public String getShortCode(String longLink) {
        String sql = "SELECT * FROM links WHERE longLink = '" + longLink + "'";

        try {
            Statement statement = JdbcUtils.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next())
                return resultSet.getString("shortCode");

            System.out.println("Ошибка: короткая ссылка не найдена");
            System.exit(0);
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
            return null;
        }
    }

    public String getLongLink(String shortCode) {
        String sql = "SELECT * FROM links WHERE shortCode = '" + shortCode + "'";

        try {
            Statement statement = JdbcUtils.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            if(resultSet.next())
                return resultSet.getString("longLink");

            System.out.println("Ошибка: короткая ссылка не найдена");
            System.exit(0);
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
            return null;
        }
    }

    public boolean containsShortCode(String shortCode) {
        String sql = "SELECT * FROM links WHERE shortCode = '" + shortCode + "'";

        try {
            Statement statement = JdbcUtils.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
            return false;
        }
    }

    public boolean containsLongLink(String longLink) {
        String sql = "SELECT * FROM links WHERE longLink = '" + longLink + "'";

        try {
            Statement statement = JdbcUtils.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
            return false;
        }
    }

    public void addPairOfLinks(String longLink, String shortCode) {
        String sql = "INSERT INTO links(shortCode, longLink) VALUES ('" + shortCode + "', '" + longLink + "')";

        try {
            Statement statement = JdbcUtils.getConnection().createStatement();
            int rows = statement.executeUpdate(sql);
        } catch(SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
