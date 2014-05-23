package org.ssha.conference.api.resources;

import java.util.ArrayList;
import java.util.Arrays;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONString;
import org.ssha.conference.api.dao.DBTransact;
import org.ssha.conference.api.dao.Filter;

@Path("/publications")
public class Publications {
    //================================================================================
    // GETS
    //================================================================================

	@GET
	@Path("{pubId}/authors")
	public Response getPublicationAuthors(@PathParam("pubId") int pubId){
		DBTransact dbt = new DBTransact();
		String results = "";
		ArrayList<Filter> filters = new ArrayList<Filter>();
		filters.add(new Filter("publication_id",Filter.Operators.Equals, Integer.toString(pubId), Filter.DataTypes.Int));

		JSONArray jArray;
		try {
			jArray = dbt.select(new ArrayList<String>(Arrays.asList("*")), "vwauthors", filters);			
			
			if (jArray != null && jArray.length() > 0){
				if (jArray.length() == 1) {
					
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

	@GET
	@Path("{pubId}")
	public Response getPublications(@PathParam("pubId") int pubId){
		DBTransact dbt = new DBTransact();
		String results = "";
		ArrayList<Filter> filters = new ArrayList<Filter>();
		filters.add(new Filter("created_at",Filter.Operators.GreaterThanOrEq,"2013-01-17", Filter.DataTypes.Date));
		filters.add(new Filter("id",Filter.Operators.Equals, Integer.toString(pubId), Filter.DataTypes.Int));

		JSONArray jArray;
		try {
			jArray = dbt.select(new ArrayList<String>(Arrays.asList("*")), "vwpublications", filters);			
			
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
	@Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public Response responseMsg() {
		DBTransact dbt = new DBTransact();
		String results = "";
		ArrayList<Filter> filters = new ArrayList<>();
		filters.add(new Filter("created_at",Filter.Operators.GreaterThanOrEq,"2013-01-17", Filter.DataTypes.Date));
		
		JSONArray jArray;
		try {
			jArray = dbt.select(new ArrayList<String>(Arrays.asList("*")), "vwpublications", filters);			
			
			if (jArray != null && jArray.length() > 0){
				results = jArray.toString();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Response.status(200).entity(results).build();
	}
	
    //================================================================================
    // PUTS
    //================================================================================
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updatePublication(JSONString pubData){
		
		return null;
		
	}
	
    //================================================================================
    // POSTS
    //================================================================================


}
