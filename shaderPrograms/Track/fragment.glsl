uniform sampler2D texture;
// simple fragment shader

// 'time' contains seconds since the program was linked.

varying vec3 N;
varying vec3 P;


vec4 shading()
{
	vec4 finalColor = vec4(0.,0.,0.,1.);

    vec4 ambient = gl_LightSource[0].ambient * gl_FrontMaterial.ambient;//*gl_FrontMaterial.ambient; // ambient
// diffuse
    vec3 L = normalize(gl_LightSource[0].position.xyz - P);
    vec3 E = normalize(-P); // we are in Eye Coordinates, so EyePos is (0,0,0)
    vec3 R = normalize(-reflect(L,N));
    vec4 diff = gl_FrontMaterial.diffuse*gl_LightSource[0].diffuse * max(dot(N,L), 0.);
// specular
    vec4 spec = gl_FrontMaterial.specular * gl_LightSource[0].specular
                * pow(max(dot(R,E),0.),gl_FrontMaterial.shininess);
    finalColor+= diff + spec + ambient;



	return finalColor;
}


void main()
{
    gl_FragColor = texture2D(texture, gl_TexCoord[0].st) + shading();
}