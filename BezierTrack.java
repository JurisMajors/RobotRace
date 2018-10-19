
package robotrace;

/**
 * Implementation of RaceTrack, creating a track from control points for a 
 * cubic Bezier curve
 */
public class BezierTrack extends RaceTrack {
    
    private Vector[] controlPoints;
    private int nr_segments;
    private double length_segments;
    
    BezierTrack(Vector[] controlPoints) {
        this.controlPoints = controlPoints;
        this.nr_segments = controlPoints.length / 4; // each segment has 4 control points
        this.length_segments = 1.0d / ((double) nr_segments); // together they form line length 1 [0, 1]
    }
    
    @Override
    protected Vector getPoint(double t) {
        t %= 1; // since t can be >1 (i.e. 1.00000000004) because its a double, safety
        int current_segment = (int) (t/ length_segments); // which segment in the distance(t) from 0 to 1 we are drawing
        t = t/length_segments - current_segment ; // (double)segment - floor(segment) := 0 to 1 fraction of drawing that segment

        // get the control points for that particular segment
        Vector P0 = controlPoints[4 * current_segment];
        Vector P1 = controlPoints[4 * current_segment + 1];
        Vector P2 = controlPoints[4 * current_segment + 2];
        Vector P3 = controlPoints[4 * current_segment + 3];

        return getCubicBezierPnt(t, P0, P1, P2, P3);

    }
    Vector getCubicBezierPnt(double t, Vector P0, Vector P1,
                             Vector P2, Vector P3){
        // P(t) = (1 - t)^3 * P0 + 3t(1-t)^2 * P1 + 3t^2 (1-t) * P2 + t^3 * P3
        return P0.scale(Math.pow(1 - t, 3))
                .add(P1.scale(3 * t * Math.pow(1 - t, 2)))
                .add(P2.scale(3 * Math.pow(t, 2) * (1 - t)))
                .add(P3.scale(Math.pow(t, 3)));
    }
    Vector getCubicBezierTng(double t, Vector P0, Vector P1,
                             Vector P2, Vector P3){

        // derivative of poinr
        return P0.scale(-3 * Math.pow(1 - t, 2))
                .add(P1.scale(3 * Math.pow(1 - t, 2) - 6 * t * (1 - t)))
                .add(P2.scale(-3 * Math.pow(t, 2) + 6 * t * (1 - t)))
                .add(P3.scale(3 * Math.pow(t, 2))).normalized();
    }




    @Override
    protected Vector getTangent(double t) {
        t %= 1; // since t can be >1 (i.e. 1.00000000004) because its a double, safety
        int current_segment = (int) (t/ length_segments); // which segment in the distance(t) from 0 to 1 we are drawing
        t = t/length_segments - current_segment ; // (double)segment - floor(segment) := 0 to 1 fraction of drawing that segment

        // get the control points for that particular segment
        Vector P0 = controlPoints[4 * current_segment];
        Vector P1 = controlPoints[4 * current_segment + 1];
        Vector P2 = controlPoints[4 * current_segment + 2];
        Vector P3 = controlPoints[4 * current_segment + 3];

        // dP(t) / dt =  -3(1-t)^2 * P0 + 3(1-t)^2 * P1 - 6t(1-t) * P1 - 3t^2 * P2 + 6t(1-t) * P2 + 3t^2 * P3
        return getCubicBezierTng(t, P0, P1, P2, P3);
    }


    

}
