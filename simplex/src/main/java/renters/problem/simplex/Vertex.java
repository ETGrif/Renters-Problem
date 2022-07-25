package renters.problem.simplex;

public class Vertex {

    int i;
    int j; 

    Vector2D position;
    
    public Vertex(int i, int j) {
        this.i = i;
        this.j = j;
        this.position = new Vector2D(0, 0);
    }

    public Vertex(int i, int j, Vector2D position){
        this.i = i;
        this.j = j;
        this.position = position;
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

    public double getX() {
        return position.x;
    }

    public double getY() {
        return position.y;
    }

    public Vector2D getPos(){
        return position;
    }

    public void setX(double x){
        position.set(x, position.y);
    }

    public void setY(double y){
        position.set(position.x, y);
    }

    public void setPosition(Vector2D position){
        this.position = position;
    }
    
    
}
