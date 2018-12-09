import org.joml.Vector3f;

public class DrawableItem {

    private Model model;

    private boolean visible;

    private boolean selected;

    private Vector3f pos;
    private Vector3f rotation;
    private Vector3f scale;

    public DrawableItem(Model model) {
        this.model = model;
        visible = true;
        selected = false;
        pos = new Vector3f();
        scale = new Vector3f(10, 10, 0.1f);
        rotation = new Vector3f();
    }

    public DrawableItem(Model model, Vector3f pos){
        this.model = model;
        visible = true;
        selected = false;
        this.pos = pos;
        scale = new Vector3f(1, 1, 1);
        rotation = new Vector3f();
    }

    public DrawableItem(Model model, Vector3f pos, Vector3f scale){
        this.model = model;
        visible = true;
        selected = false;
        this.pos = pos;
        this.scale = scale;
        rotation = new Vector3f();
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Vector3f getPosition() {
        return pos;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public Vector3f getScale() {
        return scale;
    }
}