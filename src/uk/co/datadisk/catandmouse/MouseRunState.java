package uk.co.datadisk.catandmouse;

public class MouseRunState implements State {

    private Mouse mouse;

    public MouseRunState(Mouse mouse) {
        this.mouse = mouse;
    }

    @Override
    public void enter() {

    }

    @Override
    public void performAction() {
        mouse.run();
    }

    @Override
    public void exit() {

    }
}
