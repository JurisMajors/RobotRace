package robotrace;
import static java.lang.Math.*;

/**
 * Implementation of a camera with a position and orientation.
 */
class Camera {

    /** The position of the camera. */
    public Vector eye = new Vector(3f, 6f, 5f);

    /** The point to which the camera is looking. */
    public Vector center = Vector.O;

    /** The up vector. */
    public Vector up = Vector.Z;

    /**
     * Updates the camera viewpoint and direction based on the
     * selected camera mode.
     */
    public void update(GlobalState gs, Robot focus) {

        switch (gs.camMode) {

            // First person mode
            case 1:
                setFirstPersonMode(gs, focus);
                break;

            // Default mode
            default:
                setDefaultMode(gs);
        }
    }

    /**
     * Computes eye, center, and up, based on the camera's default mode.
     */
    private void setDefaultMode(GlobalState gs) {
        double x,y,z; // for eye
        center = gs.cnt; // set center
        // the vector that we will rotate back to the eye space with the theta and phi angles
        x = cos(gs.phi) * cos(-gs.theta) * gs.vDist; // project to XY plane(cos(phi)) -> project to X plane(cos(-theta))
        y = cos(gs.phi) * sin(-gs.theta) * gs.vDist; // project to XY plane(cos(phi)) -> project to Y plane
        z = sin(gs.phi) * gs.vDist; // project to Z plane
        up = Vector.Z;
        eye = new Vector(x,y,z);

    }

    /**
     * Computes eye, center, and up, based on the first person mode.
     * The camera should view from the perspective of the robot.
     */
    private void setFirstPersonMode(GlobalState gs, Robot focus) {
        // Point camera the same way as tangent
        center = focus.position.add(focus.direction);
        // Add the point which the camera is looking at to the position of camera
        eye = focus.position.add(Vector.O);
        up = Vector.Z;
        // Move camera to head (otherwise we are at feet)
        double headLocation = 1.1;
        eye.z += headLocation;
        // Also add the same value to the focus point, otherwise camera is looking down
        center.z += headLocation;

    }
}