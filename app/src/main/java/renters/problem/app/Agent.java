package renters.problem.app;

import java.awt.Color;

import org.w3c.dom.NameList;

import renters.problem.gui.Nameable;

public class Agent implements Nameable {
    public String name;
    public Color color;

    public Agent(String name) {
        this.name = name;
        this.color = Color.RED;
    }

    public Agent(String name, Color color){
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
