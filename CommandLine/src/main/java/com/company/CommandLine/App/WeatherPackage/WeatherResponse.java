package com.company.CommandLine.App.WeatherPackage;

import com.company.CommandLine.App.ISSPackage.Coordinates;

public class WeatherResponse {

    public Coordinates coord;
    public Weather[] weather;
    public String base;
    public Main main;
    public int visibility;
    public Wind wind;
    public Clouds clouds;
    public String dt;
    public Sys sys;
    public String timezone;
    public String id;
    public String name;
    public int cod;

}
