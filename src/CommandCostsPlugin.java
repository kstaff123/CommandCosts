package com.example.commandcosts;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.earth2me.essentials.config.CommandCost;

public class CommandCostsPlugin extends JavaPlugin {

    private Map<String, CommandCost> commandCosts;

    @Override
    public void onEnable() {
        // Load the command costs from the EssentialsX config file
        File configFile = new File(getDataFolder().getParentFile(), "essentials/config.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        commandCosts = new HashMap<String, CommandCost>();
        for (String commandName : config.getConfigurationSection("command-costs").getKeys(false)) {
            double cost = config.getDouble("command-costs." + commandName);
            CommandCost commandCost = new CommandCost(cost);
            commandCosts.put(commandName, commandCost);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("commandcosts")) {
            StringBuilder messageBuilder = new StringBuilder();
            messageBuilder.append(ChatColor.translateAlternateColorCodes('&', "&bCommand Costs:\n"));
            for (Map.Entry<String, CommandCost> entry : commandCosts.entrySet()) {
                messageBuilder.append(ChatColor.translateAlternateColorCodes('&', "  &a/" + entry.getKey() + ": &e$" + entry.getValue().getCost() + "\n"));
            }
            sender.sendMessage(messageBuilder.toString());
            return true;
        }
        return false;
    }
}