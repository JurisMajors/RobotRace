package robotrace;
import static java.lang.Math.*;

/**
 * Implementation of RaceTrack, creating a track from a parametric formula
 */
public class ParametricTrack extends RaceTrack {
    
    @Override
    protected Vector getPoint(double t) { // given formula
        return new Vector(10*cos(2*PI*t), 14*sin(2*PI*t), 1);
    }

    @Override
    protected Vector getTangent(double t) { // derivative of the given formula?
        return new Vector(-20*PI*sin(2*PI*t), 28*PI*cos(2*PI*t), 0);
    }
    
}
