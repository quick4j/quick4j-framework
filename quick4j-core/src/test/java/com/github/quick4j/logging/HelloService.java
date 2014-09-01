package com.github.quick4j.logging;

import com.github.quick4j.entity.User;
import com.github.quick4j.plugin.logging.StorageMedium;
import com.github.quick4j.plugin.logging.annontation.WriteLog;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zhaojh
 */
@Component
public class HelloService {

    @WriteLog(value = "admin向${args[0].name}打招呼", data = ",${args[0]}")
    public void sayHello(User user){
        System.out.println("Hello " + user.getName());
    }

    @WriteLog(value = "${args[0].name}向${args[1].name}说：I Love U!", data = "${args[0]},${args[1]}", to = StorageMedium.FILE)
    public void sayLove(User master, User customer){
        System.out.println("I Love U!");
    }

    @WriteLog("${args[0][name]}向${args[1][name]}道歉")
    public void saySorry(Map one, Map other){
        System.out.println("I'm sorry!");
    }
}
