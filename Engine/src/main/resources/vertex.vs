#version 330

layout (location =0) in vec3 position;
layout (location =1) in vec3 in_vertex_color;

out vec3 vertex_color;

void main()
{
    gl_Position = vec4(position, 1.0);
    vertex_color = in_vertex_color;
}