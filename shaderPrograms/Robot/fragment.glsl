varying vec3 P, N, V, R;
uniform sampler2D texture;

vec4 materialShade(vec3 P, vec3 N, gl_LightProducts light, gl_MaterialParameters mat){
    vec4 result = vec4(0,0,0,1);
    // Set ambience
	result += gl_LightSource[0].ambient;
	vec3 L = normalize(gl_LightSource[0].position.xyz - P);
	// Set diffusion
	result +=  mat.diffuse * gl_LightSource[0].diffuse * max(0.0, dot(N, L));
	// Set shinniness
	result += gl_LightSource[0].specular * pow(max(0.0, dot(R, V)), mat.shininess);
    return result;
}

void main()
{
	gl_LightProducts light = gl_FrontLightProduct[0];
	gl_MaterialParameters mat = gl_FrontMaterial;
	gl_FragColor = materialShade(P, N, light, mat);}
