/**
 * File Name: LaunchEntry.java
 * Date: 2020-05-14 15:49:34
 */
package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * Description: 
 * @author shenzulun
 * @date 2020-05-14
 * @version 1.0
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class LaunchEntry {
	
	
	public static void main(String[] args){
        System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(LaunchEntry.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  若依启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                " .-------.       ____     __        \n" +
                " |  _ _   \\      \\   \\   /  /    \n" +
                " | ( ' )  |       \\  _. /  '       \n" +
                " |(_ o _) /        _( )_ .'         \n" +
                " | (_,_).' __  ___(_ o _)'          \n" +
                " |  |\\ \\  |  ||   |(_,_)'         \n" +
                " |  | \\ `'   /|   `-'  /           \n" +
                " |  |  \\    /  \\      /           \n" +
                " ''-'   `'-'    `-..-'              ");
    }
}
