package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Models table schema of a table in DB.
 *
 * @author Simone Cicerello
 * @version 1.0
 */
public class TableSchema {

    /**
     * Inner class that models attribute.
     */
    DbAccess db;
    List<Column> tableSchema = new ArrayList<>();

    /**
     * Create an instance of TableSchema following the mapped types.
     *
     * @param db        access
     * @param tableName name
     * @throws SQLException
     */
    public TableSchema(DbAccess db, String tableName) throws SQLException {
        this.db = db;
        HashMap<String, String> mapSQL_JAVATypes = new HashMap<>();
        //http://java.sun.com/j2se/1.3/docs/guide/jdbc/getstart/mapping.html
        mapSQL_JAVATypes.put("CHAR", "string");
        mapSQL_JAVATypes.put("VARCHAR", "string");
        mapSQL_JAVATypes.put("TEXT", "string");
        mapSQL_JAVATypes.put("LONGVARCHAR", "string");
        mapSQL_JAVATypes.put("BIT", "string");
        mapSQL_JAVATypes.put("SHORT", "number");
        mapSQL_JAVATypes.put("INT", "number");
        mapSQL_JAVATypes.put("LONG", "number");
        mapSQL_JAVATypes.put("FLOAT", "number");
        mapSQL_JAVATypes.put("DOUBLE", "number");

        Connection con = this.db.getConnection();
        DatabaseMetaData meta = con.getMetaData();
        ResultSet res = meta.getColumns(null, null, tableName, null);

        while (res.next()) {
            if (mapSQL_JAVATypes.containsKey(res.getString("TYPE_NAME"))) {
                Column col = new Column(res.getString("COLUMN_NAME"),
                        mapSQL_JAVATypes.get(res.getString("TYPE_NAME")));

                tableSchema.add(col);
            }
        }
        res.close();
    }

    public int getNumberOfAttributes() {
        return tableSchema.size();
    }

    /**
     * Gets column by related index
     *
     * @param index
     * @return column looked for
     */
    public Column getColumn(int index) {
        return tableSchema.get(index);
    }


    public static final class Column {
        private String name;
        private String type;

        /**
         * Create an instance of the column.
         *
         * @param name attribute name
         * @param type type name
         */
        Column(String name, String type) {
            this.name = name;
            this.type = type;
        }

        /**
         * Gets column name
         *
         * @return name
         */
        public String getColumnName() {
            return name;
        }

        /**
         * Checks if column type is numeric, return true if it is, false otherwise.
         *
         * @return boolean value
         */
        public boolean isNumber() {
            return type.equals("number");
        }

        public String toString() {
            return name + ":" + type;
        }
    }
}

		     


