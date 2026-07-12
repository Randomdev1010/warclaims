package dev.randomdev.warclaims.Classes;

import dev.randomdev.warclaims.Classes.CustomBlocks.Claimer;
import dev.randomdev.warclaims.WarClaims;
import dev.randomdev.warclaims.libs.ColorsEnum;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestAssertException;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder(WarClaims.MODID)
public class GameTests {
    @GameTest(
            setupTicks = 1L,
            template = "claimer_electricity_activation",
            templateNamespace = WarClaims.MODID
    )
    public static void electricityActivationTest(GameTestHelper helper) {
        Level level = helper.getLevel();
        BlockPos pos = helper.absolutePos(new BlockPos(0,0,0));
        BlockState state = helper.getBlockState(new BlockPos(0,0,0));
        LevelChunk chunk = level.getChunkAt(pos);
        chunk.setData(DataAttachments.CHUNK_OWNER,"");
        chunk.setData(DataAttachments.OWNER_POS,pos);
        chunk.setData(DataAttachments.IS_CAPITAL,false);
        helper.setBlock(0,0,0,Blocks.CLAIMER.get().defaultBlockState().setValue(Claimer.COLORS, ColorsEnum.BLUE));
        level.getCapability(Capabilities.EnergyStorage.BLOCK,pos, Direction.DOWN).receiveEnergy(1000,true);
        helper.succeedOnTickWhen(2,()-> {
            if(!chunk.getData(DataAttachments.CHUNK_OWNER).equals(ColorsEnum.BLUE.getSerializedName())){
                throw new GameTestAssertException("aint right >:(");
            }
        });
    }
}
