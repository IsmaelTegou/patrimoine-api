package com.ktiservice.patrimoine.models;

import java.time.LocalDateTime;


public class GestionnaireSite extends BaseEntity {
    private LocalDateTime dateAssignation;

    public GestionnaireSite(){}
    
    public GestionnaireSite(LocalDateTime dateAssignation){
        this.dateAssignation= dateAssignation;
    }

    //..Getter...
   public LocalDateTime getDateAssignation(){return dateAssignation;}
   public void setDateAssignation(LocalDateTime dateAssignation){
    this.dateAssignation= dateAssignation;
   }
}
