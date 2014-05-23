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
import org.ssha.conference.api.dao.DBTransact;
import org.ssha.conference.api.dao.Filter;
import org.ssha.conference.api.dao.Filter.LogicalOpFlag;

@Path("/people")
public class People {

	@GET
	@Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public Response responseMsg(@QueryParam("srchTxt") String srchTxt) {
		DBTransact dbt = new DBTransact();
		String results = "";
		ArrayList<Filter> filters = new ArrayList<>();
		
		if (srchTxt != null && srchTxt.length() > 2) {
			filters.add(new Filter("last_name", Filter.Operators.BeginsWith, srchTxt, Filter.DataTypes.String));
			filters.add(new Filter("first_name", Filter.Operators.BeginsWith, srchTxt, Filter.DataTypes.String, LogicalOpFlag.or));
		}
		
		JSONArray jArray;
		try {
			jArray = dbt.select(new ArrayList<String>(Arrays.asList("*")), "vwpeoplesrch", filters);

			if (jArray != null && jArray.length() > 0){
				results = jArray.toString();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Response.status(200).entity(results).build();
	}
	
	@GET
	@Path("{id}")
	public Response getPerson(@PathParam("id") int id){
		DBTransact dbt = new DBTransact();
		String results = "";
		ArrayList<Filter> filters = new ArrayList<Filter>();
		filters.add(new Filter("id",Filter.Operators.Equals, Integer.toString(id), Filter.DataTypes.Int));

		JSONArray jArray;
		try {
			jArray = dbt.select(new ArrayList<String>(Arrays.asList("*")), "vwperson", filters);			
			
			if (jArray != null && jArray.length() > 0){
				if (jArray.length() == 1) {
					//in this case, the json should be a single object
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
	@Path("{id}/publications")
	public Response getPublicationsByPersonId(@PathParam("id") int id){
		DBTransact dbt = new DBTransact();
		String results = "";
		ArrayList<Filter> filters = new ArrayList<Filter>();
		filters.add(new Filter("author_id",Filter.Operators.Equals, Integer.toString(id), Filter.DataTypes.Int));

		JSONArray jArray;
		try {
			jArray = dbt.select(new ArrayList<String>(Arrays.asList("*")), "vwpublicationauthors", filters);			
			
			if (jArray != null && jArray.length() > 0){
				if (jArray.length() == 1) {
					//in this case, the json should be a single object
					results = jArray.toString();
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
}
