package com.example.yashwanth.weatherapp;

public class Weather {

    float temperature;
    int humidity;
    String desc;
    String cityName;

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public String getCityName() {
        return cityName;
    }

    public int getHumidity() {
        return humidity;
    }

    public float getTemperature() {
        return temperature;
    }

    public String getDesc() {
        return desc;
    }
}


