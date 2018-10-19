package robotrace;

import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.GL2;

class Tree {
    // Used for height and radius of tree
    double treeSize;
    // Number of slices for shapes
    int quality;
    // Coordinates for placing trees
    double randomX;
    double randomY;
    // How many cones the tree consists of
    int numberOfBodyParts;

    public Tree(double treeSize, int quality, double randomX, double randomY, int numberOfBodyParts){
        this.treeSize = treeSize;
        this.quality = quality;
        this.randomX = randomX;
        this.randomY = randomY;
        this.numberOfBodyParts = numberOfBodyParts;

    }

    public void drawTree(GL2 gl, GLUT glut) {
        // The cone dimensions
        double radius;
        double height;
        gl.glPushMatrix();
        // Place the tree in the given coordinates
        gl.glTranslated(randomX, randomY, 0.5);
        // Draw the trunk
        drawCore(gl, glut);
        // Iterate over cones number, draw each cone, set color gradient
        gl.glTranslated(0, 0, 0.2);
        for (int i = 0; i < numberOfBodyParts; i++){
            radius = treeSize / (4 + i * 2);
            height = treeSize/(numberOfBodyParts + i * 2);
            gl.glColor3f(0.06f*i,0.4f + 0.08f*i,0.1f + 0.06f*i);
            drawBodyPart(radius, height, gl, glut);
            gl.glTranslated(0, 0, height*0.6);
        }
        gl.glPopMatrix();

    }

    public void drawCore(GL2 gl, GLUT glut){
        // Draws the trunk
        gl.glPushMatrix();
        gl.glColor3d(0.6,0.2,0.2);
        gl.glScaled(treeSize/10,treeSize/10,treeSize/7);
        glut.glutSolidCube(1);
        gl.glPopMatrix();
    }

    public void drawBodyPart(double radius, double height, GL2 gl, GLUT glut){
        // Draws the cone
        gl.glPushMatrix();
        glut.glutSolidCone(radius, height, quality, quality);
        gl.glPopMatrix();

    }

}
