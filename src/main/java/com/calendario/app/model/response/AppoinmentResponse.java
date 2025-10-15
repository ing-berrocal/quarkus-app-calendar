/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.calendario.app.model.response;

import com.calendario.app.model.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author ITPB-RH
 */
public record AppoinmentResponse
(       Long id,
        LocalDate date, 
        LocalTime time
        ){
    
}
