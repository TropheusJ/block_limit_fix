package io.github.tropheusj.block_limit_fix.mixin;

import net.minecraft.core.SectionPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundSectionBlocksUpdatePacket;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = ClientboundSectionBlocksUpdatePacket.class, priority = 100) // apply really early to let other mods still do their things
public abstract class ClientboundSectionBlocksUpdatePacketMixin implements Packet<ClientGamePacketListener> {
	@Shadow
	private SectionPos sectionPos;

	@Shadow
	private boolean suppressLightUpdates;

	@Shadow
	private short[] positions;

	@Shadow
	private BlockState[] states;

	/**
	 * @author TropheusJay
	 * @reason fix a vanilla bug with state serialization
	 */
	@Overwrite
	@Override
	public void write(FriendlyByteBuf buf) {
		buf.writeLong(this.sectionPos.asLong());
		buf.writeBoolean(this.suppressLightUpdates);
		buf.writeVarInt(this.positions.length);

		for(int i = 0; i < this.positions.length; ++i) {
			int id = Block.getId(this.states[i]);
			// that's it. that's the mod
			buf.writeVarLong(((long) id) << 12 | this.positions[i]);
		}
	}
}
