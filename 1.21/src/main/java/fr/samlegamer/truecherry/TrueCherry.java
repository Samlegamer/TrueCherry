package fr.samlegamer.truecherry;

import java.util.List;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.BasicItemListing;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(TrueCherry.MODID)
public class TrueCherry
{
    public static final String MODID = "truecherry";
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static final DeferredItem<Item> CHERRY = ITEMS.registerSimpleItem("cherry", new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.5f).build()));
    public static final DeferredItem<Item> GOLDEN_CHERRY = ITEMS.registerSimpleItem("golden_cherry", new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.5f)
    .effect(() -> new MobEffectInstance(MobEffects.HEAL, 1, 0), 1.0f).build()));
    public static final DeferredItem<Item> CHERRY_PIE = ITEMS.registerSimpleItem("cherry_pie", new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationModifier(1.5f).build()));

    public TrueCherry(IEventBus modEventBus)
    {
        ITEMS.register(modEventBus);
        modEventBus.addListener(this::addTab);
        NeoForge.EVENT_BUS.register(this);
    }
    
    private void addTab(BuildCreativeModeTabContentsEvent event)
    {
    	if(event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS)
    	{
    		event.accept(CHERRY.get());
    		event.accept(GOLDEN_CHERRY.get());
    		event.accept(CHERRY_PIE.get());
    	}
    }
	
	@SubscribeEvent
    private void villager(VillagerTradesEvent ev)
    {
    	if(ev.getType() == VillagerProfession.FARMER)
    	{
    		ItemListing CHERRY_BUY = new BasicItemListing(4, new ItemStack(CHERRY.get(), 4), 16, 5, 0.05f);
    		ItemListing CHERRY_PIE_BUY = new BasicItemListing(8, new ItemStack(CHERRY_PIE.get(), 4), 16, 5, 0.05f);
    		ItemListing GOLD_CHERRY_BUY = new BasicItemListing(10, new ItemStack(GOLDEN_CHERRY.get(), 1), 16, 5, 0.05f);

        	List<ItemListing> farmerNpclvl2 = ev.getTrades().get(2);
        	List<ItemListing> farmerNpclvl3 = ev.getTrades().get(3);
        	List<ItemListing> farmerNpclvl5 = ev.getTrades().get(5);

        	farmerNpclvl2.add(CHERRY_BUY);
        	farmerNpclvl3.add(CHERRY_PIE_BUY);
        	farmerNpclvl5.add(GOLD_CHERRY_BUY);
    	}
    }
}