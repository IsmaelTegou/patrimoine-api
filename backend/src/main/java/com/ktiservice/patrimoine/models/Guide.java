package com.ktiservice.patrimoine.models;


public class Guide extends BaseEntity{
    private String specialite;
    private Integer experience;
    private Integer nombreAvisRecieved;

    public Guide(){}

    public Guide(String specialite,Integer experience, Integer nombreAvisRecieved){
        this.specialite= specialite;
        this.experience= experience;
        this.nombreAvisRecieved= nombreAvisRecieved;
    }

    //Getter...
    public String getSpecialite(){return specialite;}
    public void setSpecialite(String specialite){
        this.specialite= specialite;
    }
    public Integer getExperience(){return experience;}
    public void  setExperience(Integer experience){
        this.experience = experience;
    }
    public Integer getNombreAvisRecieved(){return nombreAvisRecieved;}
    public void setNombreAvisRecieved(Integer nombreAvisRecieved){
        this.nombreAvisRecieved= nombreAvisRecieved;
    }
}
