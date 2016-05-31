package com.katya.ring;

/**
 * Created by Work on 23.06.2015.
 */
public class Setting {
    private int speedGrowthRing = 200;
    private static Setting ourInstance = new Setting();

    public static Setting getInstance() {
        return ourInstance;
    }

    private Setting() {
    }

    public int getSpeedGrowthRing() {
        return speedGrowthRing;
    }

    public void setSpeedGrowthRing(int speedGrowthRing) {
        if (speedGrowthRing!=0){
            this.speedGrowthRing = speedGrowthRing;
        } else {
            this.speedGrowthRing = 1;
        }

    }
}
