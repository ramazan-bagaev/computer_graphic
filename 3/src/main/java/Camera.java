import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera{

    private final Vector3f position;

    private final Vector3f rotation;

    private Matrix4f viewMatrix;

    public Camera() {
        position = new Vector3f(0, 0, 0);
        rotation = new Vector3f(0, 0, 0);
        viewMatrix = new Matrix4f();
    }

    public Camera(Vector3f position, Vector3f rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(float x, float y, float z) {
        position.x = x;
        position.y = y;
        position.z = z;
    }

    public void movePosition(float offsetX, float offsetY, float offsetZ) {
        if ( offsetY != 0 ) {
            //position.x += (float)Math.sin(Math.toRadians(rotation.z)) * -1.0f * offsetY;
            //position.y += (float)Math.cos(Math.toRadians(rotation.z)) * offsetY;
            position.x += (float)Math.sin(Math.toRadians(rotation.z)) * offsetY;
            position.y += (float)Math.cos(Math.toRadians(rotation.z)) * offsetY;
        }
        if ( offsetX != 0) {
            //position.x += (float)Math.sin(Math.toRadians(rotation.z - 90)) * -1.0f * offsetX;
            //position.y += (float)Math.cos(Math.toRadians(rotation.z - 90)) * offsetX;
            position.x += (float)Math.cos(Math.toRadians(rotation.z)) * offsetX;
            position.y += (float)Math.sin(Math.toRadians(rotation.z)) * -1.0f * offsetX;
        }
        position.z += offsetZ;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(float x, float y, float z) {
        rotation.x = x;
        rotation.y = y;
        rotation.z = z;
    }

    public void moveRotation(float offsetX, float offsetY, float offsetZ) {
        rotation.x += offsetX;
        rotation.y += offsetY;
        rotation.z += offsetZ;
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    public Matrix4f updateViewMatrix() {
        return Transformation.updateGenericViewMatrix(position, rotation, viewMatrix);
    }

}