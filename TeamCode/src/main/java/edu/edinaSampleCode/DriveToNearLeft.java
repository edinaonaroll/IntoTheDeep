package edu.edinaSampleCode;

public class DriveToNearLeft {
    private ArenaLimitsObjects _arena;

    public DriveToNearLeft(){
        _arena = new ArenaLimitsObjects();
    }

    public int DoAction1(){
        //int local_x = ArenaLimitsProps.nearLeft.x();
        ArenaLimitsProps local_center = ArenaLimitsProps.center;
        //return local_x * 2;
        //why won't this break;
        return 1;
    }

    public int DoAbility(){
        // in reality, do the actions for the ability
        // here, as an example, just return some value to show accessing the instance of the
        // arena object
        return _arena.left.x;
    }
}
