package robotrace;


import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

import java.util.Random;

import static com.jogamp.opengl.GL2.*;
import static robotrace.ShaderPrograms.*;
import static robotrace.Textures.*;

/**
 * Handles all of the RobotRace graphics functionality,
 * which should be extended per the assignment.
 *
 * OpenGL functionality:
 * - Basic commands are called via the gl object;
 * - Utility commands are called via the glu and
 *   glut objects;
 *
 * GlobalState:
 * The gs object contains the GlobalState as described
 * in the assignment:
 * - The camera viewpoint angles, phi and theta, are
 *   changed interactively by holding the left mouse
 *   button and dragging;
 * - The camera view width, vWidth, is changed
 *   interactively by holding the right mouse button
 *   and dragging upwards or downwards; (Not required in this assignment)
 * - The center point can be moved up and down by
 *   pressing the 'q' and 'z' keys, forwards and
 *   backwards with the 'w' and 's' keys, and
 *   left and right with the 'a' and 'd' keys;
 * - Other settings are changed via the menus
 *   at the top of the screen.
 *
 * Textures:
 * Place your "track.jpg", "brick.jpg", "head.jpg",
 * and "torso.jpg" files in the folder textures.
 * These will then be loaded as the texture
 * objects track, bricks, head, and torso respectively.
 * Be aware, these objects are already defined and
 * cannot be used for other purposes. The texture
 * objects can be used as follows:
 *
 * gl.glColor3f(1f, 1f, 1f);
 * Textures.track.bind(gl);
 * gl.glBegin(GL_QUADS);
 * gl.glTexCoord2d(0, 0);
 * gl.glVertex3d(0, 0, 0);
 * gl.glTexCoord2d(1, 0);
 * gl.glVertex3d(1, 0, 0);
 * gl.glTexCoord2d(1, 1);
 * gl.glVertex3d(1, 1, 0);
 * gl.glTexCoord2d(0, 1);
 * gl.glVertex3d(0, 1, 0);
 * gl.glEnd(); 
 *
 * Note that it is hard or impossible to texture
 * objects drawn with GLUT. Either define the
 * primitives of the object yourself (as seen
 * above) or add additional textured primitives
 * to the GLUT object.
 */
public class RobotRace extends Base {

    /** Array of the four robots. */
    private final Robot[] robots;

    /** Instance of the camera. */
    private final Camera camera;

    /** Instance of the race track. */
    private final RaceTrack[] raceTracks;

    /** Instance of the terrain. */
    private final Terrain terrain;

    // Create trees
    private final Tree[] trees;
    Random rand = new Random();
    // The number of trees that will be drawn; between 3 and 8
    int  treeCount = rand.nextInt(5) + 3;

    /**
     * Constructs this robot race by initializing robots,
     * camera, track, and terrain.
     */
    public RobotRace() {
        Random rand = new Random();
        trees = new Tree[treeCount];
        for (int i = 0; i < treeCount; i++){
            // Number of body parts each tree will have, between 3 and 8
            int numberOfBodyParts = rand.nextInt(5) + 3;
            // The size of a tree, between 2 and 8
            int  treeSize = rand.nextInt(6) + 2;
            // Generate the coordinates for each tree, such that they are insine the track circle
            double  randomX = rand.nextInt(15) - 7.5;
            double  randomY = rand.nextInt(15) - 7.5;
            trees[i] = new Tree(treeSize, 30, randomX, randomY, numberOfBodyParts);
        }



        // Create a new array of four robots
        robots = new Robot[4];

        // Initialize robot 0
        robots[0] = new Robot(Material.GOLD, 1.8
        );

        // Initialize robot 1
        robots[1] = new Robot(Material.SILVER, 1.8
        );

        // Initialize robot 2
        robots[2] = new Robot(Material.WOOD, 1.8
        );

        // Initialize robot 3
        robots[3] = new Robot(Material.ORANGE, 1.8
        );

        // Initialize the camera
        camera = new Camera();

        // Initialize the race tracks
        raceTracks = new RaceTrack[2];

        // Track 1
        raceTracks[0] = new ParametricTrack();

        // Track 2
        float g = 3.5f;
        raceTracks[1] = new BezierTrack(

                new Vector[] {
                        new Vector(9, 7.5, 1),
                        new Vector(8.7, 8.0, 1),
                        new Vector(7.7, 8.5, 1),
                        new Vector(7, 9, 1),

                        new Vector(7, 9, 1),
                        new Vector(4, 8.2, 1),
                        new Vector(2, 7.4, 1),
                        new Vector(0, 7, 1),

                        new Vector(0, 7, 1),
                        new Vector(-2, 7.4, 1),
                        new Vector(-4, 8.2, 1),
                        new Vector(-7, 9, 1),


                        new Vector(-7, 9, 1),
                        new Vector(-7.7, 8.5, 1),
                        new Vector(-8.7, 8.0,  1),
                        new Vector(-9, 7.5, 1),

                        new Vector(-9, 7.5, 1),
                        new Vector(-8.2, 4, 1),
                        new Vector(-7.4, 2, 1),
                        new Vector(-7, 0, 1),

                        new Vector(-7, 0, 1),
                        new Vector(-7.4, -2, 1),
                        new Vector(-8.2, -4, 1),
                        new Vector(-9, -7.5, 1),

                        new Vector(-9, -7.5, 1),
                        new Vector(-8.7, -8, 1),
                        new Vector(-7.7, -8.5, 1),
                        new Vector(-7, -9, 1),

                        new Vector(-7, -9, 1),
                        new Vector(-4, -8.2, 1),
                        new Vector(-2, -7.4, 1),
                        new Vector(0, -7, 1),

                        new Vector(0, -7, 1),
                        new Vector(2, -7.4, 1),
                        new Vector(4, -7.2, 1),
                        new Vector(7, -9, 1),

                        new Vector(7, -9, 1),
                        new Vector(7.7, -8.5, 1),
                        new Vector(8.7, -8, 1),
                        new Vector(9, -7.5, 1),

                        new Vector(9, -7.5, 1),
                        new Vector(8.2, -4, 1),
                        new Vector(7.4, -2, 1),
                        new Vector(7, 0, 1),

                        new Vector(7, 0, 1),
                        new Vector(7.4, 2, 1),
                        new Vector(8.2, 4, 1),
                        new Vector(9, 7.5, 1)
                }
        );
        // Initialize the terrain
        terrain = new Terrain();



    }

