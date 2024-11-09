package ru.korshun.elitertppremium.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.tags.ItemTagType;
import ru.korshun.elitertppremium.EliteRtpPremium;
import ru.korshun.elitertppremium.utils.ChatUtil;
import ru.korshun.elitertppremium.utils.ConfigUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MenuManager {
    private static Inventory rtpMenu = Bukkit.createInventory(null, ConfigUtils.getInt("menu.size"), ConfigUtils.getString("menu.title"));

    private static Inventory rtpWorldMenu = Bukkit.createInventory(null, ConfigUtils.getInt("world-menu.size"), ConfigUtils.getString("world-menu.title"));

    public static void setup() {
        ConfigurationSection section = ConfigUtils.getConfigurationSection("menu.items");
        for(String s : section.getKeys(false)) {
            ConfigurationSection itemSection = section.getConfigurationSection(s);
            ItemStack itemStack = new ItemStack(Material.valueOf(itemSection.getString("material")));
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(ChatUtil.translate(itemSection.getString("display_name")));
            List<String> lore = new ArrayList<>();
            for(String s1 : itemSection.getStringList("lore")) {
                lore.add(ChatUtil.translate(s1));
            }
            for(String e : itemSection.getStringList("enchantments")) {
                String[] enchantments = e.split(";");
                String
            }
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
            if(itemSection.getString("action") != null) {
                itemStack.getItemMeta().getCustomTagContainer().setCustomTag(new NamespacedKey(EliteRtpPremium.getInstance(), "action"), ItemTagType.STRING, itemSection.getString("action"));
            }
            rtpMenu.addItem(itemStack);
        }
        ConfigurationSection section1 = ConfigUtils.getConfigurationSection("world-menu.items");
        for(String s : section1.getKeys(false)) {
            ConfigurationSection itemSection = section1.getConfigurationSection(s);
            ItemStack itemStack = new ItemStack(Material.valueOf(itemSection.getString("material")));
            ItemMeta im = itemStack.getItemMeta();
            im.setDisplayName(itemSection.getString("display_name"));
            itemStack.setItemMeta(im);
            rtpWorldMenu.addItem(itemStack);
        }
        int i = 0;
        if(Bukkit.getWorlds().size() > (ConfigUtils.getEndSlot() - ConfigUtils.getStartSlot())) {
            if(ConfigUtils.getDefaultLanguage().equals("en")) {
                EliteRtpPremium.getInstance().getLogger().severe("Too many worlds have been created!");
                EliteRtpPremium.getInstance().getLogger().severe("Disabling plugin...");
            }
            else if(ConfigUtils.getDefaultLanguage().equals("ru")) {
                EliteRtpPremium.getInstance().getLogger().severe("Слишком много миров создано!");
                EliteRtpPremium.getInstance().getLogger().severe("Выключение плагина...");
            }
            EliteRtpPremium.disablePlugin();
            return;
        }
        for(World world1 : Bukkit.getWorlds()) {
            ItemStack itemStack = null;
            if(ConfigUtils.getString("world-menu.world-icons." + world1.getName()) == null) {
                itemStack = new ItemStack(Material.valueOf(ConfigUtils.getString("world-menu.default_world_icon")), 1);
                ItemMeta im = itemStack.getItemMeta();
                im.setDisplayName(world1.getName());
                itemStack.setItemMeta(im);
                rtpWorldMenu.setItem(ConfigUtils.getStartSlot() + i, itemStack);
                i++;
                break;
            }
            itemStack = new ItemStack(Material.valueOf(ConfigUtils.getString("world-menu.world-icons." + world1.getName())), 1);
            ItemMeta im = itemStack.getItemMeta();
            im.setDisplayName(world1.getName());
            itemStack.setItemMeta(im);
            rtpWorldMenu.setItem(ConfigUtils.getStartSlot() + i, itemStack);
            i++;
        }
    }

    public static Inventory getRtpMenu() {
        return rtpMenu;
    }

    public static Inventory getRtpWorldMenu() {
        return rtpWorldMenu;
    }
}
