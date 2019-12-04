#version 330

in vec3 vertex_color;

out vec4 fragColor;

void main()
{
    fragColor = vec4(vertex_color, 1.0);
}