package dev.lyze.sar.systems.wait;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import dev.lyze.sar.components.wait.WaitTimeComponent;
import lombok.var;

@All(WaitTimeComponent.class)
public class WaitTimeSystem extends IteratingSystem {
    private ComponentMapper<WaitTimeComponent> waitTimeMapper;

    @Override
    protected void process(int entityId) {
        var waitTime = waitTimeMapper.get(entityId);

        waitTime.setTimeToWait(waitTime.getTimeToWait() - world.getDelta());
        if (waitTime.getTimeToWait() > 0)
            return;

        world.edit(entityId)
                .remove(WaitTimeComponent.class)
                .add(waitTime.getTarget());
    }
}
