package io.github.epicorp.scrapcraft.api;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;

public interface ScrapNoxiousResistanceCallback {
	Event<ScrapNoxiousResistanceCallback> EVENT = EventFactory.createArrayBacked(ScrapNoxiousResistanceCallback.class, es -> entity -> {
		for (ScrapNoxiousResistanceCallback e : es) {
			if(!e.isVulnerable(entity))
				return false;
		}
		return true;
	});

	boolean isVulnerable(LivingEntity entity);
}