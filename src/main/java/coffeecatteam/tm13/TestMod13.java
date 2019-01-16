package coffeecatteam.tm13;

import coffeecatteam.tm13.commands.CommandTest;
import coffeecatteam.tm13.init.InitBlock;
import coffeecatteam.tm13.init.RegistrationHandler;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(TestMod13.MODID)
public class TestMod13 {

    public static final String MODID = "tm13";
    public static final ItemGroup GroupTM13 = new ItemGroup(MODID) {
        @OnlyIn(Dist.CLIENT)
        public ItemStack createIcon() {
            return new ItemStack(InitBlock.TEST_BLOCK);
        }

        @Override
        public boolean hasSearchBar() {
            return true;
        }

        @Override
        public int getSearchbarWidth() {
            return 161;
        }

        @Override
        public ResourceLocation getBackgroundImage() {
            return new ResourceLocation(MODID, "textures/gui/container/tab.png");
        }
    }.setNoTitle();

    public static final Logger logger = LogManager.getLogger("Test Mod 13");

    public TestMod13() {
        MinecraftForge.EVENT_BUS.register(this);

        RegistrationHandler.init();
        MinecraftForge.EVENT_BUS.register(new RegistrationHandler());
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        CommandTest.register(event.getCommandDispatcher());
        logger.info("Commands registered");
    }
}
