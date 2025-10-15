/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.calendario;

import java.time.LocalDate;

/**
 *
 * @author ITPB-RH
 */
interface IAppointmentBean {

    public int[] getSelectedHours(Long serviceInstanceId, LocalDate queryDate);
    
}
