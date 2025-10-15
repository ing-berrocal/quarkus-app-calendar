/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.calendario.app.model;

/**
 *
 * @author ITPB-RH
 */
public record Service(
    Long serviceId,
    String title,
    String description, 
    int durationInMinute, 
    int serviceConcurrency
    ) {
        
}
