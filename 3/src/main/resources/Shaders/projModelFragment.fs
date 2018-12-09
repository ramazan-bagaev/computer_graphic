#version 330

in vec2 outTexCoord;
in vec3 mvPos;
in float outSelected;
out vec4 fragColor;

uniform sampler2D texture_sampler;
uniform vec4 colour;
uniform int hasTexture;

void main()
{
    if ( hasTexture == 1 )
    {
        fragColor = colour * texture(texture_sampler, outTexCoord);
    }
    else
    {
        fragColor = colour;
    }
    if (outSelected > 0){
        fragColor = vec4(fragColor.x - fragColor.x*fragColor.x, fragColor.y - fragColor.y*fragColor.y,
        fragColor.z - fragColor.z*fragColor.z, 1);
    }
}