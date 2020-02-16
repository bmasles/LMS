package com.smoothstack.lms.borrowermicroservice.database;

import com.smoothstack.lms.borrowermicroservice.Debug;
import com.smoothstack.lms.borrowermicroservice.database.sql.SQLDataMap;
import sun.awt.image.ImageWatched;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;

public class DataAccess {

    private Connection connection = null;

    public DataAccess(Connection connection) {
        this.connection = connection;
    }

    public static <R> List<R> executeQuery(Connection connection, String sqlStatement, Function<ResultSet, R> extractor, Object... objects) throws SQLException {
        Debug.println("executeQuery:");
        PreparedStatement preparedStatement = getPreparedStatement(connection, sqlStatement, objects);

        ResultSet rs = preparedStatement.executeQuery();

        return extractResult(rs, extractor);
    }

    public static <R> List<R> executeQuery(Connection connection, String sqlStatement, Function<ResultSet, R> extractor, SQLDataMap dataMap) throws SQLException {
        Debug.println("executeQuery:");
        PreparedStatement preparedStatement = getPreparedStatement(connection, sqlStatement, dataMap);

        ResultSet rs = preparedStatement.executeQuery();

        return extractResult(rs, extractor);
    }

    public <R> List<R> executeQuery(String sqlStatement, Function<ResultSet, R> extractor, Object... objects) throws SQLException {
        return executeQuery(connection, sqlStatement, extractor, objects);
    }

    public <R> List<R> executeQuery(String sqlStatement, Function<ResultSet, R> extractor, SQLDataMap dataMap) throws SQLException {
        return executeQuery(connection, sqlStatement, extractor, dataMap);
    }

    public static PreparedStatement executeUpdate(Connection connection, String sqlStatement, Object... objects) throws SQLException {
        Debug.println("executeUpdate:");
        PreparedStatement preparedStatement = getPreparedStatement(connection, sqlStatement, objects);

        preparedStatement.executeUpdate();

        return preparedStatement;
    }

    public static PreparedStatement executeUpdate(Connection connection, String sqlStatement, SQLDataMap dataMap) throws SQLException {
        Debug.println("executeUpdate:");
        PreparedStatement preparedStatement = getPreparedStatement(connection, sqlStatement, dataMap);

        preparedStatement.executeUpdate();

        return preparedStatement;
    }

    public PreparedStatement executeUpdate(String sqlStatement, Object... objects) throws SQLException {
        return executeUpdate(connection, sqlStatement, objects);
    }

    public PreparedStatement executeUpdate(String sqlStatement, SQLDataMap dataMap) throws SQLException {
        return executeUpdate(connection, sqlStatement, dataMap);
    }


    public static PreparedStatement getPreparedStatement(Connection connection, String sqlStatement, Object... objects) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(Debug.tee(sqlStatement));

        List<Object> objectList = Arrays.asList(objects);
        for (int i = 0; i < objectList.size(); i++) {
            preparedStatement.setObject(1 + i, objectList.get(i));
            Debug.printf("-> ?[%d] = %s\n", 1 + i, objectList.get(i));
        }

        return preparedStatement;
    }

    public static PreparedStatement getPreparedStatement(Connection connection, String sqlStatement, SQLDataMap dataMap) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(Debug.tee(sqlStatement));

        String[] key = (String[]) dataMap.keySet().toArray();

        for (int keyIndex = 0; keyIndex < key.length; keyIndex++) {
            preparedStatement.setObject(1 + keyIndex, dataMap.get(key[keyIndex]));
            Debug.printf("-> ?[%d] = %s\n", 1 + keyIndex, dataMap.get(key[keyIndex]));
        }

        return preparedStatement;
    }



    public PreparedStatement getPreparedStatement(String sqlStatement, Object... objects) throws SQLException {
        return getPreparedStatement(connection, sqlStatement, objects);
    }

    public PreparedStatement getPreparedStatement(String sqlStatement, SQLDataMap dataMap) throws SQLException {
        return getPreparedStatement(connection, sqlStatement, dataMap);
    }

    public static <R> List<R> extractResult(ResultSet rs, Function<ResultSet, R> extract) throws SQLException {
        Debug.println("extractResult:");
        List<R> resultList = new ArrayList<>();
        int i = 0;
        if (!rs.next()) {
            Debug.println("<- [no result returned]");
        } else {
            do {
                R result = extract.apply(rs);
                resultList.add(result);
                    Debug.printf("<- L[%d] = %s\n",i++, result.toString());
            } while(rs.next());
        }

        return resultList;
    }

    public static void executePurge(Connection connection, String tableName) throws SQLException {
        String sqlStatement;
        PreparedStatement preparedStatement = connection.prepareStatement(
                sqlStatement = String.format("DELETE FROM %s",
                        tableName));
        Debug.println(sqlStatement);
        preparedStatement.executeUpdate();
        preparedStatement = connection.prepareStatement(
                sqlStatement = String.format("ALTER TABLE %s AUTO_INCREMENT = 1;",
                        tableName));
        Debug.println(sqlStatement);
        preparedStatement.executeUpdate();
    }

    public void executePurge(String tableName) throws SQLException {
        executePurge(connection, tableName);
    }

}
