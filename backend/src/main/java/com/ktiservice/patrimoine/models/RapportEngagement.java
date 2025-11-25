package com.ktiservice.patrimoine.models;


public class RapportEngagement extends BaseEntity {
    
    private Integer nombreUtilisateursActifs;
    private Double taux_engagement;
    private Integer nombreAvisCreesTotal;
    
    public RapportEngagement(){}
    
     public RapportEngagement(Integer nombreUtilisateursActifs, Double taux_engagement, Integer nombreAvisCreesTotal) {
        this.nombreUtilisateursActifs = nombreUtilisateursActifs;
        this.taux_engagement = taux_engagement;
        this.nombreAvisCreesTotal = nombreAvisCreesTotal;
    }

    // --- Getters ---
      public Integer getNombreUtilisateursActifs() { return nombreUtilisateursActifs; }
      public void setNombreUtilisateursActifs(Integer nombreUtilisateursActifs)
       { this.nombreUtilisateursActifs = nombreUtilisateursActifs; }

      public Double getTaux_engagement() { return taux_engagement;}
      public void setTaux_engagement(Double taux_engagement)
      { this.taux_engagement = taux_engagement;}

      public Integer getNombreAvisCreesTotal(){ return nombreAvisCreesTotal;}
      public void setNombreAvisCreesTotal(Integer nombreAvisCreesTotal)
      {this.nombreAvisCreesTotal= nombreAvisCreesTotal;}
      
}
