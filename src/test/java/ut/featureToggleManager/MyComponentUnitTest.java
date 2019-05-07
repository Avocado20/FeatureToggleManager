package ut.featureToggleManager;

import org.junit.Test;
import featureToggleManager.api.MyPluginComponent;
import featureToggleManager.impl.MyPluginComponentImpl;

import static org.junit.Assert.assertEquals;

public class MyComponentUnitTest
{
    @Test
    public void testMyName()
    {
        MyPluginComponent component = new MyPluginComponentImpl(null);
        assertEquals("names do not match!", "myComponent",component.getName());
    }
}