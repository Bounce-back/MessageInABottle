package io.github.bounceback.messageinabottle;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import io.github.bounceback.messageinabottle.MessageInABottle;

public class PlayerFishListener implements Listener{
	@EventHandler
	public void fishing(PlayerFishEvent event) {
		if(event.getState().equals(PlayerFishEvent.State.CAUGHT_ENTITY)||
				event.getState().equals(PlayerFishEvent.State.CAUGHT_FISH)) {
			Random rand=new Random();
			if(rand.nextInt(100)<1) {
				final MessageInABottle instance=MessageInABottle.getInstance();
				
				int index=rand.nextInt(instance.getBottledBooksTitles().size());
				
				ItemStack bottledMessage=new ItemStack(Material.GLASS_BOTTLE);
				ItemMeta meta=bottledMessage.getItemMeta();
				NamespacedKey keyAuthor=new NamespacedKey(instance,"bookauthor");
				NamespacedKey keyTitle=new NamespacedKey(instance,"booktitle");
				ArrayListTypeApplication.setPages(meta,instance.getBottledBooksPages().get(index));
				meta.getPersistentDataContainer().set(keyAuthor, PersistentDataType.STRING, instance.getBottledBooksAuthors().get(index));
				meta.getPersistentDataContainer().set(keyTitle, PersistentDataType.STRING, instance.getBottledBooksTitles().get(index));
				bottledMessage.setItemMeta(meta);
				bottledMessage.addUnsafeEnchantment(Enchantment.DEPTH_STRIDER, 2);
			
				Location loc=event.getCaught().getLocation(); //Might need a deep copy
				Vector vel=event.getCaught().getVelocity();
				Item droppedItem=event.getCaught().getWorld().dropItem(loc, bottledMessage);
				droppedItem.setVelocity(vel);
			
				event.getCaught().remove();
			}
		}
	}
}
