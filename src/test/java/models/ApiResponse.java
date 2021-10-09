package models;

import lombok.Data;

@Data
public class ApiResponse {
    public Integer code;
    public String type;
    public String message;
}
