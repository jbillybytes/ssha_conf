package org.ssha.conference.api.resources;

import java.util.ArrayList;
import java.util.Arrays;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.ssha.conference.api.dao.DBTransact;
import org.ssha.conference.api.dao.Filter;

@Path("/schedule")
public class Schedule {
	@GET
	@Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public Response responseMsg() {
		DBTransact dbt = new DBTransact();
		String results = "";
		ArrayList<Filter> filters = new ArrayList<>();
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
		
		return Response.status(200).entity("schedule").build();
	}
}
