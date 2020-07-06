package io.github.bounceback.messageinabottle;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PrepareItemCraftListener implements Listener{
	@EventHandler
	public void itemCraft(PrepareItemCraftEvent event) {
		MessageInABottle instance=MessageInABottle.getInstance();
		ItemStack[] items=event.getInventory().getMatrix();
		ItemStack BottledMessage=null;
		boolean hasNothingElse=true;
		for(ItemStack item:items) {
			if(item!=null&&item.getType().equals(Material.GLASS_BOTTLE)&&
					item.getEnchantments().get(Enchantment.DEPTH_STRIDER)!=null){
				BottledMessage=item;
			}
			else if(item!=null) {
				hasNothingElse=false;
			}
		}
		if(BottledMessage!=null&&hasNothingElse) {
			PersistentDataContainer container=BottledMessage.getItemMeta().getPersistentDataContainer();
			ItemStack book=new ItemStack(Material.WRITTEN_BOOK);
			BookMeta bookmeta=(BookMeta) book.getItemMeta();
			
			bookmeta.setAuthor(container.get(new NamespacedKey(instance, "bookauthor"),PersistentDataType.STRING));
			bookmeta.setTitle(container.get(new NamespacedKey(instance, "booktitle"),PersistentDataType.STRING));
			bookmeta.setPages(ArrayListTypeApplication.getPages(BottledMessage.getItemMeta()));
			book.setItemMeta(bookmeta);
			event.getInventory().setResult(book);
		}
	}
}
