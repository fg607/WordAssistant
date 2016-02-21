package com.hardwork.fg607.wordassistant.model;

/**
 * Created by fg607 on 16-1-3.
 */
public class BaseSpeak {

    String speakUK;
    String speakUS;
    String mp3UKUrl;
    String mp3USUrl;

    public BaseSpeak(String speakUK, String speakUS, String mp3USUrl, String mp3UKUrl) {
        this.speakUK = speakUK;
        this.speakUS = speakUS;
        this.mp3USUrl = mp3USUrl;
        this.mp3UKUrl = mp3UKUrl;
    }

    public String getSpeakUK() {
        return speakUK;
    }

    public void setSpeakUK(String speakUK) {
        this.speakUK = speakUK;
    }

    public String getSpeakUS() {
        return speakUS;
    }

    public void setSpeakUS(String speakUS) {
        this.speakUS = speakUS;
    }

    public String getMp3UKUrl() {
        return mp3UKUrl;
    }

    public void setMp3UKUrl(String mp3UKUrl) {
        this.mp3UKUrl = mp3UKUrl;
    }

    public String getMp3USUrl() {
        return mp3USUrl;
    }

    public void setMp3USUrl(String mp3USUrl) {
        this.mp3USUrl = mp3USUrl;
    }

    @Override
    public String toString() {
        return "BaseSpeak{" +
                "speakUK='" + speakUK + '\'' +
                ", speakUS='" + speakUS + '\'' +
                ", mp3UKUrl='" + mp3UKUrl + '\'' +
                ", mp3USUrl='" + mp3USUrl + '\'' +
                '}';
    }
}
