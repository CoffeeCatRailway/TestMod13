package coffeecatteam.tm13.init;

import coffeecatteam.tm13.TestMod13;
import coffeecatteam.tm13.objects.item.ItemTest;
import net.minecraft.item.Item;

/**
 * @author CoffeeCatRailway
 * Created: 14/01/2019
 */
public class InitItem {

    public static final Item TEST_ITEM = new ItemTest("test_item", TestMod13.GroupTM13);

    public static void init() {
        RegistrationHandler.registerItem(TEST_ITEM);
    }
}
