/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.radar.busbeaXML;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 *
 * @author angel_banuelos
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Posicion implements Serializable {
    
    public String UsuarioId;
    public String Latitud;
    public String Longitud;
    public float Velocidad;
    public String Nombre;
    public String IMEI;
    public String Fecha;
    public String Tipo;
    public int Direccion;

    @Override
    public String toString() {
        return "GPSBusVO{" + "UsuarioId=" + UsuarioId + ", Latitud=" + Latitud + ", Longitud=" + Longitud + ", Velocidad=" + Velocidad + ", Nombre=" + Nombre + ", IMEI=" + IMEI + ", Fecha=" + Fecha + ", Tipo=" + Tipo + ", Direccion=" + Direccion + '}' + "\n";
    }

}
