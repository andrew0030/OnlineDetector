package andrews.online_detector.network.server;

import java.util.function.Supplier;

import andrews.online_detector.tile_entities.AdvancedOnlineDetectorTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageServerSetPlayerHead
{
	private BlockPos pos;
	private ItemStack stack;
	
	public MessageServerSetPlayerHead(BlockPos pos, ItemStack stack)
	{
		this.pos = pos;
        this.stack = stack;
    }
	
	public void serialize(PacketBuffer buf)
	{
		buf.writeBlockPos(pos);
		buf.writeItemStack(stack);
	}
	
	public static MessageServerSetPlayerHead deserialize(PacketBuffer buf)
	{
		BlockPos pos = buf.readBlockPos();
		ItemStack stack = buf.readItemStack();
		return new MessageServerSetPlayerHead(pos, stack);
	}
	
	public static void handle(MessageServerSetPlayerHead message, Supplier<NetworkEvent.Context> ctx)
	{
		NetworkEvent.Context context = ctx.get();
		PlayerEntity player = context.getSender();
		World world = player.getEntityWorld();
		BlockPos tileEntityPos = message.pos;
		ItemStack stack = message.stack;
		
		if(context.getDirection().getReceptionSide() == LogicalSide.SERVER)
		{
			context.enqueueWork(() ->
			{
				if(world != null)
				{
					TileEntity tileentity = world.getTileEntity(tileEntityPos);
					// We make sure the TileEntity is a ChessTileEntity
					if(tileentity instanceof AdvancedOnlineDetectorTileEntity)
			        {
						AdvancedOnlineDetectorTileEntity advancedOnlineDetectorTileEntity = (AdvancedOnlineDetectorTileEntity)tileentity;
						advancedOnlineDetectorTileEntity.setOwnerHead(stack);
						world.notifyBlockUpdate(message.pos, world.getBlockState(tileEntityPos), world.getBlockState(tileEntityPos), 2);
			        }
				}
			});
			context.setPacketHandled(true);
		}
	}
}
