package renters.problem.simplex;

public class Vertex {

    int i;
    int j; 
    
    public Vertex(int i, int j) {
        this.i = i;
        this.j = j;
    }

    @Override
    public boolean equals(Object o){

        if(!(o instanceof Vertex)){
            return false;
        }

        Vertex other = (Vertex) o;
        return ( this.i == other.getI() && this.j == other.getJ());
    }

    public int getI(){
        return i;
    }

    public int getJ(){
        return j;
    }

    public Double getX() {
        return -1.0;
    }

    public Double getY() {
        return -1.0;
    }

}
