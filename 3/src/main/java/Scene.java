import org.joml.Vector3f;

import java.util.ArrayList;

public class Scene {

    private Window window;

    private SceneLight sceneLight;

    private ArrayList<DrawableItem> items;

    public Scene(Window window) throws Exception {
        this.window = window;
        init();
    }

    private void init() throws Exception {
        items = new ArrayList<>();
        items.add(new DrawableItem(new Model(OBJLoader.loadMesh("test.obj"), new Material(new Texture("test.png"))),
                new Vector3f(0, 0, 0), new Vector3f(10, 10, 0.1f)));
        items.add(new DrawableItem(new Model(OBJLoader.loadMesh("test.obj"), new Material(new Texture("test1.png"))),
                new Vector3f(0, 0, 0.6f)));
        items.add(new DrawableItem(new Model(OBJLoader.loadMesh("test.obj"), new Material(new Texture("test1.png"))),
                new Vector3f(2, 0, 0.6f)));
        items.add(new DrawableItem(new Model(OBJLoader.loadMesh("test.obj"), new Material(new Texture("test1.png"))),
                new Vector3f(0, 2, 0.6f)));
        items.add(new DrawableItem(new Model(OBJLoader.loadMesh("test.obj"), new Material(new Texture("test1.png"))),
                new Vector3f(4, 0, 0.6f)));
    }

    public void setGameItems(ArrayList<? extends DrawableItem> drawableItems) {
        for (DrawableItem item: drawableItems) {
            addItem(item);
        }
    }

    public void addItem(DrawableItem item){
        items.add(item);
    }

    public void cleanup() {
        for(DrawableItem item: items){
            //item.getModel().getMaterial().getTexture().cleanup();
            item.getModel().getMesh().cleanUp();
        }
    }

    public SceneLight getSceneLight() {
        return sceneLight;
    }

    public void setSceneLight(SceneLight sceneLight) {
        this.sceneLight = sceneLight;
    }


    public Window getWindow() {
        return window;
    }

    public ArrayList<DrawableItem> getItems() {
        return items;
    }
}