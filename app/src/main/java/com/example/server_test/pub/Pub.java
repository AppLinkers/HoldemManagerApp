package com.example.server_test.pub;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pub {
    private long id;
    private String pub_info;
    private String pub_name;
    private Game game;
    private String pub_open;
    private String pub_end;
    private String pub_img;

}
