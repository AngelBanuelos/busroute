/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.radar.busbeaXML;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author angel_banuelos
 */
@XmlRootElement(name="Posiciones")
@XmlAccessorType(XmlAccessType.FIELD)
public class Posiciones implements Serializable {

    @XmlElement(name = "Posicion")
    List<Posicion> buses;

    @Override
    public String toString() {
        return "Busses{" + "buses=" + buses + '}';
    }

    public List<Posicion> getBuses() {
        return buses;
    }
    
}
