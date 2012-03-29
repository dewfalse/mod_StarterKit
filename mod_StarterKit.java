package net.minecraft.src;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.Item;
import net.minecraft.src.MLProp;
import net.minecraft.src.ModLoader;


public class mod_StarterKit extends BaseModMp {
	@MLProp(info="true: drop items when first login  |  false: drop items when player login")
	public static boolean firstLoginOnly = true;
	@MLProp(info="format: blockID x Count, blockID x Count ...")
	public static String blocks = "50x3";
	@MLProp(info="format: itemID x Count, itemID x Count ...")
	public static String items = "41x1,24x2";

	public static Map<Integer, Integer> blocksList = new HashMap();
	public static Map<Integer, Integer> ItemsList = new HashMap();

	public static List<String> playersList = new ArrayList();

	@Override
	public String getVersion() {
		return "[1.2.3] StarterKit 0.0.1";
	}

	@Override
	public void load() {
		{
			String str = blocks;
			String[] tokens = str.split(",");
			for(String token : tokens) {
				String[] parts = token.split("x");
				if(parts.length >= 2) {
					blocksList.put(Integer.parseInt(parts[0].trim()), Integer.parseInt(parts[1].trim()));
				}
			}
		}

		{
			String str = items;
			String[] tokens = str.split(",");
			for(String token : tokens) {
				String[] parts = token.split("x");
				if(parts.length >= 2) {
					ItemsList.put(Integer.parseInt(parts[0].trim()), Integer.parseInt(parts[1].trim()));
				}
			}
		}
		ModLoader.setInGameHook(this, true, true);
	}

	@Override
	public boolean hasClientSide() {
		return false;
	}

	public void dropStarterKit(EntityPlayerMP entityplayermp) {
		for(Map.Entry<Integer, Integer> e : blocksList.entrySet()) {
			entityplayermp.dropItem(e.getKey(), e.getValue());
		}
		for(Map.Entry<Integer, Integer> e : ItemsList.entrySet()) {
			entityplayermp.dropItem(e.getKey() + 256, e.getValue());
		}
	}

	@Override
	public void handleLogin(EntityPlayerMP entityplayermp) {
		System.out.println(playersList);
		if(firstLoginOnly) {
			if(playersList.contains(entityplayermp.username) == false) {
				System.out.println("not found");
				dropStarterKit(entityplayermp);
				playersList.add(entityplayermp.username);
			}
		} else {
			System.out.println("disabled");
			dropStarterKit(entityplayermp);
		}
	}

}
