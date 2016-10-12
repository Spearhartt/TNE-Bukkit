package com.github.tnerevival.commands.money;

import com.github.tnerevival.core.transaction.TransactionType;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.github.tnerevival.TNE;
import com.github.tnerevival.commands.TNECommand;
import com.github.tnerevival.core.Message;
import com.github.tnerevival.utils.AccountUtils;
import com.github.tnerevival.utils.MISCUtils;

public class MoneyGiveCommand extends TNECommand {

	public MoneyGiveCommand(TNE plugin) {
		super(plugin);
	}

	@Override
	public String getName() {
		return "give";
	}

	@Override
	public String[] getAliases() {
		return new String[0];
	}

	@Override
	public String getNode() {
		return "tne.money.give";
	}

	@Override
	public boolean console() {
		return true;
	}
	
	@Override
	public boolean execute(CommandSender sender, String command, String[] arguments) {
		if(arguments.length >= 2) {
      String world = (arguments.length == 3)? arguments[2] : TNE.instance.defaultWorld;
      Double value = Double.valueOf(arguments[1].replace(TNE.instance.api.getString("Core.Currency.Decimal", world, MISCUtils.getID(getPlayer(sender)).toString()), "."));
			if(value < 0) {
				sender.sendMessage(new Message("Messages.Money.Negative").translate());
				return false;
			}
			
			if(getPlayer(sender, arguments[0]) != null) {

        AccountUtils.transaction(MISCUtils.getID(getPlayer(sender, arguments[0])).toString(), MISCUtils.getID(getPlayer(sender)).toString(), value, TransactionType.MONEY_GIVE, world);
				Message gave = new Message("Messages.Money.Gave");
				gave.addVariable("$amount",  MISCUtils.formatBalance(world, AccountUtils.round(value)));
				gave.addVariable("$player", arguments[0]);
				sender.sendMessage(gave.translate());
				return true;
			}
		} else {
			help(sender);
		}
		return false;
	}
	
	

	@Override
	public void help(CommandSender sender) {
		sender.sendMessage(ChatColor.GOLD + "/money give <player> <amount> [world] - summon money from air and give it to a player");
	}
}