    /**
     * Called upon the start of the application.
     * Primarily used to configure OpenGL.
     */
    @Override
    public void initialize() {

        // Enable blending.
        gl.glEnable(GL_BLEND);
        gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        // Enable depth testing.
        gl.glEnable(GL_DEPTH_TEST);
        gl.glDepthFunc(GL_LESS);

        // Enable face culling for improved performance
        // gl.glCullFace(GL_BACK);
        // gl.glEnable(GL_CULL_FACE);

        // Normalize normals.
        gl.glEnable(GL_NORMALIZE);

        // Try to load four textures, add more if you like in the Textures class
        Textures.loadTextures();
        reportError("reading textures");

        // Try to load and set up shader programs
        ShaderPrograms.setupShaders(gl, glu);
        reportError("shaderProgram");

    }

    /**
     * Configures the viewing transform.
     */
    @Override
    public void setView() {
        // Select part of window.
        gl.glViewport(0, 0, gs.w, gs.h);

        // Set projection matrix.
        gl.glMatrixMode(GL_PROJECTION);
        gl.glLoadIdentity();

        // Set the perspective.
        glu.gluPerspective(45, (float)gs.w / (float)gs.h, 0.1*gs.vDist, 10*gs.vDist);

        // Set camera.
        gl.glMatrixMode(GL_MODELVIEW);
        gl.glLoadIdentity();

        // Add light source
        gl.glLightfv(GL_LIGHT0, GL_POSITION, new float[]{0f,0f,0f,1f}, 0);

        // Update the view according to the camera mode and robot of interest.
        // For camera modes 1 to 4, determine which robot to focus on.
        camera.update(gs, robots[3]);
        glu.gluLookAt(camera.eye.x(),    camera.eye.y(),    camera.eye.z(),
                camera.center.x(), camera.center.y(), camera.center.z(),
                camera.up.x(),     camera.up.y(),     camera.up.z());
    }

    /**
     * Draws the entire scene.
     */
    @Override
    public void drawScene() {

        gl.glUseProgram(defaultShader.getProgramID());
        reportError("program");

        // Background color.
        gl.glClearColor(1f, 1f, 1f, 0f);

        // Clear background.
        gl.glClear(GL_COLOR_BUFFER_BIT);

        // Clear depth buffer.
        gl.glClear(GL_DEPTH_BUFFER_BIT);

        // Set color to black.
        gl.glColor3f(0f, 0f, 0f);

        gl.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);


        // Draw hierarchy example.
        //drawHierarchy();

        // Draw the axis frame.
        if (gs.showAxes) {
            drawAxisFrame();
        }
        // Draw all the trees
        for (int i = 0; i < treeCount; i++){
            trees[i].drawTree(gl, glut);
        }

