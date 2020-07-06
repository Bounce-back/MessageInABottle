package io.github.bounceback.messageinabottle;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import io.github.bounceback.messageinabottle.MessageInABottle;

public class BottleDropListener implements Listener{
	@EventHandler
	public void dropBottleAttempt(PlayerDropItemEvent event) {
		final Item droppedBottle=event.getItemDrop();
		final MessageInABottle instance=MessageInABottle.getInstance();
		if(droppedBottle.getItemStack().getType()==Material.GLASS_BOTTLE&&
				droppedBottle.getItemStack().getEnchantments().containsKey(Enchantment.DEPTH_STRIDER)) {
			final int[] count= {0};
			BukkitRunnable runnable=new BukkitRunnable() {
				public void run() {
					if(droppedBottle.getLocation().getBlock().isLiquid()) {
						PersistentDataContainer container=droppedBottle.getItemStack().getItemMeta().getPersistentDataContainer();
						String title=container.get(new NamespacedKey(instance, "booktitle"),PersistentDataType.STRING);
						String author=container.get(new NamespacedKey(instance, "booktitle"),PersistentDataType.STRING);
						List<String> pages=ArrayListTypeApplication.getPages(droppedBottle.getItemStack().getItemMeta());
						instance.updateBooks(title,author,pages);
						droppedBottle.remove();
						this.cancel();
					}
					else if(count[0]>=10) {
						this.cancel();
					}
					count[0]+=1;
				}
			};
			runnable.runTaskTimer(instance ,0L, 20L);
			
		}
	}
}
