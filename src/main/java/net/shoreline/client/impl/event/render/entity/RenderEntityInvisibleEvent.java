package net.shoreline.client.impl.event.render.entity;

import net.minecraft.entity.LivingEntity;
import net.shoreline.client.api.event.Cancelable;
import net.shoreline.client.api.event.Event;

@Cancelable
public class RenderEntityInvisibleEvent extends Event {
    private final LivingEntity entity;

    public RenderEntityInvisibleEvent(LivingEntity entity) {
        this.entity = entity;
    }

    public LivingEntity getEntity() {
        return entity;
    }
}
