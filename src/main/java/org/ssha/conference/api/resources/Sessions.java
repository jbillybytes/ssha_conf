package org.ssha.conference.api.resources;

import java.util.ArrayList;
import java.util.Arrays;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ssha.conference.api.dao.*;

@Path("/sessions")
public class Sessions {
	
	@GET
	@Path("{sessionId}/publications")
	@Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public Response getPapersBySession(@PathParam("sessionId") int sessionId) {
		DBTransact dbt = new DBTransact();
		String results = "";
		ArrayList<Filter> filters = new ArrayList<>();

		filters.add(new Filter("conference_session_id", Filter.Operators.Equals, Integer.toString(sessionId), Filter.DataTypes.Int));

		JSONArray jArray;
		try {
			jArray = dbt.select(new ArrayList<String>(Arrays.asList("*")), "vwsessionpublications", filters);

			if (jArray != null && jArray.length() > 0) {
				results = jArray.toString();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Response.status(200).entity(results).build();
	}
	
	@GET
	@Path("{sessionId}/people")
	@Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public Response getPeopleBySession(@PathParam("sessionId") int sessionId) {
		DBTransact dbt = new DBTransact();
		String results = "";
		ArrayList<Filter> filters = new ArrayList<>();

		filters.add(new Filter("conference_session_id", Filter.Operators.Equals, Integer.toString(sessionId), Filter.DataTypes.Int));

		JSONArray jArray;
		try {
			jArray = dbt.select(new ArrayList<String>(Arrays.asList("role_name", "peopleid", "first_name", "last_name")), "vwConferenceSessionPeople", filters);

			if (jArray != null && jArray.length() > 0) {
				results = jArray.toString();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Response.status(200).entity(results).build();
	}
	
	@GET
	@Path("{sessionId}")
	public Response getSession(@PathParam("sessionId") int sessionId){
		DBTransact dbt = new DBTransact();
		String results = "";
		ArrayList<Filter> filters = new ArrayList<Filter>();
		filters.add(new Filter("created_at",Filter.Operators.GreaterThanOrEq,"2013-01-17", Filter.DataTypes.Date));
		filters.add(new Filter("id",Filter.Operators.Equals, Integer.toString(sessionId), Filter.DataTypes.Int));

		JSONArray jArray;
		try {
			//start by getting the basic info
			jArray = dbt.select(new ArrayList<String>(Arrays.asList("*")), "vwsessions", filters);			
			
			if (jArray != null && jArray.length() > 0){
				if (jArray.length() == 1) {					
					results = jArray.toString().replace("[", "").replace("]", "");
				}
				else
				results = jArray.toString();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Response.status(200).entity(results).build();
	}	
	
	@GET
	@Path("{sessionId}/available_volunteers")
	@Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public Response getVolunteersBySession(@PathParam("sessionId") int sessionId) {		
		DBTransact dbt = new DBTransact();
		String results = "";
		ArrayList<Filter> filters = new ArrayList<>();
		JSONArray jArray;
		
		//get primary network id for given session
		int primary_network_id = 0;
		filters.add(new Filter("id", Filter.Operators.Equals, Integer.toString(sessionId), Filter.DataTypes.Int));
		
		try {
			jArray = dbt.select(new ArrayList<String>(Arrays.asList("primary_network_id")), "conference_sessions", filters);

			if (jArray != null && jArray.length() == 1) {
				JSONObject jOb = jArray.getJSONObject(0);
				primary_network_id = Integer.parseInt(jOb.get("primary_network_id").toString());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(primary_network_id);
		
		dbt = new DBTransact();
		filters = new ArrayList<>();
	
		//if the value is null, pass in sql type for field val
		filters.add(new Filter("session_id", Filter.Operators.Is, Integer.toString(java.sql.Types.INTEGER), Filter.DataTypes.Null));
		filters.add(new Filter("network_id", Filter.Operators.Equals, Integer.toString(primary_network_id), Filter.DataTypes.Int));
		
		try {
			jArray = dbt.select(new ArrayList<String>(Arrays.asList("*")), "vwvolunteers", filters);

			if (jArray != null && jArray.length() > 0) {
				results = jArray.toString();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Response.status(200).entity(results).build();
	}
	
	@GET
	@Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public Response responseMsg() {
		DBTransact dbt = new DBTransact();
		String results = "";
		ArrayList<Filter> filters = new ArrayList<Filter>();
		filters.add(new Filter("created_at",Filter.Operators.GreaterThanOrEq,"2013-01-17", Filter.DataTypes.Date));
		
		JSONArray jArray;
		try {
			jArray = dbt.select(new ArrayList<String>(Arrays.asList("*")), "vwsessions", filters);			
			
			if (jArray != null && jArray.length() > 0){
				results = jArray.toString();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Response.status(200).entity(results).build();
	}	

}