/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.radar.vo.map;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import com.radar.busbeaXML.Posicion;
import com.radar.busbeaXML.Posiciones;
import com.radar.vo.GPSBusVO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.Document;

/**
 *
 * @author angel_banuelos
 */
public class GPSBusVOMapper {

    public GPSBusVO mapPosicion(Posicion pos) {
        GPSBusVO gpsbvo = new GPSBusVO();
        if (pos != null) {
            try {
                gpsbvo.setDirection(pos.Direccion);
                gpsbvo.setIMEI(pos.IMEI);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                gpsbvo.setLastUpdate(sdf.parse(pos.Fecha));
                if (pos.Latitud.length() > 3) {
                    float lat = Float.parseFloat(pos.Latitud.subSequence(0, 2) + "." + pos.Latitud.substring(2));
                    gpsbvo.setLat(lat);
                }
                if (pos.Longitud.length() > 4) {
                    float lon = Float.parseFloat(pos.Longitud.subSequence(0, 4) + "." + pos.Longitud.substring(4));
                    gpsbvo.setLng(lon);
                }
                gpsbvo.setName(pos.Nombre);
                gpsbvo.setSpeed(pos.Velocidad);
                gpsbvo.setType(pos.Tipo);
                gpsbvo.setUserId(pos.UsuarioId);
            } catch (ParseException ex) {
                Logger.getLogger(GPSBusVOMapper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return gpsbvo;
    }

    public GPSBusVO[] mapPosiciones(Posiciones poss) {
        if (poss != null && poss.getBuses() != null) {
            int size = poss.getBuses().size();
            GPSBusVO[] result = new GPSBusVO[size];
            int i = 0;
            for (Posicion buse : poss.getBuses()) {
                result[i] = mapPosicion(buse);
                i++;
            }
            return result;
        } else {
            return null;
        }

    }

    public GPSBusVO[] mapPosicionesAndInsert(Posiciones poss, MongoCollection mongoCollection, int routeID) {
        if (poss != null && poss.getBuses() != null) {
            int size = poss.getBuses().size();
            GPSBusVO[] result = new GPSBusVO[size];
            int i = 0;
            for (Posicion buse : poss.getBuses()) {
                result[i] = mapPosicion(buse);
                if (result[i] != null && result[i].getIMEI() != null && result[i].getLastUpdate() != null) {
                    try {
                        mongoCollection.updateOne(new Document().append("IMEI", result[i].getIMEI()).append("lastUpdate", result[i].getLastUpdate()), result[i].getUpdateDocument(routeID), (new UpdateOptions()).upsert(true));
                        //mongoCollection.insertOne(result[i].getDocument().append("routeID", routeID));
                    } catch (Exception e) {
                        System.err.println("Err mapPosicionesAndInsert -> " + e.getMessage() );
                    }
                }
                i++;
            }
            return result;
        } else {
            return null;
        }
    }

}
