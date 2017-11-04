package uk.co.datadisk.catandmouse;

public interface State {
    public void enter();
    public void performAction();
    public void exit();
}
