import java.util.List;
import java.util.function.Consumer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;


public class Model {

    private Mesh mesh;

    private Material material;

    public Model(Mesh mesh, Material material) {
        this.mesh = mesh;
        this.material = material;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Mesh getMesh() {
        return mesh;
    }

    private void initRender() {
        Texture texture = material.getTexture();
        if (texture != null) {
            // Activate firs texture bank
            glActiveTexture(GL_TEXTURE0);
            // Bind the texture
            glBindTexture(GL_TEXTURE_2D, texture.getId());
        }

        // Draw the mesh
        mesh.initRender();
    }

    private void endRender() {
        // Restore state
        mesh.endRender();

        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public void render() {
        initRender();

        glDrawElements(GL_TRIANGLES, mesh.getVertexCount(), GL_UNSIGNED_INT, 0);

        endRender();
    }

    public void renderList(List<DrawableItem> drawableItems, Consumer<DrawableItem> consumer) {
        initRender();

        for (DrawableItem drawableItem : drawableItems) {
            // Set up data requiered by drawableItem
            consumer.accept(drawableItem);
            // Render this game items
            glDrawElements(GL_TRIANGLES, mesh.getVertexCount(), GL_UNSIGNED_INT, 0);
        }

        endRender();
    }

}
