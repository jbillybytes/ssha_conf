package org.ssha.conference.api.resources;
import java.util.ArrayList;
import java.util.Arrays;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.ssha.conference.api.dao.DBTransact;
import org.ssha.conference.api.dao.Filter;

@Path("/networks")
public class Networks {
	
	@GET
	@Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
	@Path("{networkId}")
	public Response getNetwork(@PathParam("networkId") int networkId) {
		DBTransact dbt = new DBTransact();
		String results = "";
		
		ArrayList<Filter> filters = new ArrayList<Filter>();
		filters.add(new Filter("id",Filter.Operators.Equals, Integer.toString(networkId), Filter.DataTypes.Int));
		
		JSONArray jArray;
		try {
			jArray = dbt.select(new ArrayList<String>(Arrays.asList("*")), "networks", filters);			
			
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
	@Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
	@Path("{networkId}/representatives")
	public Response getNetworkReps(@PathParam("networkId") int networkId) {
		DBTransact dbt = new DBTransact();
		String results = "";
		
		ArrayList<Filter> filters = new ArrayList<Filter>();
		filters.add(new Filter("represented_network_id",Filter.Operators.Equals, Integer.toString(networkId), Filter.DataTypes.Int));
		
		JSONArray jArray;
		try {
			jArray = dbt.select(new ArrayList<String>(Arrays.asList("*")), "vwperson", filters);			
			
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
	@Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
	@Path("{networkId}/orphan_papers")
	public Response getOrphanPapers(@PathParam("networkId") int networkId) {
		DBTransact dbt = new DBTransact();
		String results = "";
		
		ArrayList<Filter> filters = new ArrayList<Filter>();
		filters.add(new Filter("network_id",Filter.Operators.Equals, Integer.toString(networkId), Filter.DataTypes.Int));
		
		JSONArray jArray;
		try {
			jArray = dbt.select(new ArrayList<String>(Arrays.asList("*")), "vworphanpapers", filters);			
			
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
	@Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public Response responseMsg() {
		DBTransact dbt = new DBTransact();
		String results = "";
				
		JSONArray jArray;
		try {
			jArray = dbt.select(new ArrayList<String>(Arrays.asList("*")), "networks", null);			
			
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
