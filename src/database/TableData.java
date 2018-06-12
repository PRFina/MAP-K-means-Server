package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import database.TableSchema.Column;


public class TableData {

	DbAccess db;

	
	public TableData(DbAccess db) {
		this.db=db;
	}

	public List<Example> getDistinctTransactions(String table) throws SQLException, EmptySetException{
		List<Example> exampleList = new ArrayList<>();
		Connection conn = db.getConnection();
		Statement stmt = conn.createStatement();
		String sqlQuery = String.format("SELECT DISTINCT * FROM %s", table);
		ResultSet result = stmt.executeQuery(sqlQuery);
		TableSchema schema = new TableSchema(db, table);
		if(!result.isBeforeFirst()){
			throw new EmptySetException("No results for this query.");
		}

		while(result.next()){
			Example ex = new Example();
			for(int i=1; i<=schema.getNumberOfAttributes(); i++){
				if(schema.getColumn(i-1).isNumber()){
					ex.add(result.getDouble(i));
				}else{
					ex.add(result.getString(i));
				}
			}
			exampleList.add(ex);
		}
		result.close();
		stmt.close();

		return exampleList;
	}


	public  Set<Object>getDistinctColumnValues(String table, Column column) throws SQLException{
		Set<Object> attributeValues = new TreeSet<>();

		Statement stmt = db.getConnection().createStatement();
		String columnName = column.getColumnName();
		String SQLQuery = String.format("SELECT DISTINCT %s FROM %s ORDER BY %s ASC", columnName, table, columnName);
		ResultSet res = stmt.executeQuery(SQLQuery);

		if (column.isNumber()){
			while(res.next()){
				attributeValues.add(res.getDouble(columnName));
			}
		}else {
			while(res.next()){
				attributeValues.add(res.getString(columnName));
			}
		}
		res.close();
		stmt.close();

		return  attributeValues;


	}

	public  Object getAggregateColumnValue(String table,Column column,QUERY_TYPE aggregate) throws SQLException {
		Statement stmt = db.getConnection().createStatement();
		String columnName = column.getColumnName();
		String SQLQueryMin = String.format("SELECT MIN(%s) AS %s FROM %s",columnName, columnName, table);
		String SQLQueryMax = String.format("SELECT MAX(%s) AS %s FROM %s",columnName, columnName, table);
		ResultSet res = null;

		if(aggregate==QUERY_TYPE.MIN)
			res = stmt.executeQuery(SQLQueryMin);
		else if(aggregate==QUERY_TYPE.MAX)
			res = stmt.executeQuery(SQLQueryMax);

		res.next();

		Double resDouble = 0.0;
		String resString = "";

		if(column.isNumber()) {
			resDouble = res.getDouble(columnName);
		}else
			resString = res.getString(columnName);

		res.close();
		stmt.close();

		if(column.isNumber()) {
			return resDouble;
		}else
			return resString;
	}


	public static void main(String[] args) throws SQLException, DatabaseConnectionException, ClassNotFoundException, NoValueException {
		DbAccess db = new DbAccess();
		db.initConnection();
		TableData td = new TableData(db);
		TableSchema ts = new TableSchema(db,"playtennis");


		System.out.println(td.getAggregateColumnValue("playtennis", ts.getColumn(1), QUERY_TYPE.MIN));
		System.out.println(td.getAggregateColumnValue("playtennis", ts.getColumn(1), QUERY_TYPE.MAX));
	}
	

}
