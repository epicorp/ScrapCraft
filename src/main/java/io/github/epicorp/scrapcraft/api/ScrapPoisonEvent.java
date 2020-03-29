package io.github.epicorp.scrapcraft.api;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;

public interface ScrapPoisonEvent {
	Event<ScrapPoisonEvent> EVENT = EventFactory.createArrayBacked(ScrapPoisonEvent.class, es -> entity -> {
		for (ScrapPoisonEvent e : es) {
			if (e.isInvulnerable(entity)) {
				return true;
			}
		}

		return false;
	});

	/**
	 * return true if the entity is invulnerable to being exposed to noxious fumes,
	 * here u can add custom handling for effects.
	 *
	 * @param entity the entity being poisonsed
	 * @return true if the entity is invulnerable to the effects of noxious fumes.
	 */
	boolean isInvulnerable(LivingEntity entity);
}
