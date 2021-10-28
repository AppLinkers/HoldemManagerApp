package com.example.server_test.competition;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class Competition {

    private long id;
    private String competition_name;
    private String competition_place;
    private int competition_buyIn;
    private String competition_start;
    private String competition_end;

}
