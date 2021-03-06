package com.feed_the_beast.ftbu.cmd.ranks;

import com.feed_the_beast.ftbl.api.IForgePlayer;
import com.feed_the_beast.ftbl.lib.cmd.CmdBase;
import com.feed_the_beast.ftbu.api.FTBULang;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.server.permission.PermissionAPI;

/**
 * @author LatvianModder
 */
public class CmdCheckPerm extends CmdBase
{
	public CmdCheckPerm()
	{
		super("check_permission", Level.OP);
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index)
	{
		return index == 0;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		checkArgs(sender, args, 2);
		IForgePlayer player = getForgePlayer(args[0]);
		boolean perm = player.isOnline() ? PermissionAPI.hasPermission(player.getPlayer(), args[1]) : PermissionAPI.hasPermission(player.getProfile(), args[1], null);
		FTBULang.PERM_FOR.sendMessage(sender, args[1], player.getName(), String.valueOf(perm));
	}
}