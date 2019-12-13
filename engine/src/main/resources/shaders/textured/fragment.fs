#version 330

in vec2 outTexCoords;
in vec3 outNormals;

out vec4 fragColor;

uniform sampler2D texture_sampler;
uniform vec2 texture_repeat;

void main()
{
    fragColor = texture(texture_sampler, outTexCoords * texture_repeat);
}