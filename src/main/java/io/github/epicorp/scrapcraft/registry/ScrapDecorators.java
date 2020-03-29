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

package io.github.epicorp.scrapcraft.registry;

import io.github.epicorp.scrapcraft.feature.ScrapHeapDecorator;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.decorator.CountDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.DecoratorConfig;
import io.github.epicorp.scrapcraft.ScrapCraftMod;

public final class ScrapDecorators {
	public static final Decorator<CountDecoratorConfig> SCRAP_HEAP = register("scrap_heap", new ScrapHeapDecorator(CountDecoratorConfig::deserialize));

	private static <T extends DecoratorConfig, G extends Decorator<T>> G register(String path, G decorator) {
		return Registry.register(Registry.DECORATOR, ScrapCraftMod.id(path), decorator);
	}

	public static void init() {
		// NO-OP
	}

	private ScrapDecorators() {
		throw new AssertionError("You should not be instantiating this");
	}
}
