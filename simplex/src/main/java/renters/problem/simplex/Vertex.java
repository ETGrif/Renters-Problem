package renters.problem.simplex;

public class Vertex {

    int x;
    int y; 
    
    public Vertex(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o){

        if(!(o instanceof Vertex)){
            return false;
        }

        Vertex other = (Vertex) o;
        return ( this.x == other.getX() && this.y == other.getY());
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

}
