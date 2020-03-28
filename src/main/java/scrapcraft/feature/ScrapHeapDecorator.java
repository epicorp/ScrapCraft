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

package scrapcraft.feature;

import com.mojang.datafixers.Dynamic;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.decorator.CountDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;

import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ScrapHeapDecorator extends Decorator<CountDecoratorConfig> {
	public ScrapHeapDecorator(Function<Dynamic<?>, ? extends CountDecoratorConfig> configDeserializer) {
		super(configDeserializer);
	}

	@Override
	public Stream<BlockPos> getPositions(IWorld world, ChunkGenerator<? extends ChunkGeneratorConfig> generator, Random random, CountDecoratorConfig config, BlockPos pos) {
		int count = random.nextInt(config.count);
		return IntStream.range(0, count).mapToObj((ix) -> {
			int x = random.nextInt(16) + pos.getX();
			int z = random.nextInt(16) + pos.getZ();
			int y = world.getTopY(Heightmap.Type.MOTION_BLOCKING, x, z);
			return new BlockPos(x, y, z);
		});
	}
}
