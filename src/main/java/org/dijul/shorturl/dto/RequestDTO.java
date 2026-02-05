package org.dijul.shorturl.dto;


import lombok.Data;

import java.util.Optional;

@Data
public class RequestDTO {
    String link;
    String shortCode;
}
