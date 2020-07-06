package io.github.bounceback.messageinabottle;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import io.github.bounceback.messageinabottle.MessageInABottle;


public class RecipeListener implements Listener{
	@EventHandler
	public void craftAttempt(CraftItemEvent event) {
		MessageInABottle instance=MessageInABottle.getInstance();
		ItemStack[] items=event.getInventory().getMatrix();
		ItemStack Book=null;
		ItemStack Bottle=null;
		boolean hasNothingElse=true;
		for(ItemStack item:items) {
			if(item!=null&&item.getType().equals(Material.WRITTEN_BOOK)){
				Book=item;
			}
			else if(item!=null&&item.getType().equals(Material.GLASS_BOTTLE)){
				Bottle=item;
			}
			else if(item!=null) {
				hasNothingElse=false;
			}
		}
		if(Book!=null&&Bottle!=null&&hasNothingElse) {
			ItemStack bottledMessage=new ItemStack(Material.GLASS_BOTTLE);
			ItemMeta meta=bottledMessage.getItemMeta();
			NamespacedKey keyAuthor=new NamespacedKey(instance,"bookauthor");
			NamespacedKey keyTitle=new NamespacedKey(instance,"booktitle");
			BookMeta bookmeta=(BookMeta) Book.getItemMeta();
			ArrayListTypeApplication.setPages(meta,bookmeta.getPages());
			meta.getPersistentDataContainer().set(keyAuthor, PersistentDataType.STRING, bookmeta.getAuthor());
			meta.getPersistentDataContainer().set(keyTitle, PersistentDataType.STRING, bookmeta.getTitle());
			bottledMessage.setItemMeta(meta);
			bottledMessage.addUnsafeEnchantment(Enchantment.DEPTH_STRIDER, 2);
			event.getInventory().setResult(bottledMessage);
		}
		
	}
}
