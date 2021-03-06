package com.feed_the_beast.ftbu.api;

import com.feed_the_beast.ftbl.api.IForgePlayer;
import com.feed_the_beast.ftbl.lib.util.StringUtils;
import net.minecraft.stats.StatBase;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;

/**
 * @author LatvianModder
 */
public class Leaderboard extends IForgeRegistryEntry.Impl<Leaderboard>
{
	private final ITextComponent title;
	private final Function<IForgePlayer, ITextComponent> playerToValue;
	private final Comparator<IForgePlayer> comparator;
	private final Predicate<IForgePlayer> validValue;

	public Leaderboard(ITextComponent t, Function<IForgePlayer, ITextComponent> v, Comparator<IForgePlayer> c, Predicate<IForgePlayer> vv)
	{
		title = t;
		playerToValue = v;
		comparator = c.thenComparing((o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));
		validValue = vv;
	}

	public final ITextComponent getTitle()
	{
		return title;
	}

	public final Comparator<IForgePlayer> getComparator()
	{
		return comparator;
	}

	public final ITextComponent createValue(IForgePlayer player)
	{
		return playerToValue.apply(player);
	}

	public final boolean hasValidValue(IForgePlayer player)
	{
		return validValue.test(player);
	}

	public static class FromStat extends Leaderboard
	{
		public static final IntFunction<ITextComponent> DEFAULT = value -> new TextComponentString(value <= 0 ? "0" : Integer.toString(value));
		private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("########0.00");
		public static final IntFunction<ITextComponent> TIME = value -> {
			double seconds = value / 20D;
			double minutes = seconds / 60D;
			double hours = minutes / 60D;
			double days = hours / 24D;
			double years = days / 365D;

			if (years > 0.5D)
			{
				return new TextComponentString(DECIMAL_FORMAT.format(years) + " y");
			}
			else if (days > 0.5D)
			{
				return new TextComponentString(DECIMAL_FORMAT.format(days) + " d");
			}
			else if (hours > 0.5D)
			{
				return new TextComponentString(DECIMAL_FORMAT.format(hours) + " h");
			}

			return new TextComponentString(minutes > 0.5D ? DECIMAL_FORMAT.format(minutes) + " m" : seconds + " s");
		};

		public FromStat(ITextComponent t, StatBase statBase, boolean from0to1, IntFunction<ITextComponent> valueToString)
		{
			super(t,
					player -> valueToString.apply(player.stats().readStat(statBase)),
					(o1, o2) -> {
						int i = Integer.compare(o1.stats().readStat(statBase), o2.stats().readStat(statBase));
						return from0to1 ? i : -i;
					},
					player -> player.stats().readStat(statBase) > 0);
		}

		public FromStat(StatBase statBase, boolean from0to1, IntFunction<ITextComponent> valueToString)
		{
			this(StringUtils.color(statBase.getStatName(), null), statBase, from0to1, valueToString);
		}
	}
}