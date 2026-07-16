package dev.randomdev.warclaims.Classes.CustomBlocks;

import dev.randomdev.warclaims.Classes.BlockEntities;
import dev.randomdev.warclaims.Classes.CustomBlocks.BlockEntities.ClaimerEntity;
import dev.randomdev.warclaims.Classes.CustomSaveData.SavedTeams;
import dev.randomdev.warclaims.Classes.DataAttachments;
import dev.randomdev.warclaims.libs.ColorsEnum;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BeaconBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import static dev.randomdev.warclaims.Classes.Events.TEAMSDATA;

public class Claimer extends Block implements EntityBlock {
    public static final EnumProperty<ColorsEnum> COLORS = EnumProperty.create("color",ColorsEnum.class);
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    protected ClaimerEntity blockEntity;

    public Claimer(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(COLORS,ColorsEnum.WHITE).setValue(POWERED,false));
    }

    public static void turnOn(BlockState state,Level level,BlockPos pos){
        level.setBlock(pos,state.setValue(POWERED,true),2);
        level.playSound((Player) null, pos, SoundEvents.BEACON_ACTIVATE, SoundSource.BLOCKS);
        LevelChunk chunk = level.getChunkAt(pos);
        if(chunk.getData(DataAttachments.CHUNK_OWNER).isEmpty()){
            chunk.setData(DataAttachments.CHUNK_OWNER,state.getValue(COLORS).getSerializedName());
            chunk.setData(DataAttachments.OWNER_POS,pos);
            chunk.setData(DataAttachments.IS_CAPITAL,false);
        }
    }
    public static void turnOff(BlockState state,Level level,BlockPos pos){
        level.setBlock(pos,state.setValue(POWERED,false),2);
        level.playSound((Player) null, pos, SoundEvents.BEACON_DEACTIVATE, SoundSource.BLOCKS);
        LevelChunk chunk = level.getChunkAt(pos);
        if (chunk.getData(DataAttachments.OWNER_POS).equals(pos)){
            chunk.setData(DataAttachments.CHUNK_OWNER,"");
            chunk.setData(DataAttachments.OWNER_POS,new BlockPos(0,0,0));
            chunk.setData(DataAttachments.IS_CAPITAL,false);
        }
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        if(TEAMSDATA==null){return super.getStateForPlacement(context);}
        SavedTeams.SafeEditor edit = TEAMSDATA.editInfo();
        if (ColorsEnum.getFromName(edit.getTeamName(context.getPlayer().getUUID())) == null){
            return super.getStateForPlacement(context);
        }else{
            return this.defaultBlockState().setValue(COLORS, ColorsEnum.getFromName(edit.getTeamName(context.getPlayer().getUUID())));
        }
    }

    /*@Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if(state.getValue(POWERED)){
            turnOff(state,level,pos);
        }else{
            turnOn(state,level,pos);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        Item item = stack.getItem();

        HashMap<String, Item> dyes = new HashMap<>();
        dyes.put("white",Items.WHITE_DYE);
        dyes.put("lightgray",Items.LIGHT_GRAY_DYE);
        dyes.put("gray",Items.GRAY_DYE);
        dyes.put("black",Items.BLACK_DYE);
        dyes.put("blue",Items.BLUE_DYE);
        dyes.put("lightblue",Items.LIGHT_BLUE_DYE);
        dyes.put("cyan",Items.CYAN_DYE);
        dyes.put("green",Items.GREEN_DYE);
        dyes.put("lime",Items.LIME_DYE);
        dyes.put("magenta",Items.MAGENTA_DYE);
        dyes.put("pink",Items.PINK_DYE);
        dyes.put("purple",Items.PURPLE_DYE);
        dyes.put("red",Items.RED_DYE);
        dyes.put("yellow",Items.YELLOW_DYE);
        dyes.put("orange",Items.ORANGE_DYE);
        dyes.put("brown",Items.BROWN_DYE);

        AtomicBoolean returnUsed = new AtomicBoolean(false);
        dyes.forEach((name,checkItem)->{
            if(checkItem==item){
                level.setBlock(pos,state.setValue(COLORS, Objects.requireNonNull(ColorsEnum.getFromName(name))),2);
                returnUsed.set(true);
            }
        });
        if(returnUsed.get()){
            level.playSound((Player) null, pos, SoundEvents.COW_MILK, SoundSource.BLOCKS);
            return ItemInteractionResult.SUCCESS;
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }*/

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(COLORS,POWERED);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        blockEntity = new ClaimerEntity(blockPos,blockState);
        return blockEntity;
    }
    private static <E extends BlockEntity, A extends BlockEntity> @Nullable BlockEntityTicker<A> createTickerHelper(
            BlockEntityType<A> type, BlockEntityType<E> checkedType, BlockEntityTicker<? super E> ticker
    ) {
        return checkedType == type ? (BlockEntityTicker<A>) ticker : null;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        // You can return different tickers here, depending on whatever factors you want. A common use case would be
        // to return different tickers on the client or server, only tick one side to begin with,
        // or only return a ticker for some blockstates (e.g. when using a "my machine is working" blockstate property).
        return createTickerHelper(type, BlockEntities.CLAIMER_ENTITY.get(), ClaimerEntity::tick);
    }
}
