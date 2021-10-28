package com.example.server_test.pub;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Game {

    private Long id;
    private boolean game1;
    private boolean game2;
    private boolean game3;

    public String games() {
        String result = "";

        if (isGame1()) {
            result += "game1 ";
        }

        if (isGame2()) {
            result += "game2 ";
        }

        if (isGame3()) {
            result += "game3 ";
        }
        return result;
    }

}

