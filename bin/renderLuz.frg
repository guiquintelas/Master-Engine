

uniform sampler2D tex0;
uniform sampler2D luz;



void main(){
  vec2 loc = gl_TexCoord[0].st;
  
  vec4 color = texture2D(tex0,loc.xy);
  vec4 colorMap = texture2D(luz,loc.xy);
  
  color.a = 1.0;
  color.r = color.r * colorMap.r;
  color.g = color.g * colorMap.g;
  color.b = color.b * colorMap.b;
  
  gl_FragColor = color; 
}
