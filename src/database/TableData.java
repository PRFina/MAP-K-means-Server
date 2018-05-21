package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
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

	/*
	public  Set<Object>getDistinctColumnValues(String table,Column column) throws SQLException{


	}

	public  Object getAggregateColumnValue(String table,Column column,QUERY_TYPE aggregate) throws SQLException,NoValueException{

	}

*/
	public static void main(String[] args) throws SQLException, DatabaseConnectionException, ClassNotFoundException, EmptySetException {
		DbAccess db = new DbAccess();
		db.initConnection();
		TableData td = new TableData(db);
		System.out.println(td.getDistinctTransactions("playtennis"));
	}
	

}
