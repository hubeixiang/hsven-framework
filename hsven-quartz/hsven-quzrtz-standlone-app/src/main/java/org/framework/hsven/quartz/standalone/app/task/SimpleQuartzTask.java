package org.framework.hsven.quartz.standalone.app.task;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class SimpleQuartzTask {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void cronTask() {
        System.out.println("conTask at " + dateFormat.format(new Date()));
    }

    public void simpleTask() {
        System.out.println("simpleTask at " + dateFormat.format(new Date()));
    }
}
