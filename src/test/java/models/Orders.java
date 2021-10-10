package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Orders {

    public Integer id;
    public Integer petId;
    public Integer quantity;
    public String shipDate;
    public String status;
    public Boolean complete;
}
