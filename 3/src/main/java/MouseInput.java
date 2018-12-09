import org.joml.Vector2d;
import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.*;

public class MouseInput{

    private final Vector2d previousPosRight;
    private final Vector2d previousPosLeft;

    private final Vector2d currentPos;

    private boolean clicked = false;

    private boolean inWindow = false;

    private boolean leftPressed = false;

    private boolean rightPressed = false;

    private boolean leftPressedPrev = false;

    private boolean rightPressedPrev = false;

    private boolean leftAction = false;

    private boolean rightAction = false;

    private Vector2f displVecRight;
    private Vector2f displVecLeft;

    private float scrollDelta;

    public MouseInput() {
        previousPosRight = new Vector2d(-1, -1);
        previousPosLeft = new Vector2d(-1, -1);
        currentPos = new Vector2d(0, 0);
        displVecRight = new Vector2f();
        displVecLeft = new Vector2f();
        scrollDelta = 0;
    }


    public void init(Window window) {
        glfwSetCursorPosCallback(window.getWindowHandle(), (windowHandle, xpos, ypos) -> {
            currentPos.x = xpos;
            currentPos.y = ypos;
        });
        glfwSetCursorEnterCallback(window.getWindowHandle(), (windowHandle, entered) -> {
            inWindow = entered;
        });
        glfwSetMouseButtonCallback(window.getWindowHandle(), (windowHandle, button, action, mode) -> {
            leftPressed = button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS;
            rightPressed = button == GLFW_MOUSE_BUTTON_2 && action == GLFW_PRESS;
        });
        glfwSetScrollCallback(window.getWindowHandle(), (windowHandle, xoffset, yoffset) -> {
            scrollDelta = (float) yoffset;
        });
    }

    public void input(Window window) {
        if (leftPressedPrev && !leftPressed) leftAction = true;
        else leftAction = false;
        if (rightPressedPrev && !rightPressed) rightAction = true;
        else rightAction = false;
        leftPressedPrev = leftPressed;
        rightPressedPrev = rightPressed;

        displVecRight.x = 0;
        displVecRight.y = 0;
        if (previousPosRight.x > 0 && previousPosRight.y > 0 && inWindow) {
            double deltax = currentPos.x - previousPosRight.x;
            double deltay = currentPos.y - previousPosRight.y;
            boolean rotateX = deltax != 0;
            boolean rotateY = deltay != 0;
            if (rotateX) {
                displVecRight.y = (float) deltax;
            }
            if (rotateY) {
                displVecRight.x = (float) deltay;
            }
        }
        previousPosRight.x = currentPos.x;
        previousPosRight.y = currentPos.y;

        displVecLeft.x = 0;
        displVecLeft.y = 0;
        if (previousPosLeft.x > 0 && previousPosLeft.y > 0 && inWindow) {
            double deltax = currentPos.x - previousPosLeft.x;
            double deltay = currentPos.y - previousPosLeft.y;
            boolean rotateX = deltax != 0;
            boolean rotateY = deltay != 0;
            if (rotateX) {
                displVecLeft.y = (float) deltax;
            }
            if (rotateY) {
                displVecLeft.x = (float) deltay;
            }
        }
        previousPosLeft.x = currentPos.x;
        previousPosLeft.y = currentPos.y;
    }

    public Vector2d getCurrentPos() {
        return currentPos;
    }

    public boolean leftButtonAction(){
        return leftAction;
    }

    public boolean rightButtonAction(){
        return rightAction;
    }

    public boolean isRightPressed(){
        return rightPressed;
    }

    public boolean isLeftPressed(){
        return leftPressed;
    }

    public Vector2f getDisplVecRight() {
        return displVecRight;
    }

    public Vector2f getDisplVecLeft() {
        return displVecLeft;
    }

    public float getScrollDelta() {
        return scrollDelta;
    }

    public void setScrollDelta(float delta){
        this.scrollDelta = delta;
    }
}