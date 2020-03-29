/*
 * The MIT License (MIT)
 *
 * Copyright (c) i509VCB
 * Copyright (c) HalfOf2
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package io.github.epicorp.scrapcraft;

import io.github.epicorp.scrapcraft.feature.ScrapHeapFeatureConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.FabricLootSupplierBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.loot.ConstantLootTableRange;
import net.minecraft.loot.LootManager;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.CountDecoratorConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.github.epicorp.scrapcraft.registry.ScrapBlocks;
import io.github.epicorp.scrapcraft.registry.ScrapDecorators;
import io.github.epicorp.scrapcraft.registry.ScrapItems;
import io.github.epicorp.scrapcraft.registry.ScrapRegistry;
import io.github.epicorp.scrapcraft.registry.ScrapWorldFeatures;

public class ScrapCraftMod implements ModInitializer {
	public static final String MODID = "scrapcraft";
	public static final Logger LOGGER = LogManager.getLogger(ScrapCraftMod.class);

	@Override
	public void onInitialize() {
		LOGGER.info("One man's trash is another's treasure.");
		ScrapRegistry.init();

		// Register out feature to overworld like biomes
		Registry.BIOME.stream()
				.filter(ScrapCraftMod::isOverworldLike)
				.forEach(ScrapCraftMod::addFeature);
		// Register to new biomes afterwards
		RegistryEntryAddedCallback.event(Registry.BIOME)
				.register(ScrapCraftMod::checkAndAddToBiome);

		LootTableLoadingCallback.EVENT.register(ScrapCraftMod::modifyFishingRodTable);
	}

	private static void modifyFishingRodTable(ResourceManager manager, LootManager lootManager, Identifier identifier, FabricLootSupplierBuilder lootSupplierBuilder, LootTableLoadingCallback.LootTableSetter lootTableSetter) {
		if (identifier.equals(LootTables.FISHING_JUNK_GAMEPLAY)) {
			FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
					.withRolls(ConstantLootTableRange.create(1))
					.withEntry(ItemEntry.builder(ScrapItems.SCRAP));

			lootSupplierBuilder.withPool(poolBuilder);
		}
	}

	private static void checkAndAddToBiome(int rawId, Identifier id, Biome biome) {
		if (ScrapCraftMod.isOverworldLike(biome)) {
			ScrapCraftMod.addFeature(biome);
		}
	}

	private static boolean isOverworldLike(Biome biome) {
		Biome.Category category = biome.getCategory();
		return !(category == Biome.Category.NETHER || category == Biome.Category.THEEND || category == Biome.Category.NONE);
	}

	private static void addFeature(Biome biome) {
		biome
				.addFeature(GenerationStep.Feature.LOCAL_MODIFICATIONS, ScrapWorldFeatures.SCRAP_HEAP_FEATURE
					.configure(new ScrapHeapFeatureConfig(ScrapBlocks.DENSE_SCRAP_BLOCK.getDefaultState(), 0))
					.createDecoratedFeature(ScrapDecorators.SCRAP_HEAP.configure(new CountDecoratorConfig(2))));
	}

	public static Identifier id(String path) {
		return new Identifier(MODID, path);
	}
}
