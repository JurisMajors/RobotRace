// simple vertex shader
varying vec3 p;


float offset(vec3 p){
	float height;
    height = fract(sin(dot(p.xy ,vec2(12.9898, 78.233))) * 43758.5453)+0.25;
	return height;
}


void main()
{
	p = gl_Vertex.xyz/gl_Vertex.w;
	p.z += offset(p);
    gl_Position  = gl_ModelViewProjectionMatrix * vec4(p, 1);      // model view transform
	gl_FrontColor = gl_Color;
}