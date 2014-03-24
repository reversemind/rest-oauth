package ru.ttk.baloo.rest.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
@Path("/service")
public class Simple {

	@GET
	@Path("/create")
	public String createInfo(){
		return "\n\n\t!!!Protected Resource(createInfo) Accessed !!!! Returning from Myresource createInfo\n";

	}
	
	@GET
	@Path("/get")
	public String getMyInfo(){
		return "\n\n\t Protected Resource(getMyInfo) Accessed !!!! Returning from Myresource getMyInfo\n";
	}

	@GET
	@Path("/update")
	public String updateMyInfo(){
		return "\n\n\t Protected Resource(updateInfo) Accessed !!!! Returning from Myresource updateInfo\n";
		
	}

}
