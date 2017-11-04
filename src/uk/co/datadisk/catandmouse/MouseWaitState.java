package uk.co.datadisk.catandmouse;

public class MouseWaitState implements State {

    private Mouse mouse;

    public MouseWaitState(Mouse mouse) {
        this.mouse = mouse;
    }

    @Override
    public void enter() {
        mouse.stop();
        mouse.moveIntoCell();
        if(mouse.foundCheese()){
            mouse.eatCheese();
        }
    }

    @Override
    public void performAction() {

    }

    @Override
    public void exit() {

    }
}
