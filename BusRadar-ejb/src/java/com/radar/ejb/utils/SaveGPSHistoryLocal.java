/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.radar.ejb.utils;

import com.mongodb.MongoClient;
import com.radar.vo.GPSBusVO;
import javax.ejb.Local;

/**
 *
 * @author Angel.Sahagun
 */
@Local
public interface SaveGPSHistoryLocal {
    
    public String helloSingleton();
    
    public MongoClient mongoConnection();
    
    public GPSBusVO[] getPossitions(int id);
    
}
