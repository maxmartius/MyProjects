public class Linie {

    private Punkt ursprungsPunkt;
    private Punkt endPunkt;
    private double laenge;
    private double grad;

    public Linie(Punkt ursprungsPunkt, Punkt endPunkt) {
        this.ursprungsPunkt = ursprungsPunkt;
        this.endPunkt = endPunkt;
        berechneLeange();
        berechneGrad();
        this.grad = this.grad % 360;
    }

    public Linie(Punkt ursprungsPunkt, double laenge, double grad) {
        this.ursprungsPunkt = ursprungsPunkt;
        this.laenge = laenge;
        this.grad = grad;
        berechneEndPunkt();
        this.grad = this.grad % 360;
    }

    private void berechneLeange(){
        double a = this.ursprungsPunkt.getX()-this.endPunkt.getX();
        double b = this.ursprungsPunkt.getY()-this.endPunkt.getY();
        this.laenge = (Math.sqrt((a*a)+(b*b)));
    }

    private void berechneGrad() {
        double x = this.ursprungsPunkt.getX()-this.endPunkt.getX();
        double y = this.ursprungsPunkt.getY()-this.endPunkt.getY();
        x = Math.sqrt(x*x);
        y = Math.sqrt(y*y);
        double rad = Math.atan(y/x);
        if(this.ursprungsPunkt.getX() <= this.endPunkt.getX()) {
            if (this.ursprungsPunkt.getY() <= this.endPunkt.getY()) {
                this.grad = (rad*180/Math.PI);
            }else {
                this.grad = 270+(rad*180/Math.PI);
            }
        }else {
            if(this.ursprungsPunkt.getY() >= this.endPunkt.getY()) {
                this.grad = 90+(rad*180/Math.PI);
            }else {
                this.grad = 180+(rad*180/Math.PI);
            }
        }

    }

    private void berechneEndPunkt() {
        double x = this.ursprungsPunkt.getX();
        double y = this.ursprungsPunkt.getY();
        if(this.grad <= 90){
            double rad = (this.grad) * (Math.PI/180.0);
            x += this.laenge * (Math.cos(rad));
            y -= this.laenge * (Math.sin(rad));
        }else if(this.grad <= 180){
            double rad = (180 - this.grad) * (Math.PI/180.0);
            x -= this.laenge * (Math.cos(rad));
            y -= this.laenge * (Math.sin(rad));
        }else  if(this.grad <= 270){
            double rad = (this.grad - 180) * (Math.PI/180.0);
            x -= this.laenge * (Math.cos(rad));
            y += this.laenge * (Math.sin(rad));
        }else{
            double rad = (360 - this.grad) * (Math.PI/180.0);
            x += this.laenge * (Math.cos(rad));
            y += this.laenge * (Math.sin(rad));
        }

        Punkt endPunkt = new Punkt(x , y);
        this.endPunkt = endPunkt;

    }

    public double getGrad() {
        return this.grad;
    }

    public double getLaenge() {
        return this.laenge;
    }

    public Punkt getEndPunkt() {
        return this.endPunkt;
    }

    public Punkt getUrsprungsPunkt() {
        return this.ursprungsPunkt;
    }

    public String ausgabe() {
        String l = this.ursprungsPunkt.ausgabe()+" - "+this.endPunkt.ausgabe();
        return l;
    }

}