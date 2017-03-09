package eyeq.leek;

import eyeq.leek.block.BlockLeek;
import eyeq.leek.block.BlockTorchLeek;
import eyeq.leek.event.LeekEventHandler;
import eyeq.leek.item.ItemLeek;
import eyeq.leek.item.ItemSwordLeek;
import eyeq.leek.world.gen.WorldGenLeek;
import eyeq.util.client.model.UModelCreator;
import eyeq.util.client.model.gson.ItemmodelJsonFactory;
import eyeq.util.client.renderer.ResourceLocationFactory;
import eyeq.util.client.renderer.block.statemap.StateMapper;
import eyeq.util.client.resource.ULanguageCreator;
import eyeq.util.client.resource.lang.LanguageResourceManager;
import eyeq.util.oredict.CategoryTypes;
import eyeq.util.oredict.UOreDictionary;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;

import static eyeq.leek.Leek.MOD_ID;

@Mod(modid = MOD_ID, version = "1.0", dependencies = "after:eyeq_util")
@Mod.EventBusSubscriber
public class Leek {
    public static final String MOD_ID = "eyeq_leek";

    @Mod.Instance(MOD_ID)
    public static Leek instance;

    private static final ResourceLocationFactory resource = new ResourceLocationFactory(MOD_ID);

    public static Block leeks;
    public static Block leeksTorch;
    public static Block leeksTorchLight;

    public static Item leek;
    public static Item leekSword;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new LeekEventHandler());
        if(event.getSide().isServer()) {
            return;
        }
        renderBlockModels();
        renderItemModels();
        createFiles();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        GameRegistry.registerWorldGenerator(new WorldGenLeek(), 8);
    }

    @SubscribeEvent
    protected static void registerBlocks(RegistryEvent.Register<Block> event) {
        leeks = new BlockLeek().setUnlocalizedName("leeks").setCreativeTab(null);
        leeksTorch = new BlockTorchLeek(false).setHardness(0.0F).setUnlocalizedName("leeks").setCreativeTab(null);
        leeksTorchLight = new BlockTorchLeek(true).setHardness(0.0F).setUnlocalizedName("leeks").setCreativeTab(null);

        GameRegistry.register(leeks, resource.createResourceLocation("leeks"));
        GameRegistry.register(leeksTorch, resource.createResourceLocation("leeks_torch"));
        GameRegistry.register(leeksTorchLight, resource.createResourceLocation("leeks_torch_light"));
    }

    @SubscribeEvent
    protected static void registerItems(RegistryEvent.Register<Item> event) {
        Item.ToolMaterial toolMaterialLeek = EnumHelper.addToolMaterial("leek", 3, 1561, 8.0F, 10.0F, 10);

        leek = new ItemLeek(3, 1.5F, leeks, Blocks.FARMLAND).setUnlocalizedName("leek");
        leekSword = new ItemSwordLeek(toolMaterialLeek, 3, 1.5F).setEatPotionEffect(new PotionEffect(MobEffects.HUNGER, 30, 0), 1.0F).setUnlocalizedName("leek").setCreativeTab(null);

        GameRegistry.register(new ItemBlock(leeksTorch), leeks.getRegistryName());

        GameRegistry.register(leek, resource.createResourceLocation("leek"));
        GameRegistry.register(leekSword, resource.createResourceLocation("leek_sword"));

        UOreDictionary.registerOre(CategoryTypes.PREFIX_CROP, "leek", leek);
        UOreDictionary.registerOre(CategoryTypes.VEGETABLE, "leek", leek);
    }

    @SideOnly(Side.CLIENT)
    public static void renderBlockModels() {
        ModelLoader.setCustomStateMapper(leeksTorchLight, new StateMapper(resource, null, "leeks_torch"));
    }

    @SideOnly(Side.CLIENT)
    public static void renderItemModels() {
        ModelResourceLocation model = ResourceLocationFactory.createModelResourceLocation(leek);
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(leeksTorch), 0, model);

        ModelLoader.setCustomModelResourceLocation(leek, 0, model);
        ModelLoader.setCustomModelResourceLocation(leekSword, 0, model);
    }

    public static void createFiles() {
        File project = new File("../1.11.2-Leek");

        LanguageResourceManager language = new LanguageResourceManager();

        language.register(LanguageResourceManager.EN_US, leeks, "Leek Block");
        language.register(LanguageResourceManager.JA_JP, leeks, "ねぎブロック");

        language.register(LanguageResourceManager.EN_US, leek, "Leek");
        language.register(LanguageResourceManager.JA_JP, leek, "ねぎ");

        ULanguageCreator.createLanguage(project, MOD_ID, language);

        UModelCreator.createItemJson(project, leek, ItemmodelJsonFactory.ItemmodelParent.GENERATED);
    }
}
