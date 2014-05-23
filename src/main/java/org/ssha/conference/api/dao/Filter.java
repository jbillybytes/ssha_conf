package org.ssha.conference.api.dao;

public class Filter {
	private String FIELD_NAME;
	private String FIELD_VALUE;
	private Operators OPERATOR;
	private DataTypes DATA_TYPE;
	// LogicalOpFlag of a filter is assumed to be 'and' unless otherwise indicated
	// this field is used to indicate how to concatenate a given condition when compiling the where clause
	private LogicalOpFlag LOF = LogicalOpFlag.and; 
	
	public Filter(String fieldName, Operators operator, String fieldVal, DataTypes dataType){
		FIELD_NAME = fieldName;
		OPERATOR = operator;
		FIELD_VALUE = fieldVal;
		DATA_TYPE = dataType;
	}
	
	public Filter(String fieldName, Operators operator, String fieldVal, DataTypes dataType, LogicalOpFlag lof){
		FIELD_NAME = fieldName;
		OPERATOR = operator;
		FIELD_VALUE = fieldVal;
		DATA_TYPE = dataType;
		LOF = lof;
	}
	
	public String getFieldName(){
		return FIELD_NAME;
	}
	
	public void setFieldName(String name){
		FIELD_NAME = name;
	}
	
	public String getFieldValue(){
		return FIELD_VALUE;
	}
	
	public void setFieldValue(String value){
		FIELD_VALUE = value;
	}
	
	public Operators getOperator(){
		return OPERATOR;
	}
	
	public void setOperator(Operators ops){
		OPERATOR = ops;
	}
	
	public DataTypes getDataType(){
		return DATA_TYPE;
	}
	
	public void setDataType(DataTypes dataType){
		DATA_TYPE = dataType;
	}
	
	public LogicalOpFlag getLogicalOpFlag(){
		return this.LOF;
	}
	
	public String getLogicalOpStr(){
		return this.LOF == LogicalOpFlag.and ? " and " : " or ";
	}
	
	public void setLogicalOp(LogicalOpFlag lof){
		LOF = lof;
	}
	
	public enum Operators {
		BeginsWith, Contains, EndsWith, Equals, GreaterThan, GreaterThanOrEq, Is, IsNot, LessThan, LessThanOrEq, NotEquals
	};
	
	public enum LogicalOpFlag {
		and, or
	};
	
	public enum DataTypes {
		String, Date, Int, Dec, Null
	};
	
	public String getStmt(){
		String condition = this.FIELD_NAME + " "; 
		
		switch (this.OPERATOR){
		case BeginsWith:
			condition += "like CONCAT(?, '%')";
			break;
		case Contains:
			condition += "like CONCAT('%', ?, '%')";
			break;
		case EndsWith:
			condition += "like CONCAT(?, '%')";
			break;
		case Equals:
			condition += " = ?";
			break;
		case GreaterThan:
			condition += " > ?";
			break;
		case GreaterThanOrEq:
			condition += " >= ?";
			break;
		case Is:
			condition += " is ?";
			break;
		case IsNot:
			condition += " is not ?";
			break;
		case LessThan:
			condition += " < ?";
			break;
		case LessThanOrEq:
			condition += " <= ?";
			break;
		case NotEquals:
			condition += " <> ?";
			break;
		
		default:
			break;				
		}
		
		return condition;
		
	}
	
}
