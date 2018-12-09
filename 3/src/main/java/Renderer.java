import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11C.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11C.glClear;
import static org.lwjgl.opengl.GL11C.glViewport;

public class Renderer {

    /**
     * Field of View in Radians
     */
    private float FOV = (float) Math.toRadians(60.0f);

    private float Z_NEAR = 0.01f;

    private float Z_FAR = 1000.f;

    private static final int MAX_POINT_LIGHTS = 5;

    private static final int MAX_SPOT_LIGHTS = 5;

    private final Transformation transformation;

    private ShaderProgram sceneShaderProgram;
    private ShaderProgram projModelShaderProgram;

    private final float specularPower;

    private boolean projModel;

    public Renderer() {
        transformation = new Transformation();
        specularPower = 10f;
        projModel = false;
    }

    public void init(Window window) throws Exception {
        setupSceneShader();
        setupProjModelShader();
    }

    private void setupSceneShader() throws Exception {
        // Create shader
        sceneShaderProgram = new ShaderProgram();
        sceneShaderProgram.createVertexShader(Utils.loadResource("/Shaders/vertex.vs"));
        sceneShaderProgram.createFragmentShader(Utils.loadResource("/Shaders/fragment.fs"));
        sceneShaderProgram.link();

        // Create uniforms for modelView and projection matrices and texture
        sceneShaderProgram.createUniform("projectionMatrix");
        sceneShaderProgram.createUniform("modelViewMatrix");
        sceneShaderProgram.createUniform("texture_sampler");
        // Create uniform for material
        sceneShaderProgram.createMaterialUniform("material");
        // Create lighting related uniforms
        sceneShaderProgram.createUniform("specularPower");
        sceneShaderProgram.createUniform("ambientLight");
        sceneShaderProgram.createPointLightListUniform("pointLights", MAX_POINT_LIGHTS);
        sceneShaderProgram.createSpotLightListUniform("spotLights", MAX_SPOT_LIGHTS);
        sceneShaderProgram.createDirectionalLightUniform("directionalLight");
        sceneShaderProgram.createUniform("selected");
    }

    private void setupProjModelShader() throws Exception {
        projModelShaderProgram = new ShaderProgram();
        projModelShaderProgram.createVertexShader(Utils.loadResource("/Shaders/projModelVertex.vs"));
        projModelShaderProgram.createFragmentShader(Utils.loadResource("/Shaders/projModelFragment.fs"));
        projModelShaderProgram.link();

        projModelShaderProgram.createUniform("projModelMatrix");
        projModelShaderProgram.createUniform("colour");
        projModelShaderProgram.createUniform("hasTexture");
        projModelShaderProgram.createUniform("selected");
    }


    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void render(Window window, Camera camera, Scene scene) {
        clear();

        if (window.isResized()) {
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(false);
        }

        // Update projection and view matrices once per render cycle
        //transformation.updateProjectionMatrix(FOV, window.getWidth(), window.getHeight(), Z_NEAR, Z_FAR);
        window.updateProjectionMatrix();
        //transformation.updateViewMatrix(camera);

        if (projModel) renderProjModelScene(window, camera, scene);
        else renderScene(window, camera, scene);

        //renderSkyBox(window, camera, scene);

    }


    public void renderScene(Window window, Camera camera, Scene scene) {
        sceneShaderProgram.bind();

        Matrix4f projectionMatrix = window.getProjectionMatrix();//transformation.getProjectionMatrix();
        sceneShaderProgram.setUniform("projectionMatrix", projectionMatrix);

        Matrix4f viewMatrix = camera.getViewMatrix();//transformation.getViewMatrix();

        SceneLight sceneLight = scene.getSceneLight();
        renderLights(viewMatrix, sceneLight);

        sceneShaderProgram.setUniform("texture_sampler", 0);
        // Render each mesh with the associated game Items

        for(DrawableItem item: scene.getItems()) {
            Model model = item.getModel();
            sceneShaderProgram.setUniform("material", model.getMaterial());
            Matrix4f modelViewMatrix = transformation.buildModelViewMatrix(item, viewMatrix);
            sceneShaderProgram.setUniform("modelViewMatrix", modelViewMatrix);
            model.render();

        }
        sceneShaderProgram.unbind();
    }

