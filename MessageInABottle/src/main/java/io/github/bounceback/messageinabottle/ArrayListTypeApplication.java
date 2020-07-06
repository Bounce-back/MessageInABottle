package io.github.bounceback.messageinabottle;

import java.util.List;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

public class ArrayListTypeApplication {
	private static ArrayListDataType datatype=new ArrayListDataType();
	private static MessageInABottle instance=MessageInABottle.getInstance();
	private static NamespacedKey key=new NamespacedKey(instance, "bookpages");
	
	public static void setPages(ItemMeta meta, List<String> pages) {
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(key, datatype, pages);
    }
    
    public static List<String> getPages(ItemMeta meta) {
        PersistentDataContainer container = meta.getPersistentDataContainer();
        return container.get(key, datatype);
    }
	
}
