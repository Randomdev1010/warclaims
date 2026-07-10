package dev.randomdev.warclaims.Classes.CustomBlocks;

import dev.randomdev.warclaims.Classes.CustomSaveData.SavedTeams;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import static dev.randomdev.warclaims.Classes.Events.TEAMSDATA;

public class Claimer extends Block {
    public static final EnumProperty<ColorsEnum> COLORS = EnumProperty.create("color",ColorsEnum.class);
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public Claimer(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(COLORS,ColorsEnum.WHITE).setValue(POWERED,false));
    }

    private static void turnOn(BlockState state,Level level,BlockPos pos){
        level.setBlock(pos,state.setValue(POWERED,true),2);
        level.playSound((Player) null, pos, SoundEvents.BEACON_ACTIVATE, SoundSource.BLOCKS);
    }
    private static void turnOff(BlockState state,Level level,BlockPos pos){
        level.setBlock(pos,state.setValue(POWERED,false),2);
        level.playSound((Player) null, pos, SoundEvents.BEACON_DEACTIVATE, SoundSource.BLOCKS);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        SavedTeams.SafeEditor edit = TEAMSDATA.editInfo();
        return this.defaultBlockState().setValue(COLORS, Objects.requireNonNull(ColorsEnum.getFromName(edit.getTeamName(context.getPlayer().getUUID()))));
    }

    @Override
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

        /*HashMap<String, Item> dyes = new HashMap<>();
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
        }*/
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(COLORS,POWERED);
    }
}
