package database;

import database.TableSchema.Column;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Class that models and interrogate data table through its services.
 *
 * @author Simone Cicerello
 * @version 1.0
 */

public class TableData {

    DbAccess db;

    /**
     * Constructs an instance of the table data to be modeled.
     *
     * @param db access to DB
     */
    public TableData(DbAccess db) {
        this.db = db;
    }

    /**
     * Interrogates data table getting list of distinct values (no duplicate values).
     *
     * @param table name
     * @return list of results
     * @throws SQLException for generics SQL errors
     * @throws EmptySetException if result set of queries is empty
     */
    public List<Example> getDistinctTransactions(String table) throws EmptySetException, DatabaseQueryException {
        List<Example> exampleList = new ArrayList<>();
        Connection conn = db.getConnection();

        try (Statement stmt = conn.createStatement()) {
            String sqlQuery = String.format("SELECT DISTINCT * FROM %s", table);
            try (ResultSet result = stmt.executeQuery(sqlQuery)) {

                TableSchema schema = new TableSchema(db, table);
                if (!result.isBeforeFirst()) {
                    throw new EmptySetException("No results for this query.");
                }

                while (result.next()) {
                    Example ex = new Example();
                    for (int i = 1; i <= schema.getNumberOfAttributes(); i++) {
                        if (schema.getColumn(i - 1).isNumber()) {
                            ex.add(result.getDouble(i));
                        } else {
                            ex.add(result.getString(i));
                        }
                    }
                    exampleList.add(ex);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseQueryException("Can't process the request, the resource doesn't exists!", e);
        }

        return exampleList;
    }

    /**
     * Interrogates data table getting list of distinct values of a selected attribute, ordered by ascending mode.
     *
     * @param table  name
     * @param column attribute
     * @return list of results
     * @throws SQLException for generics SQL errors
     */
    public Set<Object> getDistinctColumnValues(String table, Column column) throws DatabaseQueryException {
        Set<Object> attributeValues = new TreeSet<>();

        try (Statement stmt = db.getConnection().createStatement()) {

            String columnName = column.getColumnName();
            String SQLQuery = String.format("SELECT DISTINCT %s FROM %s ORDER BY %s ASC", columnName, table, columnName);

            try (ResultSet res = stmt.executeQuery(SQLQuery)) {
                if (column.isNumber()) {
                    while (res.next()) {
                        attributeValues.add(res.getDouble(columnName));
                    }
                } else {
                    while (res.next()) {
                        attributeValues.add(res.getString(columnName));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseQueryException("Can't process the request, the resource doesn't exists!", e);
        }


        return attributeValues;


    }

    /**
     * Interrogates data table getting value obtained from chosen operations on a selected attribute.
     *
     * @param table name
     * @param column attribute
     * @param aggregate choose type of operation (min/max)
     * @return result set
     * @throws SQLException for generics SQL errors
     */
    public Object getAggregateColumnValue(String table, Column column, QUERY_TYPE aggregate) throws SQLException {

        try(Statement stmt = db.getConnection().createStatement()){

            String columnName = column.getColumnName();
            String SQLQueryMin = String.format("SELECT MIN(%s) AS %s FROM %s", columnName, columnName, table);
            String SQLQueryMax = String.format("SELECT MAX(%s) AS %s FROM %s", columnName, columnName, table);
            ResultSet res = null;

            if (aggregate == QUERY_TYPE.MIN)
                res = stmt.executeQuery(SQLQueryMin);
            else if (aggregate == QUERY_TYPE.MAX)
                res = stmt.executeQuery(SQLQueryMax);

            res.next();

            Double resDouble = 0.0;
            String resString = "";

            if (column.isNumber()) {
                resDouble = res.getDouble(columnName);
            } else
                resString = res.getString(columnName);

            res.close();
            stmt.close();

            if (column.isNumber()) {
                return resDouble;
            } else
                return resString;
        }
    }
}
