varying vec3 N;
varying vec3 P;

void main()
{
	P = vec3(gl_ModelViewMatrix * gl_Vertex);
   	N = normalize(gl_NormalMatrix * gl_Normal);
	gl_Position    = gl_ModelViewProjectionMatrix * gl_Vertex;
	gl_FrontColor  = gl_Color;
	gl_TexCoord[0] = gl_MultiTexCoord0;
}