        //assign material to track
        gl.glMaterialfv(GL_FRONT, GL_DIFFUSE, Material.WOOD.diffuse, 0);
        gl.glMaterialfv(GL_FRONT, GL_SPECULAR, Material.WOOD.specular, 0);
        gl.glMaterialf(GL_FRONT, GL_SHININESS, Material.WOOD.shininess);
        // Draw the race track.
        gl.glUseProgram(trackShader.getProgramID());
        raceTracks[gs.trackNr].draw(gl, glu, glut);


        // Draw the terrain.
        terrain.draw(gl, glu, glut);
        reportError("terrain:");


        // Draw the robots, each with  different movement speed
        gl.glUseProgram(robotShader.getProgramID());
        double t;
        for(int i = 0; i < 4; i++){
            t = gs.tAnim / (10 + i * 1.3);
            robots[i].position = raceTracks[gs.trackNr].getLanePoint(i + 1, t);
            robots[i].direction = raceTracks[gs.trackNr].getLaneTangent(i + 1, t);
            robots[i].draw(gl, glu, glut, gs.tAnim);
        }


    }

    /**
     * Draws the x-axis (red), y-axis (green), z-axis (blue),
     * and origin (yellow).
     */
    public void drawAxisFrame() {
        gl.glColor3d(0,0 ,255 ); // set color
        drawArrow(Vector.Z, 2, 0); // draw axis

        gl.glColor3d(0,255 ,0 );
        drawArrow(Vector.Y, 1, -90);


        gl.glColor3d(255, 0 ,0 );
        drawArrow(Vector.X, 0, -90);

        // Set color to yellow
        gl.glColor3d(1.0, 1.0, 0.0);
        // Draw the origin sphere
        glut.glutSolidSphere(0.25, 10, 10);
        // Set color to black
        gl.glColor3f(0f, 0f, 0f);
    }

    /**
     * Draws a single arrow
     */
    public void drawArrow(Vector a, int rotate_axis, double angle) {   // two point parameters
        double arrow_radius = 0.05;
        double arrow_height = 0.3;
        int QUALITY = 100;
        // draw the lines for the arrows
        gl.glBegin(GL_LINES);
        gl.glVertex3d(0,0,0);
        gl.glVertex3d(a.x,a.y,a.z); // 0 to the input vector
        gl.glEnd();

        gl.glPushMatrix(); // draw a cone at the end of the arrow ( arrowhead)
        gl.glTranslated(a.x, a.y, a.z); // translate to the end of line
        switch(rotate_axis){ // rotate x and y axis  heads
            case 0: gl.glRotated(angle, 0, 0, 1);
            case 1: gl.glRotated(angle, 1,0,0);
        }
        glut.glutSolidCone(arrow_radius, arrow_height, QUALITY, QUALITY); // draw cone
        gl.glPopMatrix();
    }

    /**
     * Drawing hierarchy example.
     *
     * This method draws an "arm" which can be animated using the sliders in the
     * RobotRace interface. The A and B sliders rotate the different joints of
     * the arm, while the C, D and E sliders set the R, G and B components of
     * the color of the arm respectively.
     *
     * The way that the "arm" is drawn (by calling {@link #drawSecond()}, which
     * in turn calls {@link #drawThird()} imposes the following hierarchy:
     *
     * {@link #drawHierarchy()} -> {@link #drawSecond()} -> {@link #drawThird()}
     */
    private void drawHierarchy() {
        gl.glColor3d(gs.sliderC, gs.sliderD, gs.sliderE);
        gl.glPushMatrix();
        gl.glScaled(1, 0.5, 0.5);
        glut.glutSolidCube(1);
        gl.glScaled(1, 2, 2);
        gl.glTranslated(0.5, 0, 0);
        gl.glRotated(gs.sliderA * -90.0, 0, 1, 0);
        drawSecond();
        gl.glPopMatrix();
    }

    private void drawSecond() {
        gl.glTranslated(0.5, 0, 0);
        gl.glScaled(1, 0.5, 0.5);
        glut.glutSolidCube(1);
        gl.glScaled(1, 2, 2);
        gl.glTranslated(0.5, 0, 0);
        gl.glRotated(gs.sliderB * -90.0, 0, 1, 0);
        drawThird();
    }

    private void drawThird() {
        gl.glTranslated(0.5, 0, 0);
        gl.glScaled(1, 0.5, 0.5);
        glut.glutSolidCube(1);
    }

    /**
     * Main program execution body, delegates to an instance of
     * the RobotRace implementation.
     */
    public static void main(String args[]) {
        RobotRace robotRace = new RobotRace();
        robotRace.run();
    }
}