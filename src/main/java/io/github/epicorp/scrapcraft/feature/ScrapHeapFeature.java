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

package io.github.epicorp.scrapcraft.feature;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Iterator;
import java.util.Random;
import java.util.function.Function;

public class ScrapHeapFeature extends Feature<ScrapHeapFeatureConfig> {
	public ScrapHeapFeature(Function<Dynamic<?>, ? extends ScrapHeapFeatureConfig> configDeserializer) {
		super(configDeserializer);
	}

	@Override
	public boolean generate(IWorld world, ChunkGenerator<? extends ChunkGeneratorConfig> generator, Random random, BlockPos pos, ScrapHeapFeatureConfig config) {
		while (true) {
			continueMovingDown: {
				if (pos.getY() > 3) {
					if (world.isAir(pos.down())) {
						break continueMovingDown;
					}

					Block block = world.getBlockState(pos.down()).getBlock();

					if (!isDirt(block) && !isStone(block)) {
						break continueMovingDown;
					}
				}

				if (pos.getY() <= 3) {
					return false;
				}

				int radius = config.radius;

				for (int i = 0; radius >= 0 && i < 3; ++i) {
					int x = radius + random.nextInt(2);
					int y = radius + random.nextInt(2);
					int z = radius + random.nextInt(2);
					float f = (float) (x + y + z) * 0.333F + 0.5F;
					Iterator<BlockPos> blockPosIterator = BlockPos.iterate(pos.add(-x, -y, -z), pos.add(x, y, z)).iterator();

					while (blockPosIterator.hasNext()) {
						BlockPos next = blockPosIterator.next();

						if (next.getSquaredDistance(pos) <= (double) (f * f)) {
							world.setBlockState(next, config.state, 4);
						}
					}

					pos = pos.add(-(radius + 1) + random.nextInt(2 + radius * 2), -random.nextInt(2), -(radius + 1) + random.nextInt(2 + radius * 2));
				}

				return true;
			}

			pos = pos.down();
		}
	}
}
