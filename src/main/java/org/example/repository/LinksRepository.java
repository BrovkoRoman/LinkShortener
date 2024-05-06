package org.example.repository;

import org.example.exception.BadRepositoryFunctionCallException;
import org.example.exception.UnknownShortLinkException;
import org.example.jdbc.JdbcUtils;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class LinksRepository {
    private final Connection connection;

    public LinksRepository(Connection connection) {
        this.connection = connection;
    }
    public String getShortCode(String longLink) throws  BadRepositoryFunctionCallException, SQLException {
        String sql = String.format("SELECT * FROM links WHERE longLink = '%s'", longLink);

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        if (resultSet.next())
            return resultSet.getString("shortCode");

        throw new BadRepositoryFunctionCallException("Error: long link is not found");
    }

    public String getLongLink(String shortCode) throws BadRepositoryFunctionCallException, SQLException {
        String sql = String.format("SELECT * FROM links WHERE shortCode = '%s'", shortCode);

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        if(resultSet.next())
            return resultSet.getString("longLink");

        throw new BadRepositoryFunctionCallException("Error: short code is not found");
    }

    public boolean containsShortCode(String shortCode) throws SQLException {
        String sql = String.format("SELECT * FROM links WHERE shortCode = '%s'", shortCode);

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        return resultSet.next();
    }

    public boolean containsLongLink(String longLink) throws SQLException {
        String sql = String.format("SELECT * FROM links WHERE longLink = '%s'", longLink);

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        return resultSet.next();
    }

    public void addPairOfLinks(String longLink, String shortCode) throws SQLException {
        String sql = String.format("INSERT INTO links(shortCode, longLink) VALUES ('%s', '%s')", shortCode, longLink);

        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }
}
