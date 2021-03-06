package com.feed_the_beast.ftbu.gui;

import com.feed_the_beast.ftbl.lib.client.ClientUtils;
import com.feed_the_beast.ftbl.lib.gui.GuiBase;
import com.feed_the_beast.ftbl.lib.gui.Panel;
import com.feed_the_beast.ftbl.lib.gui.SimpleTextButton;
import com.feed_the_beast.ftbl.lib.gui.misc.GuiButtonListBase;
import com.feed_the_beast.ftbl.lib.icon.Icon;
import com.feed_the_beast.ftbl.lib.util.StringUtils;
import com.feed_the_beast.ftbl.lib.util.misc.MouseButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author LatvianModder
 */
public class GuiViewCrashList extends GuiButtonListBase
{
	private static class ButtonFile extends SimpleTextButton
	{
		public ButtonFile(GuiBase gui, String title)
		{
			super(gui, 0, 0, title, Icon.EMPTY);
		}

		@Override
		public void onClicked(MouseButton button)
		{
			ClientUtils.execClientCommand("/ftb view_crash " + getTitle());
		}
	}

	private final List<String> files;

	public GuiViewCrashList(Collection<String> l)
	{
		files = new ArrayList<>(l);
		files.sort(StringUtils.IGNORE_CASE_COMPARATOR.reversed());
	}

	@Override
	public String getTitle()
	{
		return StringUtils.translate("sidebar_button.ftbu.view_crash");
	}

	@Override
	public void addButtons(Panel panel)
	{
		for (String s : files)
		{
			panel.add(new ButtonFile(this, s));
		}
	}
}