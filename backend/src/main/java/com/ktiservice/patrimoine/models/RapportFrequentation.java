package com.ktiservice.patrimoine.models;


import lombok.Getter;

@Getter
public class RapportFrequentation extends BaseEntity {
    private Integer nombre_Visite_Total;
     private String visiteParSite;
     private String visiteParJour;

     public RapportFrequentation(){}

    public RapportFrequentation(Integer nombre_Visite_Total, String visiteParSite, String visiteParJour){
        this.nombre_Visite_Total= nombre_Visite_Total;
        this.visiteParJour= visiteParJour;
        this.visiteParSite= visiteParSite;
    }

    //...Getter...>

    public void setNombre_Visite_Total(Integer nombre_Visite_Total)
    {this.nombre_Visite_Total= nombre_Visite_Total;}

    public void setVisiteParSite(String VisiteParSite)
    {this.visiteParSite= visiteParSite;}

    public void setVisiteParJour(String visiteParJour){
        this.visiteParJour= visiteParJour;
    }
}
