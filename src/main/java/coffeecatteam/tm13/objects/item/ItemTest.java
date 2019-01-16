package coffeecatteam.tm13.objects.item;

import coffeecatteam.tm13.init.InitBlock;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author CoffeeCatRailway
 * Created: 14/01/2019
 */
public class ItemTest extends ItemBase {

    public ItemTest(String id, ItemGroup group) {
        super(id, new Builder().group(group).defaultMaxDamage(10));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(new TextComponentString("What is this?"));
        if (GuiScreen.isShiftKeyDown()) {
            tooltip.add(new TextComponentString(TextFormatting.YELLOW + "Right-click " + TextFormatting.RESET + "this item and one of two things will happen! ;)"));
            tooltip.add(new TextComponentString(TextFormatting.RED + "1. " + TextFormatting.RESET + "A " + TextFormatting.BLUE + "diamond block " + TextFormatting.RESET + "will appear!"));
            tooltip.add(new TextComponentString(TextFormatting.RED + "2. " + TextFormatting.RESET + "A useless slab looking block will appear"));
        } else {
            tooltip.add(new TextComponentString(TextFormatting.YELLOW + "Hold shift for more info!"));
        }
    }

    @Override
    public EnumActionResult onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        EntityPlayer player = context.getPlayer();

        BlockPos pos = context.getPos().offset(context.getFace());
        if (world.isAirBlock(pos)) {
            IBlockState state;
            if (world.getRandom().nextFloat() < 0.5f)
                state = Blocks.DIAMOND_BLOCK.getDefaultState();
            else
                state = InitBlock.TEST_BLOCK.getDefaultState();
            world.setBlockState(pos, state);
            ItemStack itemstack = context.getItem();
            if (player instanceof EntityPlayerMP) {
                CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP) player, pos, itemstack);
            }

            if (player != null) {
                itemstack.damageItem(1, player);
            }
            return EnumActionResult.SUCCESS;
        } else {
            return EnumActionResult.FAIL;
        }
    }
}
