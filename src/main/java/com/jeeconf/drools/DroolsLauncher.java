package com.jeeconf.drools;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Launcher
 *
 * @author Victor Polischuk
 */
public class DroolsLauncher {
    public static void main(String[] args) throws Exception {
        new ClassPathXmlApplicationContext(
                "classpath:/com/jeeconf/drools/application-context.xml",
                "classpath:/com/jeeconf/drools/drools-context.xml",
                "classpath:/com/jeeconf/drools/bean/company-context.xml",
                "classpath:/com/jeeconf/drools/bean/entrepreneur-context.xml",
                "classpath:/com/jeeconf/drools/bean/person-context.xml",
                "classpath:/com/jeeconf/drools/bean/revenue-context.xml"
        );
    }
}
