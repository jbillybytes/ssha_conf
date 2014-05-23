package org.ssha.conference.api.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;
import org.ssha.conference.api.utilities.*;

/**
 * @author jerloc
 * 
 */
public class DBTransact {
	private Connection CONNECTION = null;

	private void closeConnection() {
		if (CONNECTION != null)
			try {
				CONNECTION.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	private void connect()  {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			CONNECTION = DriverManager.getConnection(ConfigSettings.connectStr);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public JSONArray generateJSON(ResultSet rs) {

		JSONArray respJSON = new JSONArray();

		try {
			java.sql.ResultSetMetaData rsmd = rs.getMetaData();
			int numColumns = rsmd.getColumnCount();
			while (rs.next()) {
				JSONObject obj = new JSONObject();
				for (int i = 1; i < numColumns + 1; i++) {

					String columnName = rsmd.getColumnName(i);
					if (rsmd.getColumnType(i) == java.sql.Types.ARRAY) {
						obj.put(columnName, rs.getArray(i));
					} else if (rsmd.getColumnType(i) == java.sql.Types.BIGINT) {
						obj.put(columnName, rs.getInt(i));
					} else if (rsmd.getColumnType(i) == java.sql.Types.BOOLEAN) {
						obj.put(columnName, rs.getBoolean(i));
					} else if (rsmd.getColumnType(i) == java.sql.Types.BLOB) {
						obj.put(columnName, rs.getBlob(i));
					} else if (rsmd.getColumnType(i) == java.sql.Types.DOUBLE) {
						obj.put(columnName, rs.getDouble(i));
					} else if (rsmd.getColumnType(i) == java.sql.Types.FLOAT) {
						obj.put(columnName, rs.getFloat(i));
					} else if (rsmd.getColumnType(i) == java.sql.Types.INTEGER) {
						obj.put(columnName, rs.getInt(i));
					} else if (rsmd.getColumnType(i) == java.sql.Types.NVARCHAR) {
						obj.put(columnName, rs.getNString(i));
					} else if (rsmd.getColumnType(i) == java.sql.Types.VARCHAR) {
						obj.put(columnName, rs.getString(i));
					} else if (rsmd.getColumnType(i) == java.sql.Types.TINYINT) {
						obj.put(columnName, rs.getInt(i));
					} else if (rsmd.getColumnType(i) == java.sql.Types.SMALLINT) {
						obj.put(columnName, rs.getInt(i));
					} else if (rsmd.getColumnType(i) == java.sql.Types.DATE) {
						obj.put(columnName, rs.getDate(i));
					} else if (rsmd.getColumnType(i) == java.sql.Types.TIMESTAMP) {
						obj.put(columnName, rs.getTimestamp(i));
					} else {
						obj.put(columnName, rs.getObject(i));
					}

				}
				respJSON.put(obj);
				// respJSON.add(obj);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		respJSON.toString();

		return respJSON;
	}

	/**
	 * The select function queries the database with the params provided
	 * 
	 * @param returnFields
	 *            - fields to be included in result set
	 * @param tableName
	 *            - name of table or view to be queried
	 * @param filters
	 *            - an array of filters to apply to the query
	 * @return JSONArray
	 * @throws Exception
	 *             if returnFields param is empty or if tableName is not provided
	 */
	public JSONArray select(ArrayList<String> returnFields, String tableName, ArrayList<Filter> filters) throws Exception {
		if (returnFields == null || returnFields.isEmpty()) {
			throw new Exception("Must contain at least one return field.");
		} else if (tableName == null || tableName.isEmpty()) {
			throw new Exception("Please provide a table name.");
		} else {
			connect();
			PreparedStatement stmt = null;
			ResultSet rslts = null;

			String queryStr = "select " + (returnFields.size() > 1 ? Helpers.join(returnFields, ", ") : returnFields.get(0)) + " from " + tableName + " ";

			
			// preparedStatement
			if (filters != null && !filters.isEmpty()) {
				queryStr += " where ";

				for (int i = 0; i < filters.size(); i++) {
					//filterStmts.add(filter.getStmt());
					//if it's the first filter, don't add the logical operator
					Filter filter = filters.get(i);
					queryStr += (i == 0 ? "" : filter.getLogicalOpStr()) + filter.getStmt();
				}				
			}

			try {
				stmt = CONNECTION.prepareStatement(queryStr);

				if (filters != null && !filters.isEmpty()) {
					//preparedStatement param index starts at 1, arraylist of filters starts at 0
					for (int i = 1; i <= filters.size(); i++) {
						Filter filter = filters.get(i-1);
						String fVal = filter.getFieldValue();

						// add filters as params
						switch (filter.getDataType()) {
						case Date:
							java.sql.Date dt1 = java.sql.Date.valueOf(fVal);
							stmt.setDate(i, dt1);
							break;
						case Dec:
							stmt.setFloat(i, Float.parseFloat(fVal));
							break;
						case Int:
							stmt.setInt(i, Integer.parseInt(fVal));
							break;
						case String:
							stmt.setString(i, fVal);
							break;
						case Null:
							System.out.println("null fired");
							stmt.setNull(i, Integer.parseInt(fVal));
							break;
						}
					}
				}

				System.out.println(stmt.toString());
				rslts = stmt.executeQuery();
				return generateJSON(rslts);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} finally {
				closeConnection();
			}

		}
	}
	
	/**
	 * 
	 * @param storedProcName
	 * 	- the name of the corresponding stored procedure in the database
	 * @param filters 
	 * 	- filters are the conditions to query against (ex: id = 123)
	 * @param data
	 * 	- the data to pass as params to the stored procedure
	 * 	- this json string is expected to be wrapped in an object named "data" (ex: {"data":{...}} where ... is the json data you want to update the database with)
	 * @return
	 */
	public String update(String storedProcName, ArrayList<Filter> filters, JSONString data){
		JSONObject jObj = new JSONObject(data);
		if (isValid(jObj)){
			
			
		}
		
		return storedProcName;		
	}
	
	/**
	 * The JSONObject passed in should be wrapped in an object named "data" 
	 * 	- (ex: {"data":{...}} where ... is the json data you want to update the database with)
	 * @param data
	 * @return
	 */
	public boolean isValid(JSONObject data){
		try {
			JSONArray jArray = data.getJSONArray("data");
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
}
