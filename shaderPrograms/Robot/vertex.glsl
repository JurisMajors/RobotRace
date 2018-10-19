// simple vertex shader
varying vec3 N, P, V, R;

void main() {
    // Similar to ShaderMaker
    gl_LightSourceParameters light = gl_LightSource[0];
    //gl_MaterialParameters mat = gl_FrontMaterial;
    // Normal to view space
	N = normalize(gl_NormalMatrix * gl_Normal);
	// Vertex in world coordinates
	P = normalize(vec3(gl_ModelViewMatrix * gl_Vertex));
	// Vector to light
	vec3 L = normalize(light.position.xyz - P);
	// Vector to camera
	V = -P;
	// Reflection
	R = normalize(2 * dot(N, L)*N - L);
	gl_TexCoord[0] = gl_MultiTexCoord0;
	gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;

}