    public void renderProjModelScene(Window window, Camera camera, Scene scene){
        projModelShaderProgram.bind();

        Matrix4f ortho = transformation.getOrthoProjectionMatrix(0, window.getWidth(), window.getHeight(), 0);

        for(DrawableItem item: scene.getItems()) {
            Model model = item.getModel();
            Matrix4f projModelMatrix = transformation.buildOrtoProjModelMatrix(item, ortho);
            projModelShaderProgram.setUniform("projModelMatrix", projModelMatrix);
            projModelShaderProgram.setUniform("colour", model.getMaterial().getAmbientColour());
            projModelShaderProgram.setUniform("hasTexture", model.getMaterial().isTextured() ? 1 : 0);
            projModelShaderProgram.setUniform("selected", item.isSelected() ? 1.0f : 0.0f);

        }

    }

    private void renderLights(Matrix4f viewMatrix, SceneLight sceneLight) {

        sceneShaderProgram.setUniform("ambientLight", sceneLight.getAmbientLight());
        sceneShaderProgram.setUniform("specularPower", specularPower);

        // Process Point Lights
        PointLight[] pointLightList = sceneLight.getPointLightList();
        int numLights = pointLightList != null ? pointLightList.length : 0;
        for (int i = 0; i < numLights; i++) {
            // Get a copy of the point light object and transform its position to view coordinates
            PointLight currPointLight = new PointLight(pointLightList[i]);
            Vector3f lightPos = currPointLight.getPosition();
            Vector4f aux = new Vector4f(lightPos, 1);
            aux.mul(viewMatrix);
            lightPos.x = aux.x;
            lightPos.y = aux.y;
            lightPos.z = aux.z;
            sceneShaderProgram.setUniform("pointLights", currPointLight, i);
        }

        // Process Spot Ligths
        SpotLight[] spotLightList = sceneLight.getSpotLightList();
        numLights = spotLightList != null ? spotLightList.length : 0;
        for (int i = 0; i < numLights; i++) {
            // Get a copy of the spot light object and transform its position and cone direction to view coordinates
            SpotLight currSpotLight = new SpotLight(spotLightList[i]);
            Vector4f dir = new Vector4f(currSpotLight.getConeDirection(), 0);
            dir.mul(viewMatrix);
            currSpotLight.setConeDirection(new Vector3f(dir.x, dir.y, dir.z));

            Vector3f lightPos = currSpotLight.getPointLight().getPosition();
            Vector4f aux = new Vector4f(lightPos, 1);
            aux.mul(viewMatrix);
            lightPos.x = aux.x;
            lightPos.y = aux.y;
            lightPos.z = aux.z;

            sceneShaderProgram.setUniform("spotLights", currSpotLight, i);
        }

        // Get a copy of the directional light object and transform its position to view coordinates
        if (sceneLight.getDirectionalLight() == null) return;
        DirectionalLight currDirLight = new DirectionalLight(sceneLight.getDirectionalLight());
        Vector4f dir = new Vector4f(currDirLight.getDirection(), 0);
        dir.mul(viewMatrix);
        currDirLight.setDirection(new Vector3f(dir.x, dir.y, dir.z));
        sceneShaderProgram.setUniform("directionalLight", currDirLight);
    }

    public void cleanup() {
        if (sceneShaderProgram != null) {
            sceneShaderProgram.cleanup();
        }
        if (projModelShaderProgram != null){
            projModelShaderProgram.cleanup();
        }
    }

    public void setProjModel(boolean projModel) {
        this.projModel = projModel;
    }

    public boolean isProjModel() {
        return projModel;
    }


}