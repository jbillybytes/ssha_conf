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

@Path("/volunteers")
public class Volunteer {

	@GET
	@Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public Response responseMsg() {
		DBTransact dbt = new DBTransact();
		String results = "";
		
		JSONArray jArray;
		try {
			jArray = dbt.select(new ArrayList<String>(Arrays.asList("*")), "vwvolunteers", null);			
			
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
