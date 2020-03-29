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

package io.github.epicorp.scrapcraft.block;

import io.github.epicorp.scrapcraft.api.ScrapPoisonEvent;
import io.github.epicorp.scrapcraft.item.NoxiousGasFilter;
import io.github.epicorp.scrapcraft.registry.ScrapDamageSources;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

/**
 * a gas block that poisons players when they come in contact with it
 */
public class NoxiousGasBlock extends Block {
	public static final IntProperty EMISSION_DISTANCE = IntProperty.of("emission_distance", 0, 7);

	public NoxiousGasBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.getDefaultState().with(EMISSION_DISTANCE, 0));
	}

	@Override
	@Deprecated
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.INVISIBLE;
	}

	@Override
	@Deprecated
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext context) {
		return VoxelShapes.empty();
	}

	@Override
	@Deprecated
	public boolean isAir(BlockState state) {
		return true;
	}

	@Override
	@Deprecated
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if (world.isClient()) {
			return;
		}

		if (entity instanceof LivingEntity) {
			LivingEntity living = (LivingEntity) entity;

			if (living.isInvulnerable()) {
				return;
			}

			if (living instanceof PlayerEntity) {
				if (((PlayerEntity) living).isCreative() || living.isSpectator()) {
					return;
				}
			}

			ItemStack headStack = living.getEquippedStack(EquipmentSlot.HEAD);

			boolean isSafe = false;

			if (headStack.getItem() instanceof NoxiousGasFilter) {
				NoxiousGasFilter filterItem = (NoxiousGasFilter) headStack.getItem();
				isSafe = filterItem.isSafe(world, living, headStack);

				if (isSafe) {
					living.sendEquipmentBreakStatus(EquipmentSlot.HEAD);
				}
			}

			if (!isSafe) {
				if(ScrapPoisonEvent.EVENT.invoker().isInvulnerable(living))
					isSafe = true;
			}

			if (!isSafe) {
				float damage = 1.5F;

				int respiration = EnchantmentHelper.getRespiration(living);

				if (respiration > 0) {
					int divisor = 1;
					divisor += respiration;

					damage /= divisor;
				}

				entity.damage(ScrapDamageSources.NOXIOUS_GAS, damage);
			}
		}
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(EMISSION_DISTANCE);
	}
}
