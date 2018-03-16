/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.radar.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import org.bson.Document;

/**
 *
 * @author angel_banuelos
 */
public class GPSBusVO implements Serializable {

    String userId;
    float lat;
    float lng;
    float speed;
    String name;
    String IMEI;
    Date lastUpdate;
    String type;
    int direction;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lon) {
        this.lng = lon;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "GPSBusVO{" + "userId=" + userId + ", lat=" + lat + ", lng=" + speed + ", speed=" + speed + ", name=" + name + ", IMEI=" + IMEI + ", lastUpdate=" + lastUpdate + ", type=" + type + ", direction=" + direction + '}';
    }
    
    public Document getUpdateDocument(int routeId) {
        Document doc = new Document();
        doc.put("lat", lat);
        doc.put("lng", lng);
        doc.put("speed", speed);
        doc.put("name", name);
        doc.put("type", type);
        doc.put("direction", direction);
        doc.put("routeID", routeId);
        return new Document("$set", doc);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GPSBusVO other = (GPSBusVO) obj;
        if (!Objects.equals(this.IMEI, other.IMEI)) {
            return false;
        }
        if (!Objects.equals(this.lastUpdate, other.lastUpdate)) {
            return false;
        }
        return true;
    }

}
