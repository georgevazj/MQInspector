package com.bbva.mmap.listener;

import com.bbva.mmap.conf.ApplicationContext;
import junit.framework.TestCase;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by jorge on 15/03/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationContext.class)
public class MQMessageListenerTest extends TestCase {
    @Autowired
    private DefaultMessageListenerContainer messageListenerContainer;

}