package edu.edina.subsystems;

import static java.lang.Thread.sleep;

public class SubsystemBase {
    public void sleep(int milliseconds) {
        try {
            java.lang.Thread.sleep(milliseconds);
        }
        catch (InterruptedException e) {
        }
    }
}