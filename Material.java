package robotrace;

/**
 * Materials that can be used for the robots.
 */
public enum Material {

    /**
     * Gold material properties.
     * Modify the default values to make it look like gold.
     */
    GOLD (

            new float[] {0.83f, 0.69f, 0.22f, 1},
            new float[] {0.83f, 0.69f, 0.22f, 1},
            30

    ),

    /**
     * Silver material properties.
     * Modify the default values to make it look like silver.
     */
    SILVER (

            new float[] {0.75f, 0.75f, 0.75f, 1},
            new float[] {0.75f, 0.75f, 0.75f, 1},
            60

    ),

    /**
     * Orange material properties.
     * Modify the default values to make it look like orange.
     */
    ORANGE (

            new float[] {1, 0.45f, 0, 1},
            new float[] {1, 0.45f, 0, 1},
            100

    ),

    /**
     * Wood material properties.
     * Modify the default values to make it look like Wood.
     */
    WOOD (

            new float[] {0.63f, 0.33f, 0.18f, 1},
            new float[] {0.63f, 0.33f, 0.18f, 1},
            100

    );

    /** The diffuse RGBA reflectance of the material. */
    float[] diffuse;

    /** The specular RGBA reflectance of the material. */
    float[] specular;

    /** The specular exponent of the material. */
    float shininess;

    /**
     * Constructs a new material with diffuse and specular properties.
     */
    private Material(float[] diffuse, float[] specular, float shininess) {
        this.diffuse = diffuse;
        this.specular = specular;
        this.shininess = shininess;
    }
}
