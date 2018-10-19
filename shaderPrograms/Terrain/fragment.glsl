varying vec3 p;
void main()
{
    vec4 c = vec4(vec3(0.), 1);
    if(p.z <= 0){
        c += vec4(0, 0, 255, 0);
    } else if(p.z < 0.5) {
        c += vec4(1, 1, 0 , 0);
    } else{
        c += vec4(0, 255,0, 0);
    }

    gl_FragColor = c;
}
