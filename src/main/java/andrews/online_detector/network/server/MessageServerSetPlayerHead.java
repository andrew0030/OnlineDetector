package andrews.online_detector.network.server;

import andrews.online_detector.block_entities.AdvancedOnlineDetectorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageServerSetPlayerHead
{
	private final BlockPos pos;
	private final ItemStack stack;
	
	public MessageServerSetPlayerHead(BlockPos pos, ItemStack stack)
	{
		this.pos = pos;
        this.stack = stack;
    }
	
	public void serialize(FriendlyByteBuf buf)
	{
		buf.writeBlockPos(pos);
		buf.writeItemStack(stack, false);
	}
	
	public static MessageServerSetPlayerHead deserialize(FriendlyByteBuf buf)
	{
		BlockPos pos = buf.readBlockPos();
		ItemStack stack = buf.readItem();
		return new MessageServerSetPlayerHead(pos, stack);
	}
	
	public static void handle(MessageServerSetPlayerHead message, Supplier<NetworkEvent.Context> ctx)
	{
		NetworkEvent.Context context = ctx.get();
		Player player = context.getSender();
		Level level = player.getLevel();
		BlockPos blockEntityPos = message.pos;
		ItemStack stack = message.stack;
		
		if(context.getDirection().getReceptionSide() == LogicalSide.SERVER)
		{
			context.enqueueWork(() ->
			{
				if(level != null)
				{
					BlockEntity blockEntity = level.getBlockEntity(blockEntityPos);
					// We make sure the TileEntity is an AdvancedOnlineDetectorBlockEntity
					if(blockEntity instanceof AdvancedOnlineDetectorBlockEntity advancedOnlineDetectorBlockEntity)
			        {
						advancedOnlineDetectorBlockEntity.setOwnerHead(stack);
						level.sendBlockUpdated(message.pos, level.getBlockState(blockEntityPos), level.getBlockState(blockEntityPos), 2);
			        }
				}
			});
			context.setPacketHandled(true);
		}
	}
}