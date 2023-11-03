package com.nierot.bierenschede;

import com.nierot.bierenschede.parsers.KlaasUndKockParser;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {

        KlaasUndKockParser p = new KlaasUndKockParser();

        try {
            p.process();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Hello from RESTEasy Reactive";
    }
}
