package ClothingFactory.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Role {
    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    public Role() {
    }

    public Role(String id, String name, String location) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
