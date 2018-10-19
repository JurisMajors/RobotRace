package robotrace;

import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import static com.jogamp.opengl.GL.*;
import static com.jogamp.opengl.GL2ES3.GL_QUADS;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.*;

/**
 * Represents a Robot, to be implemented according to the Assignments.
 */
class Robot {

    /** The position of the robot. */
    public Vector position = new Vector(0, 0, 0);

    /** The direction in which the robot is running. */
    public Vector direction = new Vector(1, 0, 0);

    /** The material from which this robot is built. */
    private final Material material;

    // Size of robot
    double robotSize;
    // Animation speed of arms and legs and head
    double robotAnimationSpeed = 3;
    /**
     * Constructs the robot with initial parameters.
     */
    public Robot(Material material, double robotSize

    ) {
        this.material = material;
        this.robotSize = robotSize;

    }

    public void draw(GL2 gl, GLU glu, GLUT glut, float tAnim) {
        // Set materials
        gl.glPushMatrix();
        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, material.diffuse, 0);
        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, material.specular, 0);
        gl.glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, material.shininess);
        // Rotate the robot according to tangent
        double rotation = Math.toDegrees(Math.acos(Vector.X.dot(direction)));
        // Check if negative, if it is, then change rotation, so we have 360 degrees
        if(Vector.X.cross(direction).z <= 0){
            rotation = -rotation;
        }
        // Place the robot on the correct coordinates
        gl.glTranslated(position.x, position.y, position.z);
        gl.glRotated(rotation, 0,0,1);
        gl.glRotated(90, 0,0,1);
        // Move so that the robot is on the track
        gl.glTranslated(0, 0, -robotSize*1.15);
        // Draws head
        drawHead(gl, glu, glut, tAnim);
        // Draws body
        drawBody(gl, glu, glut, tAnim);
        // Two calls for each arm, true means right arm, false means left
        drawArm(gl, glu, glut, tAnim, true);
        drawArm(gl, glu, glut, tAnim, false);
        // Same with legs
        drawLeg(gl, glu, glut, tAnim, true);
        drawLeg(gl, glu, glut, tAnim, false);
        gl.glPopMatrix();

    }

    public void drawHead(GL2 gl, GLU glu, GLUT glut, float tAnim){
        gl.glPushMatrix();
        // Translate head to robot size, then draw it
        gl.glTranslated(0, 0, (2.2*robotSize));
        gl.glRotated((Math.abs(Math.cos(tAnim*robotAnimationSpeed))*20)-10, 1, 0, 0);
        gl.glScaled(robotSize/5,robotSize/5,robotSize/5);
        glut.glutSolidCube(1);
        gl.glPopMatrix();

    }

    public void drawBody(GL2 gl, GLU glu, GLUT glut, float tAnim){
        // Draw neck below head
        gl.glPushMatrix();
        gl.glTranslated(0,0, (2.05*robotSize));
        gl.glScaled(robotSize/10,robotSize/10,robotSize/8);
        glut.glutSolidCube(1);
        gl.glPopMatrix();
        // Draw body below neck
        gl.glPushMatrix();
        gl.glTranslated(0,0, (1.8*robotSize));
        gl.glScaled(robotSize/3,robotSize/6,robotSize/2.5);
        glut.glutSolidCube(1);
        gl.glPopMatrix();

    }

    public void drawArm(GL2 gl, GLU glu, GLUT glut, float tAnim, boolean whichArm) {
        // Draw the rotation point, then draw upper arm
        gl.glPushMatrix();
        // If statement for x coordinates, so we can draw both arms
        if(whichArm == false){
            gl.glTranslated(robotSize/4.6, 0, (1.97*robotSize));
            // Rotation angle
            gl.glRotated((Math.abs(Math.sin(tAnim*robotAnimationSpeed))*60)-35, 1, 0, 0);
        }else{
            gl.glTranslated(-robotSize/4.6, 0, (1.97*robotSize));
            gl.glRotated((Math.abs(Math.cos(tAnim*robotAnimationSpeed))*60)-35, 1, 0, 0);
        }
        // Draw a rotation point for arm, which rotates according to the rotatation angle
        glut.glutSolidSphere(0.01,10,10);
        drawUpperArm(gl, glu, glut, tAnim, whichArm);
        gl.glPopMatrix();

    }

    public void drawUpperArm(GL2 gl, GLU glu, GLUT glut, float tAnim, boolean whichArm){
        gl.glPushMatrix();
        // Draw upper arm and then call to draw lower arm
        gl.glTranslated(0, 0, -robotSize/10);
        gl.glScaled(robotSize/10, robotSize/10, robotSize/4);
        glut.glutSolidCube(1);
        drawLowerArm(gl, glu, glut, tAnim, true);
        gl.glScaled(1/(robotSize/10), 1/(robotSize/10), 1/(robotSize/4));
        drawLowerArm(gl, glu, glut, tAnim, whichArm);
        gl.glPopMatrix();

    }

    public void drawLowerArm(GL2 gl, GLU glu, GLUT glut, float tAnim, boolean whichArm){
        // Place rotation point for lower arm, then draw it
        gl.glPushMatrix();
        gl.glTranslated(0, 0, -(robotSize/8));
        if(whichArm == true){
            gl.glRotated((Math.abs(Math.cos(tAnim*robotAnimationSpeed))*90)-70, 1, 0, 0);
        }else{
            gl.glRotated((Math.abs(Math.sin(tAnim*robotAnimationSpeed))*90)-70, 1, 0, 0);
        }
        glut.glutSolidSphere(robotSize/20,10,10);
        gl.glTranslated(0, 0, -(robotSize/8));
        gl.glScaled(robotSize/13, robotSize/13, robotSize/5);
        glut.glutSolidCube(1);
        gl.glPopMatrix();
    }

    public void drawLeg(GL2 gl, GLU glu, GLUT glut, float tAnim, boolean leg) {
        // Draw the rotation point for legs, then draw upper leg
        gl.glPushMatrix();
        if(leg == false){
            gl.glTranslated(robotSize/10, 0, (1.53* robotSize));
            gl.glRotated((Math.abs(Math.cos(tAnim*robotAnimationSpeed))*30)-15, 1, 0, 0);
        }else{
            gl.glTranslated(-robotSize/10, 0, (1.53* robotSize));
            gl.glRotated((Math.abs(Math.sin(tAnim*robotAnimationSpeed))*30)-15, 1, 0, 0);
        }
        glut.glutSolidSphere(robotSize/20,10,10);
        drawUpperLeg(gl, glu, glut, tAnim, leg);
        gl.glPopMatrix();

    }

    public void drawUpperLeg(GL2 gl, GLU glu, GLUT glut, float tAnim, boolean leg){
        gl.glPushMatrix();
        // Draw upper leg
        gl.glTranslated(0, 0, -(1.525* robotSize -1.46* robotSize));
        gl.glScaled(robotSize/9, robotSize/9, robotSize/3.5);
        glut.glutSolidCube(1);
        // Scale back and draw lower leg
        gl.glScaled(1/(robotSize/9), 1/(robotSize/9), 1/(robotSize/3.5));
        drawLowerLeg(gl, glu, glut, tAnim, leg);
        gl.glPopMatrix();

    }

    public void drawLowerLeg(GL2 gl, GLU glu, GLUT glut, float tAnim, boolean leg){
        gl.glPushMatrix();
        // Place rotation point, draw lower leg
        gl.glTranslated(0, 0, -(robotSize/7));
        if(leg == true){
            gl.glRotated((Math.abs(Math.sin(tAnim*robotAnimationSpeed))*60)-20, 1, 0, 0);
        }else{
            gl.glRotated((Math.abs(Math.cos(tAnim*robotAnimationSpeed))*60)-20, 1, 0, 0);
        }
        glut.glutSolidSphere(0.05,10,10);
        gl.glTranslated(0, 0, -(robotSize/9));
        gl.glScaled(robotSize/12, robotSize/12, robotSize/5);
        glut.glutSolidCube(1);
        gl.glPopMatrix();

    }
}