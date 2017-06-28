package com.arun.student;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SchoolService {

    public List<String> getSchoolNames() {
        simulateOneSecondDelay();
        return Arrays.asList("Sunnyvale Public School", "Cupertino High School");
    }

    private void simulateOneSecondDelay() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
