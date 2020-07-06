package io.github.bounceback.messageinabottle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class MessageInABottle extends JavaPlugin{
	private static MessageInABottle instance;
	private static List<String> bottledBooksTitles;
	private static List<String> bottledBooksAuthors;
	private static List<List<String>> bottledBooksPages;
	@Override
	public void onEnable() {
		getLogger().info("onEnable has been invoked!");
		getServer().getPluginManager().registerEvents(new RecipeListener(), this);
		getServer().getPluginManager().registerEvents(new BottleDropListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerFishListener(), this);
		getServer().getPluginManager().registerEvents(new PrepareItemCraftListener(), this);
		instance=this;
		
		ItemStack bottle=new ItemStack(Material.GLASS_BOTTLE);	
		bottle.addUnsafeEnchantment(Enchantment.DEPTH_STRIDER, 2);
		ItemMeta bottleMeta=bottle.getItemMeta();
		bottleMeta.setDisplayName("Bottled Message");
		bottle.setItemMeta(bottleMeta);
		NamespacedKey key=new NamespacedKey(this,"bottle");
		ShapelessRecipe recipe=new ShapelessRecipe(key,bottle);
		recipe.addIngredient(Material.GLASS_BOTTLE);
		recipe.addIngredient(Material.WRITTEN_BOOK);
		Bukkit.addRecipe(recipe);
		
		bottledBooksTitles=new ArrayList<String>();;
		bottledBooksAuthors=new ArrayList<String>();;
		bottledBooksPages=new ArrayList<List<String>>();
		File file=new File("books.txt");
		try {
			if(!file.exists()) {
				file.createNewFile();
			}
			else {
				FileInputStream f = new FileInputStream(file);
				ObjectInputStream o=new ObjectInputStream(f);
				bottledBooksTitles=(List<String>) o.readObject();
				bottledBooksAuthors=(List<String>) o.readObject();
				bottledBooksPages=(List<List<String>>) o.readObject();
				o.close();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void onDisable() {
		getLogger().info("onDisable has been invoked!");
	}
	 
	public static MessageInABottle getInstance() {
		return instance;
	}
	 
	public List<String> getBottledBooksTitles() {
		return bottledBooksTitles;
	}
	
	public List<String> getBottledBooksAuthors() {
		return bottledBooksAuthors;
	}
	
	public List<List<String>> getBottledBooksPages() {
		return bottledBooksPages;
	}
	 
	public void updateBooks(String title, String author, List<String> pages) {
		if(bottledBooksTitles.size()<1000) {
			bottledBooksTitles.add(title);
			bottledBooksAuthors.add(author);
			bottledBooksPages.add(pages);
		}
		else {
			bottledBooksTitles.set(new Random().nextInt(bottledBooksTitles.size()), title);
			bottledBooksAuthors.set(new Random().nextInt(bottledBooksAuthors.size()), author);
			bottledBooksPages.set(new Random().nextInt(bottledBooksPages.size()), pages);
		}
		
		File file=new File("books.txt");
		try {
			if(!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream fo=new FileOutputStream(file);
			ObjectOutputStream out=new ObjectOutputStream(fo);
			out.writeObject(bottledBooksTitles);
			out.writeObject(bottledBooksAuthors);
			out.writeObject(bottledBooksPages);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	 }
}
