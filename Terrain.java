package robotrace;

import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

import static com.jogamp.opengl.GL.GL_TRIANGLE_STRIP;
import static robotrace.ShaderPrograms.defaultShader;
import static robotrace.ShaderPrograms.terrainShader;

/**
 * Represents the terrain, to be implemented according to the Assignments.
 */
class Terrain {


    public Terrain() {

    }

    /**
     * Draws the terrain.
     */
    public void draw(GL2 gl, GLU glu, GLUT glut) {
        int size = 20; // 20x20 grid
        Vector top_left = new Vector(-size, -size, -.5);
        Vector water_top_left = new Vector(-size, -size, 0);
        gl.glUseProgram(terrainShader.getProgramID()); // use shader for height manipulation
        //draw flat terrain
        draw_flat_plane(gl, top_left, size);
        gl.glUseProgram(defaultShader.getProgramID()); // use default shader so that water is flat
        // draw water plane
        gl.glColor4d(128,128,128, 0.6); // set grey transparent color
        draw_flat_plane(gl, water_top_left, size);
    }


    /**
     *  Draws flat plane with triangle strip by drawing rows of polygons starting from top_left
     * @param gl
     * @param top_left indication starting vertex to draw the plane
     * @param size size of plane
     */
    void draw_flat_plane(GL2 gl, Vector top_left, int size) {
        for (int i = 0; i < size * 2; i++) { // rows
            double x, y, tempy;
            y = top_left.y + i; // go to next row
            gl.glBegin(GL_TRIANGLE_STRIP); // start of new row
            for (int j = 0; j < size * 2; j++) {
                /*
                this loops draws 'columns' of two polygons positioned as a square
                 */
                x = top_left.x + j; // move to the right
                gl.glNormal3d(0, 0, 1);
                gl.glVertex3d(x, y, top_left.z); // add vertex
                tempy = y + 1; // add vertex in next row so that polygon would cover properly
                gl.glNormal3d(0, 0, 1);
                gl.glVertex3d(x, tempy, top_left.z); // add vertex
            }
            gl.glEnd(); // end of row
        }

    }
}