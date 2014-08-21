package latmod.core;
import latmod.core.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraftforge.oredict.*;

public class ODItems
{
	public static final String WOOD = "logWood";
	public static final String PLANKS = "plankWood";
	public static final String STICK = "stickWood";
	public static final String GLASS = "blockGlassColorless";
	public static final String GLASS_ANY = "blockGlass";
	public static final String GLASS_PANE = "paneGlassColorless";
	public static final String GLASS_PANE_ANY = "paneGlass";
	public static final String STONE = "stone";
	public static final String COBBLE = "cobblestone";
	public static ItemStack OBSIDIAN;
	
	public static final String SLIMEBALL = "slimeball";
	public static final String MEAT_RAW = "meatCooked";
	public static final String MEAT_COOKED = "meatCooked";
	public static final String RUBBER = "itemRubber";
	
	public static final String REDSTONE = "dustRedstone";
	public static final String GLOWSTONE = "dustGlowstone";
	public static final String QUARTZ = "gemQuartz";
	public static final String LAPIS = "gemLapis";
	
	public static final String IRON = "ingotIron";
	public static final String GOLD = "ingotGold";
	public static final String DIAMOND = "gemDiamond";
	public static final String TIN = "ingotTin";
	public static final String COPPER = "ingotCopper";
	public static final String LEAD = "ingotLead";
	public static final String BRONZE = "ingotBronze";
	public static final String SILVER = "ingotSilver";
	
	public static final String RUBY = "gemRuby";
	public static final String SAPPHIRE = "gemSapphire";
	public static final String PERIDOT = "gemPeridot";
	
	public static final String NUGGET_GOLD = "nuggetGold";
	public static final String NUGGET_TIN = "nuggetTin";
	public static final String NUGGET_COPPER = "nuggetCopper";
	public static final String NUGGET_LEAD = "nuggetLead";
	public static final String NUGGET_SILVER = "nuggetSilver";
	
	public static final class OreStackEntry
	{
		public final ItemStack itemStack;
		public final String oreName;
		
		public OreStackEntry(ItemStack is, String s)
		{
			itemStack = is;
			oreName = s;
		}
		
		public boolean equals(Object o)
		{
			ItemStack is = (o == null) ? null : ((o instanceof OreStackEntry) ? ((OreStackEntry)o).itemStack : (ItemStack)o);
			return is.getItem() == itemStack.getItem() && (is.getItemDamage() == itemStack.getItemDamage() || itemStack.getItemDamage() == LatCoreMC.ANY);
		}
	}
	
	public static void preInit()
	{
		OBSIDIAN = new ItemStack(Blocks.obsidian);
		
		LatCoreMC.addOreDictionary(MEAT_RAW, new ItemStack(Items.beef));
		LatCoreMC.addOreDictionary(MEAT_RAW, new ItemStack(Items.porkchop));
		LatCoreMC.addOreDictionary(MEAT_RAW, new ItemStack(Items.chicken));
		
		LatCoreMC.addOreDictionary(MEAT_COOKED, new ItemStack(Items.cooked_beef));
		LatCoreMC.addOreDictionary(MEAT_COOKED, new ItemStack(Items.cooked_porkchop));
		LatCoreMC.addOreDictionary(MEAT_COOKED, new ItemStack(Items.cooked_chicken));
	}
	
	public static FastList<String> getOreNames(ItemStack is)
	{
		int[] ai = OreDictionary.getOreIDs(is);
		if(ai == null || ai.length == 0) return null;
		FastList<String> l = new FastList<String>();
		for(int i : ai) l.add(OreDictionary.getOreName(i));
		return l;
	}
}