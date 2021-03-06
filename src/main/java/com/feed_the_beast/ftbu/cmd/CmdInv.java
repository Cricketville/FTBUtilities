package com.feed_the_beast.ftbu.cmd;

import com.feed_the_beast.ftbl.lib.cmd.CmdBase;
import com.feed_the_beast.ftbl.lib.cmd.CmdTreeBase;
import com.feed_the_beast.ftbl.lib.internal.FTBLibLang;
import com.feed_the_beast.ftbl.lib.util.CommonUtils;
import com.feed_the_beast.ftbl.lib.util.InvUtils;
import com.feed_the_beast.ftbl.lib.util.NBTUtils;
import com.feed_the_beast.ftbl.lib.util.StringUtils;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;

import javax.annotation.Nullable;
import java.io.File;

public class CmdInv extends CmdTreeBase
{
	public static class CmdView extends CmdBase
	{
		public CmdView()
		{
			super("view", Level.OP);
		}

		@Override
		public boolean isUsernameIndex(String[] args, int i)
		{
			return i == 0;
		}

		@Override
		public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
		{
			checkArgs(sender, args, 1);
			EntityPlayerMP ep0 = getCommandSenderAsPlayer(sender);
			EntityPlayerMP ep = getPlayer(server, sender, args[0]);
			ep0.displayGUIChest(new InvSeeInventory(ep));
		}
	}

	public static class CmdSave extends CmdBase
	{
		public CmdSave()
		{
			super("save", Level.OP);
		}

		@Override
		public boolean isUsernameIndex(String[] args, int i)
		{
			return i == 0;
		}

		@Override
		public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
		{
			checkArgs(sender, args, 2);
			EntityPlayerMP ep = getPlayer(server, sender, args[0]);
			File file = new File(CommonUtils.folderLocal, "ftbutilities/playerinvs/" + StringUtils.fromUUID(ep.getGameProfile().getId()) + "_" + args[1].toLowerCase() + ".dat");

			try
			{
				NBTTagCompound tag = new NBTTagCompound();
				writeItemsToNBT(ep.inventory, tag, "Inventory");
				IInventory baubles = InvUtils.getBaubles(ep);

				if (baubles != null)
				{
					writeItemsToNBT(baubles, tag, "Baubles");
				}

				NBTUtils.writeTag(file, tag);
			}
			catch (Exception ex)
			{
				if (CommonUtils.DEV_ENV)
				{
					ex.printStackTrace();
				}

				throw FTBLibLang.ERROR.commandError(ex.toString());
			}
		}

		private static void writeItemsToNBT(IInventory inv, NBTTagCompound compound, String s)
		{
			NBTTagList nbttaglist = new NBTTagList();

			for (int i = 0; i < inv.getSizeInventory(); ++i)
			{
				ItemStack is = inv.getStackInSlot(i);

				if (!is.isEmpty())
				{
					NBTTagCompound nbttagcompound = is.serializeNBT();
					nbttagcompound.setInteger("Slot", i);
					nbttaglist.appendTag(nbttagcompound);
				}
			}

			compound.setTag(s, nbttaglist);
		}
	}

	public static class CmdLoad extends CmdBase
	{
		public CmdLoad()
		{
			super("load", Level.OP);
		}

		@Override
		public boolean isUsernameIndex(String[] args, int i)
		{
			return i == 0;
		}

		@Override
		public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
		{
			checkArgs(sender, args, 2);
			EntityPlayerMP ep = getPlayer(server, sender, args[0]);
			File file = new File(CommonUtils.folderLocal, "ftbutilities/playerinvs/" + StringUtils.fromUUID(ep.getGameProfile().getId()) + "_" + args[1].toLowerCase() + ".dat");

			try
			{
				NBTTagCompound tag = NBTUtils.readTag(file);
				readItemsFromNBT(ep.inventory, tag, "Inventory");
				IInventory baubles = InvUtils.getBaubles(ep);

				if (baubles != null)
				{
					readItemsFromNBT(baubles, tag, "Baubles");
				}
			}
			catch (Exception ex)
			{
				if (CommonUtils.DEV_ENV)
				{
					ex.printStackTrace();
				}

				throw FTBLibLang.ERROR.commandError(ex.toString());
			}
		}

		private static void readItemsFromNBT(@Nullable IInventory inv, NBTTagCompound compound, String s)
		{
			if (inv == null)
			{
				return;
			}

			NBTTagList nbttaglist = compound.getTagList(s, 10);

			for (int i = 0; i < nbttaglist.tagCount(); ++i)
			{
				NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
				int j = nbttagcompound.getInteger("Slot");

				if (j >= 0 && j < inv.getSizeInventory())
				{
					inv.setInventorySlotContents(j, new ItemStack(nbttagcompound));
				}
			}
		}
	}

	public static class CmdList extends CmdBase
	{
		public CmdList()
		{
			super("list", Level.OP);
		}

		@Override
		public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
		{
		}
	}

	public CmdInv()
	{
		super("inv");
		addSubcommand(new CmdView());
		addSubcommand(new CmdSave());
		addSubcommand(new CmdLoad());
		//addSubcommand(new CmdList());
	}
}