package andrews.online_detector.objects.blocks;

import java.util.Random;
import java.util.function.ToIntFunction;

import andrews.online_detector.config.ODConfigs;
import andrews.online_detector.tile_entities.OnlineDetectorTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class OnlineDetectorBlock extends Block
{
	public static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final BooleanProperty IS_ACTIVE = BooleanProperty.create("is_active");
	protected static final VoxelShape BOTTOM_PART = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 13.0D, 16.0D);
	protected static final VoxelShape TOP_PART = Block.makeCuboidShape(3.0D, 13.0D, 3.0D, 13.0D, 14.0D, 13.0D);
	protected static final VoxelShape AABB = VoxelShapes.or(BOTTOM_PART, TOP_PART);
	private static final float PIXEL_SIZE = 0.0625F;
	
	public OnlineDetectorBlock()
	{
		super(getProperties());
		this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL_FACING, Direction.NORTH).with(IS_ACTIVE, false));
	}
	
	/**
	 * @return - The properties for this Block
	 */
	private static Properties getProperties()
	{
		Properties properties = Block.Properties.create(Material.ROCK);
		properties.hardnessAndResistance(1.5F, 6.0F);
		properties.setRequiresTool();
		properties.setLightLevel(getLightValueLit(7));
		
		return properties;
	}
	
	/**
	 * @param lightValue - The light value this Block should have if its active.
	 * @return - The light value this Block should have based on if its active or not.
	 */
	private static ToIntFunction<BlockState> getLightValueLit(int lightValue)
	{
		return (state) ->
		{
			return state.get(IS_ACTIVE) ? lightValue : 0;
		};
	}
	
	@Override
	public int getWeakPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side)
	{
		if(!blockState.get(IS_ACTIVE))
		{
			return 0;
		}
		else
		{
			switch(side)
			{
			default:
			case DOWN:
				return 0;
			case UP:
			case NORTH:
			case SOUTH:
			case EAST:
			case WEST:
				return 15;
				
			}
		}
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack)
	{
		if(!worldIn.isRemote && placer instanceof PlayerEntity)
		{
			PlayerEntity player = (PlayerEntity) placer;
			TileEntity tileentity = worldIn.getTileEntity(pos);
			
			if(tileentity instanceof OnlineDetectorTileEntity)
	        {
				OnlineDetectorTileEntity onlineDetectorTileEntity = (OnlineDetectorTileEntity)tileentity;
				
				onlineDetectorTileEntity.setOwnerUUID(player.getUniqueID());
				onlineDetectorTileEntity.setOwnerName(player.getName().getString());
	        }
		}
	}
	
	@Override
	public boolean hasTileEntity(BlockState state)
	{
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world)
	{
		return new OnlineDetectorTileEntity();
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		return AABB;
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		return this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing().rotateY()).with(IS_ACTIVE, false);
	}
	
	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(HORIZONTAL_FACING, IS_ACTIVE);
	}
	
	@Override
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand)
	{
		if(stateIn.get(IS_ACTIVE))
		{
			if(ODConfigs.ODClientConfig.shouldShowRedstoneParticles.get())
			{
				if(rand.nextInt(1) == 0)
				{
					worldIn.addParticle(RedstoneParticleData.REDSTONE_DUST, pos.getX() + PIXEL_SIZE * 8, pos.getY() + PIXEL_SIZE * 2, pos.getZ() - PIXEL_SIZE, 0.0D, 0.0D, 0.0D);
					worldIn.addParticle(RedstoneParticleData.REDSTONE_DUST, pos.getX() + PIXEL_SIZE * 8, pos.getY() + PIXEL_SIZE * 2, pos.getZ() + PIXEL_SIZE * 17, 0.0D, 0.0D, 0.0D);
					worldIn.addParticle(RedstoneParticleData.REDSTONE_DUST, pos.getX() - PIXEL_SIZE, pos.getY() + PIXEL_SIZE * 2, pos.getZ() + PIXEL_SIZE * 8, 0.0D, 0.0D, 0.0D);
					worldIn.addParticle(RedstoneParticleData.REDSTONE_DUST, pos.getX() + PIXEL_SIZE * 17, pos.getY() + PIXEL_SIZE * 2, pos.getZ() + PIXEL_SIZE * 8, 0.0D, 0.0D, 0.0D);
					worldIn.addParticle(RedstoneParticleData.REDSTONE_DUST, pos.getX() + PIXEL_SIZE * 8, pos.getY() - PIXEL_SIZE, pos.getZ() + PIXEL_SIZE * 8, 0.0D, 0.0D, 0.0D);
				}
			}
			
			if(ODConfigs.ODClientConfig.shouldShowPortalParticles.get())
			{
				for(int i = 0; i < 3; ++i)
				{
					int XAxisRandom = rand.nextInt(2) * 2 - 1;
					int ZAxisRandom = rand.nextInt(2) * 2 - 1;
					double xPos = (double) pos.getX() + 0.5D + 0.25D * (double) XAxisRandom;
					double yPos = (double) ((float) pos.getY() + 0.5F + rand.nextFloat());
					double zPos = (double) pos.getZ() + 0.5D + 0.25D * (double) ZAxisRandom;
					double xMotion = (double) (rand.nextFloat() * (float) XAxisRandom);
					double yMotion = ((double) rand.nextFloat() - 0.5D) * 0.125D;
					double zMotion = (double) (rand.nextFloat() * (float) ZAxisRandom);
					worldIn.addParticle(ParticleTypes.PORTAL, xPos, yPos, zPos, xMotion, yMotion, zMotion);
				}
			}
		}
	}
}