package renters.problem.app;

public class Resource {

    public String name;
    public String notes;
    public String imageLocation;

    public Resource(String name) {
        this.name = name;
    }

    public Resource(String name, String notes, String imageLocation) {
        this.name = name;
        this.notes = notes;
        this.imageLocation = imageLocation;
    }

}
