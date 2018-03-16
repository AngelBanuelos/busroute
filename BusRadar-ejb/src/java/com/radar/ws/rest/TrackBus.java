/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.radar.ws.rest;

import com.mongodb.client.DistinctIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.radar.ejb.utils.SaveGPSHistoryLocal;
import com.radar.vo.GPSBusVO;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.bson.Document;

/**
 *
 * @author angel_banuelos
 */
@Path("/trackbus")
@Stateless
public class TrackBus {

    @PostConstruct
    public void init() {
        System.out.println("Ready to Service" + TrackBus.class);
    }

    @GET
    @Path("/getPosition/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"administrator","client"})
    public String getLocation(@PathParam("id") int id) {
        StringBuilder json = new StringBuilder();
        if (conn != null && conn.mongoConnection() != null && conn.mongoConnection().getDatabase("BusRadar") != null) {
            MongoCollection mongoCollection = conn.mongoConnection().getDatabase("BusRadar").getCollection("GPSBus");
            DistinctIterable<String> a = mongoCollection.distinct("IMEI", new Document().append("routeID", id), String.class);
            json.append("[");
            for (String imei : a) {
                FindIterable<Document> bus = mongoCollection
                        .find(new Document().append("routeID", id)
                                .append("IMEI", imei))
                        .sort(new Document().append("_id", -1)).limit(1);
                for (Document bus1 : bus) {
                    json.append(bus1.toJson());
                    json.append(",");
                }
            }
            if (json.lastIndexOf(",") != -1) {
                json.delete(json.lastIndexOf(","), json.length());
            }
            json.append("]");

            System.out.println(conn.helloSingleton());
        }
        return json.toString();
    }
    
    @GET
    @Path("/getPosition/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"administrator", "client"})
    public GPSBusVO[] getLocation2(@PathParam("id") int id) {
        return conn.getPossitions(id);
    }

    @PreDestroy
    public void destroy() {
        System.out.println("There is not too much load, I will die! " + TrackBus.class);
    }

    @EJB
    SaveGPSHistoryLocal conn;

}
