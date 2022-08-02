package renters.problem.app;

import java.awt.Color;
import renters.problem.gui.Nameable;

public class Resource implements Nameable{

    public String name;
    public Color color;
    public String notes;
    public String imageLocation;

    public Resource(String name) {
        this.name = name;
        this.color = Color.WHITE;
    }

    public Resource(String name, Color color){
        this.name = name;
        this.color = color;
    }

    public Resource(String name, String notes, String imageLocation) {
        this.name = name;
        this.notes = notes;
        this.imageLocation = imageLocation;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

}
