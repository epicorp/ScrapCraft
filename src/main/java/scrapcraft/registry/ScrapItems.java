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

import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;
import scrapcraft.ScrapCraftMod;
import scrapcraft.item.ScrapItem;

public final class ScrapItems {
	public static final Item SCRAP = register("scrap", new ScrapItem(ScrapItemSettings.SCRAP));

	private static Item register(String path, Item item) {
		return Registry.register(Registry.ITEM, ScrapCraftMod.id(path), item);
	}

	public static void init() {
		// NO-OP
	}

	private ScrapItems() {
		throw new AssertionError("You should not be instantiating this");
	}
}
