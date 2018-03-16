/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.radar.ejb.utils;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.radar.busbeaXML.Posiciones;
import com.radar.vo.GPSBusVO;
import com.radar.vo.map.GPSBusVOMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import java.util.HashMap;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Angel.Sahagun
 */
@Singleton
@Startup
public class SaveGPSHistory implements SaveGPSHistoryLocal {

    String url = "http://www.1.monitoreatubus.com/busbeaXML/PosicionesRutaXML.aspx?";
    String paramR = "ruta=";
    String parmDb = "&db=sistecozome";
    final int TOTALROUTES = 2;
    int i = 0;
    private MongoClient mongoConection;
    private MongoDatabase mongoBusRadar;
    private boolean isTestingEnv = true;
    private HashMap <Integer, GPSBusVO[]> hashGPSBus = new HashMap();

    @Resource
    TimerService tmr;

    @PostConstruct
    public void init() {
        tmr.createIntervalTimer(10000, 3000, null);
        if (!isTestingEnv) {
            mongoConection = new MongoClient("localhost", 27017);
            mongoBusRadar = mongoConection.getDatabase("BusRadar");
        }
    }

    @Timeout
    public void timeout() {
        int currentBus = ((i % TOTALROUTES) + 1);
        String complexURL = url + paramR + currentBus + parmDb;
        Posiciones bus = null;
        Client client = null;
        try {
            client = Client.create();
            WebResource resource
                    = client.resource(complexURL);
            bus = resource.accept(MediaType.TEXT_PLAIN).get(Posiciones.class);
        } catch (Exception e) {
            System.err.println("Timer Excetion --> BusRadar " + e.getMessage());
        }
        GPSBusVOMapper mapper = new GPSBusVOMapper();
        hashGPSBus.put(currentBus, mapper.mapPosiciones(bus));
        if (!isTestingEnv ) {
            GPSBusVO[] all = mapper.mapPosicionesAndInsert(bus, mongoBusRadar.getCollection("GPSBus"), currentBus);
        }
        i++;
    }

    @PreDestroy
    public void cancelTimer() {
        for (Timer t : tmr.getAllTimers()) {
            t.cancel();
        }
        if (mongoConection != null) {
            mongoConection.close();
        }
    }

    public MongoClient mongoConnection() {
        return mongoConection;
    }

    public String helloSingleton() {
        return "Hello :)";
    }
    
    public GPSBusVO[] getPossitions(int id){
        if(hashGPSBus == null) return null;
        return hashGPSBus.get(id);
    }

}
