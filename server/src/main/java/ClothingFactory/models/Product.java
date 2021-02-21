package ClothingFactory.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Product {
    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("category")
    private String category;

    @JsonProperty("quality")
    private String quality;

    @JsonProperty("price")
    private double price;

    @JsonProperty("image")
    private String image;

    @JsonProperty("status")
    private String status;

    @JsonProperty("creation_date")
    private String creation_date;

    @JsonProperty("type")
    private Type type;

    @JsonProperty("store")
    private Store store;

    @JsonProperty("employees")
    private List<Employee> employees;


    public Product() {
    }

    public Product(String id, String name, String category, String quality, double price, String image, String status, String creation_date, Type type, Store store, List<Employee> employees) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.quality = quality;
        this.price = price;
        this.image = image;
        this.status = status;
        this.creation_date = creation_date;
        this.type = type;
        this.store = store;
        this.employees = employees;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
