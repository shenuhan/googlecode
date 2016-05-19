#ifdef GL_ES
precision highp float; 
#endif

attribute vec3 a_position;
attribute vec2 a_texCoords;
attribute vec4 a_color;

uniform mat4 u_projTrans;
uniform mat4 u_lightProjTrans;

varying vec4 v_color;
varying vec4 v_lightSpacePosition;
varying vec2 v_texCoords;

void main(void) 
{
	v_color = a_color;
	v_texCoords = a_texCoords;
	gl_Position = u_projTrans * vec4(a_position,1.0) ;
	v_lightSpacePosition  = u_lightProjTrans * vec4(a_position,1.0) ;
}