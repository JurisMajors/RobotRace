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
        double nr_of_polygons = 1d/100d;
        int scale = 1;
        drawRing(gl, nr_of_polygons, 1, scale); // top
        //drawRing(gl, n, -1, scale); // bottom
        drawEdge(gl, nr_of_polygons, true, scale); // inner
        drawEdge(gl, nr_of_polygons, false, scale+4); // not inner
    }

    /**
     * Draw the ellipse plane for the track in two iterations by drawing
     * in each iteration two lanes
     */
    void drawRing(GL2 gl, double precision, int z, int k ){
        Textures.track.bind(gl); // bind texture
        double t; // time aka distance
        int vertex_counter; // for texture coordinate assigning
        for (int i = 0; i < 2 ; i++) {// draw ring in two iterations of each iteration drawing 2 lanes
            gl.glBegin(GL_TRIANGLE_STRIP);
            t = 0; // reset t for each iteration
            vertex_counter = 0;
            Vector vertex, p2, direction_inner, direction_outer;
            while(t < 1. + precision ) {

                vertex = getPoint(t); // start of two lanes

                // get vector that will displace the point
                direction_inner = getTangent(t).cross(Vector.Z).normalized().scale(k*laneWidth);
                direction_outer = getTangent(t).cross(Vector.Z).normalized().scale((k+2)*laneWidth);

                gl.glNormal3i(0, 0 , z); // set-up normal depending whether bottom or up
                if(vertex_counter%2 == 0){
                    gl.glTexCoord2d(0, 1);
                }else {
                    gl.glTexCoord2d(0, 0);
                }
                gl.glVertex3d(vertex.x + direction_inner.x, vertex.y + direction_inner.y, z);
                gl.glNormal3i(0, 0 , z); // set-up normal depending whether bottom or up

                if(vertex_counter%2 == 0){
                    gl.glTexCoord2d(1, 1);
                }else{
                    gl.glTexCoord2d(1, 0);
                }
                gl.glVertex3d(vertex.x + direction_outer.x, vertex.y + direction_outer.y, z);
                vertex_counter++;
                t += precision;
            }
            k+=2; // go to next two lanes
            gl.glEnd();
        }
    }

    /**
     *
     * @param gl
     * @param precision 1/ amount of polygons
     * @param inward which edge
     * @param k scaling for distance from centre
     */

    void drawEdge(GL2 gl, double precision, boolean inward, int k){
        Textures.brick.bind(gl);
        double t;
        int vertex_counter;
        for (int i = 0; i <= 2 ; i++) {
            t = 0;
            gl.glBegin(GL_TRIANGLE_STRIP);
            Vector p1,tangent, N, direction;
            vertex_counter = 0;
            while(t < 1. + precision){
                p1 = getPoint(t); // point
                tangent = getTangent(t).normalized().scale(k * laneWidth); // get tangent for point
                // calculate normal which is product of tangent
                if(inward){
                    N = Vector.Z.cross(tangent);
                }else{
                    N = tangent.cross(Vector.Z);
                }
                direction = tangent.cross(Vector.Z);
                gl.glNormal3d(N.x, N.y, N.z); // add the normal
                // assign brick texture coordinates
                if(vertex_counter%2 == 0){
                    gl.glTexCoord2d(0, 0);
                }else {
                    gl.glTexCoord2d(1, 0);
                }
                gl.glVertex3d(p1.x + direction.x,p1.y + direction.y,1); // move the point to the correct direction
                gl.glNormal3d(N.x, N.y, N.z); // add the normal
                // assign texture coordinates
                if(vertex_counter%2 == 0){
                    gl.glTexCoord2d(0, 1);
                }else {
                    gl.glTexCoord2d(1, 1);
                }
                gl.glVertex3d(p1.x + direction.x,p1.y + direction.y,-1);// move the point to the correct direction
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
        // direction for the points
        Vector start_direction = getTangent(t).cross(Vector.Z).normalized().scale(lane * laneWidth);
        Vector end_direction = getTangent(t).cross(Vector.Z).normalized().scale((lane+1)*laneWidth);
        Vector p = getPoint(t);
        Vector end = new Vector(p.x + end_direction.x, p.y + end_direction.y, p.z);
        Vector start = new Vector(p.x + start_direction.x, p.y+start_direction.y, p.z);
        // end is outer part of lane, start is inner part. returns the middle
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