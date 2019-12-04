#version 330

layout (location =0) in vec3 position;
layout (location =1) in vec3 in_vertex_color;

out vec3 vertex_color;

uniform mat4 projectionMatrix;
uniform mat4 modelViewMatrix;

void main()
{
    gl_Position = projectionMatrix * modelViewMatrix * vec4(position, 1.0);
    vertex_color = in_vertex_color;
}