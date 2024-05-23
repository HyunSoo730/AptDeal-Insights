package com.ssafy.finalproject.email;

import lombok.Data;

@Data
public class EmailSendDTO {

    private String to;

    private String subject;

    private String body;
}
