package com.example.server_test.competition;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class Competition {
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private String competition_name;
    private String competition_place;
    private int competition_buyIn;
    private String competition_start;
    private String competition_end;

    public String getCompetition_name() {
        return competition_name;
    }

    public void setCompetition_name(String competition_name) {
        this.competition_name = competition_name;
    }

    public String getCompetition_place() {
        return competition_place;
    }

    public void setCompetition_place(String competition_place) {
        this.competition_place = competition_place;
    }

    public int getCompetition_buyIn() {
        return competition_buyIn;
    }

    public void setCompetition_buyIn(int competition_buyIn) {
        this.competition_buyIn = competition_buyIn;
    }

    public String getCompetition_start() {
        return competition_start;
    }

    public void setCompetition_start(String competition_start) {
        this.competition_start = competition_start;
    }

    public String getCompetition_end() {
        return competition_end;
    }

    public void setCompetition_end(String competition_end) {
        this.competition_end = competition_end;
    }
}
