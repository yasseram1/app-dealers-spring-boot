package com.app.appdealers.util.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DealersData {
    private Integer id;
    private String nombre;
    private String email;
    private String numDoc;
    private String telefono;

}
