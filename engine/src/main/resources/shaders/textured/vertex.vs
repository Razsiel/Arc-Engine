#version 330

layout (location =0) in vec3 position;
layout (location =1) in vec2 texCoords;
layout (location =2) in vec3 normals;

out vec3 outPosition;
out vec3 outNormal;
out vec2 outTextureCoordinate;

uniform mat4 projectionMatrix;
uniform mat4 modelViewMatrix;

void main()
{
    vec4 vertexPos = modelViewMatrix * vec4(position, 1.0);
    gl_Position = projectionMatrix * vertexPos;
    outTextureCoordinate = texCoords;
    outNormal = normalize(modelViewMatrix * vec4(normals, 0.0)).xyz;
    outPosition = vertexPos.xyz;
}