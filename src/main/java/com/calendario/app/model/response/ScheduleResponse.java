/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.calendario.app.model.response;

import model.Tuple;

/**
 *
 * @author ITPB-RH
 */
public record ScheduleResponse
(       Long serviceInstanceId,
        String employeeName, 
        Tuple[] availableTimes
        ){
    
}
