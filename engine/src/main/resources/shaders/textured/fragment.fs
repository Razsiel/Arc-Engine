#version 330

in vec2 outTextureCoordinate;
in vec3 outNormal;
in vec3 outPosition;

out vec4 fragColor;

struct DirectionalLight
{
    vec3 color;
    vec3 direction;
    float intensity;
};

uniform sampler2D texture_sampler;
uniform sampler2D shadow;
uniform vec2 texture_repeat;
uniform DirectionalLight directional_light;
uniform vec4 color;

vec4 ambient;
vec4 diffuse;
vec4 specular;

vec4 calcLightColor(vec3 light_color, float light_intensity, vec3 position, vec3 to_light_dir, vec3 normal)
{
    vec4 diffuseColor = vec4(0, 0, 0, 0);
    vec4 specularColor = vec4(0, 0, 0, 0);

    // DIFFUSE COMPONENT
    float diffuseFactor = max(dot(normal, to_light_dir), 0.0);
    diffuseColor = diffuse * vec4(light_color, 1.0) * light_intensity * diffuseFactor;

    // SPECULAR COMPONENT
    vec3 camera_direction = normalize(-position);
    vec3 from_light_dir = -to_light_dir;
    vec3 reflected_light = normalize(reflect(from_light_dir, normal));
    float specularFactor = max(dot(camera_direction, reflected_light), 0.0);
    specularFactor = pow(specularFactor, 10);
    float reflectance = 1.25;
    specularColor = specular * light_intensity * specularFactor * reflectance * vec4(light_color, 1.0);

    return (diffuseColor + specularColor);
}

vec4 calcDirectionalLight(DirectionalLight directional, vec3 position, vec3 normal)
{
    return calcLightColor(directional.color, directional.intensity, position, normalize(directional.direction), normal);
}

void main()
{
    diffuse = vec4(1, 1, 1, 0);
    specular = vec4(1, 1, 1, 1);
    ambient = texture(texture_sampler, outTextureCoordinate * texture_repeat) * color;

    vec4 diffuseSpecular = calcDirectionalLight(directional_light, outPosition, outNormal);

    float shade = texture(shadow, outTextureCoordinate).x;
    fragColor = ambient + diffuseSpecular + shade;
//     fragColor = fragColor - fragColor + vec4(shade);
}