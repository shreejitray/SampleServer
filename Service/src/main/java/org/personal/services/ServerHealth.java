package org.personal.services;

import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * author: schaud3
 * 14/05/21
 */
@Component
@Path("health")
public class ServerHealth {

    @Path("basic")
    @GET
    public String simpleServerCheck(){
        return "Server is up!";
    }
}
