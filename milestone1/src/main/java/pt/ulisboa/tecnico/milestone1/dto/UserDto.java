package pt.ulisboa.tecnico.milestone1.dto;

public class UserDto {
    private int id;
    private String name;

    public UserDto(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public UserDto() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
