import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Engine implements Runnable {

    public static final int TARGET_FPS = 75;

    public static final int TARGET_UPS = 30;

    private final Window window;

    private final Thread gameLoopThread;

    private final Timer timer;

    private final MouseInput mouseInput;

    private Scene scene;

    private final Renderer renderer;

    private final Camera camera;

    private final Vector3f cameraInc;

    private static final float CAMERA_POS_STEP = 0.1f;
    private static final float Z_STEP = 0.1f;
    private static final float MOUSE_SENSITIVITY = 0.1f;

    private static float height;
    private static float width;

    public Engine(String windowTitle, int width, int height, boolean vSync) throws Exception {
        gameLoopThread = new Thread(this, "GAME_LOOP_THREAD");
        window = new Window(windowTitle, width, height, vSync);
        mouseInput = new MouseInput();
        timer = new Timer();
        renderer = new Renderer();
        camera = new Camera();
        cameraInc = new Vector3f();
    }

    public void start() {
        String osName = System.getProperty("os.name");
        if ( osName.contains("Mac") ) {
            gameLoopThread.run();
        } else {
            gameLoopThread.start();
        }
    }

    @Override
    public void run() {
        try {

            init();
            loop();
        } catch (Exception excp) {
            excp.printStackTrace();
        } finally {
            cleanup();
        }
    }

    protected void init() throws Exception {
        window.init();
        timer.init();
        renderer.init(window);
        scene = new Scene(window);
        mouseInput.init(window);
        width = window.getWidth();
        height = window.getHeight();
        setupLights();
        camera.getPosition().x = 5f;
        camera.getPosition().z = 10f;
        camera.getPosition().y = -15f;
        camera.getRotation().x = 315;
        camera.getRotation().y = 0;
        camera.getRotation().z = 0;
    }

    protected void loop() throws Exception {
        float elapsedTime;
        float accumulator = 0f;
        float interval = 1f / TARGET_UPS;

        boolean running = true;
        int ups = 0;
        int frames = 0;
        double lastTime = timer.getTime();
        while (running && !window.windowShouldClose()) {
            elapsedTime = timer.getElapsedTime();
            accumulator += elapsedTime;
            input();
            while (accumulator >= interval) {
                update(interval);
                accumulator -= interval;
                ups++;
            }
            render();

            if (!window.isvSync()) {
                sync();
            }

            frames++;
            if ((timer.getTime() - lastTime) > 1) {
                lastTime += 1;
                System.out.println(ups + " ups, " + frames + " fps");
                ups = 0;
                frames = 0;
            }
        }
    }

    protected void cleanup() {
        System.out.println("cleaned");
        window.cleanup();
        scene.cleanup();
        renderer.cleanup();
    }

    private void sync() {
        float loopSlot = 1f / TARGET_FPS;
        double endTime = timer.getLastLoopTime() + loopSlot;
        while (timer.getTime() < endTime) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ie) {
            }
        }
    }

    protected void input() throws Exception {
        mouseInput.input(window);
        cameraInc.set(0, 0, 0);
        if (window.isKeyPressed(GLFW_KEY_W)) {
            cameraInc.y = 1;
        } else if (window.isKeyPressed(GLFW_KEY_S)) {
            cameraInc.y = -1;
        }
        if (window.isKeyPressed(GLFW_KEY_A)) {
            cameraInc.x = -1;
        } else if (window.isKeyPressed(GLFW_KEY_D)) {
            cameraInc.x = 1;
        }
        if (window.isKeyPressed(GLFW_KEY_Z)) {
            cameraInc.z = -1;
        } else if (window.isKeyPressed(GLFW_KEY_X)) {
            cameraInc.z = 1;
        }

        if (window.isKeyPressed(GLFW_KEY_1)){
            Vector3f pos = camera.getPosition();
            pos.x = 5;
            pos.y = -15;
            pos.z = 10;
            Vector3f rot = camera.getRotation();
            rot.x = 315;
            rot.y = 0;
            rot.z = 0;
        }

        if (window.isKeyPressed(GLFW_KEY_2)){
            Vector3f pos = camera.getPosition();
            pos.x = 0;
            pos.y = 0;
            pos.z = 10;
            Vector3f rot = camera.getRotation();
            rot.x = 0;
            rot.y = 0;
            rot.z = 0;
        }

        if (window.isKeyPressed(GLFW_KEY_3)){
            Vector3f pos = camera.getPosition();
            pos.x = 0;
            pos.y = -10;
            pos.z = 0;
            Vector3f rot = camera.getRotation();
            rot.x = 270;
            rot.y = 0;
            rot.z = 0;
        }

        if (window.isKeyPressed(GLFW_KEY_I)){
            window.setFOV((float) (window.getFOV() + Math.toRadians(1)));
        }
        if (window.isKeyPressed(GLFW_KEY_O)){
            window.setFOV((float) (window.getFOV() - Math.toRadians(1)));
        }

        if (window.isKeyPressed(GLFW_KEY_K)){
            window.setZ_FAR(window.getZ_FAR() + 1);
        }
        if (window.isKeyPressed(GLFW_KEY_L)){
            window.setZ_FAR(window.getZ_FAR() - 1);
        }

        if (window.isKeyPressed(GLFW_KEY_N)){
            window.setZ_NEAR(window.getZ_NEAR() + 1);
        }
        if (window.isKeyPressed(GLFW_KEY_M)){
            window.setZ_NEAR(window.getZ_NEAR() - 1);
        }


    }

    protected void update(float interval) {
        camera.movePosition(cameraInc.x * CAMERA_POS_STEP,
                cameraInc.y * CAMERA_POS_STEP , cameraInc.z * Z_STEP);
        if (mouseInput.isRightPressed()) {
            Vector2f rotVec = mouseInput.getDisplVecRight();
            camera.moveRotation(rotVec.x * MOUSE_SENSITIVITY, 0, rotVec.y * MOUSE_SENSITIVITY);
        }
        /*if (mouseInput.isLeftPressed()) {
            Vector2f rotVec = mouseInput.getDisplVecLeft();
            camera.movePosition(-rotVec.y * camera.getPosition().z/width,rotVec.x * camera.getPosition().z /height, 0);
        }*/
        camera.updateViewMatrix();
    }

    protected void render() {
        window.update();
        renderer.render(window, camera, scene);
    }


    private void setupLights() {
        SceneLight sceneLight = new SceneLight();
        scene.setSceneLight(sceneLight);

        // Ambient Light
        sceneLight.setAmbientLight(new Vector3f(0.3f, 0.3f, 0.3f));

        // Directional Light
        float lightIntensity = 1.5f;
        Vector3f lightPosition = new Vector3f(0f, 0f, -1f);
        sceneLight.setDirectionalLight(new DirectionalLight(new Vector3f(1, 1, 1), lightPosition, lightIntensity));

        PointLight pointLight = new PointLight(new Vector3f(0.3f, 0.3f, 0.3f), new Vector3f(50, 50, 50), 5f);
        PointLight[] pointLights = new PointLight[]{pointLight};
        sceneLight.setPointLightList(pointLights);
    }
}