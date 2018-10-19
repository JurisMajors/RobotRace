package robotrace;

import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import static com.jogamp.opengl.GL2.*;
import static robotrace.ShaderPrograms.terrainShader;
import static robotrace.ShaderPrograms.trackShader;

/**
 * Implementation of a race track that is made from Bezier segments.
 */
abstract class RaceTrack {

    /** The width of one lane. The total width of the track is 4 * laneWidth. */
    private final static float laneWidth = 1.22f;

    /**
     * Constructor for the default track.
     */
    public RaceTrack() {


    }

    /**
     * Draws this track, based on the control points.
     */
    public void draw(GL2 gl, GLU glu, GLUT glut) {
//        gl.glEnable(GL_TEXTURE_2D);
        double n = 0.02;
        int scale = 1;
        drawRing(gl, n, 1, scale); // top
        //drawRing(gl, n, -1, scale); // bottom
        drawEdge(gl, n, true, scale); // inner
        drawEdge(gl, n, false, scale+4); // not inner
    }

    void drawRing(GL2 gl, double precision, int z, int k ){
        Textures.track.bind(gl);
        double t;
        int vertex_counter;
        for (int i = 0; i < 2 ; i++) {// draw ring in two iterations of each iteration drawing 2 lanes
            gl.glBegin(GL_TRIANGLE_STRIP);
            t = 0; // reset t for each iteration
            vertex_counter = 0;
            Vector p1, p2, tan1, tan2;
            while(t < 1. + precision) {
                p1 = getPoint(t); // start of two lanes
                p2 = getPoint(t); // end of two lanes

                tan1 = getTangent(t).cross(Vector.Z).normalized().scale(k*laneWidth);
                tan2 = getTangent(t).cross(Vector.Z).normalized().scale((k+2)*laneWidth);

                gl.glNormal3i(0, 0 , z); // set-up normal depending whether bottom or up
                if(vertex_counter%2 == 0){
                    gl.glTexCoord2d(0, 1);
                }else {
                    gl.glTexCoord2d(0, 0);
                }
                gl.glVertex3d(p1.x + tan1.x, p1.y + tan1.y, z);
                gl.glNormal3i(0, 0 , z); // set-up normal depending whether bottom or up

                if(vertex_counter%2 == 0){
                    gl.glTexCoord2d(1, 1);
                }else{
                    gl.glTexCoord2d(1, 0);
                }
                gl.glVertex3d(p2.x + tan2.x, p2.y + tan2.y, z);
                vertex_counter++;
                t += precision;
            }
            k+=2; // go to next two lanes
            gl.glEnd();
        }
    }

    void drawEdge(GL2 gl, double precision, boolean inward, int k){
        Textures.brick.bind(gl);
        double t;
        int vertex_counter;
        for (int i = 0; i <= 2 ; i++) {
            t = 0;
            gl.glBegin(GL_TRIANGLE_STRIP);
            Vector p1,tangent, N;
            vertex_counter = 0;
            while(t < 1. + precision){
                p1 = getPoint(t);
                tangent = getTangent(t).normalized().scale(k * laneWidth);
                // calculate normal
                if(inward){
                    N = Vector.Z.cross(tangent);
                }else{
                    N = tangent.cross(Vector.Z);
                }
                tangent = tangent.cross(Vector.Z);
                gl.glNormal3d(N.x, N.y, N.z);
                if(vertex_counter%2 == 0){
                    gl.glTexCoord2d(0, 0);
                }else {
                    gl.glTexCoord2d(1, 0);
                }
                gl.glVertex3d(p1.x + tangent.x,p1.y + tangent.y,1);
                gl.glNormal3d(N.x, N.y, N.z);
                if(vertex_counter%2 == 0){
                    gl.glTexCoord2d(0, 1);
                }else {
                    gl.glTexCoord2d(1, 1);
                }
                gl.glVertex3d(p1.x + tangent.x,p1.y + tangent.y,-1);
                vertex_counter++;
                t+= precision;
            }
            gl.glEnd();
        }
    }


    /**
     * Returns the center of a lane at 0 <= t < 1.
     * Use this method to find the position of a robot on the track.
     */

    public Vector getLanePoint(int lane, double t){
        Vector tan = getTangent(t).cross(Vector.Z).normalized().scale(lane * laneWidth);
        Vector tan2 = getTangent(t).cross(Vector.Z).normalized().scale((lane+1)*laneWidth);
        Vector p = getPoint(t);
        Vector end = new Vector(p.x + tan2.x, p.y + tan2.y, p.z);
        Vector start = new Vector(p.x + tan.x, p.y+tan.y, p.z);
        return end.add(start).scale(0.5);

    }
    /**
     * Returns the tangent of a lane at 0 <= t < 1.
     * Use this method to find the orientation of a robot on the track.
     */
    public Vector getLaneTangent(int lane, double t){
        return getTangent(t).normalized(); // tangent is same no matter location
    }

    // Returns a point on the test track at 0 <= t < 1.
    protected abstract Vector getPoint(double t);

    // Returns a tangent on the test track at 0 <= t < 1.
    protected abstract Vector getTangent(double t);
}