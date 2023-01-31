package GUIProject;

public class GUIThread implements Runnable {
    public GUIThread()
    {
        Thread myThread = new Thread(this);
        myThread.start();
    }
    public void run()
    {
        LoginFrame frame = new LoginFrame();
            frame.setVisible(true);
    }
}
