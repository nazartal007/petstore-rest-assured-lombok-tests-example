package models;


import lombok.Data;

import java.util.List;

@Data
public class Pet {
    public Integer id;
    public Category category;
    public String name;
    public List<String> photoUrls;
    public List<Tag> tags;
    public String status;
}
