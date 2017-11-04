package uk.co.datadisk.catandmouse;

public class CatWanderState implements State {
    private Cat cat;

    public CatWanderState(Cat cat) {
        this.cat = cat;
    }

    @Override
    public void enter() {
        cat.setSpeed(Cat.SPEED_WANDER);
    }

    @Override
    public void performAction() {
        cat.followPath();
    }

    @Override
    public void exit() {
        cat.moveIntoCell();
    }
}
