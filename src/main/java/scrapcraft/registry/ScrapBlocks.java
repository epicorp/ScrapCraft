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

package scrapcraft.registry;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import scrapcraft.ScrapCraftMod;
import scrapcraft.block.ScrapBlock;
import scrapcraft.block.ScrapSlabBlock;
import scrapcraft.mixin.MaterialBuilderAccessor;

public final class ScrapBlocks {
	private static final Block.Settings SCRAP_SETTINGS = FabricBlockSettings.of(((MaterialBuilderAccessor) new Material.Builder(MaterialColor.IRON)).accessor$burnable().build()).build();

	public static final Block SCRAP_BLOCK = registerAsScrapLike("scrap_block", new ScrapBlock(SCRAP_SETTINGS), ScrapItemSettings.SCRAP, 3, 2);
	public static final Block SCRAP_SLAB_BLOCK = registerAsScrapLike("scrap_block_slab", new ScrapSlabBlock(SCRAP_SETTINGS), ScrapItemSettings.SCRAP, 3, 2);
	public static final Block DENSE_SCRAP_BLOCK = registerAsScrapLike("dense_scrap_block", new ScrapBlock(SCRAP_SETTINGS), ScrapItemSettings.SCRAP, 5, 6);

	private static Block registerAsScrapLike(String path, Block block, Item.Settings settings, int burnChance, int spreadChance) {
		return Util.make(() -> {
			Block scrap = register(path, block, settings);
			FireBlock fire = (FireBlock) Blocks.FIRE;
			fire.registerFlammableBlock(scrap, burnChance, spreadChance);
			return scrap;
		});
	}

	private static Block register(String path, Block block, Item.Settings settings) {
		Block b = Registry.register(Registry.BLOCK, ScrapCraftMod.id(path), block);
		Registry.register(Registry.ITEM, ScrapCraftMod.id(path), new BlockItem(block, settings));
		return b;
	}

	private static Block registerNoItem(String path, Block block) {
		return Registry.register(Registry.BLOCK, ScrapCraftMod.id(path), block);
	}

	public static void init() {
		// NO-OP
	}

	private ScrapBlocks() {
		throw new AssertionError("You should not be instantiating this");
	}
}
