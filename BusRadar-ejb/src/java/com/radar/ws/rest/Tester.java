/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.radar.ws.rest;

import com.radar.vo.map.GPSBusVOMapper;
import com.radar.busbeaXML.Posiciones;
import com.radar.vo.GPSBusVO;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import java.util.Arrays;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author angel_banuelos
 */
public class Tester {
    
    static String url = "http://www.1.monitoreatubus.com/busbeaXML/PosicionesRutaXML.aspx?";
    static String paramR = "ruta=";
    static String parmDb = "&db=sistecozome";

    
    public static void main(String[] args) {
        Client client = Client.create();
        WebResource resource
                = client.resource(url + paramR + 1 + parmDb);
        Posiciones bus = resource.accept(MediaType.TEXT_PLAIN).get(Posiciones.class);
        
        GPSBusVOMapper mapper = new GPSBusVOMapper();
        GPSBusVO[] all = mapper.mapPosiciones(bus);
        System.out.println(Arrays.toString(all));
    }
}
