package io.github.epicorp.scrapcraft.api;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;

public interface ScrapPoisonEvent {
	Event<ScrapPoisonEvent> EVENT = EventFactory.createArrayBacked(ScrapPoisonEvent.class, es -> entity -> {
		for (ScrapPoisonEvent e : es) {
			if(e.isInvulnerable(entity))
				return true;
		}
		return false;
	});

	boolean isInvulnerable(LivingEntity entity);
}