/* Fragment shader */

uniform sampler2D tex0;
uniform sampler2D luz;

uniform float offset;


void main(){
  vec2 loc = gl_TexCoord[0].st;
  
  //loc.x = loc.x + cos(loc.y + offset)/2.0;
  
  
  vec4 color = texture2D(tex0,loc.xy);
  vec4 colorMap = texture2D(luz,loc.xy);
  
  color.r = color.r * colorMap.r;
  color.g = color.g * colorMap.g;
  color.b = color.b * colorMap.b;
  
  

  gl_FragColor = color; 
}
