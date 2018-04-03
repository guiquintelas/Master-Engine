/* Fragment shader */

uniform sampler2D tex0;
uniform sampler2D luz;
uniform float raio;
uniform vec2 centro;
uniform vec4 corLuz;

void main(){
  vec2 loc = gl_TexCoord[0].st; 
  vec4 color = texture2D(tex0,loc);
  
  vec2 pos = gl_FragCoord.xy;
  
  float dist = distance(centro, pos);
  
  if (dist > raio) {
  	color = vec4(0.0, 0.0, 0.0, 0.0);
  } else {
	  color.a = 1.0;
	  color.r = (corLuz.r / raio) * (raio - dist) * corLuz.a + color.r;
	  color.g = (corLuz.g / raio) * (raio - dist) * corLuz.a + color.g;
	  color.b = (corLuz.b / raio) * (raio - dist) * corLuz.a + color.b;
  
  }
  
  
  
  

  gl_FragColor = color; 
}
